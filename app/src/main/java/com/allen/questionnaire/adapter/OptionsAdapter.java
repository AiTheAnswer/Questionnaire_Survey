package com.allen.questionnaire.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.allen.questionnaire.R;
import com.allen.questionnaire.service.model.Option;

import java.util.List;

/**
 * 问题选项的适配器
 */
public class OptionsAdapter extends BaseAdapter {
    private Resources resources;
    private LayoutInflater mInflater;
    private List<Option> mOptionList;
    private String[] options;

    public OptionsAdapter(Context context, List<Option> optionList) {
        this.mInflater = LayoutInflater.from(context);
        this.mOptionList = optionList;
        resources = context.getResources();
        options = context.getResources().getStringArray(R.array.options);
    }

    @Override
    public int getCount() {
        return (null != mOptionList) ? mOptionList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mOptionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        OptionsAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new OptionsAdapter.ViewHolder();
            convertView = mInflater.inflate(R.layout.item_analysis_list_option, null);
            viewHolder.mTxtOption = convertView.findViewById(R.id.item_analysis_txt_option);
            viewHolder.mTxtOptionName = convertView.findViewById(R.id.item_analysis_txt_option_name);
            convertView.setTag(viewHolder);
        }
        Option option = mOptionList.get(position);
        viewHolder = (OptionsAdapter.ViewHolder) convertView.getTag();
        viewHolder.mTxtOption.setBackground(resources.getDrawable(R.drawable.option_bg_un_select));
        viewHolder.mTxtOption.setTextColor(resources.getColor(R.color.black));
        viewHolder.mTxtOptionName.setTextColor(resources.getColor(R.color.black));
        viewHolder.mTxtOption.setText(options[position]);
        viewHolder.mTxtOptionName.setText(option.getOptionDes());
        return convertView;
    }

    class ViewHolder {
        private TextView mTxtOption;
        private TextView mTxtOptionName;
    }
}
