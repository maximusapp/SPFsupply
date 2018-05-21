package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.maximus09.spfsupply.data.model.GetOrdersById;
import com.example.maximus09.spfsupply.data.model.ResponseOrdersById;
import com.example.maximus09.spfsupply.util.Preference;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrderNumberHomeActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private static final String GET_ORDER_BY_ID_USER = "http://api.spfsupply.com/public/api/user/orders/get_by_id";

    TextView tvOrderDate;
    TextView tvTotalPrice;
    TextView tvDeliveryLocation;
    TextView tvDocumentName;

    RecyclerView rvProdoctListUser;
    ItemListOrderNumUserAdapter itemListOrderNumUserAdapter;

    String chatId;
    String orderNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_number_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set font style for title ActionBar
        TextView tv=(TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);

        GetOrderNumberUserById getOrderNumberById = new GetOrderNumberUserById();
        getOrderNumberById.execute();


        tvOrderDate = (TextView)findViewById(R.id.textCompany_order_date_title);
        tvTotalPrice = (TextView)findViewById(R.id.total_price_order_number);
        tvDeliveryLocation = (TextView)findViewById(R.id.delivery_location_order_number);
        tvDocumentName = (TextView)findViewById(R.id.invoice_order_order_number);


        rvProdoctListUser = (RecyclerView)findViewById(R.id.rvUserOrderNumberList);
        rvProdoctListUser.setLayoutManager(new LinearLayoutManager(this));
        itemListOrderNumUserAdapter = new ItemListOrderNumUserAdapter(null,this);
        rvProdoctListUser.setAdapter(itemListOrderNumUserAdapter);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_order_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_mail_order_number_home) {
            Intent intentMail = new Intent(OrderNumberHomeActivity.this, ChatHomeActivity.class);
            intentMail.putExtra("chat_id", chatId);
            intentMail.putExtra("order_name", orderNumber);
            startActivity(intentMail);
            finish();
            //Toast.makeText(this, "Pressed Mail button", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("StaticFieldLeak")
    private class GetOrderNumberUserById extends AsyncTask<String, String, ResponseOrdersById> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseOrdersById doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            //Get StringExtras from OrderActivity.class
            Intent intentExtras = getIntent();
            String ordersUser_id = intentExtras.getStringExtra("ordersUser_id");
            Log.d("ORDERS_DATA_IS", ordersUser_id);

            Preference preference = new Preference(getApplicationContext());
            GetOrdersById getOrderNumberById = new GetOrdersById(preference.getToken(), ordersUser_id);

            try{
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(getOrderNumberById));

                Request request = new Request.Builder()
                        .url(GET_ORDER_BY_ID_USER)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DATA_OF_ORDERS ", responseBody);


                Gson gsonFromServer = new Gson();
                ResponseOrdersById responseOrdersById = gsonFromServer.fromJson(responseBody, ResponseOrdersById.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseOrdersById;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(ResponseOrdersById responseOrdersById) {
            super.onPostExecute(responseOrdersById);

            if (responseOrdersById.getOrders_data().getOrder_name() != null) {
                getSupportActionBar().setTitle(responseOrdersById.getOrders_data().getOrder_name());
            }

            itemListOrderNumUserAdapter.updateOrderProductUser(responseOrdersById.getOrders_data().getProducts());

            tvOrderDate.setText(responseOrdersById.getOrders_data().getOrder_date());
            tvTotalPrice.setText(responseOrdersById.getOrders_data().getTotal_count());
            tvDeliveryLocation.setText(responseOrdersById.getOrders_data().getDelivery_location());

            chatId = responseOrdersById.getOrders_data().getChat_id();
            orderNumber = responseOrdersById.getOrders_data().getOrder_name();

        }
    }
}
