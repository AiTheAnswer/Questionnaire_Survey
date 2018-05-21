package com.allen.questionnaire.utils;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.allen.questionnaire.model.MultiBarChartBean;
import com.allen.questionnaire.model.PieChartBean;
import com.allen.questionnaire.model.SingleBarChartBean;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 显示图表的工具类
 */
public class ChartUtils {
    public static int[] colors = {Color.parseColor("#89c4ff"), Color.parseColor("#f68686"), Color.parseColor("#fbf5a7"),
            Color.parseColor("#8ff5d2"), Color.parseColor("#e79e85"), Color.parseColor("#99e3fe"), Color.parseColor("#ebc6ff"),
            Color.parseColor("#a6ed8e"), Color.parseColor("#c9b061"), Color.parseColor("#9f9fed"), Color.parseColor("#ee8ab4"),
            Color.parseColor("#597fce"), Color.parseColor("#ced0ce"), Color.parseColor("#ffd0bc"), Color.parseColor("#e4f1fe"),
            Color.parseColor("#f7bd24")};

    /**
     * <p>柱状图</p>
     *
     * @param barChart           柱状图表
     * @param singleBarChartBean 图表数据
     */
    public static void showBarChart(final BarChart barChart, SingleBarChartBean singleBarChartBean) {
        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(false);//设置按比例放缩柱状图
        barChart.setDrawBorders(false);  ////是否在折线图上添加边框
        barChart.setDrawGridBackground(false); // 是否显示表格颜色
        barChart.setGridBackgroundColor(0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度
        barChart.setTouchEnabled(true); // 设置是否可以触摸
        barChart.setDragEnabled(false);// 是否可以拖拽
        barChart.setScaleEnabled(false);// 是否可以缩放
        barChart.setExtraOffsets(0, 10, 0, 10);
        barChart.setDrawBarShadow(false);

        //x坐标轴设置
        XAxis xAxis = barChart.getXAxis();//获取x轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴标签显示位置
        xAxis.setDrawGridLines(false);//不绘制格网线
        xAxis.setGridLineWidth(1f);//设置最小间隔，防止当放大时，出现重复标签。
        xAxis.setTextSize(12);//设置标签字体大小
        xAxis.setGranularity(1);

        YAxis axisLeft = barChart.getAxisLeft();
        axisLeft.setAxisMinimum(0); //设置最小值，不设置会有间隙或显示不对
        axisLeft.setEnabled(false);

        barChart.getAxisRight().setEnabled(false);//禁用右侧y轴

        ArrayList<BarEntry> yValues = singleBarChartBean.getyValues();
        ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
        BarDataSet barDataSet = new BarDataSet(yValues, singleBarChartBean.getLegendName());
        barDataSet.setColor(colors[0]);
        barDataSet.setValueTextSize(12f);
        barDataSet.setHighlightEnabled(false);
        barDataSets.add(barDataSet); // add the datasets
        xAxis.setValueFormatter(new IndexAxisValueFormatter(singleBarChartBean.getxValues()));
        BarData barData = new BarData(barDataSets);
        // 设置数据显示格式
        barData.setBarWidth(0.5f);
        barData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                int formatValue = (int) value;
                return String.format("%s", formatValue);
            }
        });
        barChart.setData(barData);
        barChart.invalidate();
        Legend legend = barChart.getLegend();  //设置比例图
        legend.setEnabled(singleBarChartBean.isShowLegend());
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextColor(Color.parseColor("#666666"));
        legend.setTextSize(15);
        legend.setWordWrapEnabled(true);
        legend.setFormSize(10);
        legend.setXEntrySpace(20);
    }

    public static void showBarChart(final BarChart barChart, MultiBarChartBean multiBarChartBean) {

        barChart.getDescription().setEnabled(false);
        barChart.setPinchZoom(true);//设置按比例放缩柱状图
        barChart.setDrawBorders(false);  ////是否在折线图上添加边框
        barChart.setDrawGridBackground(false); // 是否显示表格颜色
        barChart.setGridBackgroundColor(0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度
        barChart.setTouchEnabled(true); // 设置是否可以触摸
        barChart.setDragEnabled(true);// 是否可以拖拽
        barChart.setScaleEnabled(true);// 是否可以缩放
        barChart.setScaleXEnabled(true);
        barChart.setScaleYEnabled(false);
        barChart.setExtraOffsets(0, 10, 0, 10);
        barChart.setDrawBarShadow(false);

        //x坐标轴设置
        XAxis xAxis = barChart.getXAxis();//获取x轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴标签显示位置
        xAxis.setDrawGridLines(false);//不绘制格网线
        xAxis.setGridLineWidth(1f);//设置最小间隔，防止当放大时，出现重复标签。
        xAxis.setTextSize(12);//设置标签字体大小
        xAxis.setGranularity(1);
        xAxis.setAvoidFirstLastClipping(false);

        YAxis axisLeft = barChart.getAxisLeft();
        axisLeft.setAxisMinimum(0); //设置最小值，不设置会有间隙或显示不对
        axisLeft.setEnabled(false);

        barChart.getAxisRight().setEnabled(false);//禁用右侧y轴
        ArrayList<IBarDataSet> barDataSets = new ArrayList<>();
        List<ArrayList<BarEntry>> yValues = multiBarChartBean.getyValues();
        List<String> xValues = multiBarChartBean.getxValues();
        IValueFormatter formatter = multiBarChartBean.getValueFormatter();
        for (int i = 0; i < yValues.size(); i++) {
            BarDataSet barDataSet = new BarDataSet(yValues.get(i), multiBarChartBean.getLegends() != null ? multiBarChartBean.getLegends().get(i) : "");
            barDataSet.setValueFormatter(formatter);
            barDataSet.setColor(colors[i % colors.length]);
            barDataSet.setValueTextSize(12f);
            barDataSet.setHighlightEnabled(false);

            barDataSets.add(barDataSet); // add the datasets
        }
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));

        BarData barData = new BarData(barDataSets);
        barChart.setData(barData);
        // 设置数据显示格式
        switch (multiBarChartBean.getType()) {
            case SINGLE://每一个系列只有一个x轴数据
                xAxis.setCenterAxisLabels(false);
                barData.setBarWidth(0.5f);
                break;
            case MULTI://每一个系列有多个x轴的数据
                xAxis.setCenterAxisLabels(true);//设置标签居中
                float groupSpace = 0.1f;
                float barSpace = 0.05f;
                int groupSize = yValues.size();
                int groupCount = xValues.size();
                float barWidth = (1 - groupSpace) / groupSize - barSpace;
                barChart.getBarData().setBarWidth(barWidth);
                barChart.groupBars(0, groupSpace, barSpace);
                barChart.getXAxis().setAxisMinimum(0);
                barChart.getXAxis().setAxisMaximum(groupCount);
                break;
        }
        barChart.invalidate();
        Legend legend = barChart.getLegend();  //设置比例图
        legend.setEnabled(multiBarChartBean.isShowLegend());
        legend.setForm(Legend.LegendForm.SQUARE);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setTextColor(Color.parseColor("#666666"));
        legend.setTextSize(15);
        legend.setWordWrapEnabled(true);
        legend.setFormSize(10);
        legend.setXEntrySpace(20);


    }

    /**
     * <p>自定义饼状图的颜色</p>
     *
     * @param pieChart     饼状图
     * @param pieChartBean 数据
     */
    public static void showPieChart(PieChart pieChart, PieChartBean pieChartBean) {
        //显示为圆环
        // 设置饼图中心是否是空心的
        pieChart.setDrawHoleEnabled(true);
        //设置中间圆的半径
        pieChart.setHoleRadius(65f);
        //设置透明圈的半径
        pieChart.setTransparentCircleRadius(0f);
        // 描述信息
        pieChart.getDescription().setEnabled(false);
        pieChart.setNoDataText("");
        // 设置旋转角度
        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);
        pieChart.setDrawEntryLabels(false);
        //设置比例图
        Legend legend = pieChart.getLegend();
        //设置默认为中上
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        PieDataSet dataSet = new PieDataSet(pieChartBean.getyValues(), "");
        //使用系统颜色?使用自定义颜色
        dataSet.setColors(pieChartBean.getColor() == null ? colors : pieChartBean.getColor());
        // 设置饼图区块之间的距离
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);
        dataSet.setValueLineColor(Color.parseColor("#999999"));
        dataSet.setValueLinePart2Length(1f);
        dataSet.setValueLineWidth(1f);
        dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData mPieData = new PieData(dataSet);
        mPieData.setDrawValues(true);
        mPieData.setValueTextSize(12f);
        DecimalFormat df = new DecimalFormat("###,###,##0.00");
        mPieData.setValueFormatter(new PercentFormatter(df));//设置百分比显示
        mPieData.setValueTextColor(Color.parseColor("#333333"));
        pieChart.setData(mPieData);
        // undo all highlights
        pieChart.highlightValues(null);
        pieChart.invalidate();
        // 显示百分比
        pieChart.setUsePercentValues(true);
        // 描述信息
        pieChart.getDescription().setEnabled(false);
        pieChart.setNoDataText("");
        // 设置偏移量
        pieChart.setExtraOffsets(0, 0, 0, 10);
        // 设置滑动减速摩擦系数
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        // 设置饼图中心是否是空心的
        pieChart.setDrawHoleEnabled(false);
        // 设置旋转角度   ？？
        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(false);
        pieChart.setHighlightPerTapEnabled(false);
        pieChart.setDrawEntryLabels(false);
    }

}
