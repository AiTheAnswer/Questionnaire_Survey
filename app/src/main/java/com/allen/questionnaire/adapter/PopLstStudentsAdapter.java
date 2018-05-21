package com.allen.questionnaire.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.allen.questionnaire.R;
import com.allen.questionnaire.service.model.Student;

import java.util.List;

public class PopLstStudentsAdapter extends BaseAdapter {
    private List<Student> studentList;
    private LayoutInflater mInflater;

    public PopLstStudentsAdapter(Context context, List<Student> studentList) {
        this.mInflater = LayoutInflater.from(context);
        this.studentList = studentList;
    }

    @Override
    public int getCount() {
        return (null != studentList) ? studentList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return studentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_pop_window_student_lst, null);
            viewHolder.mTxtStudentName = convertView.findViewById(R.id.pop_lst_student_name);
            viewHolder.mTxtStudentId = convertView.findViewById(R.id.pop_lst_student_id);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Student student = studentList.get(position);
        viewHolder.mTxtStudentName.setText(student.getName());
        viewHolder.mTxtStudentId.setText(student.getStudentId());
        return convertView;
    }

    class ViewHolder {
        private TextView mTxtStudentName;
        private TextView mTxtStudentId;
    }
}
