package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.maximus09.spfsupply.data.model.PostGetManufInfoHome;
import com.example.maximus09.spfsupply.data.model.ResponseManufDataUser;
import com.example.maximus09.spfsupply.util.Preference;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InformationHomeActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private static final String GET_MANUF_BY_ID_URL = "http://api.spfsupply.com/public/api/manufacturers/get_by_id";

    ImageView ivLogo;
    TextView tvCompName;
    TextView tvLocation;
    TextView tvWebSite;

    RecyclerView rvProducts;
    ItemListProductInfoHome itemListProductInfoHome;
    List<ResponseManufDataUser.ProductsUser> responseProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //set font style for title ActionBar
        TextView tv=(TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);


        ivLogo = (ImageView)findViewById(R.id.logo_information_user);
        tvCompName = (TextView)findViewById(R.id.compName_information_user);
        tvLocation = (TextView)findViewById(R.id.company_location1_info_user);
        tvWebSite = (TextView)findViewById(R.id.company_site_information_user);
        rvProducts = (RecyclerView)findViewById(R.id.rvInfoProductHome);

        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        itemListProductInfoHome = new ItemListProductInfoHome(null, this){
            @Override
            public void ClickAllProductList(ResponseManufDataUser.ProductsUser productsUser) {
                Log.d("ID_OF_PRODUCT", productsUser.getId());
                Intent intentDetails = new Intent(InformationHomeActivity.this, DetailsHomeActivity.class);
                intentDetails.putExtra("product_id", productsUser.getId());
                startActivity(intentDetails);
            }
        };
        rvProducts.setAdapter(itemListProductInfoHome);


        GetManufacturersById getManufacturersById = new GetManufacturersById();
        getManufacturersById.execute();


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
    }


    @SuppressLint("StaticFieldLeak")
    private class GetManufacturersById extends AsyncTask<String, String, ResponseManufDataUser> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseManufDataUser doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            // Get Manufacturer id from HomeActivity.
            Intent intentGetExtras = getIntent();
            String manufId = intentGetExtras.getStringExtra("manufacturers_id");

            Preference preference = new Preference(getApplicationContext());

            PostGetManufInfoHome postGetManufInfoHome = new PostGetManufInfoHome(preference.getToken(), manufId);

            try {

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postGetManufInfoHome));

                Request request = new Request.Builder()
                        .url(GET_MANUF_BY_ID_URL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("MANUFACTURERS_DATA", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseManufDataUser responseManufDataUser = gsonFromServer.fromJson(responseBody, ResponseManufDataUser.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseManufDataUser;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResponseManufDataUser responseManufDataUser) {
            super.onPostExecute(responseManufDataUser);

            responseProduct = responseManufDataUser.getManufacturers_data().getProducts();

            if (responseManufDataUser.getManufacturers_data().getLogo() != null)
            Glide.with(getApplicationContext()).load(responseManufDataUser.getManufacturers_data().getLogo()).into(ivLogo);

            tvCompName.setText(responseManufDataUser.getManufacturers_data().getCompany_name());
            tvLocation.setText(responseManufDataUser.getManufacturers_data().getLocation());
            tvWebSite.setText(responseManufDataUser.getManufacturers_data().getWebsite());

            itemListProductInfoHome.updateAllProductList(responseProduct);

        }
    }





}
