package com.allen.questionnaire.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.questionnaire.R;
import com.allen.questionnaire.fragment.AnalysisFragment;
import com.allen.questionnaire.fragment.MeFragment;
import com.allen.questionnaire.fragment.QuestionnairesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.txt_questionnaire)
    TextView mTxtQuestionnaire;
    @BindView(R.id.layout_questionnaire)
    RelativeLayout mLayoutQuestionnaire;
    @BindView(R.id.txt_analysis)
    TextView mTxtAnalysis;
    @BindView(R.id.layout_analysis)
    RelativeLayout mLayoutAnalysis;
    @BindView(R.id.txt_me)
    TextView mTxtMe;
    @BindView(R.id.layout_me)
    RelativeLayout mLayoutMe;
    @BindView(R.id.btn_questionnaire)
    View mBtnQuestionnaire;
    @BindView(R.id.btn_analysis)
    View mBtnAnalysis;
    @BindView(R.id.btn_me)
    View mBtnMe;
    @BindView(R.id.layout_content)
    FrameLayout mLayoutContent;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;

    /**
     * 问卷Fragment
     */
    private QuestionnairesFragment questionnaireFragment;
    /**
     * 分析Fragment
     */
    private AnalysisFragment analysisFragment;
    /**
     * 我的Fragment
     */
    private MeFragment meFragment;

    /**
     * Fragment的管理者
     */
    private FragmentManager fragmentManager;

    enum UI_TYPE {
        TYPE_QUESTIONNAIRE,//问卷
        TYPE_ANALYSIS,//分析
        TYPE_ME//我
    }

    private UI_TYPE mCurrentType = UI_TYPE.TYPE_QUESTIONNAIRE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initData();
        initListener();

    }

    private void initData() {
        mBtnQuestionnaire.setSelected(true);
        mTxtQuestionnaire.setTextColor(getResources().getColor(R.color.home_select));
        mTxtTitle.setText("问卷");
        questionnaireFragment = new QuestionnairesFragment();
        analysisFragment = new AnalysisFragment();
        meFragment = new MeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.layout_content, questionnaireFragment)
                .add(R.id.layout_content, analysisFragment).add(R.id.layout_content, meFragment).commit();
        fragmentManager.beginTransaction().hide(analysisFragment).hide(meFragment).commit();

    }

    private void initListener() {
        mLayoutQuestionnaire.setOnClickListener(this);
        mLayoutAnalysis.setOnClickListener(this);
        mLayoutMe.setOnClickListener(this);
    }

    /**
     * 切换Fragment
     *
     * @param uiType Fragment的类型
     */
    private void switchFragment(UI_TYPE uiType) {
        if (mCurrentType == uiType) {
            return;
        }
        mCurrentType = uiType;
        updateBottomLabel();
        fragmentManager.beginTransaction().hide(questionnaireFragment).hide(analysisFragment).hide(meFragment).commit();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (mCurrentType) {
            case TYPE_QUESTIONNAIRE://问卷
                fragmentTransaction.show(questionnaireFragment);
                mTxtTitle.setText("问卷");
                mTxtTitle.setVisibility(View.VISIBLE);
                break;
            case TYPE_ANALYSIS://分析
                fragmentTransaction.show(analysisFragment);
                mTxtTitle.setText("分析");
                mTxtTitle.setVisibility(View.VISIBLE);
                break;
            case TYPE_ME://我
                fragmentTransaction.show(meFragment);
                mTxtTitle.setText("我的");
                mTxtTitle.setVisibility(View.VISIBLE);
                break;
            default:
                fragmentTransaction.show(questionnaireFragment);
                break;
        }
        fragmentTransaction.commit();

    }

    /**
     * 更新底部标签
     */
    private void updateBottomLabel() {
        //所有的标签置为未选中
        mBtnQuestionnaire.setSelected(false);
        mTxtQuestionnaire.setTextColor(getResources().getColor(R.color.home_un_select));
        mBtnAnalysis.setSelected(false);
        mTxtAnalysis.setTextColor(getResources().getColor(R.color.home_un_select));
        mBtnMe.setSelected(false);
        mTxtMe.setTextColor(getResources().getColor(R.color.home_un_select));
        switch (mCurrentType) {
            case TYPE_QUESTIONNAIRE://问卷
                mBtnQuestionnaire.setSelected(true);
                mTxtQuestionnaire.setTextColor(getResources().getColor(R.color.home_select));
                break;
            case TYPE_ANALYSIS://分析
                mBtnAnalysis.setSelected(true);
                mTxtAnalysis.setTextColor(getResources().getColor(R.color.home_select));
                break;
            case TYPE_ME://我
                mBtnMe.setSelected(true);
                mTxtMe.setTextColor(getResources().getColor(R.color.home_select));
                break;
            default:
                mBtnQuestionnaire.setSelected(true);
                mTxtQuestionnaire.setTextColor(getResources().getColor(R.color.home_select));
                break;

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_questionnaire://问卷
                switchFragment(UI_TYPE.TYPE_QUESTIONNAIRE);
                break;
            case R.id.layout_analysis://分析
                switchFragment(UI_TYPE.TYPE_ANALYSIS);
                break;
            case R.id.layout_me://我
                switchFragment(UI_TYPE.TYPE_ME);
                break;
        }
    }
}
