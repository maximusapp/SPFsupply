package com.example.maximus09.spfsupply;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.maximus09.spfsupply.data.model.ResponseBuyersInformation;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;


public class BuyerInformationFragment extends android.support.v4.app.Fragment {

    private int GALLERY_REQUEST = 1;
    public File file_path;

    CommandReceiver commandReceiver;

    ImageView imageView_logo;
    EditText textView_companyName;
    EditText tvEmail;
    EditText tvOwner;
    EditText tvPhone;
    EditText tvBusinessAddress;
    EditText tvDeliveryAddress;
    EditText tvPaymentMethod;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buyer_information_fragment, container, false);

        imageView_logo = (ImageView) view.findViewById(R.id.logo_buyers_info_admin);
        textView_companyName = (EditText)view.findViewById(R.id.company_name_profile_admin);
        tvEmail = (EditText)view.findViewById(R.id.email_profile_admin);
        tvOwner = (EditText)view.findViewById(R.id.owner_name_profile_admin);
        tvPhone = (EditText)view.findViewById(R.id.phone_profile_admin);
        tvBusinessAddress = (EditText)view.findViewById(R.id.business_adress_profile_admin);
        tvDeliveryAddress = (EditText)view.findViewById(R.id.delivery_adress_profile_admin);
        tvPaymentMethod = (EditText)view.findViewById(R.id.peyment_method_profile_admin);

        commandReceiver = new CommandReceiver();

        getActivity().registerReceiver(commandReceiver, new IntentFilter("Profile"));

        return  view;

    }


    @Override
    public void onDestroyView() {
        // при удалении.
        try {
            getActivity().unregisterReceiver(commandReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroyView();
    }

    private class CommandReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {


            try {
                String command = intent.getStringExtra("cmd");
                final String data = "data";
                switch (command){
                    case data:{
                        String json = intent.getStringExtra("key");

                        Gson gsonFromServer = new Gson();
                        ResponseBuyersInformation responseBuyersInformation = gsonFromServer.fromJson(json, ResponseBuyersInformation.class);

                        if (responseBuyersInformation.getBuyers_data().getCompany_logo() == null || responseBuyersInformation.getBuyers_data().getCompany_logo().length() == 0) {
                            imageView_logo.setImageResource(R.drawable.shape_buyers);
                        } else {
                            Glide.with(context).load(responseBuyersInformation.getBuyers_data().getCompany_logo()).into(imageView_logo);
                        }


                        imageView_logo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                photoPickerIntent.setType("image/*");
                                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                            }
                        });


                        textView_companyName.setText(responseBuyersInformation.getBuyers_data().getCompany_name());
                        tvEmail.setText(responseBuyersInformation.getBuyers_data().getEmail());
                        tvOwner.setText(responseBuyersInformation.getBuyers_data().getManager_name());
                        tvPhone.setText(responseBuyersInformation.getBuyers_data().getPhone_number());
                        tvBusinessAddress.setText(responseBuyersInformation.getBuyers_data().getBusiness_address());
                        tvDeliveryAddress.setText(responseBuyersInformation.getBuyers_data().getDelivery_address());


                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission
                    .READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                return;
            }
        }

        if(resultCode == RESULT_OK && requestCode == GALLERY_REQUEST) {

            final Uri imageUri = data.getData();
            final File file = new File(getRealPathFromURI(getActivity(), imageUri));
            file_path = file;

            Log.d("URI OF IMAGE ", getRealPathFromURI(getActivity(), imageUri));

            Glide.with(getActivity()).load(file).into(imageView_logo);

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



    public String getImagePath(){
        if(getActivity() == null || getActivity().isFinishing())
            return "";
        return file_path == null ? null : file_path.getAbsolutePath();
    }

    public String GetCompanyName(){
        if(getActivity() == null || getActivity().isFinishing())
            return "";
        return textView_companyName.getText().toString();
    }

    public String GetMail(){
        if(getActivity() == null || getActivity().isFinishing())
            return "";
        return tvEmail.getText().toString();
    }


    public String GetOwner(){
        if(getActivity() == null || getActivity().isFinishing())
            return "";
        return tvOwner.getText().toString();
    }

    public String GetPhone(){
        if(getActivity() == null || getActivity().isFinishing())
            return "";
        return tvPhone.getText().toString();
    }

    public String GetBussinessAddress(){
        if(getActivity() == null || getActivity().isFinishing())
            return "";
        return tvBusinessAddress.getText().toString();
    }

    public String GetDeliveryAddress(){
        if(getActivity() == null || getActivity().isFinishing())
            return "";
        return tvDeliveryAddress.getText().toString();
    }

    public String GetPaymentMethod(){
        if(getActivity() == null || getActivity().isFinishing())
            return "";
        return tvPaymentMethod.getText().toString();
    }

}
