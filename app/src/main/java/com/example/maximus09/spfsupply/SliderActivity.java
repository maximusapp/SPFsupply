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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.maximus09.spfsupply.data.model.GetSlidersData;
import com.example.maximus09.spfsupply.data.model.PostNewForAdmin;
import com.example.maximus09.spfsupply.data.model.ResponseAllManufacturers;
import com.example.maximus09.spfsupply.data.model.ResponseAllNewAdmin;
import com.example.maximus09.spfsupply.data.model.ResponseSlidersData;
import com.example.maximus09.spfsupply.util.Preference;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SliderActivity extends AppCompatActivity {

    private static final String SET_SLIDER_URL = "http://api.spfsupply.com/public/api/manufacturers/slider/set";
    private static final String GET_SLIDERS_DATA_URL = "http://api.spfsupply.com/public/api/manufacturers/slider/get";
    private static final String GET_COUNT_OF_NEW_ADMIN = "http://api.spfsupply.com/public/api/admin/get_count_of_new";

    ListView listViewBuyers;

    SearchView searchView;
    List<ResponseSlidersData.SlidersData> sliderData;
    ArrayList<ResponseSlidersData.SlidersData> permissionsArrayList = new ArrayList<>();

    RecyclerView rvSlider;
    ItemListSliderAdapter itemListSliderAdapter;

    RecyclerView rvDrawerItem;
    ArrayList<ItemsDrawer> listItems;
    ItemListAdapter itemListAdapter;

    //components in bottom of activity
   // static CheckBox checkBox;
    ImageView ivCheckAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GetSliderData getSliderData = new GetSliderData();
        getSliderData.execute();

        //set font style for title ActionBar
        TextView tv = (TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);

        searchView = (SearchView)findViewById(R.id.search_sliders);
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


        //Get count of new
        GetCountOfNew getCountOfNew = new GetCountOfNew();
        getCountOfNew.execute();


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


        rvSlider = (RecyclerView)findViewById(R.id.recyclerSlider);
        rvSlider.setLayoutManager(new LinearLayoutManager(this));
        itemListSliderAdapter = new ItemListSliderAdapter(null, this){
            @Override
            public void ClickSlider(ResponseSlidersData.SlidersData sliderActiveData) {

                sliderData = itemListSliderAdapter.getSlidersData();

               SetSliderStatus setSliderStatus = new SetSliderStatus();
               setSliderStatus.execute();

            }
        };

        rvSlider.setAdapter(itemListSliderAdapter);

        ivCheckAll = (ImageView)findViewById(R.id.bottom_checkbox_slider);
        ivCheckAll.setImageResource(R.drawable.uncheckes);

        ivCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivCheckAll.setImageResource(R.drawable.checkes);
            }
        });

    }


    // Find in searchView.
    private void filter(String s) {
        List<ResponseSlidersData.SlidersData> temp = new ArrayList<>();
        for (int i = 0; i < sliderData.size(); i++) {
            if (sliderData.get(i).getCompany_name().toLowerCase().startsWith(s.toLowerCase())) {
                temp.add(sliderData.get(i));
            }
        }

        itemListSliderAdapter.updateSliderList(temp);

    }


    // Handling press on button in Drawer Menu.
    public void closeDrawer(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_buyers);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetSliderData extends AsyncTask<String, String, ResponseSlidersData> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseSlidersData doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Preference preference = new Preference(getApplicationContext());
            GetSlidersData getSlidersData = new GetSlidersData(preference.getToken());

            try {
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(getSlidersData));

                Request request = new Request.Builder()
                        .url(GET_SLIDERS_DATA_URL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DATA_OF_SLIDER ", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseSlidersData responseSlidersData = gsonFromServer.fromJson(responseBody, ResponseSlidersData.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseSlidersData;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResponseSlidersData responseSlidersData) {
            super.onPostExecute(responseSlidersData);

            sliderData = responseSlidersData.getSliders_data();

            itemListSliderAdapter.updateSliderList(sliderData);

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



    @SuppressLint("StaticFieldLeak")
    private class SetSliderStatus extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();

            Preference preference = new Preference(getApplicationContext());

            try {
                JSONObject obj = new JSONObject();
                obj.put("token", preference.getToken());


                JSONObject jsonObject = new JSONObject();

                for (int i = 0; i < sliderData.size(); i++) {
                    jsonObject.put(sliderData.get(i).getId(), sliderData.get(i).getIs_slider());
                }

                obj.put("manufacturers_data" , jsonObject);


                //noinspection ResultOfMethodCallIgnored
                obj.toString();
                Log.d("In_Json", String.valueOf(obj));

                RequestBody body = RequestBody.create(MediaType.parse("application/json"), String.valueOf(obj));

                Request request = new Request.Builder()
                        .url(SET_SLIDER_URL)
                        .post(body)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("RESPONSE_SLIDE_DATA", responseBody);


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }



}
