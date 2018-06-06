package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.maximus09.spfsupply.data.model.GetProductByIDUser;
import com.example.maximus09.spfsupply.data.model.PostAddProduct;
import com.example.maximus09.spfsupply.data.model.ResponseProductInfo;
import com.example.maximus09.spfsupply.util.Preference;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DetailsHomeActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private static final String GET_PRODUCT_BY_ID_URL = "http://api.spfsupply.com/public/api/manufacturers/products/get_by_id";
    private static final String ADD_PRODUCT_URL = "http://api.spfsupply.com/public/api/basket/add_product";

    Menu menu;

    ImageView ivLogo;
    TextView tvProdTitle;
    TextView tvDescribe;
    TextView tvPrice;

    TextView tvQuantity;
    ImageView ivMinus;
    ImageView ivPlus;
    int counter = 1;

    ResponseProductInfo responseProductInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GetProductByIdUser getProductByIdUser = new GetProductByIdUser();
        getProductByIdUser.execute();

        //set font style for title ActionBar
        TextView tv=(TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);

        ivLogo = (ImageView)findViewById(R.id.ivLogoDateils);
        tvProdTitle = (TextView)findViewById(R.id.tvProd_title);
        tvDescribe = (TextView)findViewById(R.id.tvDescription);
        tvPrice = (TextView)findViewById(R.id.tvPrice);

        tvQuantity = (TextView)findViewById(R.id.tvQuantity);
        ivMinus = (ImageView)findViewById(R.id.ivMinus);
        ivPlus = (ImageView)findViewById(R.id.ivPlus);


        ivMinus.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (responseProductInfo == null)
                    return;

                counter--;
                if (counter <= 0)
                ivMinus.setVisibility(View.INVISIBLE);
                tvQuantity.setText(Integer.toString(counter));

                int ans = Integer.parseInt(responseProductInfo.getProduct_data().getPrice())  * counter;
                tvPrice.setText(String.valueOf(ans));
            }
        });


        ivPlus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (responseProductInfo == null)
                    return;

                counter++;
                if (counter >= 1)
                ivMinus.setVisibility(View.VISIBLE);
                tvQuantity.setText(Integer.toString(counter));

               int ans = Integer.parseInt(responseProductInfo.getProduct_data().getPrice())  * counter;
               tvPrice.setText(String.valueOf(ans));
            }
        });



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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details_menu, menu);
        this.menu = menu;

        menu.findItem(R.id.action_add_to_card_details).setTitle((Html.fromHtml("<font color='#2196f3'>Add to Cart</font>")));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_to_card_details) {

            AddProduct addProduct = new AddProduct();
            addProduct.execute();

            //Toast.makeText(this, "Pressed ADD to CARD Button", Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("StaticFieldLeak")
    private class GetProductByIdUser extends AsyncTask<String, String, ResponseProductInfo> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseProductInfo doInBackground(String... strings) {

            // Get Manufacturer id from HomeActivity.
            Intent intentGetExtras = getIntent();
            String productId = intentGetExtras.getStringExtra("product_id");

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Preference preference = new Preference(getApplicationContext());
            GetProductByIDUser getProductByIDUser = new GetProductByIDUser(preference.getToken(), productId);

            try {

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                        gson.toJson(getProductByIDUser));

                Request request = new Request.Builder()
                        .url(GET_PRODUCT_BY_ID_URL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("PRODUCT_INFO", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseProductInfo responseProductInfo = gsonFromServer.fromJson(responseBody, ResponseProductInfo.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseProductInfo;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResponseProductInfo responseProductInfo) {
            super.onPostExecute(responseProductInfo);

            if (responseProductInfo.getProduct_data().getLogo() != null)
            Glide.with(getApplicationContext()).load(responseProductInfo.getProduct_data().getLogo()).into(ivLogo);

            tvProdTitle.setText(responseProductInfo.getProduct_data().getTitle());
            tvDescribe.setText(responseProductInfo.getProduct_data().getDescription());
            tvPrice.setText(responseProductInfo.getProduct_data().getPrice());

            DetailsHomeActivity.this.responseProductInfo = responseProductInfo;

            MenuItem dd = menu.findItem(R.id.action_add_to_card_details);

            if (responseProductInfo.getProduct_data().getCan_buy() != null && responseProductInfo.getProduct_data().getCan_buy().equals("0")) {
                if(dd != null)
                    dd.setVisible(false);
            } else {
                dd.setVisible(true);
            }

        }
    }


    @SuppressLint("StaticFieldLeak")
    private class AddProduct extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {


            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Preference preference = new Preference(getApplicationContext());
            PostAddProduct postAddProduct = new PostAddProduct(preference.getToken(),
                    responseProductInfo.getProduct_data().getId(), String.valueOf(counter));

            try {

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                        gson.toJson(postAddProduct));

                Request request = new Request.Builder()
                        .url(ADD_PRODUCT_URL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("PRODUCT_ADD?", responseBody);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (isFinishing()) {
                return;
            }

            Intent intent = new Intent(DetailsHomeActivity.this, HomeActivity.class);
            startActivity(intent);

        }
    }
}
