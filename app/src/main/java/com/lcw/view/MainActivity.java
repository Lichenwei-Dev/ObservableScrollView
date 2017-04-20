package com.lcw.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 高仿美团APP页面滑动标题栏渐变效果
 * Create by: chenwei.li
 * Date: 2017/4/18
 * Time: 下午10:51
 * Email: lichenwei.me@foxmail.com
 */
public class MainActivity extends AppCompatActivity implements ObservableScrollView.OnObservableScrollViewListener {

    private ObservableScrollView mObservableScrollView;
    private TextView mTextView;
    private LinearLayout mHeaderContent;

    private int mHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置透明状态栏
        StatusbarUtils.enableTranslucentStatusbar(this);
        setContentView(R.layout.activity_main);

        //初始化控件
        mObservableScrollView = (ObservableScrollView) findViewById(R.id.sv_main_content);
        mTextView = (TextView) findViewById(R.id.tv_main_topContent);
        mHeaderContent = (LinearLayout) findViewById(R.id.ll_header_content);

        //获取标题栏高度
        ViewTreeObserver viewTreeObserver = mTextView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mHeight = mTextView.getHeight() - mHeaderContent.getHeight();//这里取的高度应该为图片的高度-标题栏
                //注册滑动监听
                mObservableScrollView.setOnObservableScrollViewListener(MainActivity.this);
            }
        });
    }


    /**
     * 获取ObservableScrollView的滑动数据
     *
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    public void onObservableScrollViewListener(int l, int t, int oldl, int oldt) {
        if (t <= 0) {
            //顶部图处于最顶部，标题栏透明
            mHeaderContent.setBackgroundColor(Color.argb(0, 48, 63, 159));
        } else if (t > 0 && t < mHeight) {
            //滑动过程中，渐变
            float scale = (float) t / mHeight;//算出滑动距离比例
            float alpha = (255 * scale);//得到透明度
            mHeaderContent.setBackgroundColor(Color.argb((int) alpha, 48, 63, 159));
        } else {
            //过顶部图区域，标题栏定色
            mHeaderContent.setBackgroundColor(Color.argb(255, 48, 63, 159));
        }
    }

}
