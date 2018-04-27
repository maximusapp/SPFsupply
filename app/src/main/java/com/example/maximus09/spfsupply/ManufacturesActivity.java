package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maximus09.spfsupply.data.model.PostAllManufacturers;
import com.example.maximus09.spfsupply.data.model.ResponseAllManufacturers;
import com.example.maximus09.spfsupply.util.Preference;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ManufacturesActivity extends AppCompatActivity {

    private static final String GET_ALL_MANUFACTURERS = "http://spf.yobibyte.in.ua/api/manufacturers/get_for_admin/";


    RecyclerView recyclerView_manufacturers;
    ItemListManufacturersAdapter itemListManufacturersAdapter;

    ListView listView;

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

        //Handle AsyncTask
        GetAllManufacturerResponse getAllManufacturer = new GetAllManufacturerResponse();
        getAllManufacturer.execute();

        // set ListView in Drawer
        listView = (ListView)findViewById(R.id.drawer_menu_list);
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
        listView.setAdapter(itemListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    Intent intentFirst = new Intent(view.getContext(), ManufacturesActivity.class);
                    startActivity(intentFirst);
                    finish();
                }

                if(position == 1) {
                    Intent intentBuyers = new Intent(view.getContext(), BuyersActivity.class);
                    startActivity(intentBuyers);
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
            itemListManufacturersAdapter.updateListManufacturer(responseAllManufacturers.getManufacturers_data());
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

}