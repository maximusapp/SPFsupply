package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.maximus09.spfsupply.data.model.PostAllManufacturers;
import com.example.maximus09.spfsupply.data.model.PostNewForAdmin;
import com.example.maximus09.spfsupply.data.model.ResponseAllManufacturers;
import com.example.maximus09.spfsupply.data.model.ResponseAllNewAdmin;
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

public class ManufacturesActivity extends AppCompatActivity {

    private static final String GET_ALL_MANUFACTURERS = "http://api.spfsupply.com/public/api/manufacturers/get_for_admin";
    private static final String GET_COUNT_OF_NEW_ADMIN = "http://api.spfsupply.com/public/api/admin/get_count_of_new";

    SearchView searchView;
    List<ResponseAllManufacturers.ManufacturersData> respons;

    RecyclerView recyclerView_manufacturers;
    ItemListManufacturersAdapter itemListManufacturersAdapter;


    RecyclerView rvDrawerItem;
    ArrayList<ItemsDrawer> listItems;
    ItemListAdapter itemListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manufactures);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set font style for title ActionBar
        TextView tv=(TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        searchView = (SearchView)findViewById(R.id.search_manufacturers);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s.toString());
                return false;
            }
        });


        //Handle AsyncTask
        GetAllManufacturerResponse getAllManufacturer = new GetAllManufacturerResponse();
        getAllManufacturer.execute();

        //Get count of new
        GetCountOfNew getCountOfNew = new GetCountOfNew();
        getCountOfNew.execute();


        listItems = new ArrayList<>();
        rvDrawerItem = (RecyclerView)findViewById(R.id.drawer_menu_list);
        rvDrawerItem.setHasFixedSize(true);
        rvDrawerItem.setLayoutManager(new LinearLayoutManager(this));


        listItems.add(new ItemsDrawer("Manufacturers", ""));
        listItems.add(new ItemsDrawer("Buyers", "1"));
        listItems.add(new ItemsDrawer("Orders", ""));
        listItems.add(new ItemsDrawer("Messages", ""));
        listItems.add(new ItemsDrawer("Slider", ""));

        itemListAdapter = new ItemListAdapter(listItems, this){
            @Override
            public void Click(int itemsDrawer) {
                switch (itemsDrawer) {
                    case 0:
                        Intent intent = new Intent(getApplicationContext(), ManufacturesActivity.class );
                        startActivity(intent);
                        finish();
                        break;
                    case 1:
                        Intent intentBuyers = new Intent(getApplicationContext(), BuyersActivity.class);
                        startActivity(intentBuyers);
                        finish();
                        break;
                    case 2:
                        Intent intentOrders = new Intent(getApplicationContext(), OrdersActivity.class);
                        startActivity(intentOrders);
                        finish();
                        break;
                    case 3:
                        Intent intentMessages = new Intent(getApplicationContext(), MessagesActivity.class);
                        startActivity(intentMessages);
                        finish();
                        break;
                    case 4:
                        Intent intentSlider = new Intent(getApplicationContext(), SliderActivity.class);
                        startActivity(intentSlider);
                        finish();
                        break;


                }
            }
        };
        rvDrawerItem.setAdapter(itemListAdapter);


        //Find RecyclerView for All Manufacturers and set adapter
       recyclerView_manufacturers = (RecyclerView)findViewById(R.id.recycler_manufacturers);
       recyclerView_manufacturers.setLayoutManager(new LinearLayoutManager(this));
       itemListManufacturersAdapter = new ItemListManufacturersAdapter(null, this){
           @Override
           public void Click(ResponseAllManufacturers.ManufacturersData resp) {
               Log.d("ID_OF_MANUF", resp.getId());
               Intent intentInfo = new Intent(ManufacturesActivity.this, InformationActivity.class);
               intentInfo.putExtra("manufacturers_id", resp.getId());
               startActivity(intentInfo);
           }
       };

       recyclerView_manufacturers.setAdapter(itemListManufacturersAdapter);
    }

    //Find in searchView
    private void filter(String s) {
        List<ResponseAllManufacturers.ManufacturersData> temp = new ArrayList<>();
        for (int i = 0; i < respons.size(); i++) {
            if (respons.get(i).getCompany_name().toLowerCase().startsWith(s.toLowerCase())) {
                temp.add(respons.get(i));
            }
        }

        itemListManufacturersAdapter.updateListManufacturer(temp);

    }


    @SuppressLint("StaticFieldLeak")
    private class GetAllManufacturerResponse extends AsyncTask<String, String, ResponseAllManufacturers> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseAllManufacturers doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Preference preference = new Preference(getApplicationContext());
            PostAllManufacturers postAllManufacturers = new PostAllManufacturers(preference.getToken());

            try {
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postAllManufacturers));

                Request request = new Request.Builder()
                        .url(GET_ALL_MANUFACTURERS)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("ALL_MANUFACTURER", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseAllManufacturers responseAllManufacturers = gsonFromServer.fromJson(responseBody, ResponseAllManufacturers.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseAllManufacturers;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResponseAllManufacturers responseAllManufacturers) {
            super.onPostExecute(responseAllManufacturers);

            respons = responseAllManufacturers.getManufacturers_data();

            itemListManufacturersAdapter.updateListManufacturer(respons);
        }
    }

    // handling press on button in Drawer Menu
    public void closeDrawer(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manufactures, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intentNewManufacturer = new Intent(this, NewManufacturerActivity.class);
            startActivity(intentNewManufacturer);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @SuppressLint("StaticFieldLeak")
    private class GetCountOfNew extends AsyncTask<String, String,ResponseAllNewAdmin> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseAllNewAdmin doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Preference preference = new Preference(getApplicationContext());
            PostNewForAdmin postNewForAdmin = new PostNewForAdmin(preference.getToken());

            try {
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postNewForAdmin));

                Request request = new Request.Builder()
                        .url(GET_COUNT_OF_NEW_ADMIN)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();



                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("ALL_NEW", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseAllNewAdmin responseAllNewAdmin = gsonFromServer.fromJson(responseBody, ResponseAllNewAdmin.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseAllNewAdmin;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResponseAllNewAdmin responseAllNewAdmin) {
            super.onPostExecute(responseAllNewAdmin);

            listItems.clear();

            listItems.add(new ItemsDrawer("Manufacturers", ""));
            listItems.add(new ItemsDrawer("Buyers", responseAllNewAdmin.getNew_buyers_count()));
            listItems.add(new ItemsDrawer("Orders", responseAllNewAdmin.getNew_orders_count()));
            listItems.add(new ItemsDrawer("Messages", responseAllNewAdmin.getNew_message_count()));
            listItems.add(new ItemsDrawer("Slider", ""));

            itemListAdapter.updateOrderProductUser(listItems);

        }
    }






}