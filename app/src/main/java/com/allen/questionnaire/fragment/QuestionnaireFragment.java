package com.allen.questionnaire.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.allen.questionnaire.MyApplication;
import com.allen.questionnaire.R;
import com.allen.questionnaire.activity.HomeActivity;
import com.allen.questionnaire.adapter.QuestionnaireListAdapter;
import com.allen.questionnaire.service.datatrasfer.IDataCallBack;
import com.allen.questionnaire.service.model.Category;
import com.allen.questionnaire.service.model.Questionnaire;
import com.allen.questionnaire.service.model.RespQuestionnaireList;
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
 * 问卷没一个类别展示的Fragment
 */

public class QuestionnaireFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.list_view)
    ListView mListView;
    private MyApplication myApplication;
    private HomeActivity mActivity;
    private Category mCategory;
    private List<Questionnaire> mQuestionnaireList;
    private QuestionnaireListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getQuestionList();
    }

    private void initData() {
        mActivity = (HomeActivity) getActivity();
        myApplication = (MyApplication) mActivity.getApplication();


    }

    public void setData(Category category) {
        this.mCategory = category;
    }

    /**
     * 获取当前类别下问卷列表
     */
    private void getQuestionList() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.TOKEN, myApplication.getToken());
        params.put("categoryId",  mCategory.getId()+"");

        IDataCallBack<RespQuestionnaireList> callback = new IDataCallBack<RespQuestionnaireList>() {
            @Override
            public void onSuccess(RespQuestionnaireList result) {
                if (null != result && result.OK()) {
                    List<Questionnaire> resultObject = result.getObject();
                    if (null != resultObject && resultObject.size() > 0) {
                        mQuestionnaireList = resultObject;
                        updateQuestionnaire();
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
        CommonRequest.postQuestionnaireList(mActivity, "/getQuestionnaire", mActivity, params, callback);
    }

    /**
     * 更新问卷列表
     */
    private void updateQuestionnaire() {
        mAdapter = new QuestionnaireListAdapter(mActivity, mQuestionnaireList);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
