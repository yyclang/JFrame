package com.zinc.libjframe.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.zinc.libjframe.R;
import com.zinc.libjframe.utils.FragmentCompat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiang zinc
 * @date 创建时间：2018/4/20
 * @description activity 基类
 */

public abstract class JBaseActivity extends AppCompatActivity {

    //装载FrameLayout的布局容器
    protected final static int COMMON_FRAME_LAYOUT = R.layout.j_common_frame_layout;
    //装载JRecycleView的布局容器
    protected final static int COMMON_RECYCLE_VIEW_LAYOUT= R.layout.j_common_recycle_view;
    //装载FrameLayout的布局容器中的FrameLayout的id
    protected final static int ID_FRAME_LAYOUT_CONTAINER = R.id.frame_layout_container;
    //装载JRecycleView的布局容器中的JRecycleView的id
    protected final static int ID_RECYCLE_VIEW = R.id.recycle_view;

    //当前存活的activity，用于关闭所有的activity
    private static LinkedList<Activity> existActivities = new LinkedList<>();

    protected Bundle mSavedInstanceState;

    protected static final int NONE = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSavedInstanceState = savedInstanceState;
        existActivities.add(this);

        //传NONE，不进行设置视图的layout，可以由子类自行操作
        if(getLayout() != NONE){
            setContentView(getLayout());
        }

        initIntent(getIntent());
        initView();

    }

    @Override
    protected void onDestroy() {

        existActivities.remove(this);

        super.onDestroy();
    }

    //退出，清空所有的存活activity
    public void exit(){
        Iterator<Activity> activityIterator = existActivities.iterator();
        while (activityIterator.hasNext()){
            Activity next = activityIterator.next();
            activityIterator.remove();
            next.finish();
        }
    }

    //当前activity是否为用户所见的activity
    private boolean isCurActivity(){
        return existActivities.getLast() == this;
    }

    //添加Fragment
    public void addLayerFragment(int containerId, Fragment fragment){
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(fragment);
        addLayerFragment(containerId, fragments, 0);
    }

    public void addLayerFragment(int containerId, List<Fragment> fragments){
        addLayerFragment(containerId, fragments, 0);
    }

    public void addLayerFragment(int containerId, List<Fragment> fragments, int showPosition){
        if(fragments == null){
            fragments = new ArrayList<>();
        }
        if (mSavedInstanceState == null) {
            FragmentCompat.Layer.init(getSupportFragmentManager(), containerId, showPosition, fragments);
        }else {
            FragmentCompat.Layer.restoreInstance(getSupportFragmentManager(), fragments);
        }
    }

    //切换fragment
    public void toggleLayerFragment(Fragment from, Fragment to){
        FragmentCompat.Layer.toggle(getSupportFragmentManager(), from, to);
    }

    //获取视图layout的id
    protected abstract int getLayout();
    //初始化intent
    protected abstract void initIntent(Intent intent);
    //初始化视图的View
    protected abstract void initView();


}
