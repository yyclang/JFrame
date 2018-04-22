package com.zinc.libjframe.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zinc.jrecycleview.JRecycleView;
import com.zinc.jrecycleview.adapter.JRefreshAndLoadMoreAdapter;
import com.zinc.libjframe.R;
import com.zinc.libjframe.adapter.JFrameAdapter;
import com.zinc.libjframe.callback.IStateListener;

/**
 * @author Jiang zinc
 * @date 创建时间：2018/4/22
 * @description
 */

public abstract class JLoadMoreFragment extends JListFragment {

    //暴露给子类初始化adapter
    protected void initAdapterForChild(RecyclerView.Adapter adapter) {
        this.mAdapter.setIsOpenLoadMore(requestLoadMore());
        if(requestLoadMore()){
            this.mAdapter.setOnLoadMoreListener(new JRefreshAndLoadMoreAdapter.OnLoadMoreListener() {
                @Override
                public void onLoading() {
                    getLoadMoreData();
                }
            });
        }
    }

    @Override
    protected boolean requestRefresh() {
        return true;
    }

    protected boolean requestLoadMore(){
        return true;
    }

    public abstract void getLoadMoreData();

}
