package com.allen.questionnaire.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.allen.questionnaire.R;
import com.allen.questionnaire.adapter.PopLstAdapter;
import com.allen.questionnaire.service.model.Category;

import java.util.List;

/**
 * 显示选择问卷类型的PopupWindow
 *
 * @author Renjy
 */
public class SelectCategoryPopupWindow extends PopupWindow {
    private Window window;
    private ListView mListView;
    private PopLstAdapter mAdapter;
    private Category mSelectedCategory;

    public SelectCategoryPopupWindow(Activity activity, final List<Category> categoryList, final int selectedCategoryId) {
        super(LayoutInflater.from(activity).inflate(R.layout.pop_window_select_category, null), FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        window = activity.getWindow();
        mListView = getContentView().findViewById(R.id.pop_lst);
        mAdapter = new PopLstAdapter(activity, categoryList, selectedCategoryId);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedCategory = categoryList.get(position);
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
    public Category getSelectedCategory() {
        return mSelectedCategory;
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
