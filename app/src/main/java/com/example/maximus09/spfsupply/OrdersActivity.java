package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.maximus09.spfsupply.data.model.PostAllOrders;
import com.example.maximus09.spfsupply.data.model.PostNewForAdmin;
import com.example.maximus09.spfsupply.data.model.ResponseAllBuyers;
import com.example.maximus09.spfsupply.data.model.ResponseAllNewAdmin;
import com.example.maximus09.spfsupply.data.model.ResponseAllOrders;
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

public class OrdersActivity extends AppCompatActivity {

    private static final String GET_ALL_ORDERS = "http://api.spfsupply.com/public/api/admin/orders/get";
    private static final String GET_COUNT_OF_NEW_ADMIN = "http://api.spfsupply.com/public/api/admin/get_count_of_new";

    ListView listViewOrders;

    RecyclerView recyclerView_list_orders;
    ItemListOrdersAdapter itemListOrdersAdapter;

    SearchView searchView;
    List<ResponseAllOrders.OrdersData> response;

    RecyclerView rvDrawerItem;
    ArrayList<ItemsDrawer> listItems;
    ItemListAdapter itemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set font style for title ActionBar
        TextView tv=(TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_orders);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Get count of new
        GetCountOfNew getCountOfNew = new GetCountOfNew();
        getCountOfNew.execute();


        searchView = (SearchView)findViewById(R.id.search_orders);
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


        listItems = new ArrayList<>();
        rvDrawerItem = (RecyclerView)findViewById(R.id.drawer_orders_menu_list);
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

        GetAllOrdersResponse getAllOrdersResponse = new GetAllOrdersResponse();
        getAllOrdersResponse.execute();

        recyclerView_list_orders = (RecyclerView)findViewById(R.id.recycler_orders);
        recyclerView_list_orders.setLayoutManager(new LinearLayoutManager(this));
        itemListOrdersAdapter = new ItemListOrdersAdapter(null, this){
            @Override
            public void ClickOrderNumber(ResponseAllOrders.OrdersData ordersData) {
                Log.d("ID_OF_ORDER", String.valueOf(ordersData.getId()));
                Intent intentOrderNumber = new Intent(OrdersActivity.this, OrderNumberActivity.class);
                intentOrderNumber.putExtra("orders_id", ordersData.getId());
                startActivity(intentOrderNumber);
                finish();
            }
        };
        recyclerView_list_orders.setAdapter(itemListOrdersAdapter);

    }

    //Find in searchView
    private void filter(String s) {
        List<ResponseAllOrders.OrdersData> temp = new ArrayList<>();
        for (int i = 0; i < response.size(); i++) {
            if (response.get(i).getManufacturers_company_name().toLowerCase().startsWith(s.toLowerCase())) {
                temp.add(response.get(i));
            }
        }

        itemListOrdersAdapter.updateListOrders(temp);

    }

    // handling press on button in Drawer Menu
    public void closeDrawer(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_orders);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class GetAllOrdersResponse extends AsyncTask<String, String, ResponseAllOrders> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseAllOrders doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Preference preference = new Preference(getApplicationContext());
            PostAllOrders postAllOrders = new PostAllOrders(preference.getToken());

            try {
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postAllOrders));

                Request request = new Request.Builder()
                        .url(GET_ALL_ORDERS)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("ALL_ORDERS", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseAllOrders responseAllOrders = gsonFromServer.fromJson(responseBody, ResponseAllOrders.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseAllOrders;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResponseAllOrders responseAllOrders) {
            super.onPostExecute(responseAllOrders);

            response = responseAllOrders.getOrders_data();
            itemListOrdersAdapter.updateListOrders(response);

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