package com.allen.questionnaire.view;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.allen.questionnaire.R;
import com.allen.questionnaire.adapter.PopLstStudentsAdapter;
import com.allen.questionnaire.service.model.Student;
import com.allen.questionnaire.utils.DensityUtil;

import java.util.List;

/**
 * 显示学生列表的PopupWindow
 *
 * @author Renjy
 */
public class StudentListPopupWindow extends PopupWindow {
    private Window window;
    private ListView mListView;
    private PopLstStudentsAdapter mAdapter;
    private Student mSelectedStudent;

    public StudentListPopupWindow(Activity activity, final List<Student> studentList) {
        super(LayoutInflater.from(activity).inflate(R.layout.pop_window_show_student_list, null)
                , (int) (DensityUtil.getScreenWidth(activity) * 0.7f), (int) (DensityUtil.getScreenWidth(activity) * 0.56f));
        window = activity.getWindow();
        mListView = getContentView().findViewById(R.id.pop_window_student_list);
        mAdapter = new PopLstStudentsAdapter(activity, studentList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedStudent = studentList.get(position);
                dismiss();
            }
        });
        setFocusable(false);
        setBackgroundDrawable(new BitmapDrawable());
        setClippingEnabled(false);
        setOutsideTouchable(false);
    }

    /**
     * 获取选择的类别Id
     *
     * @return
     */
    public Student getSelectedStudent() {
        return mSelectedStudent;
    }


    public void show() {
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = 0.5f;
        window.setAttributes(attributes);
        showAtLocation(window.getDecorView(), Gravity.CENTER, 0, 0);
    }

    public void dismiss() {
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = 1.0f;
        window.setAttributes(attributes);
        super.dismiss();
    }
}
