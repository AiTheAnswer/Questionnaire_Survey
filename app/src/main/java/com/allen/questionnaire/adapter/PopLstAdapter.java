package com.allen.questionnaire.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.allen.questionnaire.R;
import com.allen.questionnaire.service.model.Category;

import java.util.List;

/**
 * 选择问卷类型的popupWindow中列表的适配器
 *
 * @author Renjy
 */
public class PopLstAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Category> mCategoryList;
    private int selectCategoryId;
    private Resources mResources;

    public PopLstAdapter(Context context, List<Category> categoryList, int selectCategoryId) {
        this.mInflater = LayoutInflater.from(context);
        this.mCategoryList = categoryList;
        this.selectCategoryId = selectCategoryId;
        this.mResources = context.getResources();
    }

    @Override
    public int getCount() {
        return (null != mCategoryList) ? mCategoryList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mCategoryList.get(position);
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
            convertView = mInflater.inflate(R.layout.item_pop_window_select_category, null);
            viewHolder.mTxtCategoryName = convertView.findViewById(R.id.item_pop_category_name);
            viewHolder.mSpaceLine = convertView.findViewById(R.id.item_pop_line);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        Category category = mCategoryList.get(position);
        viewHolder.mTxtCategoryName.setText(category.getCategoryName());
        if (category.getId() == selectCategoryId) {
            viewHolder.mTxtCategoryName.setTextColor(mResources.getColor(R.color.home_select));
        } else {
            viewHolder.mTxtCategoryName.setTextColor(mResources.getColor(R.color.black));
        }
        if (position == mCategoryList.size() - 1) {
            viewHolder.mSpaceLine.setVisibility(View.GONE);
        } else {
            viewHolder.mSpaceLine.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    class ViewHolder {
        private TextView mTxtCategoryName;
        private View mSpaceLine;
    }
}
