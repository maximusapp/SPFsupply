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
import android.support.v7.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.maximus09.spfsupply.data.model.GetUsetBascets;
import com.example.maximus09.spfsupply.data.model.PostNewCountForUser;
import com.example.maximus09.spfsupply.data.model.PostPayProduct;
import com.example.maximus09.spfsupply.data.model.PostRemoveFromBasket;
import com.example.maximus09.spfsupply.data.model.ResponseAfterPayProduct;
import com.example.maximus09.spfsupply.data.model.ResponseAllNewUser;
import com.example.maximus09.spfsupply.data.model.ResponseCheckOutProduct;
import com.example.maximus09.spfsupply.data.model.ResponseDeleteProduct;
import com.example.maximus09.spfsupply.struct.BasketsData;
import com.example.maximus09.spfsupply.struct.CardData;
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

public class CheckOutActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private static final String GET_COUNT_NEW_USER_URL = "http://api.spfsupply.com/public/api/user/get_count_of_new";
    private static final String GET_USER_BASCET_URL = "http://api.spfsupply.com/public/api/basket/user/get";
    private static final String REMONE_PRODUCT_URL = "http://api.spfsupply.com/public/api/basket/user/remove_product";
    private static final String PAY_PRODUCT_URL = "http://api.spfsupply.com/public/api/basket/pay";

    ImageView ivLogoUser;
    public TextView textView_company_name;

    RecyclerView rvDrawerItem;
    ArrayList<ItemsDrawer> listItems;
    ItemListAdapter itemListAdapter;

    RecyclerView rvListProduct;
    ItemListProductCheckOutAdapter itemListProductCheckOutAdapter;

    private List<BasketsData> items;
    public List<CardData> itemCard;

    String cardID;

    TextView tvTotalCount;
    Button buttonPay;

    TextView tvDeliver_address;

    TextView tvAddPaymentMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set font style for title ActionBar
        TextView tv=(TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);

        tvAddPaymentMethod = (TextView)findViewById(R.id.add_other_payment_method_check_out);
        tvAddPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CheckOutActivity.this, AddPaymentMethodUserActivity.class);
                startActivity(intent);
            }
        });

        tvDeliver_address = (TextView)findViewById(R.id.delivery_address_text_check_out);

        rvListProduct = (RecyclerView)findViewById(R.id.rvCheckOutProductList);
        rvListProduct.setLayoutManager(new LinearLayoutManager(this));
        //linearLayoutManager = new LinearLayoutManager(this);
        itemListProductCheckOutAdapter = new ItemListProductCheckOutAdapter(null, this){
            @Override
            public void ClickDeleteProductUser(BasketsData.ManufacturersProduct listOfChekOut) {
                String basket_id = listOfChekOut.getId();
                DeleteProduct deleteProduct = new DeleteProduct();
                deleteProduct.execute(basket_id);
            }
        };
        rvListProduct.setAdapter(itemListProductCheckOutAdapter);

        tvTotalCount = (TextView)findViewById(R.id.total_price);
        buttonPay = (Button)findViewById(R.id.buttonPay);
        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PayForProduct payForProduct = new PayForProduct();
                payForProduct.execute();
            }
        });


        GetCountNewForUser getCountNewForUser = new GetCountNewForUser();
        getCountNewForUser.execute();

        GetUserBascet getUserBascet = new GetUserBascet();
        getUserBascet.execute();


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
        rvDrawerItem = (RecyclerView)findViewById(R.id.drawer_menu_check_home_list);
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


    @SuppressLint("StaticFieldLeak")
    private class GetUserBascet  extends AsyncTask<String, String, ResponseCheckOutProduct>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseCheckOutProduct doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Preference preference = new Preference(getApplicationContext());
            GetUsetBascets getUsetBascets = new GetUsetBascets(preference.getToken());

            try {

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                        gson.toJson(getUsetBascets));

                Request request = new Request.Builder()
                        .url(GET_USER_BASCET_URL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("BASCETS_DATA", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseCheckOutProduct responseCheckOutProduct = gsonFromServer.fromJson(responseBody, ResponseCheckOutProduct.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseCheckOutProduct;
                }



            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResponseCheckOutProduct responseCheckOutProduct) {
            super.onPostExecute(responseCheckOutProduct);

            tvTotalCount.setText(responseCheckOutProduct.getTotal_count());

            items = responseCheckOutProduct.getBaskets_data();

            itemListProductCheckOutAdapter.updateListBasket(items);
            itemListProductCheckOutAdapter.notifyDataSetChanged();


            tvDeliver_address.setText(responseCheckOutProduct.getDelivery_address());


            cardID = responseCheckOutProduct.getCards().get(0).getId();
           // Log.d("CARD_ID", cardID);
            Log.d("CARD_ID", cardID);
            // If card_id is null or not null.
            if (cardID != null) {
                buttonPay.setVisibility(View.VISIBLE);
            } else {
                buttonPay.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "You have not card for pay", Toast.LENGTH_LONG).show();

            }

            // If in basket no product.
            if (responseCheckOutProduct.getBaskets_data().size() == 0) {
                buttonPay.setVisibility(View.INVISIBLE);
            }


        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteProduct extends AsyncTask<String, String, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {


            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Preference preference = new Preference(getApplicationContext());
            PostRemoveFromBasket removeFromBasket = new PostRemoveFromBasket(preference.getToken(), strings[0]);

            try {
                RequestBody requesBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(removeFromBasket));

                Request request = new Request.Builder()
                        .url(REMONE_PRODUCT_URL)
                        .post(requesBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DELETED_PRODUCT?", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseDeleteProduct responseDeleteProduct = gsonFromServer.fromJson(responseBody, ResponseDeleteProduct.class);

                if (responseDeleteProduct.getSuccess()) {
                    return true;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean response) {
            super.onPostExecute(response);

            if (isFinishing()) {
                return;
            }

            if (response) {
                GetUserBascet getUserBascet = new GetUserBascet();
                getUserBascet.execute();
            }

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class PayForProduct extends AsyncTask<String, String, ResponseAfterPayProduct> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseAfterPayProduct doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();


            Preference preference = new Preference(getApplicationContext());
            PostPayProduct postPayProduct = new PostPayProduct(preference.getToken(),cardID);


            try {

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                        gson.toJson(postPayProduct));

                Request request = new Request.Builder()
                        .url(PAY_PRODUCT_URL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("IS_PAYED?", responseBody);


                // Comment unusless strings.
                Gson gsonFromServer = new Gson();
                ResponseAfterPayProduct responseAfterPayProduct = gsonFromServer.fromJson(responseBody, ResponseAfterPayProduct.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseAfterPayProduct;
                }
//
//                if (responseAfterPayProduct.getSuccess()) {
//                    return true;
//                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResponseAfterPayProduct responseAfterPayProduct) {
            super.onPostExecute(responseAfterPayProduct);

            if (isFinishing()) {
                return;
            }

            String message = responseAfterPayProduct.getMessage();
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();


//            if (responseAfterPayProduct != null) {
//                GetUserBascet getUserBascet = new GetUserBascet();
//                getUserBascet.execute();
                //Toast.makeText(getApplicationContext(), "Payment is successful", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CheckOutActivity.this, CheckOutActivity.class);
                startActivity(intent);
//            }



        }
    }

}
