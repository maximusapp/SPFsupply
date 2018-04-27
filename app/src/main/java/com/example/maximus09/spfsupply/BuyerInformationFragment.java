package com.example.maximus09.spfsupply;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.maximus09.spfsupply.data.model.ResponseBuyersInformation;
import com.google.gson.Gson;


public class BuyerInformationFragment extends android.support.v4.app.Fragment {

    CommandReceiver commandReceiver;

    ImageView imageView_logo;
    TextView textView_companyName;
    TextView tvEmail;
    TextView tvOwner;
    TextView tvPhone;
    TextView tvBusinessAddress;
    TextView tvDeliveryAddress;
    TextView tvPaymentMethod;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buyer_information_fragment, container, false);

        imageView_logo = (ImageView)view.findViewById(R.id.logo_buyers_info_admin);
        textView_companyName = (TextView)view.findViewById(R.id.company_name_profile_admin);
        tvEmail = (TextView)view.findViewById(R.id.email_profile_admin);
        tvOwner = (TextView)view.findViewById(R.id.owner_name_profile_admin);
        tvPhone = (TextView)view.findViewById(R.id.phone_profile_admin);
        tvBusinessAddress = (TextView)view.findViewById(R.id.business_adress_profile_admin);
        tvDeliveryAddress = (TextView)view.findViewById(R.id.delivery_adress_profile_admin);
        tvPaymentMethod = (TextView)view.findViewById(R.id.peyment_method_profile_admin);

        commandReceiver = new CommandReceiver();

        getActivity().registerReceiver(commandReceiver, new IntentFilter("Profile"));

        return  view;
    }

    @Override
    public void onDestroyView() {
        //при удалении
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

                        Glide.with(context).load(responseBuyersInformation.getBuyers_data().getCompany_logo()).into(imageView_logo);
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


}
