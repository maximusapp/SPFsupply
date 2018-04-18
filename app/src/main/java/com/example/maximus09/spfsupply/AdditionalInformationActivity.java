package com.example.maximus09.spfsupply;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AdditionalInformationActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private int GALLERY_REQUEST = 1;

    public EditText editText_phone;
    public EditText editText_business_address;
    public EditText editText_delivery_address;

    Button buttonProceed;

    ImageView imageView;

   public File file_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_information);
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

        editText_phone = (EditText)findViewById(R.id.phon_edit_text_signup);
        editText_business_address = (EditText)findViewById(R.id.business_address_edit_signup);
        editText_delivery_address = (EditText)findViewById(R.id.edit_delivery_adress);

        // Handle add company logo button
        imageView = (ImageView)findViewById(R.id.add_company_logo);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

            }
        });

        buttonProceed = (Button)findViewById(R.id.button_proceed_additional);
        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = editText_phone.toString();
                String address = editText_business_address.toString();
                String delivery = editText_delivery_address.toString();

                if (phone.isEmpty()) {
                    editText_phone.setError("Enter phone Number");
                } else
                    if (address.isEmpty()) {
                    editText_business_address.setError("Enter business address");
                    } else
                        if (delivery.isEmpty()) {
                    editText_delivery_address.setError("Enter delivery address");
                        } if (!phone.isEmpty() && !address.isEmpty() && !delivery.isEmpty()) {


                    Intent intentProceed = new Intent(AdditionalInformationActivity.this, PaymentMethodActivity.class);
                    // Pass data from 2-d act to 3-d act
                    intentProceed.putExtra("phone", phone);
                    intentProceed.putExtra("address", address);
                    intentProceed.putExtra("delivery_address", delivery);

                    // If we not select image, send null on server
                    if (file_path != null)
                    intentProceed.putExtra("image_link", file_path.getAbsolutePath());

                    // Get data from first activity
                    String company_name = getIntent().getStringExtra("companyName");
                    String email = getIntent().getStringExtra("email");
                    String owner = getIntent().getStringExtra("owner");
                    String pass = getIntent().getStringExtra("password");
                    String conf_pass = getIntent().getStringExtra("confirmPassword");

                    // Pass data from 1-t act to 3-d act
                    intentProceed.putExtra("com_name", company_name);
                    intentProceed.putExtra("mailta", email);
                    intentProceed.putExtra("owners", owner);
                    intentProceed.putExtra("pass", pass);
                    intentProceed.putExtra("conf", conf_pass);

                    startActivity(intentProceed);

//                   PostMulti postMulti = new PostMulti();
//                   postMulti.execute(file_path.getAbsolutePath());

                }

            }

        });

    }

    @Override
    protected final void onActivityResult(int requestCode, int resultCode, final Intent data) {

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
                imageView.setImageBitmap(selectedImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(AdditionalInformationActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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


//    public static class PostMulti extends AsyncTask<String, String, Void> {
//
//        @Override
//        protected Void doInBackground(String... strings) {
//
//           final MediaType MEDIA_TYPE = MediaType.parse("image/*");
//           final OkHttpClient okHttpClient = new OkHttpClient();
//
//            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
//                   .addPart(
//                           Headers.of("Content-Disposition", "form-data; name=\"company_logo\""),
//                           RequestBody.create(MEDIA_TYPE, new File(strings[0]))
//                   )
//                   .build();
//
//            Request request = new Request.Builder()
//                    .url("http://spf.yobibyte.in.ua/api/sign_up")
//                    .post(requestBody)
//                    .build();
//
//            try {
//                Response response = okHttpClient.newCall(request).execute();
//
//                if (!response.isSuccessful()) {
//                    throw new IOException("Error : " + request);
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//            return null;
//        }
//    }


}
