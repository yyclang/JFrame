package com.zinc.jframe.jframe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;

import com.zinc.jframe.R;
import com.zinc.jframe.jframe.fragment.JLoadMoreFragmentDemo.LoadMoreTestFragment;
import com.zinc.libjframe.view.activity.JToolbarActivity;

/**
 * @author Jiang zinc
 * @date 创建时间：2018/4/23
 * @description
 */

public class ToolbarDemoActivity extends JToolbarActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_toolbar_demo;
    }

    @Override
    protected void initIntent(Intent intent) {

    }

    @Override
    protected void initView() {
        //设置标题
        setTitle("zincPower");

        //隐藏回退按钮
//        hideHomeBtn();

        addLayerFragment(R.id.frame_layout_container, LoadMoreTestFragment.newInstance(0, true));
    }

}
