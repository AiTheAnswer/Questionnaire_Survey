package com.allen.questionnaire.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.allen.questionnaire.MyApplication;
import com.allen.questionnaire.R;
import com.allen.questionnaire.activity.AnalysisDetailedActivity;
import com.allen.questionnaire.activity.HomeActivity;
import com.allen.questionnaire.adapter.QuestionnaireListAdapter;
import com.allen.questionnaire.service.ApiManager;
import com.allen.questionnaire.service.datatrasfer.IDataCallBack;
import com.allen.questionnaire.service.model.Questionnaire;
import com.allen.questionnaire.service.model.RespQuestionnaireList;
import com.allen.questionnaire.view.LoadingDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 有用户的作答问卷列表的Fragment
 */
public class AnsweredListFragment extends Fragment {
    @BindView(R.id.answered_questionnaire_lst)
    ListView mListView;
    Unbinder unbinder;
    private AnalysisFragment.PAGE_CATEGORY pageCategory;
    private int categoryId;
    private HomeActivity mActivity;
    private MyApplication myApplication;
    private List<Questionnaire> mQuestionnaireList;
    private QuestionnaireListAdapter mAdapter;

    /**
     * 设置当前Fragment 显示内容的类型
     *
     * @param pageCategory 内容类型
     */
    public void setPageCategory(AnalysisFragment.PAGE_CATEGORY pageCategory) {
        this.pageCategory = pageCategory;
    }

    /**
     * 设置要查看问卷类型id
     *
     * @param categoryId 类型Id
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        getAnsweredQuestionnaire();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answered_questionnaire, null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initListener();
        return view;

    }

    private void initData() {
        mActivity = (HomeActivity) getActivity();
        myApplication = (MyApplication) mActivity.getApplication();

    }

    private void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Questionnaire questionnaire = mQuestionnaireList.get(position);
                AnalysisDetailedActivity.start(mActivity,questionnaire);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getAnsweredQuestionnaire();
    }

    /**
     * 根据条件获取问卷列表
     */
    private void getAnsweredQuestionnaire() {
        LoadingDialog.showLoading(mActivity);
        Map<String, String> params = new HashMap<>();
        params.put("token", myApplication.getToken());
        params.put("categoryId", String.valueOf(categoryId));
        params.put("type", String.valueOf(pageCategory.getType()));
        IDataCallBack<RespQuestionnaireList> callback = new IDataCallBack<RespQuestionnaireList>() {
            @Override
            public void onSuccess(RespQuestionnaireList result) {
                LoadingDialog.dismissLoading();
                mQuestionnaireList = result.getObject();
                for (Questionnaire questionnaire : mQuestionnaireList) {
                    //如果是已作答的，设置是否使用为false(为了不显示已作答)
                    if (pageCategory == AnalysisFragment.PAGE_CATEGORY.ANSWERED) {
                        questionnaire.setUse(false);
                    }
                }
                mAdapter = new QuestionnaireListAdapter(mActivity, mQuestionnaireList);
                mListView.setAdapter(mAdapter);
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                LoadingDialog.dismissLoading();
            }
        };
        ApiManager.getQuestionnaireRecord(getActivity(), "/recording/getRecordings", null, params, callback);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
