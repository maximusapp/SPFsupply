package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.maximus09.spfsupply.data.model.GetLogosFromServer;
import com.example.maximus09.spfsupply.data.model.PostImageSlider;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WelcomeActivity extends AppCompatActivity {

    private static final String WELCOMEPAGE_IMAGE_URL = "http://spf.yobibyte.in.ua/api/slider/get_manufacturers_logos/";

    KKViewPager mPager;

    Button signIn;
    Button signUp;
    Button contactWelcome;
    TestFragmentAdapter testFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mPager = (KKViewPager) findViewById(R.id.kk_pager);

        //GetLogosFromServer getLogosFromServer = new GetLogosFromServer();

        testFragmentAdapter = new TestFragmentAdapter(getSupportFragmentManager(), this, new String[]{});
        mPager.setAdapter(testFragmentAdapter);

        signIn = (Button)findViewById(R.id.button_sign_in);
        signUp = (Button)findViewById(R.id.button_sign_up);
        contactWelcome = (Button)findViewById(R.id.button_contact);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sigInIntent = new Intent(getBaseContext(), SignInActivity.class);
                startActivity(sigInIntent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignUp = new Intent(WelcomeActivity.this, SignUpActivity.class);
                startActivity(intentSignUp);
            }
        });

        contactWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentContact = new Intent(WelcomeActivity.this, ContactUsActivity.class);
                startActivity(intentContact);
            }
        });


        GetImageArray getImageArray = new GetImageArray();
        getImageArray.execute();

    }

    @SuppressLint("StaticFieldLeak")
    private class GetImageArray extends AsyncTask<String, String, GetLogosFromServer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected GetLogosFromServer doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            PostImageSlider postImageSlider = new PostImageSlider(WELCOMEPAGE_IMAGE_URL);

            try {
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postImageSlider));

                Request request = new Request.Builder()
                        .url(WELCOMEPAGE_IMAGE_URL)
                        .post(body)
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("IMAGE_URL", responseBody);

                Gson gsonImageFromServer = new Gson();
                GetLogosFromServer getLogosFromServer = gsonImageFromServer.fromJson(responseBody, GetLogosFromServer.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return getLogosFromServer;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(GetLogosFromServer getLogosFromServer) {
            super.onPostExecute(getLogosFromServer);

            if(isFinishing())
                return;

            if (getLogosFromServer != null) {
                testFragmentAdapter.update(getLogosFromServer.getLogos());
            } else {
                Toast.makeText(WelcomeActivity.this, "Check Internet connection, or it's problem with server", Toast.LENGTH_LONG).show();
            }

        }
    }

}