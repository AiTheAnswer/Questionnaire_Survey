package com.allen.questionnaire.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.questionnaire.R;
import com.allen.questionnaire.activity.AnswerActivity;
import com.allen.questionnaire.adapter.QueAddOptionsAdapter;
import com.allen.questionnaire.service.model.Option;
import com.allen.questionnaire.service.model.Question;
import com.allen.questionnaire.service.model.QuestionAddOptions;
import com.allen.questionnaire.view.ListViewForScrollView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 答题Fragment
 *
 * @author Renjy
 */

public class AnswerFragment extends Fragment {
    @BindView(R.id.txt_questionnaire_type)
    TextView mTxtQuestionnaireType;
    @BindView(R.id.txt_progress)
    TextView mTxtProgress;
    @BindView(R.id.img_answer)
    ImageView mImgAnswer;
    @BindView(R.id.layout_progress)
    RelativeLayout mLayoutProgress;
    @BindView(R.id.txt_question)
    TextView mTxtQuestion;
    @BindView(R.id.lv_option)
    ListViewForScrollView mListView;
    Unbinder unbinder;
    private AnswerActivity mActivity;
    private String mProgress;
    /**
     * 当前的问题
     */
    private Question question;
    /**
     * 问题选项集合
     */
    private List<Option> options;
    /**
     * 选项的适配器
     */
    private QueAddOptionsAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answer, null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        mActivity = (AnswerActivity) getActivity();
        if (question.getType() == 0) {
            mTxtQuestionnaireType.setText("单选题");
        } else {
            mTxtQuestionnaireType.setText("多选题");
        }
        mTxtProgress.setText(mProgress);
        mTxtQuestion.setText(question.getQuestionDes());
        mAdapter = new QueAddOptionsAdapter(getContext(), options, question.getSelectOptions());
        mListView.setAdapter(mAdapter);
    }

    private void initListener() {
        mImgAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mActivity.showAnswerFragment();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                List<Option> selectOptions = question.getSelectOptions();
                Option option = options.get(position);
                option.setSelect(!option.isSelect());
                if (question.getType() == 0) {//单选题
                    selectOptions.clear();
                    if (option.isSelect()) {//是选择的
                        selectOptions.add(option);
                        mActivity.nextQuestion();
                    }
                } else {//多选题
                    if (option.isSelect()) {
                        selectOptions.add(option);
                    } else {
                        selectOptions.remove(option);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    /**
     * 设置数据
     *
     * @param progress           进度
     * @param questionAddOptions 问题和选项
     */
    public void setData(String progress, QuestionAddOptions questionAddOptions) {
        if (TextUtils.isEmpty(progress)) {
            progress = "";
        }
        this.mProgress = progress;
        question = questionAddOptions.getQuestion();
        options = questionAddOptions.getOptionList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
