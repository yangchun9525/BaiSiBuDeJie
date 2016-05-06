package com.yc.BaiSiBuDeJie.module.listview.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by YangChun on 2015/9/10.
 * 页面切换adapter，配合viewpager使用
 */
public class TabLayoutFragmentAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> mFragments;
    private String[] mTitles;

    public TabLayoutFragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] titles) {
        super(fm);
        this.mFragments = fragments;
        this.mTitles = titles;
    }

    public TabLayoutFragmentAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    public void updateTitle(String[] title){
        mTitles = title;
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

}
