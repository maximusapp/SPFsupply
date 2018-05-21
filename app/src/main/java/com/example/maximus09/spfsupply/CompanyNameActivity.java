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
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maximus09.spfsupply.data.model.Message;
import com.example.maximus09.spfsupply.data.model.PostGetMessage;
import com.example.maximus09.spfsupply.data.model.PostSendMessage;
import com.example.maximus09.spfsupply.data.model.ResponseMessage;
import com.example.maximus09.spfsupply.data.model.ResponseMessageSend;
import com.example.maximus09.spfsupply.util.Preference;
import com.google.gson.Gson;

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

public class CompanyNameActivity extends AppCompatActivity {

    // Set back button <-.
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private static final String GET_MESSAGE_URL = "http://api.spfsupply.com/public/api/chat/message/get";
    private static final String SEND_MESSAGE_URL = "http://api.spfsupply.com/public/api/chat/message/send";

    private int GALLERY_REQUEST = 1;
    public File file_path;

    ImageView ivSendMessage;
    ImageView ivAttachFile;
    EditText etWriteMessage;

    RecyclerView listMessages;
    ItemListChatAdapter itemListChatAdapter;

    public List<Message> mess;

    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_name);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_sec);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setSubtitle("UserName");

        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.setStatusBarColor(this.getColor(R.color.colorStatusBar));
            }
        }

        // Set font style for title ActionBar.
        TextView tv=(TextView) toolbar.getChildAt(0);
        Typeface typefaceActionBar = Typeface.createFromAsset(this.getAssets(), "fonts/latoregular.ttf");
        tv.setTypeface(typefaceActionBar);

        // Handle attach button press.
        ivAttachFile = (ImageView)findViewById(R.id.attachFile);
        ivAttachFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("*/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });

        // Handle Message List in Chat.
        listMessages = (RecyclerView)findViewById(R.id.rvChatList);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        listMessages.setLayoutManager(linearLayoutManager);

        itemListChatAdapter = new ItemListChatAdapter(null, this);
        itemListChatAdapter.notifyDataSetChanged();
        listMessages.setAdapter(itemListChatAdapter);

        etWriteMessage = (EditText)findViewById(R.id.enter_text_company_name);

        // Handle button of send message.
        ivSendMessage = (ImageView)findViewById(R.id.sendMessage);
        ivSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Preference preference = new Preference(getApplicationContext());

                // Get chat_id from Orders.
                Intent intentChatId = getIntent();
                String chatIdOrders = intentChatId.getStringExtra("chat_id");

                // Get chat_id from Messages.
                Intent intentExtras = getIntent();
                String chatIdMessages = intentExtras.getStringExtra("chat_id");

                String getMessage = etWriteMessage.getText().toString();

                if (getMessage.isEmpty()) {
                    etWriteMessage.setError("Enter message please");
                } else {
                    etWriteMessage.setText("");

                    SendMessage sendMessage = new SendMessage();
                    sendMessage.execute(preference.getToken(), chatIdOrders, getMessage, "text");


                }

            }
        });

        // Handle get all message for chat.
        GetMessageChat getMessageChatt = new GetMessageChat();
        getMessageChatt.execute();

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

            final Uri fileUri = data.getData();
            final File file = new File(getRealPathFromURI(this, fileUri));
            file_path = file;


            Log.d("URI_OF_FILE", getRealPathFromURI(this, fileUri));

            // final InputStream imageStream = getContentResolver().openInputStream(fileUri);
            //final Bitmap selectedFile = BitmapFactory.decodeStream(imageStream);
            //imageView_addProductPhoto.setImageBitmap(selectedImage);

            Preference preference = new Preference(getApplicationContext());

            // Get chat_id from Orders.
            Intent intentChatId = getIntent();
            String chatIdOrders = intentChatId.getStringExtra("chat_id");

            SendMessage sendFile = new SendMessage();
            sendFile.execute(preference.getToken(), chatIdOrders, file_path.getAbsolutePath(), "file");

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
        getMenuInflater().inflate(R.menu.menu_company_name, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // No inspection SimplifiableIfStatement.
        if (id == R.id.action_mail_order_number) {
            onBackPressed();
//            Intent intentCompanyName = new Intent(CompanyNameActivity.this, OrderNumberActivity.class);
//            startActivity(intentCompanyName);
            //Toast.makeText(this, "Pressed Mail button", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Get all messages in chat.
    @SuppressLint("StaticFieldLeak")
    private class GetMessageChat extends AsyncTask<String, String, ResponseMessage> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseMessage doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            Gson gson = new Gson();

            Intent intentChatId = getIntent();
//            String chatIdMessages = intentChatId.getStringExtra("messages_chat_id");
            String chatIdOrders = intentChatId.getStringExtra("chat_id");


            Preference preference = new Preference(getApplicationContext());
            PostGetMessage postGetMessage = new PostGetMessage(preference.getToken(), chatIdOrders);

            try {

                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(postGetMessage));

                Request request = new Request.Builder()
                        .url(GET_MESSAGE_URL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DEBUG_CHAT_DATA", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseMessage responseMessage = gsonFromServer.fromJson(responseBody, ResponseMessage.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseMessage;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


        @SuppressWarnings("ConstantConditions")
        @Override
        protected void onPostExecute(ResponseMessage responseMessage) {
            super.onPostExecute(responseMessage);

            Intent intentExtras = getIntent();
            String compaName = intentExtras.getStringExtra("company_name");
            String orderaNumb =  intentExtras.getStringExtra("order_number");


            if (compaName != null && orderaNumb != null) {
                getSupportActionBar().setTitle(compaName);
                getSupportActionBar().setSubtitle(orderaNumb);
            }

            mess = responseMessage.getMessages();

            itemListChatAdapter.updateListMessages(mess);
            itemListChatAdapter.notifyDataSetChanged();


        }
    }


    @SuppressLint("StaticFieldLeak")
    private class SendMessage extends AsyncTask<String, String, ResponseMessageSend> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ResponseMessageSend doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();
            final MediaType MEDIA_TYPE_FILE = MediaType.parse("*/*");

            try {
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("token", strings[0])
                        .addFormDataPart("chat_id", strings[1])
                        //.addFormDataPart("message", strings[2]);
                        .addFormDataPart("type_message", strings[3]);

                if(strings[3].equals("file")) {
                    builder.addFormDataPart("message", "files", RequestBody.create(MEDIA_TYPE_FILE, new File(strings[2])));

                } else {
                    builder.addFormDataPart("message", strings[2]);
                }


                RequestBody requestBody = builder.build();

                Request request = new Request.Builder()
                        .url(SEND_MESSAGE_URL)
                        .post(requestBody)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = okHttpClient.newCall(request).execute();

                @SuppressWarnings("ConstantConditions")
                String responseBody = response.body().string();
                Log.i("DEBUG_SEND_MESSAGES", responseBody);

                Gson gsonFromServer = new Gson();
                ResponseMessageSend responseMessageSend = gsonFromServer.fromJson(responseBody, ResponseMessageSend.class);

                // added
                int responseCode = response.code();
                if(responseCode == 200 && responseBody.length() != 0) {
                    return responseMessageSend;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(ResponseMessageSend respMess) {
            super.onPostExecute(respMess);

            if(isFinishing())
                return;

            Intent intentExtras = getIntent();
            String compaName = intentExtras.getStringExtra("company_name");
            String orderaNumb =  intentExtras.getStringExtra("order_number");

            if (compaName != null) {
                //noinspection ConstantConditions
                getSupportActionBar().setTitle(compaName);
                getSupportActionBar().setSubtitle(orderaNumb);
            }


            mess.add(respMess.getMessage_data());
            itemListChatAdapter.notifyDataSetChanged();
            listMessages.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(isFinishing())
                        return;
                    listMessages.scrollToPosition(mess.size()-1);
                }
            }, 200);



        }
    }

}
