package com.example.maximus09.spfsupply;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.maximus09.spfsupply.data.model.GetManufactureById;
import com.example.maximus09.spfsupply.data.model.PostDeleteManufacturers;
import com.example.maximus09.spfsupply.data.model.PostDeleteProduct;
import com.example.maximus09.spfsupply.data.model.ResponseAllManufacturers;
import com.example.maximus09.spfsupply.data.model.ResponseDeleteProduct;
import com.example.maximus09.spfsupply.data.model.ResponseManufactureInfoAdmin;
import com.example.maximus09.spfsupply.struct.ProductListInfoManufactureAdmin;
import com.example.maximus09.spfsupply.util.Preference;
import com.google.android.gms.maps.model.Circle;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InformationActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private static final String GET_MANUF_BY_ID = "http://api.spfsupply.com/public/api/manufacturers/get_by_id";
    private static final String DELETE_PRODUCT_URL = "http://api.spfsupply.com/public/api/manufacturers/product/delete";

    private static final String DELETE_MANUFACTURE_URL = "http://api.spfsupply.com/public/api/manufacturers/delete";
    private static final String EDIT_MANUFACTURER_URL = "http://api.spfsupply.com/public/api/manufacturers/edit";

    ImageView imageView;
    TextView textView;

    //Image and text INFORMATION
    CircleImageView imageView_logo;
    EditText textView_companyName;
    EditText textView_location;
    EditText textView_webSite;

    RecyclerView recyclerView;
    ItemListInformationAdminItemAdapter itemListInformationAdminItemAdapter;

    List<ProductListInfoManufactureAdmin> products;

    AlertDialog.Builder alert;

    String manufId;

    private int GALLERY_REQUEST = 1;
    public File file_path;


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

        Intent intentExtras1 = getIntent();
       final String manufacturerss_id = intentExtras1.getStringExtra("manufacturers_id");

        imageView = (ImageView)findViewById(R.id.add_productInformationActivity);
        textView = (TextView)findViewById(R.id.add_productInformationActivityText);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InformationActivity.this, NewProductActivity.class);
                intent.putExtra("manufacturers_id", manufacturerss_id);
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
        imageView_logo = (CircleImageView) findViewById(R.id.image_information_admin);
        textView_companyName = (EditText)findViewById(R.id.companyName_information_admin);
        textView_location = (EditText)findViewById(R.id.text_company_location_info_admin);
        textView_webSite = (EditText)findViewById(R.id.web_site_text_admin);

        imageView_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });


        recyclerView = (RecyclerView)findViewById(R.id.recycler_information_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        itemListInformationAdminItemAdapter = new ItemListInformationAdminItemAdapter(null, this){
            @Override
            public void ClickDeleteProduct(ProductListInfoManufactureAdmin productListInfo) {
                String id = productListInfo.getId();
                DeleteProduct deleteProduct = new DeleteProduct();
                deleteProduct.execute(id);
            }
        };
        recyclerView.setAdapter(itemListInformationAdminItemAdapter);

    }


    // For Image.
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
                imageView_logo.setImageBitmap(selectedImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(InformationActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
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


            String compName = textView_companyName.getText().toString();
            String location = textView_location.getText().toString();
            String webSite = textView_webSite.getText().toString();

            String logo_link = file_path == null ? null : file_path.getAbsolutePath();

            Preference preference = new Preference(getApplicationContext());

            EditManufacturer editManufacturer = new EditManufacturer();
            editManufacturer.execute(preference.getToken(), manufId, compName, location, webSite, logo_link);

            Toast.makeText(InformationActivity.this, "Manufacture edited success", Toast.LENGTH_LONG).show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intentCreate = new Intent(InformationActivity.this, ManufacturesActivity.class);
                    startActivity(intentCreate);
                    finish();
                }
            }, 2000);


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

                    DeleteManufacturers deleteManufacturers = new DeleteManufacturers();
                    deleteManufacturers.execute();

                    //Toast.makeText(getApplicationContext(), "Pressed delete button", Toast.LENGTH_SHORT).show();
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

            System.out.println("MANUF_ID" +manufacturers_id);
            //Log.d("MANUF_ID", manufacturers_id);

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


            products = responseManufactureInfoAdmin.getManufacturers_data().getProducts();

            itemListInformationAdminItemAdapter.updateListManufacturer(products);


            if (responseManufactureInfoAdmin.getManufacturers_data().getLogo() == null ||
                    responseManufactureInfoAdmin.getManufacturers_data().getLogo().length() == 0) {
                imageView_logo.setImageResource(R.drawable.shape_buyers);
            } else {
                Glide.with(getApplicationContext()).load(responseManufactureInfoAdmin.getManufacturers_data().getLogo()).into(imageView_logo);
            }

            manufId = responseManufactureInfoAdmin.getManufacturers_data().getId();

            textView_companyName.setText(responseManufactureInfoAdmin.getManufacturers_data().getCompany_name());
            textView_location.setText(responseManufactureInfoAdmin.getManufacturers_data().getLocation());
            textView_webSite.setText(responseManufactureInfoAdmin.getManufacturers_data().getWebsite());

        }
    }


    @SuppressLint("StaticFieldLeak")
    private class DeleteProduct extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();


            Preference preferencee = new Preference(getApplicationContext());
            PostDeleteProduct postDeleteProduct = new PostDeleteProduct(strings[0], preferencee.getToken());

            try {
                RequestBody requesBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postDeleteProduct));

                Request request = new Request.Builder()
                        .url(DELETE_PRODUCT_URL)
                        .post(requesBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DELETED_PRODUCT? ", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseDeleteProduct responseDeleteProduct = gsonFromServer.fromJson(responseBody, ResponseDeleteProduct.class);

                if (responseDeleteProduct.getSuccess()) {
                    return strings[0];
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return "-1";
        }

        @Override
        protected void onPostExecute(String responseManufactureInfoAdmin) {
            super.onPostExecute(responseManufactureInfoAdmin);
            //itemListInformationAdminItemAdapter.updateListManufacturer(responseManufactureInfoAdmin.getManufacturers_data().getProducts());
            if (isFinishing()) {
                return;
            }

            for (int i = 0; i < products.size(); i++){
                if (products.get(i).getId().equals(responseManufactureInfoAdmin)) {
                    products.remove(i);
                    break;
                }
            }

            itemListInformationAdminItemAdapter.updateListManufacturer(products);

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class DeleteManufacturers extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Preference preference = new Preference(getApplicationContext());
            PostDeleteManufacturers postDeleteManufacturers = new PostDeleteManufacturers(manufId, preference.getToken());

            try {

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postDeleteManufacturers));

                Request request = new Request.Builder()
                        .url(DELETE_MANUFACTURE_URL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DELETED_MANUFACTURER?", responseBody);

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Intent intent = new Intent(getApplicationContext(), ManufacturesActivity.class);
            startActivity(intent);
            finish();

        }
    }

    @SuppressLint("StaticFieldLeak")
    private class EditManufacturer extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            final MediaType MEDIA_TYPE_IMAGE = MediaType.parse("image/*");

           // editManufacturer.execute(preference.getToken(), manufId, compName, location, webSite, logo_link);

            try {

                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("token", strings[0])
                        .addFormDataPart("manufacturers_id", strings[1]);

                if (strings[2] != null) {
                    builder.addFormDataPart("company_name", strings[2]);
                }
                if (strings[3] != null) {
                    builder.addFormDataPart("location", strings[3]);
                }
                if (strings[4] != null) {
                    builder.addFormDataPart("website", strings[4]);
                }
                if (strings[5] != null) {
                    builder.addFormDataPart("logo", "logo.png",  RequestBody.create(MEDIA_TYPE_IMAGE, new File(strings[5])));
                }

                RequestBody requestBody = builder.build();

                Request request = new Request.Builder()
                        .url(EDIT_MANUFACTURER_URL)
                        .post(requestBody)
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("EDIT_MANUFACTURER?", responseBody);

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