package com.zinc.jframe.jframe.fragment.JListFragmentDemo;

import android.os.Handler;
import android.support.v7.widget.RecyclerView;

import com.zinc.jframe.jframe.adapter.MyListAdapter;
import com.zinc.jframe.jframe.fragment.MyHandler;
import com.zinc.libjframe.view.fragment.JListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiang zinc
 * @date 创建时间：2018/4/21
 * @description
 */

public class ListTestFragment extends JListFragment {

    private int count = 20;
    private List<String> data = new ArrayList<>();

    private Handler mHandler = new MyHandler(this);

    protected int testTag = 0;

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new MyListAdapter(getContext(), data);
    }

    @Override
    public void getFirstData() {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (testTag) {
                    case 0:
                        mAdapter.onEmpty();
                        break;
                    case 1:
                        mAdapter.onError();
                        break;
                    default:
                        getInitData();
                        mAdapter.onSuccess();
                        break;
                }
                ++testTag;
            }
        }, 2000);

    }

    @Override
    protected boolean requestRefresh() {
        return true;
    }

    public void getInitData() {
        this.data.clear();
        for (int i = 1; i <= count; ++i) {
            data.add("zinc Power" + i);
        }
    }

}
