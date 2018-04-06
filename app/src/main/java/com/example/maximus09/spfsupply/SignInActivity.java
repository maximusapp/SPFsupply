package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SignInActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    public final String TAG = ".SignInActivity";
    public static final String LOGIN_URL = "http://spf.yobibyte.in.ua/api/sign_in/";



    TextView textResetPassword;

    static  EditText editEmail;
    static EditText editPass;

    Button signIn;

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



//                Gson gson = new Gson();
//                Post strPostLogin = new Post("admin@gmail.com", "Pass1234");
//
//                String url = LOGIN_URL + "Login";
//                try {
//                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(strPostLogin));
//
//                    Request request = new Request.Builder()
//                            .url(url)
//                            .post(body)
//                            .addHeader("Content-Type", "application/json")
//                            //.addHeader("Authkey", "9f8cb60cd52f9dd20f8eae4260fa7228")
//                            .build();
//
//
//                    okhttp3.Response response = client.newCall(request).execute();
//                   // Response response = client.newCall(request).execute();
//                    int responseCode = response.code();
//                    @SuppressWarnings("ConstantConditions") String responseBody = response.body().string();
//                    Log.i("DEBUG", responseBody);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//

//======================================================================

//                Retrofit retrofit = new Retrofit.Builder()
//                        .baseUrl(LOGIN_URL)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//
//                SpfAPI spfAPI = retrofit.create(SpfAPI.class);
//
//                HashMap<String, String> headerMap = new HashMap<>();
//                headerMap.put("Content-Type", "application/json");
//
//                Call<ResponseBody> call = spfAPI.signin(headerMap, mail, pass);
//
//                call.enqueue(new Callback<ResponseBody>() {
//                    @Override
//                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
//                        Log.d(TAG, "onResponse: Okey: " + response.toString());

//                        try {
//                            String responseBody = response.body().string();
//                            Log.i("DEBUG", responseBody);
//
//                            Log.d(TAG, "onResponse: json: " + responseBody);
//                            JSONObject data = null;
//                            data = new JSONObject(responseBody);
//                            Log.d(TAG, "onResponse: data: " + data);
//                        }catch (JSONException e){
//                            Log.e(TAG, "onResponse: JsonException: " + e.getMessage());
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Log.e(TAG, "onFailure: Something went wrong: " + t.getMessage() );
//                        Toast.makeText(SignInActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                    }
//                });

//                Intent intent = new Intent(SignInActivity.this, ManufacturesActivity.class);
//                startActivity(intent);
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
    private class TaskLogin extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            String mail = editEmail.getText().toString().trim();
            String pass = editPass.getText().toString().trim();

            Post strPostLogin = new Post(mail, pass);
               // String url = LOGIN_URL + "Login";
                try {
                    RequestBody body = RequestBody.create(MediaType.parse("application/json"), gson.toJson(strPostLogin));

                    Request request = new Request.Builder()
                            .url(LOGIN_URL)
                            .post(body)
                            .addHeader("Content-Type", "application/json")
                            .build();

                    okhttp3.Response response = okHttpClient.newCall(request).execute();
                   // Response response = client.newCall(request).execute();
                  //  int responseCode = response.code();
                    @SuppressWarnings("ConstantConditions") String responseBody = response.body().string();
                    Log.i("DEBUG", responseBody);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Gson gson = new Gson();
            ResponseFromServer responseFromServer = gson.fromJson(result, ResponseFromServer.class);

            if (responseFromServer != null && responseFromServer.getAccountType() != null) {
                if (responseFromServer.getAccountType().equalsIgnoreCase("admin")) {
                    Intent intentAdmin = new Intent(SignInActivity.this, HomeActivity.class);
                    startActivity(intentAdmin);
                } else
                    if (responseFromServer.getAccountType().equalsIgnoreCase("user")) {
                    Intent intentUser = new Intent(SignInActivity.this, ManufacturesActivity.class);
                    startActivity(intentUser);
                    }
            }

        }

    }

}