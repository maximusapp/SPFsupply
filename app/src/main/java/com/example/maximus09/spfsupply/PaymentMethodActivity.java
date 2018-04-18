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

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PaymentMethodActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private static final String SIGN_UP_URL = "http://spf.yobibyte.in.ua/api/sign_up/";

    EditText card_num;
    EditText exp_month;
    EditText exp_year;
    EditText cvc;

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
        exp_month = (EditText)findViewById(R.id.exp_month);
        exp_year = (EditText)findViewById(R.id.exp_year);
        cvc = (EditText)findViewById(R.id.cvc);


//        // Get data from 1-t and 2-d Activities
//        //========== it is work ===========
//        Intent intentExtras = getIntent();
//
//        // From 1-t act
//        String com_name = intentExtras.getStringExtra("com_name");
//        String mailta = intentExtras.getStringExtra("mailta");
//        String owners = intentExtras.getStringExtra("owners");
//        String pass = intentExtras.getStringExtra("pass");
//        String conf_pass = intentExtras.getStringExtra("conf");
//
//        // From 2-d act
//        String image_link = intentExtras.getStringExtra("image_link");
//        String phone = intentExtras.getStringExtra("phone");
//        String business_adr = intentExtras.getStringExtra("address");
//        String delivery_address = intentExtras.getStringExtra("delivery_address");


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

//                String card_number = card_num.getText().toString().trim();
//                String expo_month = exp_month.getText().toString().trim();
//                String expo_year = exp_year.getText().toString().trim();
//                String cvcc = cvc.getText().toString().trim();

                TascSignUp tascSignUp = new TascSignUp();
                tascSignUp.execute();

                Toast.makeText(PaymentMethodActivity.this, "Registration success! Wait while admin activated your account", Toast.LENGTH_LONG).show();

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
            Gson gson = new Gson();

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
            String expo_month = exp_month.getText().toString().trim();
            String expo_year = exp_year.getText().toString().trim();
            String cvcc = cvc.getText().toString().trim();

            PostSignUp postSignUp = new PostSignUp(com_name, mailta, owners, pass, conf_pass,
                    image_link, phone, business_adr, delivery_address, card_number, expo_month, expo_year, cvcc);

            try{

                RequestBody body = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postSignUp));

                Request request = new Request.Builder()
                        .url(SIGN_UP_URL)
                        .post(body)
                        .addHeader("Content-Type", "application/json")
                        .build();

                okhttp3.Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DEBUG : ", responseBody);

            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(ResponseAfterSignUp responseAfterSignUp) {
            super.onPostExecute(responseAfterSignUp);
        }
    }
}
