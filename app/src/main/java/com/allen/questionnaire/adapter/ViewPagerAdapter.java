package com.allen.questionnaire.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import java.util.List;

/**
 * ViewPager的适配器
 *
 * @author Renjy
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    private List<String> mTitles;

    public void setTitles(List<String> titles) {
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return (null != mFragments) ? mFragments.size() : 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (null != mTitles) {
            if (mTitles.size() > position) {
                return mTitles.get(position);
            }
        }
        return "";
    }
}
