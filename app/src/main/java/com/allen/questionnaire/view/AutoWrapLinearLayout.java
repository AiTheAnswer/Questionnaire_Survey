package com.allen.questionnaire.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Allen on 2017/12/11.
 */

public class AutoWrapLinearLayout extends ViewGroup {
    private Context mContext;
    //ViewGroup的宽度
    private int mWidth;
    //ViewGroup的高度
    private int mHeight;
    //每一行是否充满
    private boolean isFull = false;
    //每一行View显示的方式
    private Gravity mGravity = Gravity.LEFT;

    //行之间的间隔
    private float mHorizontalSpace;
    //列之间的间隔
    private float mVerticalSpace;
    //所有行View的集合
    private List<WrapLine> mWrapLineGroup;

    public AutoWrapLinearLayout(Context context) {
        this(context, null);
    }

    public AutoWrapLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoWrapLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    /**
     * 设置每一行View的显示方式
     *
     * @param gravity
     */
    public void setGravity(Gravity gravity) {
        this.mGravity = gravity;
        requestLayout();
    }

    /**
     * 设置是否充满父容器
     *
     * @param isFull
     */
    public void isFull(boolean isFull) {
        this.isFull = isFull;
    }

    /**
     * 设置水View之间的间隔
     *
     * @param horizontalSpace
     */
    public void setHorizontalSpace(int horizontalSpace) {
        this.mHorizontalSpace = dip2px(mContext, horizontalSpace);
    }

    /**
     * 设置垂直View之间的间隔
     *
     * @param verticalSpace
     */
    public void setVerticalSpace(int verticalSpace) {
        this.mVerticalSpace = dip2px(mContext, verticalSpace);
    }

    /**
     * 根据子View的宽高来计算出父布局的宽高
     * 并将子View进行分组
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        mWidth = 0;
        mHeight = 0;
        //计算子View的宽高，调用子View的getMeasureWidth或者getMeasureHeight必须执行下一行代码
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        //设置宽度
        switch (widthMode) {
            case MeasureSpec.EXACTLY://match_parent 确定值
                mWidth = width;
                break;
            case MeasureSpec.AT_MOST://wrap_content 随子控件的大小或者内容进行变化，只要不超过父容器
                mWidth += getPaddingLeft() + getPaddingRight();
                for (int i = 0; i < childCount; i++) {
                    mWidth += getChildAt(i).getMeasuredWidth();
                    if (mWidth > width) {
                        mWidth = width;
                        break;
                    }
                }
                break;
            case MeasureSpec.UNSPECIFIED://一般是AdapterView，大小没有限制
                break;
            default:
                break;
        }
        /**
         * 不能够在定义属性时初始化，因为onMeasure方法会多次调用
         */
        mWrapLineGroup = new ArrayList<>();
        WrapLine wrapLine = new WrapLine();
        for (int i = 0; i < childCount; i++) {
            if (wrapLine.getRowContentWidth() + getPaddingRight() + getPaddingLeft() + getChildAt(i).getMeasuredWidth() <= mWidth) {
                wrapLine.addView(getChildAt(i));
            } else {
                mWrapLineGroup.add(wrapLine);
                wrapLine = new WrapLine();
                wrapLine.addView(getChildAt(i));
            }
        }
        mWrapLineGroup.add(wrapLine);
        mHeight += getPaddingTop() + getPaddingBottom();
        for (int i = 0; i < mWrapLineGroup.size(); i++) {
            if (i > 0) {
                mHeight += mVerticalSpace;
            }
            mHeight += mWrapLineGroup.get(i).rowHeight;
        }
        switch (heightMode) {
            case MeasureSpec.EXACTLY://确定值
                mHeight = height;
                break;
            case MeasureSpec.AT_MOST://限制了最大值
                mHeight = (mHeight > height) ? height : mHeight;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
            default:
                break;
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        top = getPaddingTop();
        for (int i = 0; i < mWrapLineGroup.size(); i++) {
            WrapLine wrapLine = mWrapLineGroup.get(i);
            int space = 0;
            int totalSpace = mWidth - wrapLine.getRowContentWidth() - getPaddingLeft() - getPaddingRight();
            if (isFull) {
                space = totalSpace / wrapLine.viewList.size() / 2;
                left = getPaddingLeft();
            } else {
                left = getPaddingLeft();
            }
            for (int j = 0; j < wrapLine.viewList.size(); j++) {
                View view = wrapLine.viewList.get(j);
                if (isFull) {//充满整行
                    view.setPadding(view.getPaddingLeft() + space, view.getPaddingTop(), view.getPaddingRight() + space, view.getPaddingBottom());
                    view.layout(left, top, left + view.getMeasuredWidth(), top + view.getMeasuredHeight());
                    left += view.getMeasuredWidth() + mHorizontalSpace + space + space;
                } else if (mGravity == Gravity.LEFT) {//居左
                    view.layout(left, top, left + view.getMeasuredWidth(), top + view.getMeasuredHeight());
                    left += view.getMeasuredWidth() + mHorizontalSpace;
                } else if (mGravity == Gravity.CENTER) {//居中
                    view.layout(left + totalSpace / 2, top, left + +totalSpace / 2 + view.getMeasuredWidth(), top + view.getMeasuredHeight());
                    left += view.getMeasuredWidth() + mHorizontalSpace;
                } else if (mGravity == Gravity.RIGHT) {//局右
                    view.layout(left + totalSpace, top, left + totalSpace + view.getMeasuredWidth(), top + view.getMeasuredHeight());
                    left += view.getMeasuredWidth() + mHorizontalSpace;
                }

            }
            top += wrapLine.rowHeight + mVerticalSpace;
        }
    }

    /**
     * 将dp 转化为px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 同一行View的集合
     */
    private class WrapLine {
        //一行View的集合
        private List<View> viewList = new ArrayList<>();
        //行的高度
        private int rowHeight;
        //行内容的宽度
        private int rowContentWidth;

        private void addView(View view) {
            if (viewList.size() > 0) {
                rowContentWidth += mHorizontalSpace;
            }
            rowContentWidth += view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();
            rowHeight = (measuredHeight > rowHeight) ? measuredHeight : rowHeight;
            viewList.add(view);
        }

        private int getRowContentWidth() {
            return rowContentWidth;
        }
    }

    /**
     * 没一行View显示方式的枚举
     */
    public enum Gravity {
        LEFT,//居左
        CENTER,//居中
        RIGHT//局右
    }

}