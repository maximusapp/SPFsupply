package com.example.maximus09.spfsupply;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;



public class TestFragmentAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private String[] content;

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Object obj = super.instantiateItem(container, position);
        return obj;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        if (object != null) {
            return ((Fragment) object).getView() == view;
        } else {
            return false;
        }
    }

    public TestFragmentAdapter(FragmentManager fm, Context context, String[] mCont) {
        super(fm);
        this.context = context;
        this.content = mCont;
    }

    @Override
    public int getItemPosition(Object object) {
        // Causes adapter to reload all Fragments when
        // notifyDataSetChanged is called
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return TestFragment.newInstance(content[position]);
    }

    @Override
    public int getCount() {
        return content == null ? 0 : content.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return content[position];
    }

    public void update(String[] sd0){
        this.content = sd0;
        notifyDataSetChanged();
    }



}
