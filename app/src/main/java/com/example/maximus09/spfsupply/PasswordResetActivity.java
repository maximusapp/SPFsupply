package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maximus09.spfsupply.data.model.PostRecoveryPassword;
import com.example.maximus09.spfsupply.data.model.ResponseAfterRecoveryPass;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PasswordResetActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private static final String RECOVER_PASS_URL = "http://api.spfsupply.com/public/api/recovery_password";

    EditText email_to_reset;
    Button passReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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


        email_to_reset = (EditText)findViewById(R.id.edit_text_reset_pass);
        passReset = (Button)findViewById(R.id.button_reset_pass);

        passReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TascRecoveryPassword tascRecoveryPassword = new TascRecoveryPassword();
                tascRecoveryPassword.execute();

            }
        });

    }


    @SuppressLint("StaticFieldLeak")
    private class TascRecoveryPassword extends AsyncTask<String, String, ResponseAfterRecoveryPass> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseAfterRecoveryPass doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            String recover_pass = email_to_reset.getText().toString().trim();

            PostRecoveryPassword postRecoveryPassword = new PostRecoveryPassword(recover_pass);

            try {

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postRecoveryPassword));

                Request request = new Request.Builder()
                        .url(RECOVER_PASS_URL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                okhttp3.Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DEBUG : ", responseBody);

                Gson gsonResponseAfterRecoveryPass = new Gson();
                ResponseAfterRecoveryPass responseAfterRecoveryPass = gsonResponseAfterRecoveryPass.fromJson(responseBody, ResponseAfterRecoveryPass.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseAfterRecoveryPass;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResponseAfterRecoveryPass responseAfterRecoveryPass) {
            super.onPostExecute(responseAfterRecoveryPass);

            if (responseAfterRecoveryPass != null) {
                Toast.makeText(getBaseContext(), responseAfterRecoveryPass.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    }


}
