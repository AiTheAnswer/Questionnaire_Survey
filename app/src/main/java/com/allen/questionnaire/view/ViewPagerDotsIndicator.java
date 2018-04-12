package com.allen.questionnaire.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.allen.questionnaire.R;


/**
 * ViewPager的圆点指示器（当前页的圆点随手指滑动）
 *
 * @author Renjy
 */

public class ViewPagerDotsIndicator extends RelativeLayout {
    /**
     * 上下文
     */
    private Context mContext;

    /**
     * 获取资源文件的资源对象
     */
    private Resources mResources;
    /**
     * ViewPager总共的页数
     */
    private int mPagerSum;
    /**
     * 当前选中的页数
     */
    private int mSelectedPosition;
    /**
     * 未选中圆点的布局
     */
    private LinearLayout mUnSelectedLayout;
    /**
     * 选中圆点的布局
     */
    private FrameLayout mSelectedLayout;
    /**
     * 圆点的半径
     */
    private int mDotRadius;
    /**
     * 圆点之间的间隔
     */
    private int mDotMargin;
    /**
     * 选中圆点的图片
     */
    private Drawable mSelectDot;
    /**
     * 未选中圆点的图片
     */
    private Drawable mUnSelectDot;


    public ViewPagerDotsIndicator(Context context) {
        this(context, null);
    }

    public ViewPagerDotsIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewPagerDotsIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 初始化
     * 设置默认的圆点大小和间隔以及圆点图片资源
     *
     * @param context 上下文
     */
    private void init(Context context) {
        this.mContext = context;
        this.mResources = context.getResources();
        this.mDotRadius = getDp2Px(4);
        this.mDotMargin = getDp2Px(12);
        mSelectDot = mResources.getDrawable(R.drawable.indicator_selected_dot);
        mUnSelectDot = mResources.getDrawable(R.drawable.indicator_unselected_dot);
        mUnSelectedLayout = new LinearLayout(mContext);
        mUnSelectedLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mUnSelectedLayout.setGravity(Gravity.CENTER);
        mSelectedLayout = new FrameLayout(mContext);
        mSelectedLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(mUnSelectedLayout);
        addView(mSelectedLayout);
    }

    /**
     * 设置ViewPager页面总数和当前选中的位置
     *
     * @param pagerSum         页面总数
     * @param selectedPosition 选中位置（从0开始）
     */
    public void setPagerNumber(int pagerSum, int selectedPosition) {
        this.mPagerSum = pagerSum;
        this.mSelectedPosition = selectedPosition;
        addUnSelectedDot();
        addSelectedDot();
        requestLayout();
    }

    /**
     * 添加未选中圆点
     */
    private void addUnSelectedDot() {
        if (mPagerSum < 1) {
            return;
        }
        //使用线性布局来放置所有未选中的圆点
        mUnSelectedLayout.removeAllViews();
        for (int i = 0; i < mPagerSum; i++) {
            View view = new View(mContext);
            LinearLayout.LayoutParams unSelectParams = new LinearLayout.LayoutParams(2 * mDotRadius, 2 * mDotRadius);
            view.setBackground(mUnSelectDot);
            if (i != 0) {
                unSelectParams.setMargins(mDotMargin, 0, 0, 0);
            }
            view.setLayoutParams(unSelectParams);
            mUnSelectedLayout.addView(view);
        }

    }

    /**
     * 添加选中圆点
     */
    private void addSelectedDot() {
        if (mPagerSum < 1) {
            return;
        }
        mSelectedLayout.removeAllViews();
        View view = new View(mContext);
        LinearLayout.LayoutParams selectParams = new LinearLayout.LayoutParams(2 * mDotRadius, 2 * mDotRadius);
        view.setBackground(mSelectDot);
        int padding = mSelectedPosition * ( 2 * mDotRadius + mDotMargin);
        mSelectedLayout.setPadding(padding,0,0,0);
        view.setLayoutParams(selectParams);
        mSelectedLayout.addView(view);

    }

    /**
     * 设置当前页的滑动
     * @param position 当前页的位置
     * @param positionOffset 当前页偏移的比例
     */
    public void  scroll(int position, float positionOffset) {
        float offset = (position + positionOffset) * (2 * mDotRadius + mDotMargin);
        mSelectedLayout.setPadding((int) offset, 0, 0, 0);
        requestLayout();
    }

    /**
     * 将dp 转化为px
     *
     * @param dpValue dp值
     * @return 将dp转化为px的值
     */
    private int getDp2Px(int dpValue) {
        final float scale = mResources.getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
