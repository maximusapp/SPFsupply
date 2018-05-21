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
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.maximus09.spfsupply.data.model.PostAllMessagesForUser;
import com.example.maximus09.spfsupply.data.model.PostNewCountForUser;
import com.example.maximus09.spfsupply.data.model.ResponseAllMessageUser;
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

public class MessagesHomeActivity extends AppCompatActivity {

    private static final String GET_CHAT_USER = "http://api.spfsupply.com/public/api/chat/user/get";
    private static final String GET_COUNT_NEW_USER_URL = "http://api.spfsupply.com/public/api/user/get_count_of_new";

    android.widget.SearchView searchView;
    List<ResponseAllMessageUser.ChatsDataUser> responseMessages;

    ImageView ivLogoUser;
    public TextView textView_company_name;


    RecyclerView recyclerView_message;
    ItemListMessageHomeAdapter itemListMessageHomeAdapter;

    RecyclerView rvDrawerItem;
    ArrayList<ItemsDrawer> listItems;
    ItemListAdapter itemListAdapter;

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


        GetCountNewForUser getCountNewForUser = new GetCountNewForUser();
        getCountNewForUser.execute();


        searchView = (android.widget.SearchView)findViewById(R.id.search_messages_home);
        searchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
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


        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view_home);
        View header = navigationView.getHeaderView(0);
        textView_company_name = (TextView)header.findViewById(R.id.textView_company_name_user_drawer);
        ivLogoUser = (ImageView)header.findViewById(R.id.imageView_logo_drawer_user);




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


        listItems = new ArrayList<>();
        rvDrawerItem = (RecyclerView)findViewById(R.id.drawer_menu_message_home_list);
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


        GetAllMessageResponseUser getAllMessageResponseUser = new GetAllMessageResponseUser();
        getAllMessageResponseUser.execute();

        recyclerView_message = (RecyclerView)findViewById(R.id.recyclerView_messages_home);
        recyclerView_message.setLayoutManager(new LinearLayoutManager(this));
        itemListMessageHomeAdapter = new ItemListMessageHomeAdapter(null, this){
            @Override
            public void ClickMessagesUser(ResponseAllMessageUser.ChatsDataUser chatsData) {
                Log.d("CHAT_ID", chatsData.getId());
                Log.d("CHAT_DATA", chatsData.getChat_name());
                Intent intentChat = new Intent(MessagesHomeActivity.this, ChatHomeActivity.class);
                intentChat.putExtra("chat_id", chatsData.getId());
                intentChat.putExtra("chat_name", chatsData.getChat_name());
                startActivity(intentChat);
            }
        };
        recyclerView_message.setAdapter(itemListMessageHomeAdapter);

    }

    //Find in searchView
    private void filter(String s) {
        List<ResponseAllMessageUser.ChatsDataUser> temp = new ArrayList<>();
        for (int i = 0; i < responseMessages.size(); i++) {
            if (responseMessages.get(i).getChat_name().toLowerCase().startsWith(s.toLowerCase())) {
                temp.add(responseMessages.get(i));
            }
        }

        itemListMessageHomeAdapter.updateListMessagesUser(temp);

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

            responseMessages = responseAllMessageUser.getChats_data();

            itemListMessageHomeAdapter.updateListMessagesUser(responseMessages);
        }

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



}
