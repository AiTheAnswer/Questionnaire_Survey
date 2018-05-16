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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.questionnaire.MyApplication;
import com.allen.questionnaire.R;
import com.allen.questionnaire.activity.HomeActivity;
import com.allen.questionnaire.adapter.ViewPagerAdapter;
import com.allen.questionnaire.service.ApiManager;
import com.allen.questionnaire.service.datatrasfer.IDataCallBack;
import com.allen.questionnaire.service.model.Category;
import com.allen.questionnaire.service.model.Questionnaire;
import com.allen.questionnaire.service.model.RespCategoryList;
import com.allen.questionnaire.service.model.RespQuestionnaireList;
import com.allen.questionnaire.utils.Constant;
import com.allen.questionnaire.view.SelectCategoryPopupWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.allen.questionnaire.fragment.AnalysisFragment.PAGE_CATEGORY.ALL;
import static com.allen.questionnaire.fragment.AnalysisFragment.PAGE_CATEGORY.ANSWERED;
import static com.allen.questionnaire.fragment.AnalysisFragment.PAGE_CATEGORY.UNANSWERED;

/**
 * 分析Fragment
 *
 * @author Renjy
 */

public class AnalysisFragment extends Fragment implements View.OnClickListener {
    Unbinder unbinder;
    @BindView(R.id.analysis_view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.analysis_txt_category_name)
    TextView mTxtCategory;
    @BindView(R.id.layout_select_category)
    RelativeLayout mLayoutSelectCategory;
    private View view;
    private HomeActivity mActivity;
    private MyApplication myApplication;
    private List<Fragment> mFragments;
    private List<String> mTitles;
    //当前用户已作答问卷列表的Fragment
    private AnsweredListFragment answeredFragment;
    //当前用户未作答问卷列表的Fragment
    private AnsweredListFragment unAnsweredFragment;
    //全部已作答的问卷列表的Fragment
    private AnsweredListFragment allAnsweredFragment;
    private ViewPagerAdapter mAdapter;
    //当前选择的类别Id
    private int selectedCategoryId = 0;

    public enum PAGE_CATEGORY {
        ANSWERED(1, "已作答"),//当前用户已作答
        UNANSWERED(2, "未作答"),//当前用户未作答
        ALL(0, "所有");//所有
        private int type;
        private String name;

        PAGE_CATEGORY(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_analysis, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        initListener();
        return view;
    }

    private void initView() {

    }

    private void initData() {
        mActivity = (HomeActivity) getActivity();
        myApplication = (MyApplication) mActivity.getApplication();
        mTitles = new ArrayList<>();
        mTitles.add(ANSWERED.getName());
        mTitles.add(UNANSWERED.getName());
        mTitles.add(ALL.getName());
        answeredFragment = new AnsweredListFragment();
        answeredFragment.setPageCategory(ANSWERED);
        unAnsweredFragment = new AnsweredListFragment();
        unAnsweredFragment.setPageCategory(PAGE_CATEGORY.UNANSWERED);
        allAnsweredFragment = new AnsweredListFragment();
        allAnsweredFragment.setPageCategory(PAGE_CATEGORY.ALL);
        mFragments = new ArrayList<>();
        mFragments.add(answeredFragment);
        mFragments.add(unAnsweredFragment);
        mFragments.add(allAnsweredFragment);
        mAdapter = new ViewPagerAdapter(mActivity.getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        mAdapter.setTitles(mTitles);
        mTabLayout.setupWithViewPager(mViewPager);
    }


    private void initListener() {
        mLayoutSelectCategory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_select_category://选择类别
                getCategoryList();
                break;
        }
    }

    /**
     * 获取问卷的分类
     */
    private void getCategoryList() {
        Map<String, String> params = new HashMap<>();
        params.put("token", myApplication.getToken());
        IDataCallBack<RespCategoryList> callback = new IDataCallBack<RespCategoryList>() {
            @Override
            public void onSuccess(RespCategoryList result) {
                if (null != result && result.OK()) {
                    ArrayList<Category> categories = result.getObject();
                    if (null != categories) {
                        showCategoryPopWindow(categories);
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
        ApiManager.postCategoryList(getActivity(), "/category/getCategoryList", getActivity(), params, callback);
    }

    /**
     * 显示选择问卷类别的PopWindow
     *
     * @param categories 类别集合
     */
    private void showCategoryPopWindow(ArrayList<Category> categories) {
        Category category = new Category();
        category.setCategoryName("全部");
        category.setId(0);
        categories.add(0,category);
        final SelectCategoryPopupWindow popupWindow = new SelectCategoryPopupWindow(mActivity, categories, selectedCategoryId);
        popupWindow.show();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Category selectedCategory = popupWindow.getSelectedCategory();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
