package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maximus09.spfsupply.data.model.PostAllBuyers;
import com.example.maximus09.spfsupply.data.model.PostNewForAdmin;
import com.example.maximus09.spfsupply.data.model.ResponseAllBuyers;
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

public class BuyersActivity extends AppCompatActivity {

    private static final String GET_ALL_BUYERS_URL = "http://api.spfsupply.com/public/api/buyers/admin/get_all";
    private static final String GET_COUNT_OF_NEW_ADMIN = "http://api.spfsupply.com/public/api/admin/get_count_of_new";

    RecyclerView rvDrawerItem;
    ArrayList<ItemsDrawer> listItems;
    ItemListAdapter itemListAdapter;

    ItemListBuyersAdapter itemListBuyersAdapter;
    RecyclerView recyclerViewBuyers;

    SearchView searchView;
    List<ResponseAllBuyers.AccountData> response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // set statusBar color
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(this.getColor(R.color.colorStatusBar));
            }
        }

        //set font style for title ActionBar
        TextView tv=(TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);

        searchView = (SearchView)findViewById(R.id.search_buyers);
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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_buyers);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        GetResponseAllBuyers getAllBuyers = new GetResponseAllBuyers();
        getAllBuyers.execute();


        //Get count of new
        GetCountOfNew getCountOfNew = new GetCountOfNew();
        getCountOfNew.execute();


        listItems = new ArrayList<>();
        rvDrawerItem = (RecyclerView)findViewById(R.id.drawer_buyers_menu_list);
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


        //Handle RecyclerView
        recyclerViewBuyers = (RecyclerView)findViewById(R.id.recyclerView_buyerss);
        recyclerViewBuyers.setLayoutManager(new LinearLayoutManager(this));
        itemListBuyersAdapter = new ItemListBuyersAdapter(null, this) {
            @Override
            public void ClickBuyers(ResponseAllBuyers.AccountData accountData) {
                Log.d("ID_OF_MANUF", String.valueOf(accountData.getId()));
                Intent intentProfile = new Intent(BuyersActivity.this, ProfileActivity.class);
                intentProfile.putExtra("BUYERS_ID", accountData.getId());
                startActivity(intentProfile);
            }
        };
        recyclerViewBuyers.setAdapter(itemListBuyersAdapter);


    }


    //Find in searchView
    private void filter(String s) {
        List<ResponseAllBuyers.AccountData> temp = new ArrayList<>();
        for (int i = 0; i < response.size(); i++) {
            if (response.get(i).getCompany_name().toLowerCase().startsWith(s.toLowerCase())) {
                temp.add(response.get(i));
            }
        }

        itemListBuyersAdapter.updateBuyersList(temp);

    }



    @SuppressLint("StaticFieldLeak")
    private class GetResponseAllBuyers extends AsyncTask<String, String, ResponseAllBuyers> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseAllBuyers doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Preference preference = new Preference(getApplicationContext());
            PostAllBuyers postAllBuyers = new PostAllBuyers(preference.getToken());

            try{
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postAllBuyers));

                Request request = new Request.Builder()
                        .url(GET_ALL_BUYERS_URL)
                        .post(body)
                        .addHeader("Content-Type", "application/json")
                        .build();

                okhttp3.Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("ALL_BUYERS", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseAllBuyers responseAllBuyers = gsonFromServer.fromJson(responseBody, ResponseAllBuyers.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseAllBuyers;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResponseAllBuyers responseAllBuyers) {
            super.onPostExecute(responseAllBuyers);

            response = responseAllBuyers.getBuyers_data();
            itemListBuyersAdapter.updateBuyersList(response);


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.buyers_mail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_mail_buyers) {

            Intent intentSendInvite = new Intent(BuyersActivity.this, SendInviteActivity.class);
            startActivity(intentSendInvite);

            //Toast.makeText(this, "Pressed Mail Button", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // handling press on button in Drawer Menu
    public void closeDrawer(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_buyers);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

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