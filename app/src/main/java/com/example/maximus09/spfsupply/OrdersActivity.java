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
import android.widget.TextView;

import com.example.maximus09.spfsupply.data.model.PostAllOrders;
import com.example.maximus09.spfsupply.data.model.ResponseAllOrders;
import com.example.maximus09.spfsupply.util.Preference;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrdersActivity extends AppCompatActivity {

    private static final String GET_ALL_ORDERS = "http://spf.yobibyte.in.ua/api/admin/orders/get/";

    ListView listViewOrders;

    RecyclerView recyclerView_list_orders;
    ItemListOrdersAdapter itemListOrdersAdapter;

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

        // set ListView in Drawer
        listViewOrders = (ListView)findViewById(R.id.drawer_orders_menu_list);
        ItemsDrawer itemsManufacturers = new ItemsDrawer("Manufacturers", "1");
        ItemsDrawer itemsBueyrs = new ItemsDrawer("Buyers", "1");
        ItemsDrawer itemsOrders = new ItemsDrawer("Orders", "1");
        ItemsDrawer itemsMessages = new ItemsDrawer("Messages", "1");
        ItemsDrawer itemsSlider = new ItemsDrawer("Slider", "1");

        final ArrayList<ItemsDrawer> itemsDrawer = new ArrayList<>();
        itemsDrawer.add(itemsManufacturers);
        itemsDrawer.add(itemsBueyrs);
        itemsDrawer.add(itemsOrders);
        itemsDrawer.add(itemsMessages);
        itemsDrawer.add(itemsSlider);

        final ItemListAdapter itemListAdapter = new ItemListAdapter(this, R.layout.custom_drawer_menu_item, itemsDrawer);
        listViewOrders.setAdapter(itemListAdapter);
        listViewOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    Intent intentFirst = new Intent(view.getContext(), ManufacturesActivity.class);
                    startActivity(intentFirst);
                }

                if(position == 1) {
                    Intent intentSec = new Intent(view.getContext(), BuyersActivity.class);
                    startActivity(intentSec);
                    finish();
                }

                if (position == 2) {
                    Intent intentOrders = new Intent(view.getContext(), OrdersActivity.class);
                    startActivity(intentOrders);
                    finish();
                }

                if (position == 3) {
                    Intent intentMessages = new Intent(view.getContext(), MessagesActivity.class);
                    startActivity(intentMessages);
                    finish();
                }

                if (position == 4) {
                    Intent intentSlider = new Intent(view.getContext(), SliderActivity.class);
                    startActivity(intentSlider);
                    finish();
                }

            }
        });

        GetAllOrdersResponse getAllOrdersResponse = new GetAllOrdersResponse();
        getAllOrdersResponse.execute();

        recyclerView_list_orders = (RecyclerView)findViewById(R.id.recycler_orders);
        recyclerView_list_orders.setLayoutManager(new LinearLayoutManager(this));
        itemListOrdersAdapter = new ItemListOrdersAdapter(null, this){
            @Override
            public void ClickOrderNumber(ResponseAllOrders.OrdersData ordersData) {
                Log.d("ID_OF_ORDER", String.valueOf(ordersData.getId()));
                Intent intentOrderNumber = new Intent(OrdersActivity.this, OrderNumberActivity.class);
                startActivity(intentOrderNumber);
            }
        };
        recyclerView_list_orders.setAdapter(itemListOrdersAdapter);

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
            itemListOrdersAdapter.updateListOrders(responseAllOrders.getOrders_data());
        }
    }



}