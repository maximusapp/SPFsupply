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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class NewProductActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private static final String CREATE_PRODUCT_URL = "http://spf.yobibyte.in.ua/api/manufacturers/product/create/";

    private int GALLERY_REQUEST = 1;
    public File file_path;

    RecyclerView recyclerView_addNewProd;
    ItemListAttachNewDocumentAdmin itemListAttachNewDocumentAdmin;

    ImageView imageView_addProductPhoto;
    EditText editText_productName;
    EditText editText_priceNewProduct;
    EditText editText_descriptionProduct;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sec);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        recyclerView_addNewProd.setLayoutManager(new LinearLayoutManager(this));
        itemListAttachNewDocumentAdmin = new ItemListAttachNewDocumentAdmin(this, null){
            @Override
            public void OnAdd() {
               Toast.makeText(NewProductActivity.this, "Pressed Attached", Toast.LENGTH_LONG).show();
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


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
