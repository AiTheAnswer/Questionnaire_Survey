package com.allen.questionnaire.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.allen.questionnaire.R;
import com.allen.questionnaire.activity.AnswerActivity;
import com.allen.questionnaire.adapter.QueAddOptionsAdapter;
import com.allen.questionnaire.service.model.Option;
import com.allen.questionnaire.service.model.Question;
import com.allen.questionnaire.service.model.QuestionAddOptions;
import com.allen.questionnaire.view.ListViewForScrollView;

import java.util.ArrayList;
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
    @BindView(R.id.txt_question)
    TextView mTxtQuestion;
    @BindView(R.id.lv_option)
    ListViewForScrollView mListView;
    Unbinder unbinder;
    private AnswerActivity mActivity;
    private Question question;
    private List<Option> options;
    private QueAddOptionsAdapter mAdapter;
    private List<Option> mSelectOptions;

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
        mSelectOptions = new ArrayList<>();
    }

    private void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Option option = options.get(position);
                option.setSelect(!option.isSelect());
                if (question.getType() == 0) {//单选题
                    mSelectOptions.clear();
                    if (option.isSelect()) {//是选择的
                        mSelectOptions.add(option);
                        mActivity.nextQuestion();
                    }
                } else {//多选题
                    if (option.isSelect()) {
                        mSelectOptions.add(option);
                    } else {
                        mSelectOptions.remove(option);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mTxtQuestion.setText(question.getQuestionDes());
        mAdapter = new QueAddOptionsAdapter(getContext(), options, mSelectOptions);
        mListView.setAdapter(mAdapter);
    }

    /**
     * 获取当前问题的类型
     *
     * @return 0：单选 1： 多选
     */
    public int getQueType() {
        return question.getType();
    }

    /**
     * 设置数据
     *
     * @param questionAddOptions 问题和选项
     */
    public void setData(QuestionAddOptions questionAddOptions) {
        question = questionAddOptions.getQuestion();
        options = questionAddOptions.getOptionList();
    }

    /**
     * 获取选择的选项集合
     * @return 选择的选项集合
     */
    public List<Option> getSelectOptions(){
        return mSelectOptions;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
