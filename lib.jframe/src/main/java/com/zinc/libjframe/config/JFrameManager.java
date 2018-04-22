package com.zinc.libjframe.config;

import android.view.View;

import com.zinc.libjframe.R;

/**
 * @author Jiang zinc
 * @date 创建时间：2018/4/21
 * @description
 */

public class JFrameManager {
    private static final JFrameManager ourInstance = new JFrameManager();

    public static JFrameManager getInstance() {
        return ourInstance;
    }

    private JFrameManager() {
        this.mIsDebug = false;
        this.mLoadingViewLayout = R.layout.j_state_loading_view;
        this.mRetryViewLayout = R.layout.j_state_retry_view;
        this.mEmptyViewLayout = R.layout.j_state_empty_view;
    }

    //是否为debug模式
    private boolean mIsDebug;

    //加载的view的layout
    private int mLoadingViewLayout;
    //重试的view的layout
    private int mRetryViewLayout;
    //空内容的View的layout
    private int mEmptyViewLayout;

    public boolean isDebug() {
        return mIsDebug;
    }

    public void setIsDebug(boolean mIsDebug) {
        this.mIsDebug = mIsDebug;
    }

    public int getLoadingViewLayout() {
        return mLoadingViewLayout;
    }

    public void setLoadingViewLayout(int mLoadingViewLayout) {
        this.mLoadingViewLayout = mLoadingViewLayout;
    }

    public int getRetryViewLayout() {
        return mRetryViewLayout;
    }

    public void setRetryViewLayout(int mRetryViewLayout) {
        this.mRetryViewLayout = mRetryViewLayout;
    }

    public int getEmptyViewLayout() {
        return mEmptyViewLayout;
    }

    public void setEmptyViewLayout(int mEmptyViewLayout) {
        this.mEmptyViewLayout = mEmptyViewLayout;
    }
}