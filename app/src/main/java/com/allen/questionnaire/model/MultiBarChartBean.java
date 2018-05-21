package com.allen.questionnaire.model;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * <P>多系列柱状图</P>
 * @author Renjy
 * @version  1.4.0 2018/1/15.
 */

public class MultiBarChartBean {
    /**
     * <p>y轴值的集合</p>
     */
    private List<ArrayList<BarEntry>> yValues;

    /**
     * <p>x轴值的集合</p>
     */
    private List<String> xValues;

    /**
     * <p>图例的名字</p>
     */
    private List<String> mLegends;

    /**
     * <p>值的格式化</p>
     */
    private IValueFormatter mValueFormatter;
    /**
     * <p>是否显示图例</p>
     */
    private boolean mIsShowLegend;
    /**
     * <p>多系列柱状图的类型</p>
     */
    private Type mType;

    /**
     * <p>多系列柱状图的类型枚举</p>
     */
    public enum Type {
        SINGLE,//只有一个x轴的数据
        MULTI//有多个x轴的数据
    }


    public MultiBarChartBean(List<ArrayList<BarEntry>> yValues, List<String> xValues, List<String> legends, IValueFormatter valueFormatter, boolean isShowLegend, Type type) {
        this.yValues = yValues;
        this.xValues = xValues;
        this.mLegends = legends;
        this.mValueFormatter = valueFormatter;
        this.mIsShowLegend = isShowLegend;
        this.mType = type;
    }

    public List<ArrayList<BarEntry>> getyValues() {
        return yValues;
    }

    public void setyValues(List<ArrayList<BarEntry>> yValues) {
        this.yValues = yValues;
    }

    public List<String> getxValues() {
        return xValues;
    }

    public void setxValues(List<String> xValues) {
        this.xValues = xValues;
    }

    public List<String> getLegends() {
        return mLegends;
    }

    public void setLegends(List<String> legends) {
        this.mLegends = legends;
    }

    public IValueFormatter getValueFormatter() {
        return mValueFormatter;
    }

    public void setValueFormatter(IValueFormatter valueFormatter) {
        this.mValueFormatter = valueFormatter;
    }

    public boolean isShowLegend() {
        return mIsShowLegend;
    }

    public void setIsShowLegend(boolean isShowLegend) {
        this.mIsShowLegend = isShowLegend;
    }

    public Type getType() {
        return mType;
    }

    public void setType(Type type) {
        this.mType = type;
    }
}
