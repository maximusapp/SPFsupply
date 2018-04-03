package com.example.maximus09.spfsupply;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderHomeActivity extends AppCompatActivity {

    ListView listView;

    ListView listOfOrdersHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);

        //set font style for title ActionBar
        TextView tv=(TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);



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

        listOfOrdersHome = (ListView)findViewById(R.id.listView_orders_home);

        ItemOrdersHome itemsOrdersHome1 = new ItemOrdersHome("Orders#", "Date", "$1232");
        ItemOrdersHome itemsOrdersHome2 = new ItemOrdersHome("Orders#", "04/02/2018", "$1232");
        ItemOrdersHome itemsOrdersHome3 = new ItemOrdersHome("Orders#", "04/02/2018", "$1232");
        ItemOrdersHome itemsOrdersHome4 = new ItemOrdersHome("Orders#", "04/02/2018", "$1232");

        final ArrayList<ItemOrdersHome> itemOrdersHomes = new ArrayList<>();
        itemOrdersHomes.add(itemsOrdersHome1);
        itemOrdersHomes.add(itemsOrdersHome2);
        itemOrdersHomes.add(itemsOrdersHome3);
        itemOrdersHomes.add(itemsOrdersHome4);

        final ItemListOrderHomeAdapter itemListOrderHomeAdapter = new ItemListOrderHomeAdapter(this, R.layout.custom_order_home_item, itemOrdersHomes);
        listOfOrdersHome.setAdapter(itemListOrderHomeAdapter);

        listOfOrdersHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    Intent intentOrderNumberHome = new Intent(view.getContext(), OrderNumberHomeActivity.class);
                    startActivity(intentOrderNumberHome);
                }
            }
        });



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


}
