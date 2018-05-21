package com.example.maximus09.spfsupply;

import android.*;
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
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.maximus09.spfsupply.data.model.PostGetBuyersInfo;
import com.example.maximus09.spfsupply.data.model.ResponseBuyersInfo;
import com.example.maximus09.spfsupply.data.model.ResponseBuyersInformation;
import com.example.maximus09.spfsupply.data.model.ResponseEditProfile;
import com.example.maximus09.spfsupply.util.Preference;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileHomeActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private static final String GET_INFO_USER_URL = "http://api.spfsupply.com/public/api/buyers/get_info";
    private static final String EDIT_DATA_PROFILE_URL = "http://api.spfsupply.com/public/api/buyers/edit";

    String buyers_id;

//    String currentPassword;

    TextView tvChangePassword;
    String currentPass;
    String newPass;

    ImageView imageView;
    EditText tvCompName;
    EditText tvEmail;
    EditText tvOwner;
    EditText tvPhone;
    EditText tvBussAddress;
    EditText tvDelivAddress;
    TextView tvPaymMethod;

    ImageView ivAddPaymentMethod;
    TextView tvAddPaymentMethod;

    private int GALLERY_REQUEST = 1;
    public File file_path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_home);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvChangePassword = (TextView)findViewById(R.id.company_change_pass_profile);
        tvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog();
            }
        });

        imageView = (ImageView)findViewById(R.id.ivLogoUserProfile);
        tvCompName = (EditText)findViewById(R.id.company_name_profile_user);
        tvEmail = (EditText)findViewById(R.id.email_profile_user);
        tvOwner = (EditText)findViewById(R.id.owner_profile_user);
        tvPhone = (EditText)findViewById(R.id.phone_profile_user);
        tvBussAddress = (EditText)findViewById(R.id.business_adr_profileUser);
        tvDelivAddress = (EditText)findViewById(R.id.delivery_adr_profileUser);
        tvPaymMethod = (TextView) findViewById(R.id.payment_method_profileUser);

        ivAddPaymentMethod = (ImageView)findViewById(R.id.user_add_payment_method_image);
        tvAddPaymentMethod = (TextView)findViewById(R.id.user_add_payment_method_text);

        // Add payment method.
        ivAddPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(ProfileHomeActivity.this, AddPaymentMethodUserActivity.class);
                startActivity(intent1);
            }
        });

        // Add payment method.
        tvAddPaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileHomeActivity.this, AddPaymentMethodUserActivity.class);
                startActivity(intent);
            }
        });



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

            }
        });


        GetBuyersInfo getBuyersInfo = new GetBuyersInfo();
        getBuyersInfo.execute();

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
    }


    // Dialog for change password.
    private void ShowDialog() {
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(ProfileHomeActivity.this);
        View promptsView = getLayoutInflater().inflate(R.layout.change_password, null);
        final EditText curentPassword = (EditText)promptsView.findViewById(R.id.enter_current_pass);
        final EditText newPassword = (EditText)promptsView.findViewById(R.id.enter_new_pass);
        Button cancel = (Button)promptsView.findViewById(R.id.cancel);
        Button apply = (Button)promptsView.findViewById(R.id.apply);

        mDialogBuilder.setView(promptsView);
        final AlertDialog alertDialog = mDialogBuilder.create();
        alertDialog.show();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    currentPass = curentPassword.getText().toString();
                    newPass = newPassword.getText().toString();

                alertDialog.dismiss();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission
                    .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
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
                Toast.makeText(ProfileHomeActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
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
        getMenuInflater().inflate(R.menu.profile_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home_check) {

            String companyName = tvCompName.getText().toString();
            String email = tvEmail.getText().toString();
            String owner = tvOwner.getText().toString();
            String phone = tvPhone.getText().toString();
            String businessAddress = tvBussAddress.getText().toString();
            String deliveryAddress = tvDelivAddress.getText().toString();

            String logo_link = file_path == null ? null : file_path.getAbsolutePath();

            Preference preference = new Preference(getApplicationContext());
            TascEditProfile tascEditProfile = new TascEditProfile();
            tascEditProfile.execute(preference.getToken(), buyers_id, email, companyName, newPass,
                    currentPass, phone, businessAddress, deliveryAddress, owner, logo_link);

           // Toast.makeText(this, "Pressed CHECK button", Toast.LENGTH_SHORT).show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("StaticFieldLeak")
    private class GetBuyersInfo extends AsyncTask<String, String, ResponseBuyersInfo>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseBuyersInfo doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Preference preference = new Preference(getApplicationContext());
            PostGetBuyersInfo postGetBuyersInfo = new PostGetBuyersInfo(preference.getToken());

            try {
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postGetBuyersInfo));

                Request request = new Request.Builder()
                        .url(GET_INFO_USER_URL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DATA_OF_BUYERS ", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseBuyersInfo responseBuyersInfo = gsonFromServer.fromJson(responseBody, ResponseBuyersInfo.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseBuyersInfo;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResponseBuyersInfo responseBuyersInfo) {
            super.onPostExecute(responseBuyersInfo);

            if (responseBuyersInfo.getBuyers_data().getCompany_logo() != null) {
                Glide.with(getApplicationContext()).load(responseBuyersInfo.getBuyers_data().getCompany_logo()).into(imageView);
            } else {
                imageView.setImageResource(R.drawable.shape_buyers);
            }

            buyers_id = responseBuyersInfo.getBuyers_data().getId();

            tvCompName.setText(responseBuyersInfo.getBuyers_data().getCompany_name());
            tvEmail.setText(responseBuyersInfo.getBuyers_data().getEmail());
            tvOwner.setText(responseBuyersInfo.getBuyers_data().getManager_name());
            tvPhone.setText(responseBuyersInfo.getBuyers_data().getPhone_number());
            tvBussAddress.setText(responseBuyersInfo.getBuyers_data().getBusiness_address());
            tvDelivAddress.setText(responseBuyersInfo.getBuyers_data().getDelivery_address());


        }
    }

    @SuppressLint("StaticFieldLeak")
    private class TascEditProfile extends AsyncTask<String, String, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            final MediaType MEDIA_TYPE_IMAGE = MediaType.parse("image/*");
            OkHttpClient okHttpClient = new OkHttpClient();

            try {

//                tascEditProfile.execute(preference.getToken(), buyers_id, email, companyName, currentPass,
//                        newPass, phone, businessAddress, deliveryAddress, owner, logo_link);

                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("token", strings[0])
                        .addFormDataPart("buyers_id", strings[1]);

                if (strings[2] != null){
                    builder.addFormDataPart("email", strings[2]);
                }
                if (strings[3] != null) {
                    builder.addFormDataPart("company_name", strings[3]);
                }
                if (strings[4] != null) {
                    builder.addFormDataPart("password", strings[4]);
                }
                if (strings[5] != null) {
                    builder.addFormDataPart("last_password", strings[5]);
                }
                if (strings[6] != null) {
                    builder.addFormDataPart("phone_number", strings[6]);
                }
                if (strings[7] != null) {
                    builder.addFormDataPart("business_address", strings[7]);
                }
                if (strings[8] != null) {
                    builder.addFormDataPart("delivery_address", strings[8]);
                }
                if (strings[9] != null) {
                    builder.addFormDataPart("manager_name", strings[9]);
                }
                if (strings[10] != null) {
                    builder.addFormDataPart("company_logo", "logo.png", RequestBody.create(MEDIA_TYPE_IMAGE, new File(strings[10])));
                }

                RequestBody requestBody = builder.build();

                Request request = new Request.Builder()
                        .url(EDIT_DATA_PROFILE_URL)
                        .post(requestBody)
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("EDITED_PROFILE_DATA?", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseEditProfile responseEditProfile = gsonFromServer.fromJson(responseBody, ResponseEditProfile.class);

                    return responseEditProfile.getSuccess();


            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);

            if (isFinishing()) {
                return;
            }

            if (s) {

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(ProfileHomeActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 1000);

//                GetBuyersInfo getBuyersInfo = new GetBuyersInfo();
//                getBuyersInfo.execute();
            }

        }
    }
}
