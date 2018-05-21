package com.example.maximus09.spfsupply;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maximus09.spfsupply.data.model.GetOrdersById;
import com.example.maximus09.spfsupply.data.model.PostRemoveDocument;
import com.example.maximus09.spfsupply.data.model.ResponseOrderDocumentData;
import com.example.maximus09.spfsupply.data.model.ResponseOrdersById;
import com.example.maximus09.spfsupply.data.model.ResponseRemoveDocument;
import com.example.maximus09.spfsupply.struct.OrdersData;
import com.example.maximus09.spfsupply.util.Preference;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OrderNumberActivity extends AppCompatActivity {

    public boolean onSupportNavigateUp(){
        onBackPressed();
        Intent intent = new Intent(OrderNumberActivity.this, OrdersActivity.class);
        startActivity(intent);
        return true;
    }

    private static final String GET_ORDER_BY_ID = "http://api.spfsupply.com/public/api/user/orders/get_by_id";
    private static final String ADD_DOCUMENT_URL = "http://api.spfsupply.com/public/api/admin/orders/document/add";
    private static final String REMOVE_PRODUCT_URL = "http://api.spfsupply.com/public/api/admin/orders/document/remove";

    private int GALLERY_REQUEST_FILE = 100;
    private int GALLERY_REQUEST = 1;
    public File file_path;
    String file_link;

    TextView tvCompanyName;
    TextView tvOrderData;
    TextView tvOrderPrice;
    TextView tvDeliveryLocation;


    String documentName;


    // Recycler for products.
    RecyclerView rvProducts;
    ItemListOrderNumAdminAdapter itemListOrderNumAdminAdapter;

    public List<OrdersData.Documents> documents;
    String orders_id;
    ImageView ivAddDocument;

    // Recycler for documents.
    RecyclerView rvDocuments;
    ItemListAddDocumentOrderNumber itemListAddDocumentOrderNumber;

    public String orderDocID;


    String comName;
    String ordNumb;
    String chatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_number);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sec);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(this.getColor(R.color.colorStatusBar));
            }
        }

        ivAddDocument = (ImageView)findViewById(R.id.image_add_document);
        // Handle add document button.
        ivAddDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });


        rvDocuments = (RecyclerView)findViewById(R.id.rvDocumentOrderNumber);
        rvDocuments.setLayoutManager(new LinearLayoutManager(this));
        itemListAddDocumentOrderNumber = new ItemListAddDocumentOrderNumber(null, this){
            @Override
            public void ClikDeletDocument(OrdersData.Documents documents) {
                RemoveDocument removeDocument = new RemoveDocument();
                removeDocument.execute(documents.getId());
            }
        };
        rvDocuments.setAdapter(itemListAddDocumentOrderNumber);


        tvCompanyName = (TextView)findViewById(R.id.textCompany_order_number);
        tvOrderData = (TextView)findViewById(R.id.textCompany_order_date_title);
        tvOrderPrice = (TextView)findViewById(R.id.total_price_order_number);
        tvDeliveryLocation = (TextView)findViewById(R.id.delivery_location_order_number);

        rvProducts = (RecyclerView)findViewById(R.id.recycler_order_number);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));
        itemListOrderNumAdminAdapter = new ItemListOrderNumAdminAdapter(null, this);
        rvProducts.setAdapter(itemListOrderNumAdminAdapter);

        //set font style for title ActionBar
        TextView tv=(TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);

        GetOrderNumberById getOrderNumberById = new GetOrderNumberById();
        getOrderNumberById.execute();



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

            final Uri imageUri = data.getData();
            final File file = new File(getRealPathFromURI(this, imageUri));
            file_path = file;

            String strFileName = file_path.getAbsolutePath();

            Log.d("URI_OF_FILE!", strFileName);

            file_link = file_path == null ? null : file_path.getAbsolutePath();

            ShowDialog();

            //final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            //final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            // imageView_addProductPhoto.setImageBitmap(selectedImage);

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void ShowDialog() {
        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(OrderNumberActivity.this);
        View promptsView = getLayoutInflater().inflate(R.layout.add_name_of_file, null);
        final EditText docName = (EditText)promptsView.findViewById(R.id.enter_doc_name);
        Button addDocButton = (Button)promptsView.findViewById(R.id.addDoc);


        mDialogBuilder.setView(promptsView);
       final AlertDialog alertDialog = mDialogBuilder.create();
        alertDialog.show();

        addDocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (file_path != null){
                    documentName = docName.getText().toString();
                    Preference preference = new Preference(getApplicationContext());
                    GetDataDocument getDataDocument = new GetDataDocument();
                    getDataDocument.execute(preference.getToken(), orders_id, file_link);
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
        getMenuInflater().inflate(R.menu.menu_order_number, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_mail_order_number) {
            Intent intentMail = new Intent(OrderNumberActivity.this, CompanyNameActivity.class);
            intentMail.putExtra("company_name", comName);
            intentMail.putExtra("order_number", ordNumb);
            intentMail.putExtra("chat_id", chatId);
            startActivity(intentMail);
            //Toast.makeText(this, "Pressed Mail button", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    private class GetOrderNumberById extends AsyncTask<String, String, ResponseOrdersById> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseOrdersById doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            //Get StringExtras from OrderActivity.class
            Intent intentExtras = getIntent();
            String orders_id = intentExtras.getStringExtra("orders_id");

            Log.d("ORDERS_DATA_IS", orders_id);

            Preference preference = new Preference(getApplicationContext());
            GetOrdersById getOrderNumberById = new GetOrdersById(preference.getToken(), orders_id);

            try {
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(getOrderNumberById));

                Request request = new Request.Builder()
                        .url(GET_ORDER_BY_ID)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DATA_OF_ORDERS ", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseOrdersById responseOrdersById = gsonFromServer.fromJson(responseBody, ResponseOrdersById.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseOrdersById;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(ResponseOrdersById responseOrdersById) {
            super.onPostExecute(responseOrdersById);

            if (responseOrdersById.getOrders_data().getOrder_name() != null) {
                getSupportActionBar().setTitle(responseOrdersById.getOrders_data().getOrder_name());
            }

            orders_id = responseOrdersById.getOrders_data().getId();

            comName = responseOrdersById.getOrders_data().getManufacturers_company_name();
            ordNumb = responseOrdersById.getOrders_data().getOrder_name();
            chatId = responseOrdersById.getOrders_data().getChat_id();

            tvCompanyName.setText(responseOrdersById.getOrders_data().getManufacturers_company_name());

            itemListOrderNumAdminAdapter.updateProductList(responseOrdersById.getOrders_data().getProducts());

            tvOrderData.setText(responseOrdersById.getOrders_data().getOrder_date());
            tvOrderPrice.setText(responseOrdersById.getOrders_data().getTotal_count());
            tvDeliveryLocation.setText(responseOrdersById.getOrders_data().getDelivery_location());

            // Update list of documents.
            documents = responseOrdersById.getOrders_data().getDocuments();
            itemListAddDocumentOrderNumber.updateListOfDoc(documents);




        }
    }

    @SuppressLint("StaticFieldLeak")
    private class GetDataDocument extends AsyncTask<String, String, ResponseOrderDocumentData> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseOrderDocumentData doInBackground(String... strings) {

            final MediaType MEDIA_TYPE_IMAGE = MediaType.parse("image/*");

            OkHttpClient okHttpClient = new OkHttpClient();

            try {

                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("token", strings[0])
                        .addFormDataPart("orders_id", strings[1]);

                if (strings[2] != null) {
                    builder.addFormDataPart("file", documentName, RequestBody.create(MEDIA_TYPE_IMAGE, new File(strings[2])));
                }

                RequestBody requestBody = builder.build();

                Request request = new Request.Builder()
                        .url(ADD_DOCUMENT_URL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DOCUMENT DATA", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseOrderDocumentData responseOrderDocumentData = gsonFromServer.fromJson(responseBody, ResponseOrderDocumentData.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseOrderDocumentData;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResponseOrderDocumentData responseOrderDocumentData) {
            super.onPostExecute(responseOrderDocumentData);

            Log.d("DO_ID_IS", responseOrderDocumentData.getOrders_documents_data().getId());

            orderDocID = responseOrderDocumentData.getOrders_documents_data().getId();

            itemListAddDocumentOrderNumber.updateListOfDoc(documents);
            itemListAddDocumentOrderNumber.notifyDataSetChanged();

            GetOrderNumberById getOrderNumberById = new GetOrderNumberById();
            getOrderNumberById.execute();


        }
    }

    @SuppressLint("StaticFieldLeak")
    private class RemoveDocument extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Preference preference = new Preference(getApplicationContext());
            PostRemoveDocument postRemoveDocument = new PostRemoveDocument(preference.getToken(), strings[0]);

            try {

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postRemoveDocument));

                Request request = new Request.Builder()
                        .url(REMOVE_PRODUCT_URL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DELETED?", responseBody);


                Gson gsonFromServer = new Gson();
                ResponseRemoveDocument responseRemoveDocument = gsonFromServer.fromJson(responseBody, ResponseRemoveDocument.class);

                if(responseRemoveDocument.getSuccess()) {
                    return true;
                }



            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);

            if (isFinishing()) {
                return;
            }

            if (s) {
                GetOrderNumberById getOrderNumberById = new GetOrderNumberById();
                getOrderNumberById.execute();
            }

        }
    }
}
