package com.example.maximus09.spfsupply;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maximus09.spfsupply.data.model.PostCreateNewManufacture;
import com.example.maximus09.spfsupply.util.Preference;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewManufacturerActivity extends AppCompatActivity {

    private static final String CREATE_NEW_MANUFACT = "http://spf.yobibyte.in.ua/api/manufacturers/create/";

    private int GALLERY_REQUEST = 1;
    public File file_path;

    //Edit areas to send on Server
    ImageView image_logo;
   public EditText company_name;
   public EditText location;
   public EditText website;

    Switch aSwitch;

    TextView text_tax_amount;
    EditText edit_add_amount;
    View view;

    TextView text_shipping;
    EditText edit_shipping;
    View view1;

    TextView text_fee;
    EditText edit_add_amount_fee;

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_manufacturer);
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

        image_logo = (ImageView)findViewById(R.id.icon_new_manuf);
        image_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

            }
        });


        company_name = (EditText)findViewById(R.id.text_enter_company);
        location = (EditText)findViewById(R.id.edit_location);
        website = (EditText)findViewById(R.id.edit_text_enter_website);

        //Find SWITCH
        aSwitch = (Switch)findViewById(R.id.switch_new_manufacturer);

        text_tax_amount = (TextView)findViewById(R.id.text_tax_amount);
        edit_add_amount = (EditText)findViewById(R.id.edit_add_amount);
        view = (View)findViewById(R.id.view6);

        text_shipping = (TextView)findViewById(R.id.text_shiping_cost);
        edit_shipping = (EditText)findViewById(R.id.edit_shiping_cost);
        view1 = (View)findViewById(R.id.view7);

        text_fee = (TextView)findViewById(R.id.text_fee);
        edit_add_amount_fee = (EditText)findViewById(R.id.edit_add_amount_fee);

        text_tax_amount.setVisibility(View.INVISIBLE);
        edit_add_amount.setVisibility(View.INVISIBLE);
        view.setVisibility(View.INVISIBLE);

        text_shipping.setVisibility(View.INVISIBLE);
        edit_shipping.setVisibility(View.INVISIBLE);
        view1.setVisibility(View.INVISIBLE);

        text_fee.setVisibility(View.INVISIBLE);
        edit_add_amount_fee.setVisibility(View.INVISIBLE);


        aSwitch.setChecked(false);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (aSwitch.isChecked()) {
                    text_tax_amount.setVisibility(View.VISIBLE);
                    edit_add_amount.setVisibility(View.VISIBLE);
                    view.setVisibility(View.VISIBLE);

                    text_shipping.setVisibility(View.VISIBLE);
                    edit_shipping.setVisibility(View.VISIBLE);
                    view1.setVisibility(View.VISIBLE);

                    text_fee.setVisibility(View.VISIBLE);
                    edit_add_amount_fee.setVisibility(View.VISIBLE);
                } else {
                    text_tax_amount.setVisibility(View.INVISIBLE);
                    edit_add_amount.setVisibility(View.INVISIBLE);
                    view.setVisibility(View.INVISIBLE);

                    text_shipping.setVisibility(View.INVISIBLE);
                    edit_shipping.setVisibility(View.INVISIBLE);
                    view1.setVisibility(View.INVISIBLE);

                    text_fee.setVisibility(View.INVISIBLE);
                    edit_add_amount_fee.setVisibility(View.INVISIBLE);
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission
                    .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                return;
            }
        }

        if(resultCode == RESULT_OK && requestCode == GALLERY_REQUEST) {

            try {
                final Uri imageUri = data.getData();
                final File file = new File(getRealPathFromURI(this, imageUri));
                file_path = file;

                Log.d("URI OF IMAGE ", getRealPathFromURI(this, imageUri));

                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                image_logo.setImageBitmap(selectedImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(NewManufacturerActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

            super.onActivityResult(requestCode, resultCode, data);
        }


    }


    // We get correct URI path of image in storage of phone
    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            @SuppressWarnings("ConstantConditions")
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_manufacturer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        String companyes_name = company_name.getText().toString().trim();
        String locations = location.getText().toString().trim();
        String websites = website.getText().toString().trim();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done_new_manufacturer) {

            if (companyes_name.isEmpty()) {
                company_name.setError("Enter company name");
            } else
                if (locations.isEmpty()) {
                location.setError("Enter location");
                } else
                    if (websites.isEmpty()) {
                website.setError("Enter website");
                    }

            TascCreateNewManufacture tascCreateNewManufacture = new TascCreateNewManufacture();
            tascCreateNewManufacture.execute(file_path.getAbsolutePath(), companyes_name, locations, websites);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("StaticFieldLeak")
    private class TascCreateNewManufacture extends AsyncTask<String, String, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {

            final MediaType MEDIA_TYPE = MediaType.parse("image/*");

            OkHttpClient okHttpClient = new OkHttpClient();
           // Gson gson = new Gson();

            String logo_link = file_path.getAbsolutePath();

            String com_name = company_name.getText().toString().trim();
            String loc = location.getText().toString().trim();
            String web = website.getText().toString().trim();
            String amoun = edit_add_amount.getText().toString().trim();
            String shipp = edit_shipping.getText().toString().trim();
            String fee = edit_add_amount_fee.getText().toString().trim();

            Preference preference = new Preference(getApplicationContext());
            PostCreateNewManufacture postCreateNewManufacture = new PostCreateNewManufacture(preference.getToken(),
                    com_name, loc, web, amoun, shipp, fee, logo_link);

            try {

                //RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postCreateNewManufacture));

                RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addPart(
                                Headers.of("Content-Disposition", "form-data; name=\"logo\""),
                                RequestBody.create(MEDIA_TYPE, new File(strings[0]))
                        ).build();


                Request request = new Request.Builder()
                        .url(CREATE_NEW_MANUFACT)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DEBUG_CREATE", responseBody);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


    }



}
