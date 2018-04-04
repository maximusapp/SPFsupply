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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SliderActivity extends AppCompatActivity {

    ListView listViewBuyers;

    ListView listViewSlider;

    static CheckBox checkBox;
    CheckBox customCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set font style for title ActionBar
        TextView tv = (TextView) toolbar.getChildAt(0);
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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_buyers);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // set ListView in Drawer
        listViewBuyers = (ListView) findViewById(R.id.drawer_buyers_menu_list);
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
        listViewBuyers.setAdapter(itemListAdapter);
        listViewBuyers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    Intent intentFirst = new Intent(view.getContext(), ManufacturesActivity.class);
                    startActivity(intentFirst);
                }

                if (position == 1) {
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




        listViewSlider = (ListView) findViewById(R.id.listSlider);


        ItemSlider itemSlider1 = new ItemSlider(R.drawable.ses_company_image, customCheckBox);
        ItemSlider itemSlider2 = new ItemSlider(R.drawable.image_home_demilec, customCheckBox);
        ItemSlider itemSlider3 = new ItemSlider(R.drawable.ses_company_image, customCheckBox);

        final ArrayList<ItemSlider> sliders = new ArrayList<>();
        sliders.add(itemSlider1);
        sliders.add(itemSlider2);
       // sliders.add(itemSlider3);

        final ItemListSliderAdapter itemListSliderAdapter = new ItemListSliderAdapter(this, R.layout.custom_slider_list_item, sliders);
        listViewSlider.setAdapter(itemListSliderAdapter);


        checkBox = (CheckBox) findViewById(R.id.bottom_checkbox_slider);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                customCheckBox = (CheckBox)findViewById(R.id.checkbox_custom_item_slider);
                for (int i = 0; i<=listViewSlider.getAdapter().getCount(); i++){
                    if (!customCheckBox.isChecked()) {
                        customCheckBox.setChecked(true);
                    }
                    else {
                        customCheckBox.setChecked(false);
                    }
                }

            }
        });


    }

    // handling press on button in Drawer Menu
    public void closeDrawer(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_buyers);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

}
