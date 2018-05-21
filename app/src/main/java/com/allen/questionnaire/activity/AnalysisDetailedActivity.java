package com.allen.questionnaire.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.allen.questionnaire.MyApplication;
import com.allen.questionnaire.R;
import com.allen.questionnaire.adapter.AnalysisDetailedListAdapter;
import com.allen.questionnaire.service.ApiManager;
import com.allen.questionnaire.service.datatrasfer.IDataCallBack;
import com.allen.questionnaire.service.model.Questionnaire;
import com.allen.questionnaire.service.model.QuestionnaireStatistics;
import com.allen.questionnaire.service.model.RespQueStatisticsList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AnalysisDetailedActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.analysis_detailed_lst)
    ListView mListView;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.img_back)
    ImageView mImgBack;
    private MyApplication myApplication;
    private Questionnaire mQuestionnaire;
    private AnalysisDetailedListAdapter mAdapter;

    public static void start(Context context, Questionnaire questionnaire) {
        Intent intent = new Intent(context, AnalysisDetailedActivity.class);
        intent.putExtra("questionnaire", questionnaire);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_detailed);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    private void initListener() {
        mImgBack.setOnClickListener(this);
    }

    private void initData() {
        myApplication = (MyApplication) getApplication();
        mQuestionnaire = (Questionnaire) getIntent().getSerializableExtra("questionnaire");
        mTxtTitle.setText(mQuestionnaire.getQuestionnaireName());
        getQueStatistics();
    }

    /**
     * 获取当前问卷的分析统计数据
     */
    private void getQueStatistics() {
        Map<String, String> params = new HashMap<>();
        params.put("token", myApplication.getToken());
        params.put("questionnaireId", mQuestionnaire.getId());
        IDataCallBack<RespQueStatisticsList> callback = new IDataCallBack<RespQueStatisticsList>() {
            @Override
            public void onSuccess(RespQueStatisticsList result) {
                RespQueStatisticsList.ObjectBean object = result.getObject();
                List<QuestionnaireStatistics> statisticsList = object.getStatisticsList();
                mAdapter = new AnalysisDetailedListAdapter(AnalysisDetailedActivity.this, statisticsList);
                mListView.setAdapter(mAdapter);
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                showToast(String.format("%s%s%s%s", errorMessage, "(", errorCode, ")"));

            }
        };
        ApiManager.getQueStatistics(this, "/recording/getQueStatistical", "", params, callback);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }
}
