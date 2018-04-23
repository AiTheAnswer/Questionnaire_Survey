package com.allen.questionnaire.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.allen.questionnaire.service.model.Category;

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

    private List<Category> mCategories;

    public void setCategories(List<Category> categories) {
        this.mCategories = categories;
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
        return mCategories.get(position).getCategoryName();
    }
}
