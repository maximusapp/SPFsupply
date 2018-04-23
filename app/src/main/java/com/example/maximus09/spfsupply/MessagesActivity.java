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

import com.example.maximus09.spfsupply.data.model.PostAllMessagesAdmin;
import com.example.maximus09.spfsupply.data.model.PostAllOrders;
import com.example.maximus09.spfsupply.data.model.ResponseAllMessagesAdmin;
import com.example.maximus09.spfsupply.util.Preference;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MessagesActivity extends AppCompatActivity {

    private static final String GET_CHAT_ADMIN_URL = "http://spf.yobibyte.in.ua/api/chat/admin/get/";

    ListView listViewMessages;

    RecyclerView recyclerView_messages;
    ItemListMessageAdminAdapter itemListMessageAdminAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set font style for title ActionBar
        TextView tv=(TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_messages);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // set ListView in Drawer
        listViewMessages = (ListView)findViewById(R.id.drawer_messages_menu_list);
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
        listViewMessages.setAdapter(itemListAdapter);
        listViewMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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


        GetAllMessagesAdminResponse getAllMessagesAdminResponse = new GetAllMessagesAdminResponse();
        getAllMessagesAdminResponse.execute();

        recyclerView_messages = (RecyclerView)findViewById(R.id.recycler_messages_admin);
        recyclerView_messages.setLayoutManager(new LinearLayoutManager(this));
        itemListMessageAdminAdapter = new ItemListMessageAdminAdapter(null, this);
        recyclerView_messages.setAdapter(itemListMessageAdminAdapter);


    }

    // handling press on button in Drawer Menu
    public void closeDrawer(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_messages);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

    }


    @SuppressLint("StaticFieldLeak")
    private class GetAllMessagesAdminResponse extends AsyncTask<String, String, ResponseAllMessagesAdmin> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseAllMessagesAdmin doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Preference preference = new Preference(getApplicationContext());
            PostAllMessagesAdmin postAllMessagesAdmin = new PostAllMessagesAdmin(preference.getToken());

            try {
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postAllMessagesAdmin));

                Request request = new Request.Builder()
                        .url(GET_CHAT_ADMIN_URL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("ALL_ORDERS", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseAllMessagesAdmin responseAllMessagesAdmin = gsonFromServer.fromJson(responseBody, ResponseAllMessagesAdmin.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseAllMessagesAdmin;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResponseAllMessagesAdmin responseAllMessagesAdmin) {
            super.onPostExecute(responseAllMessagesAdmin);
            itemListMessageAdminAdapter.updateListMessagesAdmin(responseAllMessagesAdmin.getChats_data());
        }
    }
}
