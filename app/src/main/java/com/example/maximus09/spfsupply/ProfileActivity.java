package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maximus09.spfsupply.data.model.GetBuyersById;
import com.example.maximus09.spfsupply.util.Preference;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private static final String GET_BUYERS_BY_ID_URL = "http://spf.yobibyte.in.ua/api/buyers/get_by_id/";

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sec);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        GetProfileById getProfileById = new GetProfileById();
        getProfileById.execute();


        //set font style for title ActionBar
        TextView tv=(TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager)findViewById(R.id.conteiner);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    @SuppressLint("StaticFieldLeak")
    public class GetProfileById extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Intent intentExtras = getIntent();
            String buyersId = intentExtras.getStringExtra("BUYERS_ID");
            Log.d("ID_IS", buyersId);

            Preference preference = new Preference(getApplicationContext());
            GetBuyersById getBuyersById = new GetBuyersById(preference.getToken(), buyersId);

            try {
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(getBuyersById));

                Request request = new Request.Builder()
                        .url(GET_BUYERS_BY_ID_URL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DATA_OF_BUYERS ", responseBody);

                return responseBody;

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

            Intent intent = new Intent("Profile");
            intent.putExtra("cmd", "data");
            intent.putExtra("key", s); //вместо json можешь передать нужную структуру
            sendBroadcast(intent);


        }
    }


    private void setupViewPager (ViewPager viewPager) {

        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BuyerInformationFragment(), getString(R.string.buyer_information));
        adapter.addFragment(new PermissionsFragment(), "Permissions");
        viewPager.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_check) {
            Toast.makeText(this, "Pressed Check button", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.action_delete) {
            Toast.makeText(this, "Pressed Delete button", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
