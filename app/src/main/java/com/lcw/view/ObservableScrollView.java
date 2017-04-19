package com.lcw.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 重写ScrollView对外抛出滑动监听数据
 * Create by: chenwei.li
 * Date: 2017/4/18
 * time: 10:09
 * Email: lichenwei.me@foxmail.com
 */
public class ObservableScrollView extends ScrollView {

    private OnObservableScrollViewListener mOnObservableScrollViewListener;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 注册回调接口监听事件
     *
     * @param onObservableScrollViewListener
     */
    public void setOnObservableScrollViewListener(OnObservableScrollViewListener onObservableScrollViewListener) {
        this.mOnObservableScrollViewListener = onObservableScrollViewListener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnObservableScrollViewListener != null) {
            //将所监听到的滑动数据添加
            mOnObservableScrollViewListener.onObservableScrollViewListener(l, t, oldl, oldt);
        }
    }

    /**
     * 添加回调接口，便于把滑动事件的数据向外抛
     */
    public interface OnObservableScrollViewListener {
        void onObservableScrollViewListener(int l, int t, int oldl, int oldt);
    }
}
