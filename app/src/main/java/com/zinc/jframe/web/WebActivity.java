package com.zinc.jframe.web;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zinc.jframe.R;
import com.zinc.libjframe.config.JFrameManager;
import com.zinc.libjframe.view.activity.JBaseActivity;
import com.zinc.libjframe.web.JWebViewFragment;

/**
 * @author Jiang zinc
 * @date 创建时间：2018/5/9
 * @description
 */

public class WebActivity extends JBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JFrameManager.getInstance().setIsDebug(true);
    }

    @Override
    protected int getLayout() {
        return R.layout.j_common_frame_layout;
    }

    @Override
    protected void initIntent(Intent intent) {

    }

    @Override
    protected void initView() {

//        addLayerFragment(R.id.frame_layout_container, JWebViewFragment.newInstance("https://github.com/ReactiveX/RxJava"));
        addLayerFragment(R.id.frame_layout_container, JWebViewFragment.newInstance("https://www.jianshu.com/u/9dfaf364d57f"));
//        addLayerFragment(R.id.frame_layout_container, JWebViewFragment.newInstance("https://www.baidu.com"));

    }

}
