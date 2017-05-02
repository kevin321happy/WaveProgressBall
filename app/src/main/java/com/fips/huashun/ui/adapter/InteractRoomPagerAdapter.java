package com.fips.huashun.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by kevin on 2017/1/15.
 */
public class InteractRoomPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> list;

    public InteractRoomPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.list = fragments;
    }

  

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size() == 0 ? 0 : list.size();
    }
}
