package com.allen.questionnaire.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.questionnaire.R;
import com.allen.questionnaire.service.datatrasfer.IDataCallBack;
import com.allen.questionnaire.service.model.QuestionAddOptions;
import com.allen.questionnaire.service.model.Questionnaire;
import com.allen.questionnaire.service.model.RespQueDetail;
import com.allen.questionnaire.service.model.Test;
import com.allen.questionnaire.service.net.CommonRequest;
import com.allen.questionnaire.utils.SharedPreferenceUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.allen.questionnaire.utils.Constant.TOKEN;

/**
 * 答题Activity
 *
 * @author Renjy
 */
public class AnswerActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.img_back)
    ImageView mImgBack;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.txt_questionnaire_type)
    TextView mTxtQuestionnaireType;
    @BindView(R.id.txt_progress)
    TextView mTxtProgress;
    @BindView(R.id.img_answer)
    ImageView mImgAnswer;
    @BindView(R.id.layout_progress)
    RelativeLayout mLayoutProgress;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.txt_previous_question)
    TextView mTxtPreviousQuestion;
    @BindView(R.id.txt_next_question)
    TextView mTxtNextQuestion;
    @BindView(R.id.layout_page_turning)
    LinearLayout mLayoutPageTurning;
    private SharedPreferenceUtils mPreferenceUtils;
    private Questionnaire mQuestionnaire;
    private String mToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    private void initListener() {
        mImgBack.setOnClickListener(this);
    }

    private void initData() {
        mPreferenceUtils = SharedPreferenceUtils.getInstance(this);
        mToken = mPreferenceUtils.getPreferenceString(TOKEN, "");
        mQuestionnaire = (Questionnaire) getIntent().getExtras().getSerializable("questionnaire");
        if (null == mQuestionnaire) {
            //TODO 暂无数据
            return;
        }
        mTxtTitle.setText(mQuestionnaire.getQuestionnaireName());
        getQuestionAddOptions();
    }

    /**
     * 获取当前问卷的问题和对应选项集合
     */
    private void getQuestionAddOptions() {
        Map<String, String> params = new HashMap<>();
        params.put("token",mToken);
        params.put("questionnaireId",mQuestionnaire.getId());
        IDataCallBack<Test> callback = new IDataCallBack<Test>() {
            @Override
            public void onSuccess(Test result) {
                List<Test.QuesDetailBean> resultQuesDetail = result.getQuesDetail();
                updateFragment(result);

            }

            @Override
            public void onError(int errorCode, String errorMessage) {

            }
        };
        CommonRequest.getQuestionAddOptions(this, "/getQuestionDetails", this, params, callback);
    }

    /**
     * 根据
     * @param result
     */
    private void updateFragment(Test result) {
    }

    @Override
    public void onClick(View view) {

    }
}
