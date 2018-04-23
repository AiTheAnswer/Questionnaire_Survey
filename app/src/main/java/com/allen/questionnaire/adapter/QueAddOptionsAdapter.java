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
 * 问题和选项的列表
 */
public class QueAddOptionsAdapter extends BaseAdapter {
    private Resources resources;
    private LayoutInflater mInflater;
    private List<Option> mOptionList;
    private List<Option> mSelectOptions;
    private String [] options;

    public QueAddOptionsAdapter(Context context, List<Option> optionList, List<Option> selectOptions) {
        this.mInflater = LayoutInflater.from(context);
        this.mOptionList = optionList;
        this.mSelectOptions = selectOptions;
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
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_lst_option,null);
            viewHolder.mTxtOption = convertView.findViewById(R.id.item_txt_option);
            viewHolder.mTxtOptionName = convertView.findViewById(R.id.item_txt_option_name);
            convertView.setTag(viewHolder);
        }
        Option option = mOptionList.get(position);
        viewHolder = (ViewHolder) convertView.getTag();
        if(mSelectOptions.contains(option)){
            viewHolder.mTxtOption.setBackground(resources.getDrawable(R.drawable.option_bg_select));
            viewHolder.mTxtOption.setTextColor(resources.getColor(R.color.home_select));
            viewHolder.mTxtOptionName.setTextColor(resources.getColor(R.color.home_select));
        }else{
            viewHolder.mTxtOption.setBackground(resources.getDrawable(R.drawable.option_bg_un_select));
            viewHolder.mTxtOption.setTextColor(resources.getColor(R.color.home_un_select));
            viewHolder.mTxtOptionName.setTextColor(resources.getColor(R.color.home_un_select));
        }
        viewHolder.mTxtOption.setText(options[position]);
        viewHolder.mTxtOptionName.setText(option.getOptionDes());
        return convertView;
    }
    class ViewHolder{
        private TextView mTxtOption;
        private TextView mTxtOptionName;
    }
}
