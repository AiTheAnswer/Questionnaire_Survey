package com.allen.questionnaire.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.allen.questionnaire.MyApplication;
import com.allen.questionnaire.R;
import com.allen.questionnaire.activity.HomeActivity;
import com.allen.questionnaire.adapter.ViewPagerAdapter;
import com.allen.questionnaire.service.datatrasfer.IDataCallBack;
import com.allen.questionnaire.service.model.Category;
import com.allen.questionnaire.service.model.RespCategoryList;
import com.allen.questionnaire.service.net.CommonRequest;
import com.allen.questionnaire.utils.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 问卷Fragment
 *
 * @author Renjy
 */

public class QuestionnairesFragment extends Fragment {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    Unbinder unbinder;
    private View view;
    private HomeActivity mActivity;
    private MyApplication myApplication;
    private List<Category> mCategoryList;
    private List<Fragment> mFragments;
    private ViewPagerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_questionnaire, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        mActivity = (HomeActivity) getActivity();
        myApplication = (MyApplication) mActivity.getApplication();
        mFragments = new ArrayList<>();
        getCategoryList();
    }

    /**
     * 获取问卷类别
     */
    private void getCategoryList() {
        Map<String, String> params = new HashMap<>();
        params.put("token", myApplication.getToken());

        IDataCallBack<RespCategoryList> callback = new IDataCallBack<RespCategoryList>() {
            @Override
            public void onSuccess(RespCategoryList result) {
                if (null != result && result.OK()) {
                    ArrayList<Category> resultObject = result.getObject();
                    if (null != resultObject && resultObject.size() > 0) {
                        mCategoryList = resultObject;
                        updateTab();
                    } else {
                        //TODO 暂无数据
                    }
                } else if (null != result && !TextUtils.isEmpty(result.getReason())) {
                    mActivity.showToast(result.getReason());
                } else {
                    mActivity.showToast(Constant.NET_ERROR);
                }
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                if (TextUtils.isEmpty(errorMessage)) {
                    errorMessage = Constant.NET_ERROR;
                }
                mActivity.showToast(errorMessage);
            }
        };
        CommonRequest.postCategoryList(getActivity(), "/category/getCategoryList", getActivity(), params, callback);
    }

    /**
     * 更新TabLayout
     */
    private void updateTab() {
        for (Category category : mCategoryList) {
            QuestionnaireFragment questionFragment = new QuestionnaireFragment();
            questionFragment.setData(category);
            mFragments.add(questionFragment);
        }
        mAdapter = new ViewPagerAdapter(mActivity.getSupportFragmentManager(), mFragments);
        mAdapter.setCategories(mCategoryList);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
