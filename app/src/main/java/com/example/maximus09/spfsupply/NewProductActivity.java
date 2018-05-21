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
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maximus09.spfsupply.data.model.ResponseAllManufacturers;
import com.example.maximus09.spfsupply.util.Preference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewProductActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private static final String CREATE_PRODUCT_URL = "http://api.spfsupply.com/public/api/manufacturers/product/create";

    private int GALLERY_REQUEST = 1;
    private int GALLERY_REQUEST_FILE = 100;
    public File file_path;

    public List<File>listFile;

    RecyclerView recyclerView_addNewProd;
    ItemListAttachNewDocumentAdmin itemListAttachNewDocumentAdmin;


    ImageView imageView_addProductPhoto;
    EditText editText_productName;
    EditText editText_priceNewProduct;
    EditText editText_descriptionProduct;

    String documentName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sec);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listFile = new ArrayList<>();

        //set font style for title ActionBar
        TextView tv=(TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);


        imageView_addProductPhoto = (ImageView)findViewById(R.id.imageView_add_product_admin);
        editText_productName = (EditText)findViewById(R.id.edit_new_productName);
        editText_priceNewProduct = (EditText)findViewById(R.id.price_newProduct);
        editText_descriptionProduct = (EditText)findViewById(R.id.editText_description_product);


        imageView_addProductPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

            }
        });



        recyclerView_addNewProd = (RecyclerView)findViewById(R.id.recycler_addNewProduct);
        recyclerView_addNewProd.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        itemListAttachNewDocumentAdmin = new ItemListAttachNewDocumentAdmin(this, null){
            @Override
            public void OnAdd() {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("*/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST_FILE);

            }

            @Override
            public void DeleteProduct(File file) {

                listFile.remove(file);
                itemListAttachNewDocumentAdmin.updateListOfProducts(listFile);

            }

        };
        recyclerView_addNewProd.setAdapter(itemListAttachNewDocumentAdmin);

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
                imageView_addProductPhoto.setImageBitmap(selectedImage);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(NewProductActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }


       if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_FILE) {

                final Uri fileUri = data.getData();
                final File file = new File(getRealPathFromURI(this, fileUri));

                listFile.add(file);

                Log.d("URI_OF_FILE ", getRealPathFromURI(this, fileUri));

                ShowDialog();

               // final InputStream fileStream = getContentResolver().openInputStream(fileUri);
                //final Bitmap selectedFile = BitmapFactory.decodeStream(fileStream);

                itemListAttachNewDocumentAdmin.updateListOfProducts(listFile);


       }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ShowDialog() {
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(NewProductActivity.this);
        View promptsView = getLayoutInflater().inflate(R.layout.add_name_of_file, null);
        final EditText docName = (EditText)promptsView.findViewById(R.id.enter_doc_name);
        Button addDocButton = (Button)promptsView.findViewById(R.id.addDoc);

        mDialogBuilder.setView(promptsView);
        final AlertDialog alertDialog = mDialogBuilder.create();
        alertDialog.show();

        addDocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String logo_link = file_path == null ? null : file_path.getAbsolutePath();

                Intent intentExtras = getIntent();
                String manufacturers_id = intentExtras.getStringExtra("manufacturers_id");


                String productName = editText_productName.getText().toString().trim();
                String productPrice = editText_priceNewProduct.getText().toString().trim();
                String productDesc = editText_descriptionProduct.getText().toString().trim();

                if (file_path != null){
                    documentName = docName.getText().toString();
                    Preference preference = new Preference(getApplicationContext());
                    TascCreateNewProduct tascCreateNewProduct = new TascCreateNewProduct();
                    tascCreateNewProduct.execute(productName, productPrice, productDesc, preference.getToken(), manufacturers_id, logo_link);
                }
                alertDialog.dismiss();
            }
        });

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
        getMenuInflater().inflate(R.menu.new_product, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        String productName = editText_productName.getText().toString().trim();
        String productPrice = editText_priceNewProduct.getText().toString().trim();
        String productDesc = editText_descriptionProduct.getText().toString().trim();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_done_new_product) {

            if (productName.isEmpty()) {
                editText_productName.setError("Enter product title");
            } if (productPrice.isEmpty()) {
                editText_priceNewProduct.setError("Enter price");
            } if (productDesc.isEmpty()) {
                editText_descriptionProduct.setError("Enter description");
            }

            String logo_link = file_path == null ? null : file_path.getAbsolutePath();

            Intent intentExtras = getIntent();
            String manufacturers_id = intentExtras.getStringExtra("manufacturers_id");

            Preference preference = new Preference(getApplicationContext());
            TascCreateNewProduct tascCreateNewProduct = new TascCreateNewProduct();
            tascCreateNewProduct.execute(productName, productPrice, productDesc, preference.getToken(), manufacturers_id, logo_link);

            Toast.makeText(NewProductActivity.this, "Product created success", Toast.LENGTH_LONG).show();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intentCreate = new Intent(NewProductActivity.this, ManufacturesActivity.class);
                    startActivity(intentCreate);
                    finish();
                }
            }, 3000);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressLint("StaticFieldLeak")
    public class TascCreateNewProduct extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            final MediaType MEDIA_TYPE_IMAGE = MediaType.parse("image/*");
            final MediaType MEDIA_TYPE_FILE = MediaType.parse("*/*");


            OkHttpClient okHttpClient = new OkHttpClient();

            try {
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("title", strings[0])
                        .addFormDataPart("price", strings[1])
                        .addFormDataPart("description", strings[2])
                        .addFormDataPart("token", strings[3])
                        .addFormDataPart("manufacturers_id", strings[4]);


                if (strings[5] != null) {
                    builder.addFormDataPart("logo", "logo.png", RequestBody.create(MEDIA_TYPE_IMAGE, new File(strings[5])));
                }

                for (int i = 0; i < listFile.size(); i++) {
                    builder.addFormDataPart("attachments", documentName, RequestBody.create(MEDIA_TYPE_FILE, listFile.get(i)));
                }

                RequestBody requestBody = builder.build();

                Request request = new Request.Builder()
                        .url(CREATE_PRODUCT_URL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DEBUG_CREATE_PRODUCT", responseBody);

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
