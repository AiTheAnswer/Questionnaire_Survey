package com.allen.questionnaire.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.allen.questionnaire.R;
import com.allen.questionnaire.service.model.Questionnaire;

import java.util.List;

/**
 * 问卷列表的适配器
 */

public class QuestionnaireListAdapter extends BaseAdapter {
    private List<Questionnaire> mQuestionnaireList;
    private LayoutInflater mInflater;

    public QuestionnaireListAdapter(Context context, List<Questionnaire> mQuestionnaireList) {
        this.mInflater = LayoutInflater.from(context);
        this.mQuestionnaireList = mQuestionnaireList;
    }

    @Override
    public int getCount() {
        return (null != mQuestionnaireList) ? mQuestionnaireList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mQuestionnaireList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(null == convertView){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_list_view_questionnaire,null);
            viewHolder.mTxtTitle = convertView.findViewById(R.id.txt_item_que_name);
            viewHolder.mTxtUse = convertView.findViewById(R.id.txt_item_que_use);
            viewHolder.mTxtUserNumber = convertView.findViewById(R.id.txt_item_use_number);
            viewHolder.mTxtQuestionNum = convertView.findViewById(R.id.txt_item_que_number);
            convertView.setTag(viewHolder);
        }
        Questionnaire questionnaire = mQuestionnaireList.get(position);
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.mTxtTitle.setText(questionnaire.getQuestionnaireName());
        if(!questionnaire.isUse()){
            viewHolder.mTxtUse.setVisibility(View.INVISIBLE);
        }else{
            viewHolder.mTxtUse.setVisibility(View.VISIBLE);
        }
        viewHolder.mTxtUserNumber.setText(questionnaire.getUseNumber()+"");
        viewHolder.mTxtQuestionNum.setText(questionnaire.getQuestionNumber()+"");
        return convertView;
    }
    class ViewHolder{
        private TextView mTxtTitle;
        private TextView mTxtUse;
        private TextView mTxtUserNumber;
        private TextView mTxtQuestionNum;
    }
}
