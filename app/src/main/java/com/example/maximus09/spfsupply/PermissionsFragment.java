package com.example.maximus09.spfsupply;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


public class PermissionsFragment extends android.support.v4.app.Fragment {

   private ListView listPermissions;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.permission_fragment, container, false);

        listPermissions = (ListView)view.findViewById(R.id.listView_prifile_permissions);

        ItemsProfile itemsProfile = new ItemsProfile("Manufacturer1");
        ItemsProfile itemsProfile2 = new ItemsProfile("Manufacturer2");
        ItemsProfile itemsProfile3 = new ItemsProfile("Manufacturer3");

        final ArrayList<ItemsProfile> profiles = new ArrayList<>();
        profiles.add(itemsProfile);
        profiles.add(itemsProfile2);
        profiles.add(itemsProfile3);

        final ItemListProfileAdapter itemListProfileAdapter = new ItemListProfileAdapter(view.getContext(), R.layout.custom_profile_perm_item_list, profiles);
        listPermissions.setAdapter(itemListProfileAdapter);

        return  view;
    }
}
