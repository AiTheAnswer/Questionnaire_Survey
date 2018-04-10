package com.allen.questionnaire;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.allen.questionnaire.adapter.ViewPagerAdapter;
import com.allen.questionnaire.fragment.GuideFragment;
import com.allen.questionnaire.view.ViewPagerDotsIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 引导页的Activity
 *
 * @author Renjy
 */

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.view_pager_indicator)
    ViewPagerDotsIndicator mViewPagerIndicator;
    /**
     * Fragment的集合
     */
    private List<Fragment> mFragments;
    /**
     * ViewPager 的适配器
     */
    private ViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initData();
        mViewPager.setAdapter(mAdapter);
        mViewPagerIndicator.setPagerNumber(mFragments.size(), 0);
        initListener();
    }

    private void initData() {
        mFragments = new ArrayList<>();
        mFragments.add(GuideFragment.getInstance(R.mipmap.questionnaire_guide1));
        mFragments.add(GuideFragment.getInstance(R.mipmap.questionnaire_guide2));
        mFragments.add(GuideFragment.getInstance(R.mipmap.questionnaire_guide1));
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragments);

    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mViewPagerIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


}
