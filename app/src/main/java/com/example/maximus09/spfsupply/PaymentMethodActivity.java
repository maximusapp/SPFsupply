package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maximus09.spfsupply.data.model.PostSignUp;
import com.example.maximus09.spfsupply.data.model.ResponseAfterSignUp;
import com.example.maximus09.spfsupply.data.model.ResponseFromServer;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PaymentMethodActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private static final String SIGN_UP_URL = "http://api.spfsupply.com/public/api/sign_up";

    EditText card_num;
    EditText expo_month;
    EditText expo_year;
    EditText cvcc;

    private static final int card_num_length = 16;

    TextView textSignInNow;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sec);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(this.getColor(R.color.colorStatusBar));
            }
        }

        //set font style for title ActionBar
        TextView tv=(TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);


        card_num = (EditText)findViewById(R.id.card_numb_edit_text);
        expo_month = (EditText)findViewById(R.id.exp_month);
        expo_year = (EditText)findViewById(R.id.exp_year);
        cvcc = (EditText)findViewById(R.id.cvc);



        textSignInNow = (TextView)findViewById(R.id.text_sign_in_payment_method);
        textSignInNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignIn = new Intent(PaymentMethodActivity.this, WelcomeActivity.class);
                startActivity(intentSignIn);
            }
        });

        signUpButton = (Button)findViewById(R.id.button_sign_up_payment_method);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // For Multipart registration.

                // Get data from 1-t and 2-d Activities
                //========== it is work ===========
                Intent intentExtras = getIntent();

                // From 1-t act
                String com_name = intentExtras.getStringExtra("com_name");
                String mailta = intentExtras.getStringExtra("mailta");
                String owners = intentExtras.getStringExtra("owners");
                String pass = intentExtras.getStringExtra("pass");
                String conf_pass = intentExtras.getStringExtra("conf");

                // From 2-d act
                String image_link = intentExtras.getStringExtra("image_link");
                String phone = intentExtras.getStringExtra("phone");
                String business_adr = intentExtras.getStringExtra("address");
                String delivery_address = intentExtras.getStringExtra("delivery_address");

                String card_number = card_num.getText().toString().trim();
                String exp_month = expo_month.getText().toString().trim();
                String exp_year = expo_year.getText().toString().trim();
                String cvc = cvcc.getText().toString().trim();

                TascSignUp tascSignUp = new TascSignUp();
                tascSignUp.execute( mailta,  pass, com_name,  phone,  business_adr,  delivery_address,
                        owners, card_number, exp_month, exp_year, cvc,image_link);

                // End part for multipart registration.


               // Toast.makeText(PaymentMethodActivity.this, "Registration success! Wait while admin activated your account", Toast.LENGTH_LONG).show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intentSignUp = new Intent(PaymentMethodActivity.this, WelcomeActivity.class);
                        startActivity(intentSignUp);
                    }
                }, 4000);


            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private class TascSignUp extends AsyncTask<String, String, ResponseAfterSignUp>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseAfterSignUp doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            //Gson gson = new Gson();

            /*
            *
            *

            // Get data from 1-t and 2-d Activities
            //========== it is work ===========
            Intent intentExtras = getIntent();

            // From 1-t act
            String com_name = intentExtras.getStringExtra("com_name");
            String mailta = intentExtras.getStringExtra("mailta");
            String owners = intentExtras.getStringExtra("owners");
            String pass = intentExtras.getStringExtra("pass");
            String conf_pass = intentExtras.getStringExtra("conf");

            // From 2-d act
            String image_link = intentExtras.getStringExtra("image_link");
            String phone = intentExtras.getStringExtra("phone");
            String business_adr = intentExtras.getStringExtra("address");
            String delivery_address = intentExtras.getStringExtra("delivery_address");

            String card_number = card_num.getText().toString().trim();
            String exp_month = expo_month.getText().toString().trim();
            String exp_year = expo_year.getText().toString().trim();
            String cvc = cvcc.getText().toString().trim();

            PostSignUp postSignUp = new PostSignUp( mailta,  pass, com_name,  phone,  business_adr,  delivery_address,
                    owners, card_number, exp_month, exp_year, cvc,image_link);

                    *
                    */


            try{

               // RequestBody body = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postSignUp));

                final MediaType MEDIA_TYPE = MediaType.parse("image/*");

                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("email", strings[0])
                        .addFormDataPart("password", strings[1])
                        .addFormDataPart("company_name", strings[2])
                        .addFormDataPart("phone_number", strings[3])
                        .addFormDataPart("business_address", strings[4])
                        .addFormDataPart("delivery_address", strings[5])
                        .addFormDataPart("manager_name", strings[6]);

                String cardNumber = strings[7];
                String expMonth = strings[8];
                String expYear = strings[9];
                String cvc = strings[10];
                //String imageLink = strings[11];

                if (cardNumber != null && !cardNumber.isEmpty()) {
                    builder.addFormDataPart("card_number", strings[7]);
                }
                if (expMonth != null && !expMonth.isEmpty()) {
                    builder.addFormDataPart("exp_month", strings[8]);
                }
                if (expYear != null && !expYear.isEmpty()) {
                    builder.addFormDataPart("exp_year", strings[9]);
                }
                if (cvc != null && !cvc.isEmpty()) {
                    builder.addFormDataPart("cvc", strings[10]);
                }

                if (strings[11] != null) {
                    builder.addFormDataPart("company_logo", "logo.png",  RequestBody.create(MEDIA_TYPE, new File(strings[11])));
                }

                RequestBody requestBody = builder.build();


                Request request = new Request.Builder()
                        .url(SIGN_UP_URL)
                        .post(requestBody)
                       // .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DEBUG_REGISTER_DATA ", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseAfterSignUp responseAfterSignUp = gsonFromServer.fromJson(responseBody, ResponseAfterSignUp.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseAfterSignUp;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(ResponseAfterSignUp responseAfterSignUp) {
            super.onPostExecute(responseAfterSignUp);

                String message = responseAfterSignUp.getMessage();

            if (message != null) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }


        }
    }
}
