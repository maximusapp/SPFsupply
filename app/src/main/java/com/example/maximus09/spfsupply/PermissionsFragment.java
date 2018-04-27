package com.example.maximus09.spfsupply;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.maximus09.spfsupply.data.model.ResponseBuyersInformation;
import com.google.gson.Gson;

import java.util.List;


public class PermissionsFragment extends android.support.v4.app.Fragment {

    CommandReceiver commandReceiver;

   private RecyclerView listPermissions;
   ItemListProfileAdapter itemListProfileAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.permission_fragment, container, false);

        listPermissions = (RecyclerView) view.findViewById(R.id.recycler_profile_permissions);

        listPermissions.setLayoutManager(new LinearLayoutManager(getActivity()));
        itemListProfileAdapter = new ItemListProfileAdapter(null, getActivity());
        listPermissions.setAdapter(itemListProfileAdapter);

        commandReceiver = new CommandReceiver();

        getActivity().registerReceiver(commandReceiver, new IntentFilter("Profile"));

        return  view;
    }

    @Override
    public void onDestroyView() {
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
                final String dataPermiss = "data";
                switch (command){
                    case dataPermiss:{
                        String jsonPermiss = intent.getStringExtra("key");

                        Gson gsonFromServerPerm = new Gson();
                        ResponseBuyersInformation responseBuyersInformation = gsonFromServerPerm.fromJson(jsonPermiss, ResponseBuyersInformation.class);

                        itemListProfileAdapter.updatePermissionsList(responseBuyersInformation.getPermissions());

                        Log.d("PERMISS", String.valueOf(responseBuyersInformation.getPermissions()));

                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
