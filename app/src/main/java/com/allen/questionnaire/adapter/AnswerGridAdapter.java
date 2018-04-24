package com.allen.questionnaire.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.allen.questionnaire.R;
import com.allen.questionnaire.service.model.Question;

import java.util.List;

public class AnswerGridAdapter extends BaseAdapter {
    private Resources resources;
    private LayoutInflater mInflater;
    private List<Question> mQuestionList;

    public AnswerGridAdapter(Context context, List<Question> questionList) {
        this.mInflater = LayoutInflater.from(context);
        resources = context.getResources();
        this.mQuestionList = questionList;
    }

    @Override
    public int getCount() {
        return (null != mQuestionList) ? mQuestionList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mQuestionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_grid_answer_fragment, null);
            viewHolder.mTxtAnswer = convertView.findViewById(R.id.item_txt_answer);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        Question question = mQuestionList.get(position);
        if (question.getSelectOptions().size() > 0) {
            viewHolder.mTxtAnswer.setBackground(resources.getDrawable(R.drawable.bg_answer_select));
        } else {
            viewHolder.mTxtAnswer.setBackground(resources.getDrawable(R.drawable.bg_answer_un_select));
        }
        viewHolder.mTxtAnswer.setText(String.format("%s", position + 1));
        return convertView;
    }

    class ViewHolder {
        private TextView mTxtAnswer;
    }
}
