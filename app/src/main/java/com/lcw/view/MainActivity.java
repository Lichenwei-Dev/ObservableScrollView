package com.lcw.view;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ObservableScrollView.OnObservableScrollViewListener {

    private ObservableScrollView mObservableScrollView;
    private ListViewForScrollView mListView;
    private TextView mImageView;
    private LinearLayout mHeaderContent;

    private int mHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        StatusbarUtils.enableTranslucentStatusbar(this);
        setContentView(R.layout.activity_main);

        //初始化控件
        mObservableScrollView = (ObservableScrollView) findViewById(R.id.sv_main_content);
        mListView = (ListViewForScrollView) findViewById(R.id.lv_main_list);
        mImageView = (TextView) findViewById(R.id.iv_main_topImg);
        mHeaderContent = (LinearLayout) findViewById(R.id.ll_header_content);

        mImageView.setFocusable(true);
        mImageView.setFocusableInTouchMode(true);
        mImageView.requestFocus();

        List<String> mList = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            mList.add("测试数据" + i);
        }
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout
                .simple_expandable_list_item_1, mList);
        mListView.setAdapter(stringArrayAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //获取标题栏高度
        ViewTreeObserver viewTreeObserver = mImageView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mImageView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mHeight = mImageView.getHeight() - mHeaderContent.getHeight();//这里取的高度应该为图片的高度-标题栏
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
            float scale = (float) t / mHeight;
            float alpha = (255 * scale);
            mHeaderContent.setBackgroundColor(Color.argb((int) alpha, 48, 63, 159));
        } else {
            //过顶部图区域，标题栏定色
            mHeaderContent.setBackgroundColor(Color.argb(255, 48, 63, 159));
        }
    }

}
