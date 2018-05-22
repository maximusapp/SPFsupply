package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maximus09.spfsupply.data.model.Post;
import com.example.maximus09.spfsupply.data.model.ResponseFromServer;
import com.example.maximus09.spfsupply.util.Preference;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignInActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    public static final String LOGIN_URL = "http://api.spfsupply.com/public/api/sign_in";

    TextView textResetPassword;

    private EditText editEmail;
    private EditText editPass;

    Button signIn;

    String message_from_server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sec);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set font style for title ActionBar
        TextView tv=(TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);

        editEmail = (EditText)findViewById(R.id.edit_email_signin);
        editPass = (EditText)findViewById(R.id.edit_password_signin);

        signIn = (Button)findViewById(R.id.button_sign_in);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mail = editEmail.getText().toString().trim();
                String pass = editPass.getText().toString().trim();

                String expression ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

                if (mail.matches(expression)) {
                    Toast.makeText(getApplicationContext(), "Email and Password correct", Toast.LENGTH_SHORT).show();
                }

                if (!mail.matches(expression)) {
                    Toast.makeText(getApplicationContext(), "Email or Password incorrect", Toast.LENGTH_LONG).show();
                }


                if (mail.isEmpty()) {
                    editEmail.setHint("Enter email");
                    editEmail.setHintTextColor(getResources().getColor(R.color.colorAccent));
                } else
                if (pass.isEmpty()) {
                    editPass.setHint("Enter password");
                    editPass.setHintTextColor(getResources().getColor(R.color.colorAccent));
                }
                    TaskLogin taskLogin = new TaskLogin();
                    taskLogin.execute();
            }
        });

        textResetPassword = (TextView)findViewById(R.id.text_reset_password);
        textResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResetPassword = new Intent(SignInActivity.this, PasswordResetActivity.class);
                startActivity(intentResetPassword);
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private class TaskLogin extends AsyncTask<String, String, ResponseFromServer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseFromServer doInBackground(String... strings) {

            InstanceID instanceID = InstanceID.getInstance(SignInActivity.this);
            String token = null;
            try {
                token = instanceID.getToken("641074772817", GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
                Log.i("token", " " + token);

            } catch (IOException e) {
                e.printStackTrace();
            }

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            String mail = editEmail.getText().toString().trim();
            String pass = editPass.getText().toString().trim();

            Post strPostLogin = new Post(mail, pass, token);

                try {
                    RequestBody body = RequestBody.create(MediaType.parse("application/json"), gson.toJson(strPostLogin));

                    Request request = new Request.Builder()
                            .url(LOGIN_URL)
                            .post(body)
                            .addHeader("Content-Type", "application/json")
                            .build();

                    Response response = okHttpClient.newCall(request).execute();

                    @SuppressWarnings("ConstantConditions")
                    String responseBody = response.body().string();
                    Log.i("DEBUG", responseBody);

                    Gson gsonFromServer = new Gson();
                    ResponseFromServer responseFromServer = gsonFromServer.fromJson(responseBody, ResponseFromServer.class);


                    Preference preference = new Preference(getApplicationContext());
                    preference.setToken(responseFromServer.getToken());

                    // added
                    int responseCode = response.code();
                    if(responseCode == 200 && responseBody.length() != 0) {
                        return responseFromServer;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

           return null;
        }

        @Override
        protected void onPostExecute(ResponseFromServer result) {
            super.onPostExecute(result);

            if (result != null && "admin".equalsIgnoreCase(result.getAccount_type())){
                Intent intentAdmin = new Intent(SignInActivity.this, ManufacturesActivity.class);
                startActivity(intentAdmin);
            } else
            if (result != null && "user".equalsIgnoreCase(result.getAccount_type())) {
                Intent intentUser = new Intent(SignInActivity.this, HomeActivity.class);
                startActivity(intentUser);
            }


          }

        }

    }