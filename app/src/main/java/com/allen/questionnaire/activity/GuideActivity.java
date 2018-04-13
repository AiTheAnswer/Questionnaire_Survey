package com.allen.questionnaire.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.allen.questionnaire.R;
import com.allen.questionnaire.adapter.ViewPagerAdapter;
import com.allen.questionnaire.fragment.GuideFragment;
import com.allen.questionnaire.utils.SharedPreferenceUtils;
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
    @BindView(R.id.txt_jump_over)
    TextView mTxtJumpOver;
    /**
     * Fragment的集合
     */
    private List<Fragment> mFragments;
    /**
     * ViewPager 的适配器
     */
    private ViewPagerAdapter mAdapter;
    /**
     * SharedPreference的工具类
     */
    private SharedPreferenceUtils mPreferenceUtils;
    private static final String IS_FIRST = "isFirst";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        //验证是否首次进入
        mPreferenceUtils = SharedPreferenceUtils.getInstance(this);
        if (!mPreferenceUtils.getPreferenceBoolean(IS_FIRST, true)) {
            toLoginActivity();
            finish();
            return;
        }
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
        mPreferenceUtils.setPreference(IS_FIRST, false);

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
        mTxtJumpOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toLoginActivity();
                finish();
            }
        });
    }

    /**
     * 跳转到登录Activity
     */
    private void toLoginActivity(){
        startActivity(new Intent(this, LoginActivity.class));
    }


}
