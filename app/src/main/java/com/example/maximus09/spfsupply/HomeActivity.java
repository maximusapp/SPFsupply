package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.maximus09.spfsupply.data.model.PostGetManufacturerUser;
import com.example.maximus09.spfsupply.data.model.PostNewCountForUser;
import com.example.maximus09.spfsupply.data.model.ResponseAllManufacturersUser;
import com.example.maximus09.spfsupply.data.model.ResponseAllNewUser;
import com.example.maximus09.spfsupply.util.Preference;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {

    private static final String GET_USER_MANUFACTURERS_URL = "http://api.spfsupply.com/public/api/manufacturers/get_for_user";
    private static final String GET_COUNT_NEW_USER_URL = "http://api.spfsupply.com/public/api/user/get_count_of_new";

    ImageView ivLogoUser;
    public TextView textView_company_name;

    RecyclerView rvDrawerItem;
    ArrayList<ItemsDrawer> listItems;
    ItemListAdapter itemListAdapter;

    RecyclerView rvManufFeature;
    ItemListManufacturerFeatureUserAdapter itemListManufacturerFeatureUserAdapter;
    public List<ResponseAllManufacturersUser.ManufactFeature> respFeatureManuf;

    RecyclerView rvManufAll;
    ItemListManufacturerAllUserAdapter itemListManufacturerAllUserAdapter;
    public List<ResponseAllManufacturersUser.ManufactAll> respAllManuf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        //set font style for title ActionBar
        TextView tv=(TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);

        rvManufFeature = (RecyclerView)findViewById(R.id.rvFeatureUser);
        rvManufAll = (RecyclerView)findViewById(R.id.rvAllManufUser);

        rvManufFeature.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        itemListManufacturerFeatureUserAdapter = new ItemListManufacturerFeatureUserAdapter(null, this) {
            @Override
            public void ClickFeatureUserList(ResponseAllManufacturersUser.ManufactFeature manufacturersFeaturesData) {
                Log.d("ID_OF_MANUF", manufacturersFeaturesData.getId());
                Intent intentFeatures = new Intent(HomeActivity.this, InformationHomeActivity.class);
                intentFeatures.putExtra("manufacturers_id", manufacturersFeaturesData.getId());
                startActivity(intentFeatures);
            }
        };
        rvManufFeature.setAdapter(itemListManufacturerFeatureUserAdapter);

        rvManufAll.setLayoutManager(new LinearLayoutManager(this));
        itemListManufacturerAllUserAdapter = new ItemListManufacturerAllUserAdapter(null, this) {
            @Override
            public void ClickAllUserList(ResponseAllManufacturersUser.ManufactAll manufacturersAllData) {
                Log.d("ID_OF_MANUF", manufacturersAllData.getId());
                Intent intentFeatures = new Intent(HomeActivity.this, InformationHomeActivity.class);
                intentFeatures.putExtra("manufacturers_id", manufacturersAllData.getId());
                startActivity(intentFeatures);
            }
        };

        rvManufAll.setAdapter(itemListManufacturerAllUserAdapter);


        GetManufacturers getManufacturers = new GetManufacturers();
        getManufacturers.execute();

        GetCountNewForUser getCountNewForUser = new GetCountNewForUser();
        getCountNewForUser.execute();

        // set statusBar color
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorStatusBar));
        }

        //set menu icon in left_top side of toolBar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_home_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view_home);
        View header = navigationView.getHeaderView(0);
        textView_company_name = (TextView)header.findViewById(R.id.textView_company_name_user_drawer);
        ivLogoUser = (ImageView)header.findViewById(R.id.imageView_logo_drawer_user);


        listItems = new ArrayList<>();
        rvDrawerItem = (RecyclerView)findViewById(R.id.drawer_menu_home_list);
        rvDrawerItem.setHasFixedSize(true);
        rvDrawerItem.setLayoutManager(new LinearLayoutManager(this));

        listItems.add(new ItemsDrawer("Home", ""));
        listItems.add(new ItemsDrawer("Orders", "1"));
        listItems.add(new ItemsDrawer("Check-out", ""));
        listItems.add(new ItemsDrawer("Messages", ""));


        itemListAdapter = new ItemListAdapter(listItems, this){
            @Override
            public void Click(int itemsDrawer) {
                switch (itemsDrawer) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class );
                        startActivity(intent);
                        finish();
                        break;
                    case 1:
                        Intent intentBuyers = new Intent(getApplicationContext(), OrderHomeActivity.class);
                        startActivity(intentBuyers);
                        finish();
                        break;
                    case 2:
                        Intent intentOrders = new Intent(getApplicationContext(), CheckOutActivity.class);
                        startActivity(intentOrders);
                        finish();
                        break;
                    case 3:
                        Intent intentMessages = new Intent(getApplicationContext(), MessagesHomeActivity.class);
                        startActivity(intentMessages);
                        finish();
                        break;
                }
            }
        };
        rvDrawerItem.setAdapter(itemListAdapter);


    }


    // handling press on button in Drawer Menu
    public void closeDrawerHome(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_home_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

    }


    // handling text pressed in nav_header_home
    public void openProfileHome(View view) {
        Intent intentProfileHome = new Intent(view.getContext(), ProfileHomeActivity.class);
        startActivity(intentProfileHome);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_mail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_mail_home) {

            Intent intent = new Intent(HomeActivity.this, ContactUsActivity.class);
            startActivity(intent);
          // Toast.makeText(this, "Pressed MAIL button", Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("StaticFieldLeak")
    private class GetCountNewForUser extends AsyncTask<String, String, ResponseAllNewUser> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseAllNewUser doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Preference preference = new Preference(getApplicationContext());
            PostNewCountForUser postNewCountForUser = new PostNewCountForUser(preference.getToken());

            try {
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                        gson.toJson(postNewCountForUser));

                Request request = new Request.Builder()
                        .url(GET_COUNT_NEW_USER_URL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("ALL_NEW", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseAllNewUser responseAllNewUser = gsonFromServer.fromJson(responseBody, ResponseAllNewUser.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseAllNewUser;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(ResponseAllNewUser responseAllNewUser) {
            super.onPostExecute(responseAllNewUser);

            textView_company_name.setText(responseAllNewUser.getCompany_name());
            Glide.with(getApplicationContext()).load(responseAllNewUser.getCompany_logo()).into(ivLogoUser);

            listItems.clear();

            listItems.add(new ItemsDrawer("Home", ""));
            listItems.add(new ItemsDrawer("Orders", ""));
            listItems.add(new ItemsDrawer("Check-out", responseAllNewUser.getCheckout_count()));
            listItems.add(new ItemsDrawer("Messages", responseAllNewUser.getMessage_count()));

            itemListAdapter.updateOrderProductUser(listItems);


        }
    }


    @SuppressLint("StaticFieldLeak")
    private class GetManufacturers extends AsyncTask<String, String, ResponseAllManufacturersUser> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseAllManufacturersUser doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Preference preference = new Preference(getApplicationContext());
            PostGetManufacturerUser postGetManufacturerUser = new PostGetManufacturerUser(preference.getToken());

            try {

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postGetManufacturerUser));

                Request request = new Request.Builder()
                        .url(GET_USER_MANUFACTURERS_URL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("ALL_MANUFACTURER_USER", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseAllManufacturersUser responseAllManufacturersUser = gsonFromServer.fromJson(responseBody, ResponseAllManufacturersUser.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseAllManufacturersUser;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResponseAllManufacturersUser responseAllManufacturersUser) {
            super.onPostExecute(responseAllManufacturersUser);

            respFeatureManuf = responseAllManufacturersUser.getManufacturers_featured();
            respAllManuf = responseAllManufacturersUser.getManufacturers_all();

            itemListManufacturerFeatureUserAdapter.updateFeaturesUserList(respFeatureManuf);
            itemListManufacturerAllUserAdapter.updateAllUserList(respAllManuf);

        }
    }



}
