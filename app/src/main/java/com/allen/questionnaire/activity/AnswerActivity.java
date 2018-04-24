package com.allen.questionnaire.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.allen.questionnaire.R;
import com.allen.questionnaire.adapter.ViewPagerAdapter;
import com.allen.questionnaire.fragment.AnswerFragment;
import com.allen.questionnaire.fragment.AnswerSheetFragment;
import com.allen.questionnaire.service.datatrasfer.IDataCallBack;
import com.allen.questionnaire.service.model.QuestionAddOptions;
import com.allen.questionnaire.service.model.Questionnaire;
import com.allen.questionnaire.service.model.RespQueDetail;
import com.allen.questionnaire.service.model.RespQueDetailObject;
import com.allen.questionnaire.service.net.CommonRequest;
import com.allen.questionnaire.utils.SharedPreferenceUtils;

import java.util.ArrayList;
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
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.txt_previous_question)
    TextView mTxtPreviousQuestion;
    @BindView(R.id.txt_next_question)
    TextView mTxtNextQuestion;
    @BindView(R.id.layout_page_turning)
    LinearLayout mLayoutPageTurning;
    @BindView(R.id.txt_commit_answer)
    TextView mTxtCommitAnswer;
    private SharedPreferenceUtils mPreferenceUtils;
    private Questionnaire mQuestionnaire;
    private String mToken = "";
    private List<Fragment> mFragments;
    private ViewPagerAdapter mAdapter;
    /**
     * 当前选择的页
     */
    private int mCurrentPage = 0;

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
        mTxtPreviousQuestion.setOnClickListener(this);
        mTxtNextQuestion.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == mFragments.size() - 1) {//答题卡
                    AnswerSheetFragment sheetFragment = (AnswerSheetFragment) mFragments.get(mFragments.size() - 1);
                    sheetFragment.updateData();
                    mLayoutPageTurning.setVisibility(View.GONE);
                    mTxtCommitAnswer.setVisibility(View.VISIBLE);
                    return;
                } else {
                    mLayoutPageTurning.setVisibility(View.VISIBLE);
                    mTxtCommitAnswer.setVisibility(View.GONE);
                }
                if (position == 0) {
                    mTxtPreviousQuestion.setTextColor(getResources().getColor(R.color.grey));
                    mTxtPreviousQuestion.setClickable(false);
                } else {
                    mTxtPreviousQuestion.setTextColor(getResources().getColor(R.color.white));
                    mTxtPreviousQuestion.setClickable(true);
                }
                if (position == mFragments.size() - 2) {
                    mTxtNextQuestion.setTextColor(getResources().getColor(R.color.grey));
                    mTxtNextQuestion.setClickable(false);
                } else {
                    mTxtNextQuestion.setTextColor(getResources().getColor(R.color.white));
                    mTxtNextQuestion.setClickable(true);
                }
                mCurrentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        mPreferenceUtils = SharedPreferenceUtils.getInstance(this);
        mToken = mPreferenceUtils.getPreferenceString(TOKEN, "");
        mQuestionnaire = (Questionnaire) getIntent().getExtras().getSerializable("questionnaire");
        mFragments = new ArrayList<>();
        mTxtTitle.setText(mQuestionnaire.getQuestionnaireName());
        mTxtPreviousQuestion.setTextColor(getResources().getColor(R.color.grey));
        mTxtPreviousQuestion.setClickable(false);
        getQuestionAddOptions();
    }

    /**
     * 获取当前问卷的问题和对应选项集合
     */
    private void getQuestionAddOptions() {
        Map<String, String> params = new HashMap<>();
        params.put("token", mToken);
        params.put("questionnaireId", mQuestionnaire.getId());
        IDataCallBack<RespQueDetailObject> callback = new IDataCallBack<RespQueDetailObject>() {
            @Override
            public void onSuccess(RespQueDetailObject result) {
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
     *
     * @param result
     */
    private void updateFragment(RespQueDetailObject result) {
        RespQueDetail respQueDetail = result.getObject();
        if (null == respQueDetail || null == respQueDetail.getQuesDetail() || respQueDetail.getQuesDetail().size() < 1) {
            return;
        }
        List<QuestionAddOptions> quesDetail = respQueDetail.getQuesDetail();
        for (int i = 0; i < quesDetail.size(); i++) {
            QuestionAddOptions questionAddOptions = quesDetail.get(i);
            AnswerFragment answerFragment = new AnswerFragment();
            String progress = formatProgress(i + 1, quesDetail.size());
            answerFragment.setData(progress, questionAddOptions);
            mFragments.add(answerFragment);
        }
        if (mFragments.size() > 0) {
            //添加选项卡
            AnswerSheetFragment sheetFragment = new AnswerSheetFragment();
            sheetFragment.setData(quesDetail);
            mFragments.add(sheetFragment);
            //设置初始问题类型 和当前进度
            AnswerFragment fragment = (AnswerFragment) mFragments.get(0);
        }
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
    }

    /**
     * 进度格式化
     *
     * @param progress 进度
     * @param total    总数
     * @return 格式化的数值
     */
    private String formatProgress(int progress, int total) {
        return String.format("%s", progress + "/" + total);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back://返回按钮
                finish();
                break;
            case R.id.txt_previous_question:
                mViewPager.setCurrentItem(mCurrentPage - 1);
                break;
            case R.id.txt_next_question:
                mViewPager.setCurrentItem(mCurrentPage + 1);
                break;
        }
    }

    /**
     * 切换到下一个问题
     */
    public void nextQuestion() {
        mViewPager.setCurrentItem(mCurrentPage + 1);
    }

    /**
     * 切花到指定页
     *
     * @param page 指定页
     */
    public void setCurrentPage(int page) {
        if (page >= 0 && page < mAdapter.getCount()) {
            mViewPager.setCurrentItem(page);
        }
    }

    /**
     * 显示答题卡Fragment
     */
    public void showAnswerFragment() {
        mViewPager.setCurrentItem(mAdapter.getCount() - 1);
    }
}
