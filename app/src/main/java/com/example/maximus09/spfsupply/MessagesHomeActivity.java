package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.maximus09.spfsupply.data.model.PostAllMessagesForUser;
import com.example.maximus09.spfsupply.data.model.ResponseAllMessageUser;
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

public class MessagesHomeActivity extends AppCompatActivity {

    private static final String GET_CHAT_USER = "http://spf.yobibyte.in.ua/api/chat/user/get/";

    ListView listView;

    RecyclerView recyclerView_message;
    ItemListMessageHomeAdapter itemListMessageHomeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
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

        //set menu icon in left_top side of toolBar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_home_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        listView = (ListView)findViewById(R.id.drawer_menu_home_list);
        ItemsDrawer itemsHome = new ItemsDrawer("Home", "1");
        ItemsDrawer itemsOrders = new ItemsDrawer("Orders", "1");
        ItemsDrawer itemsCheckOut = new ItemsDrawer("Check-out", "1");
        ItemsDrawer itemsMessages = new ItemsDrawer("Messages", "1");

        final ArrayList<ItemsDrawer> itemsDrawerHome = new ArrayList<>();
        itemsDrawerHome.add(itemsHome);
        itemsDrawerHome.add(itemsOrders);
        itemsDrawerHome.add(itemsCheckOut);
        itemsDrawerHome.add(itemsMessages);

        final ItemListAdapter itemListAdapter = new ItemListAdapter(this, R.layout.custom_drawer_menu_item, itemsDrawerHome);
        listView.setAdapter(itemListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent intentHome = new Intent(view.getContext(), HomeActivity.class);
                    startActivity(intentHome);
                    finish();
                }

                if (i == 1) {
                    Intent intentOrderHome = new Intent(view.getContext(), OrderHomeActivity.class);
                    startActivity(intentOrderHome);
                    finish();
                }

                if (i == 2) {
                    Intent intentCheckOut = new Intent(view.getContext(), CheckOutActivity.class);
                    startActivity(intentCheckOut);
                    finish();
                }

                if (i == 3) {
                    Intent intentMessagesHome = new Intent(view.getContext(), MessagesHomeActivity.class);
                    startActivity(intentMessagesHome);
                    finish();
                }

            }
        });

        GetAllMessageResponseUser getAllMessageResponseUser = new GetAllMessageResponseUser();
        getAllMessageResponseUser.execute();

        recyclerView_message = (RecyclerView)findViewById(R.id.recyclerView_messages_home);
        recyclerView_message.setLayoutManager(new LinearLayoutManager(this));
        itemListMessageHomeAdapter = new ItemListMessageHomeAdapter(null, this);
        recyclerView_message.setAdapter(itemListMessageHomeAdapter);

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


    @SuppressLint("StaticFieldLeak")
    private class GetAllMessageResponseUser extends AsyncTask<String, String, ResponseAllMessageUser> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseAllMessageUser doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Preference preference = new Preference(getApplicationContext());
            PostAllMessagesForUser postAllMessagesForUser = new PostAllMessagesForUser(preference.getToken());

            try {
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postAllMessagesForUser));

                Request request = new Request.Builder()
                        .url(GET_CHAT_USER)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("ALL_MESSAGES_USER", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseAllMessageUser responseAllMessageUser = gsonFromServer.fromJson(responseBody, ResponseAllMessageUser.class);


                int responseCode = response.code();
                if (responseCode == 200 && responseBody.length() != 0) {
                    return responseAllMessageUser;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(ResponseAllMessageUser responseAllMessageUser) {
            super.onPostExecute(responseAllMessageUser);
            itemListMessageHomeAdapter.updateListMessagesAdmin(responseAllMessageUser.getChats_data());
        }

    }
}
