package com.allen.questionnaire.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.allen.questionnaire.MyApplication;
import com.allen.questionnaire.R;
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

public class AnalysisDetailedActivity extends BaseActivity {

    @BindView(R.id.analysis_detailed_lst)
    ListView mListView;
    private MyApplication myApplication;
    private Questionnaire mQuestionnaire;

    public static void start(Context context,Questionnaire questionnaire){
        Intent intent = new Intent(context,AnalysisDetailedActivity.class);
        intent.putExtra("questionnaire",questionnaire);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis_detailed);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        myApplication = (MyApplication) getApplication();
        mQuestionnaire = (Questionnaire) getIntent().getSerializableExtra("questionnaire");
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
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                String s = errorMessage;

            }
        };
        ApiManager.getQueStatistics(this, "/recording/getQueStatistical", "", params, callback);
    }
}
