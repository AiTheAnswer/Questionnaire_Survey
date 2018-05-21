package com.allen.questionnaire.model;

import com.github.mikephil.charting.data.PieEntry;

import java.util.List;

/**
 * <p>饼图</p>
 *
 * @author chentt
 * @version 2018/3/21 0021.
 */

public class PieChartBean {

    /**
     * xValues
     */
    private List<String> xValues;

    /**
     * YValues
     */
    private List<PieEntry> yValues;

    /**
     * color
     */
    private int[] color;

    /**
     * <p>初始化</p>
     *
     * @param xValues x轴数据
     * @param yValues y轴数据
     * @param color   自定义颜色
     */
    public PieChartBean(List<String> xValues, List<PieEntry> yValues, int[] color) {
        this.xValues = xValues;
        this.yValues = yValues;
        this.color = color;
    }

    /**
     * <p>初始化</p>
     *
     * @param xValues x轴数据
     * @param yValues y轴数据
     */
    public PieChartBean(List<String> xValues, List<PieEntry> yValues) {
        this.xValues = xValues;
        this.yValues = yValues;
    }

    public List<String> getxValues() {
        return xValues;
    }

    public void setxValues(List<String> xValues) {
        this.xValues = xValues;
    }

    public List<PieEntry> getyValues() {
        return yValues;
    }

    public void setyValues(List<PieEntry> yValues) {
        this.yValues = yValues;
    }

    public int[] getColor() {
        return color;
    }

    public void setColor(int[] color) {
        this.color = color;
    }
}
