package com.zinc.jframe.jframe.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.zinc.jframe.R;
import com.zinc.jframe.jframe.fragment.JBaseFragmentDemo.OrdinaryTestFragment;
import com.zinc.jframe.jframe.fragment.JListFragmentDemo.ListTestFragment;
import com.zinc.jframe.jframe.fragment.JLoadMoreFragmentDemo.LoadMoreTestFragment;
import com.zinc.libjframe.view.activity.JBaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiang zinc
 * @date 创建时间：2018/4/22
 * @description
 */

public class FrameContainerActivity extends JBaseActivity {

    private String type;

    @Override
    protected int getLayout() {
        return COMMON_FRAME_LAYOUT;
    }

    @Override
    protected void initView() {
        switch (type){
            case "ordinary":
                addLayerFragment(ID_FRAME_LAYOUT_CONTAINER, new OrdinaryTestFragment());
                break;
            case "lazy":

                break;
            case "list":
                addLayerFragment(ID_FRAME_LAYOUT_CONTAINER, new ListTestFragment());
                break;
            case "loadMore":
                addLayerFragment(ID_FRAME_LAYOUT_CONTAINER, LoadMoreTestFragment.newInstance(0, true));
                break;
        }
    }

    @Override
    protected void initIntent(Intent intent) {
        type = intent.getStringExtra("type");
    }

}
