package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.example.maximus09.spfsupply.data.model.GetManufactureById;
import com.example.maximus09.spfsupply.data.model.ResponseAllManufacturers;
import com.example.maximus09.spfsupply.data.model.ResponseManufactureInfoAdmin;
import com.example.maximus09.spfsupply.util.Preference;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InformationActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private static final String GET_MANUF_BY_ID = "http://spf.yobibyte.in.ua/api/manufacturers/get_by_id/";

    ImageView imageView;
    TextView textView;

    //Image and text INFORMATION
    ImageView imageView_logo;
    TextView textView_companyName;
    TextView textView_location;
    TextView textView_webSite;

    RecyclerView recyclerView;
    ItemListInformationAdminItemAdapter itemListInformationAdminItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sec);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        GetManufacturerById getManufacturerById = new GetManufacturerById();
        getManufacturerById.execute();


        //set font style for title ActionBar
        TextView tv=(TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);

        imageView = (ImageView)findViewById(R.id.add_productInformationActivity);
        textView = (TextView)findViewById(R.id.add_productInformationActivityText);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InformationActivity.this, NewProductActivity.class);
                startActivity(intent);
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




        //Find information of Manufacturer
        imageView_logo = (ImageView)findViewById(R.id.image_information_admin);
        textView_companyName = (TextView)findViewById(R.id.textName_information_admin);
        textView_location = (TextView)findViewById(R.id.text_company_location_info_admin);
        textView_webSite = (TextView)findViewById(R.id.web_site_text_admin);


        recyclerView = (RecyclerView)findViewById(R.id.recycler_information_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemListInformationAdminItemAdapter = new ItemListInformationAdminItemAdapter(null, this);
        recyclerView.setAdapter(itemListInformationAdminItemAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_information, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit_information) {
            Toast.makeText(this, "Pressed EDIT button", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("StaticFieldLeak")
    public class GetManufacturerById extends AsyncTask<String, String, ResponseManufactureInfoAdmin> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseManufactureInfoAdmin doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            //Get StringExtras from ManufacturersActivity.class
            Intent intentExtras = getIntent();
            String manufacturers_id = intentExtras.getStringExtra("manufacturers_id");

            Log.d("MANUF_ID", manufacturers_id);

            Preference preference = new Preference(getApplicationContext());
            GetManufactureById getManufactureById = new GetManufactureById(manufacturers_id, preference.getToken());

            try {
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(getManufactureById));

                Request request = new Request.Builder()
                        .url(GET_MANUF_BY_ID)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DATA_OF_MANUFACTURE : ", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseManufactureInfoAdmin responseManufactureInfoAdmin = gsonFromServer.fromJson(responseBody, ResponseManufactureInfoAdmin.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseManufactureInfoAdmin;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResponseManufactureInfoAdmin responseManufactureInfoAdmin) {
            super.onPostExecute(responseManufactureInfoAdmin);
            itemListInformationAdminItemAdapter.updateListManufacturer(responseManufactureInfoAdmin.getManufacturers_data().getProducts());

            Glide.with(getApplicationContext()).load(responseManufactureInfoAdmin.getManufacturers_data().getLogo()).into(imageView_logo);

            textView_companyName.setText(responseManufactureInfoAdmin.getManufacturers_data().getCompany_name());
            textView_location.setText(responseManufactureInfoAdmin.getManufacturers_data().getLocation());
            textView_webSite.setText(responseManufactureInfoAdmin.getManufacturers_data().getWebsite());

        }
    }


}
