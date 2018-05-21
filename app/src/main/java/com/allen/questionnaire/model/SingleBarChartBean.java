package com.allen.questionnaire.model;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>单系列柱状图</p>
 * @author Renjy
 * @version 1.4.0 2018/1/15.
 */

public class SingleBarChartBean {
    /**
     * <p>y轴值的集合</p>
     */
    private ArrayList<BarEntry> yValues;

    /**
     * <p>x轴值的集合</p>
     */
    private List<String> xValues;

    /**
     * <p>图例的名字 (默认为空串)</p>
     */
    private String mLegendName = "";


    /**
     * <p>是否展示图例</p>
     */
    private boolean mIsShowLegend;

    public SingleBarChartBean(ArrayList<BarEntry> yValues, List<String> xValues, boolean isShowLegend) {
        this.yValues = yValues;
        this.xValues = xValues;
        this.mIsShowLegend = isShowLegend;
    }

    public SingleBarChartBean(ArrayList<BarEntry> yValues, List<String> xValues, String legendName, boolean isShowLegend) {
        this.yValues = yValues;
        this.xValues = xValues;
        this.mLegendName = legendName;
        this.mIsShowLegend = isShowLegend;
    }

    public ArrayList<BarEntry> getyValues() {
        return yValues;
    }

    public void setyValues(ArrayList<BarEntry> yValues) {
        this.yValues = yValues;
    }

    public List<String> getxValues() {
        return xValues;
    }

    public void setxValues(List<String> xValues) {
        this.xValues = xValues;
    }

    public String getLegendName() {
        return mLegendName;
    }

    public void setLegendName(String mLegendName) {
        this.mLegendName = mLegendName;
    }


    public boolean isShowLegend() {
        return mIsShowLegend;
    }

    public void setIsShowLenged(boolean isShowLegend) {
        this.mIsShowLegend = isShowLegend;
    }
}

