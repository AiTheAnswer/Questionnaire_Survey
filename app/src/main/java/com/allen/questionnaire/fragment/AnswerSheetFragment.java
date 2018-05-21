package com.allen.questionnaire.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.allen.questionnaire.R;
import com.allen.questionnaire.activity.AnswerActivity;
import com.allen.questionnaire.adapter.AnswerSheetGridAdapter;
import com.allen.questionnaire.service.model.Question;
import com.allen.questionnaire.service.model.QuestionAddOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 答题卡Fragment
 *
 * @author Renjy
 */
public class AnswerSheetFragment extends Fragment {
    @BindView(R.id.grid_view)
    GridView gridView;
    Unbinder unbinder;
    @BindView(R.id.txt_answer_progress)
    TextView mTxtAnswerProgress;
    private AnswerActivity activity;
    private List<QuestionAddOptions> mQuesDetail;
    private List<Question> questionList;
    private AnswerSheetGridAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_answer_sheet, null);
        unbinder = ButterKnife.bind(this, view);
        initListener();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                activity.setCurrentPage(position);
            }
        });
    }

    private void initData() {
        questionList = new ArrayList<>();
        for (QuestionAddOptions addOptions : mQuesDetail) {
            questionList.add(addOptions.getQuestion());
        }
        setProgress();
        activity = (AnswerActivity) getActivity();
        mAdapter = new AnswerSheetGridAdapter(activity, questionList);
        gridView.setAdapter(mAdapter);
    }

    /**
     * 设置答题进度
     */
    private void setProgress() {
        int completeNum = 0;
        for (Question question : questionList) {
            if (question.getSelectOptions().size() > 0) {
                completeNum++;
            }
        }
        mTxtAnswerProgress.setText(String.format("%s", "已完成(" + completeNum + "/" + questionList.size() + ")"));

    }

    /**
     * 设置数据
     *
     * @param quesDetail 问题和选项集合
     */
    public void setData(List<QuestionAddOptions> quesDetail) {
        this.mQuesDetail = quesDetail;
    }

    /**
     * 更新数据
     */
    public void updateData() {
        setProgress();
        mAdapter.notifyDataSetChanged();
    }
}
