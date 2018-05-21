package com.example.maximus09.spfsupply;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maximus09.spfsupply.data.model.GetBuyersById;
import com.example.maximus09.spfsupply.data.model.PostBuyersEdit;
import com.example.maximus09.spfsupply.data.model.PostDeleteBuyers;
import com.example.maximus09.spfsupply.data.model.ResponseBuyersInformation;
import com.example.maximus09.spfsupply.util.Preference;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ProfileActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    SectionsPagerAdapter adapter;

    private static final String GET_BUYERS_BY_ID_URL = "http://api.spfsupply.com/public/api/buyers/get_by_id";
    private static final String DELETE_BUYERS_URL = "http://api.spfsupply.com/public/api/buyers/delete";
    private static final String BUYERS_EDIT = "http://api.spfsupply.com/public/api/buyers/edit";


    List<ResponseBuyersInformation.Permissions> permissionsList;


    AlertDialog.Builder alert;

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

        adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BuyerInformationFragment(), getString(R.string.buyer_information));
        adapter.addFragment(new PermissionsFragment(), getString(R.string.buyer_permissions));
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

            BuyerInformationFragment buyerInformationFragment = (BuyerInformationFragment) adapter.getItem(0);
            PermissionsFragment permissionsFragment = (PermissionsFragment)adapter.getItem(1);

            Intent intentExtras = getIntent();
            String buyerId = intentExtras.getStringExtra("BUYERS_ID");

            Preference preference = new Preference(getApplication());

            buyerInformationFragment.getImagePath();
            buyerInformationFragment.GetCompanyName();
            buyerInformationFragment.GetMail();
            buyerInformationFragment.GetOwner();
            buyerInformationFragment.GetPhone();
            buyerInformationFragment.GetBussinessAddress();
            buyerInformationFragment.GetDeliveryAddress();
            buyerInformationFragment.GetPaymentMethod();


            permissionsList = permissionsFragment.getPermissFromAdapter();

            BuyersEdit buyersEdit = new BuyersEdit();
            buyersEdit.execute(
                    preference.getToken(),
                    buyerId,
                    buyerInformationFragment.getImagePath(),
                    buyerInformationFragment.GetCompanyName(),
                    buyerInformationFragment.GetMail(),
                    buyerInformationFragment.GetOwner(),
                    buyerInformationFragment.GetPhone(),
                    buyerInformationFragment.GetBussinessAddress(),
                    buyerInformationFragment.GetDeliveryAddress(),
                    buyerInformationFragment.GetPaymentMethod()

            );

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intentCreate = new Intent(ProfileActivity.this, BuyersActivity.class);
                    startActivity(intentCreate);
                    finish();
                }
            }, 2000);

           // Toast.makeText(this, "Pressed Check button", Toast.LENGTH_SHORT).show();
            return true;
        }

        //For Alert Dialog
        String title = "Delete Buyer";
        String message = "Are you sure that you want to remove this buyer?";
        String button1 = "Yes";
        String button2 = "No";

        if (id == R.id.action_delete) {

            alert = new AlertDialog.Builder(this);
            alert.setTitle(title);
            alert.setMessage(message);
            alert.setPositiveButton(button1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    DeleteBuyers deleteBuyers = new DeleteBuyers();
                    deleteBuyers.execute();

                    Toast.makeText(getApplicationContext(), "Pressed delete button", Toast.LENGTH_SHORT).show();
                }
            });

            alert.setNegativeButton(button2, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    alert.setCancelable(true);
                    Toast.makeText(getApplicationContext(), "You canceled delete", Toast.LENGTH_SHORT).show();
                }
            });

            alert.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteBuyers extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Intent intentExtras = getIntent();
            String buyerId = intentExtras.getStringExtra("BUYERS_ID");

            Preference preference = new Preference(getApplicationContext());
            PostDeleteBuyers postDeleteBuyers = new PostDeleteBuyers(preference.getToken(), buyerId);

            try {
                RequestBody requesBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postDeleteBuyers));

                Request request = new Request.Builder()
                        .url(DELETE_BUYERS_URL)
                        .post(requesBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DELETED? ", responseBody);

                return responseBody;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Intent intent = new Intent(getApplicationContext(), BuyersActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class BuyersEdit extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            final MediaType MEDIA_TYPE_IMAGE = MediaType.parse("image/*");

            //Gson gson = new Gson();
            OkHttpClient okHttpClient = new OkHttpClient();

            Map<String, String> map = new HashMap<String, String>();

            try {


//                for (int i = 0; i < permissionsList.size(); i++) {
//                    //s += "\"" + permissionsList.get(i).getId() + "\":\"" + permissionsList.get(i).getChecked() + "\",";
//                    //noinspection MismatchedQueryAndUpdateOfCollection
//                    map.put(permissionsList.get(i).getId(), permissionsList.get(i).getChecked());
////                map.put("45", "0");
////                map.put("46", "0");
////                map.put("47", "1");
////                map.put("48", "1");
////                map.put("49", "1");
//                }


                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("token", strings[0])
                        .addFormDataPart("buyers_id", strings[1])
                        .addFormDataPart("company_name", strings[3])
                        .addFormDataPart("email", strings[4])
                        .addFormDataPart("manager_name", strings[5])
                        .addFormDataPart("phone_number", strings[6])
                        .addFormDataPart("business_address", strings[7])
                        .addFormDataPart("delivery_address", strings[8]);
                        //.addFormDataPart("permissions_data", String.valueOf(new JSONObject(map)));


                if (strings[2] != null) {
                    builder.addFormDataPart("company_logo", "logo.png", RequestBody.create(MEDIA_TYPE_IMAGE, new File(strings[2])));
                }


                Log.d("PERMISS", String.valueOf(new JSONObject(map)));

               /* String s = "{"; // "[";
                for (int i = 0; i < permissionsList.size(); i++) {
                    s += "" + permissionsList.get(i).getId() + ":" + permissionsList.get(i).getChecked() + ",";
                }
                s = s.substring(0, s.length() - 1);
                s += "}";

                Log.d("TOKEN", strings[0]);
                Log.d("B_ID", strings[1]);
                //Log.d("IMAGE", strings[2]);
                Log.d("COM_NAME", strings[3]);
                Log.d("MAIL", strings[4]);
                Log.d("manager_name", strings[5]);
                Log.d("phone_number", strings[6]);
                Log.d("business_address", strings[7]);
                Log.d("delivery_address", strings[8]);

                Log.d("STRING_LOG", s);


               // builder.addFormDataPart("permissions_data", s);*/

                for (int i = 0; i < permissionsList.size(); i++) {

                    builder.addFormDataPart("permissions_data["+permissionsList.get(i).getId()+"]", permissionsList.get(i).getChecked() );
                }

                if (strings[1] == null || strings[1].length() == 0) {
                    builder.addFormDataPart("email", strings[1]);
                }

                RequestBody requestBody = builder.build();

                Request request = new Request.Builder()
                        .url(BUYERS_EDIT)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();


                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DEBUG_EDIT_BUYERS", responseBody);

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

}



//            try{
//                infoClass.put("companyName", strings[0]);
//                infoClass.put("companyMail", strings[1]);
//                infoClass.put("companyOwner", strings[2]);
//                infoClass.put("companyPhone", strings[3]);
//                infoClass.put("companyBusAddr", strings[4]);
//                infoClass.put("companyDelAddr", strings[5]);
//                infoClass.put("companyPayMeth", strings[6]);
//
//
//                for (int i = 0; i < permissionsList.size(); i++) {
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put(permissionsList.get(i).getId(), permissionsList.get(i).getChecked());
//                    permissArray.put(jsonObject);
//                }
//
//                infoClass.put("permissions_data", permissArray);
//                infoClass.put("token",preference.getToken());
//                infoClass.put("buyers_id", buyerId);
//
//                json = infoClass.toString();
//                Log.d("classInJson", json);
//
//
//                RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
//
//                Request request = new Request.Builder()
//                        .url(BUYERS_EDIT)
//                        .post(body)
//                        .addHeader("Content-Type", "application/json")
//                        .build();
//
//                okhttp3.Response response = okHttpClient.newCall(request).execute();
//
//                @SuppressWarnings("ConstantConditions")
//                String responseBody = response.body().string();
//                Log.i("RESPONSE_EDIT_DATA ", responseBody);
//
//