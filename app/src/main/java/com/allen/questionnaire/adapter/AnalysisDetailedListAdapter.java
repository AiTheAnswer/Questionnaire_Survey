package com.allen.questionnaire.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.allen.questionnaire.R;
import com.allen.questionnaire.model.MultiBarChartBean;
import com.allen.questionnaire.model.PieChartBean;
import com.allen.questionnaire.model.SingleBarChartBean;
import com.allen.questionnaire.service.model.Option;
import com.allen.questionnaire.service.model.OptionStatistics;
import com.allen.questionnaire.service.model.Question;
import com.allen.questionnaire.service.model.QuestionnaireStatistics;
import com.allen.questionnaire.utils.ChartUtils;
import com.allen.questionnaire.view.AutoWrapLinearLayout;
import com.allen.questionnaire.view.ListViewForScrollView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 问卷统计分析详情ListView的适配器
 *
 * @author Renjy
 */
public class AnalysisDetailedListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private Resources mResources;
    private List<QuestionnaireStatistics> mStatisticsList;

    public AnalysisDetailedListAdapter(Context context, List<QuestionnaireStatistics> mStatisticsList) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.mResources = context.getResources();
        this.mStatisticsList = mStatisticsList;
    }

    @Override
    public int getCount() {
        return (null != mStatisticsList) ? mStatisticsList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mStatisticsList.get(position);
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
            convertView = mInflater.inflate(R.layout.item_lst_analysis_detailed, null);
            viewHolder.mTxtQueName = convertView.findViewById(R.id.txt_analysis_detailed_question_name);
            viewHolder.mBarChart = convertView.findViewById(R.id.bar_chart);
            viewHolder.mPieChart = convertView.findViewById(R.id.pie_chart);
            viewHolder.mAutoWrapLinearLayout = convertView.findViewById(R.id.auto_line_layout);
            viewHolder.mLst = convertView.findViewById(R.id.item_lst_option);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        QuestionnaireStatistics statistics = mStatisticsList.get(position);
        Question question = statistics.getQuestion();
        String title = question.getQuestionDes();
        if (question.getType() == 0) {//单选题
            title = "Q" + (position + 1) + ": " + title + "(单选题)";
        } else {//多选题
            title = "Q" + (position + 1) + ": " + title + "(多选题)";
        }
        viewHolder.mTxtQueName.setText(title);
        viewHolder.mAutoWrapLinearLayout.removeAllViews();
        List<OptionStatistics> optionStatistics = statistics.getOptionStatistics();
        String[] stringArray = mResources.getStringArray(R.array.options);
        if (optionStatistics.size() % 2 == 0 || true) {//偶数个选项
            viewHolder.mPieChart.setVisibility(View.GONE);
            viewHolder.mBarChart.setVisibility(View.VISIBLE);
            List<ArrayList<BarEntry>> yValues = new ArrayList<>();

            for (int i = 0; i < optionStatistics.size(); i++) {
                ArrayList<BarEntry> barEntries = new ArrayList<>();
                barEntries.add(new BarEntry(i, optionStatistics.get(i).getCount()));
                yValues.add(barEntries);
            }
            List<String> xValues = new ArrayList<>();
            for (int i = 0; i < optionStatistics.size(); i++) {
                xValues.add(stringArray[i % stringArray.length]);
            }

            IValueFormatter valueFormatter = new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    int formatValue = (int) value;
                    return String.format("%s", formatValue);
                }
            };
            MultiBarChartBean barChartBean = new MultiBarChartBean(yValues, xValues, xValues, valueFormatter, false, MultiBarChartBean.Type.SINGLE);
            ChartUtils.showBarChart(viewHolder.mBarChart, barChartBean);

        } else {//奇数个选项
            viewHolder.mPieChart.setVisibility(View.VISIBLE);
            viewHolder.mBarChart.setVisibility(View.GONE);
            List<String> xValues = new ArrayList<>();
            for (int i = 0; i < optionStatistics.size(); i++) {
                xValues.add(stringArray[i % stringArray.length]);
            }
            List<PieEntry> yValues = new ArrayList<>();
            for (int i = 0; i < optionStatistics.size(); i++) {
                yValues.add(new PieEntry(optionStatistics.get(i).getCount(), xValues.get(i)));
            }
            PieChartBean pieCharBean = new PieChartBean(xValues, yValues);
            ChartUtils.showPieChart(viewHolder.mPieChart, pieCharBean);
        }
        List<Option> options = new ArrayList<>();
        for (int i = 0; i < optionStatistics.size(); i++) {
            options.add(optionStatistics.get(i).getOption());
        }
        OptionsAdapter adapter = new OptionsAdapter(mContext, options);
        viewHolder.mLst.setAdapter(adapter);

        return convertView;
    }

    class ViewHolder {
        private TextView mTxtQueName;
        private BarChart mBarChart;
        private PieChart mPieChart;
        private AutoWrapLinearLayout mAutoWrapLinearLayout;
        private ListViewForScrollView mLst;
    }
}
