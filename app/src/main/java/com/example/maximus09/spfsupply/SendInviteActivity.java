package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maximus09.spfsupply.data.model.PostInvite;
import com.example.maximus09.spfsupply.util.Preference;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.ref.Reference;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SendInviteActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    //Server url to invite user to app
    private static final String INVITE_BUYERS_BY_EMAIL = "http://api.spfsupply.com/public/api/buyers/invite_by_email";

    EditText edit_email;
    EditText edit_message;

    Button button_send_invite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_invite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set font style for title ActionBar
        TextView tv=(TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);

        edit_email = (EditText)findViewById(R.id.edit_text_send_invite);
        edit_message = (EditText)findViewById(R.id.edit_text_message_invite);

        button_send_invite = (Button)findViewById(R.id.send_invite_button);
        button_send_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = edit_email.getText().toString().trim();
                String message = edit_message.getText().toString().trim();

                if (mail.isEmpty()) {
                    edit_email.setError("Enter email");
                }
                if (message.isEmpty()) {
                    edit_message.setError("Enter invitation text");
                }

                InviteBuyers inviteBuyers = new InviteBuyers();
                inviteBuyers.execute(mail, message);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intentSignUp = new Intent(SendInviteActivity.this, BuyersActivity.class);
                        startActivity(intentSignUp);
                    }
                }, 2000);


            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    private class InviteBuyers extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Preference preference = new Preference(getApplication());
            PostInvite postInvite = new PostInvite(preference.getToken(), strings[0], strings[1]);

            try {

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postInvite));

                Request request = new Request.Builder()
                        .url(INVITE_BUYERS_BY_EMAIL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("Response Invite", responseBody);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send_invite_menu, menu);
        menu.findItem(R.id.action_send_invite_with_sms).setTitle((Html.fromHtml("<font color='#2196f3'><u>Invite with SMS</u></font>")));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send_invite_with_sms) {

            Toast.makeText(this, "Pressed SMS button", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
