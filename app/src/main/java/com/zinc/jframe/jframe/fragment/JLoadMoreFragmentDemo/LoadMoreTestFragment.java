package com.zinc.jframe.jframe.fragment.JLoadMoreFragmentDemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;

import com.zinc.jframe.jframe.adapter.MyListAdapter;
import com.zinc.jframe.jframe.fragment.MyHandler;
import com.zinc.libjframe.view.fragment.JLoadMoreFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiang zinc
 * @date 创建时间：2018/4/22
 * @description
 */

public class LoadMoreTestFragment extends JLoadMoreFragment {

    private List data = new ArrayList();
    private int count = 1;
    private int mLength = 20;

    private Handler mHandler = new MyHandler(this);

    @Override
    protected void initArgs(Bundle arguments) {

    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new MyListAdapter(getContext(), data);
    }

    @Override
    public void getFirstData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getInitData();
                mAdapter.onSuccess();
            }
        }, 2000);
    }

    @Override
    public void getLoadMoreData() {
        for (int i = 0; i < 20; ++i) {
            data.add("zinc Power" + (count + i));
        }

        count = count + 20;

        mAdapter.setLoadComplete();
        mAdapter.notifyDataSetChanged();
    }

    public void getInitData() {
        this.data.clear();
        count = 1;
        for (; count <= mLength; ++count) {
            data.add("zinc Power" + count);
        }
    }

}
