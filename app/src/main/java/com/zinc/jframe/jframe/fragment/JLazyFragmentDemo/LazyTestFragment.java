package com.zinc.jframe.jframe.fragment.JLazyFragmentDemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zinc.jframe.R;
import com.zinc.jframe.jframe.fragment.MyHandler;
import com.zinc.libjframe.view.fragment.JLazyFragment;

/**
 * @author Jiang zinc
 * @date 创建时间：2018/4/22
 * @description
 */

public class LazyTestFragment extends JLazyFragment {

    private int page;

    private TextView tv_state_show;
    private TextView tv_page;

    private Handler myHandler = new MyHandler(this);

    public static LazyTestFragment newInstance(int page) {
        LazyTestFragment lazyTestFragment = new LazyTestFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("page", page);

        lazyTestFragment.setArguments(bundle);
        return lazyTestFragment;
    }

    @Override
    protected void initArgs(Bundle arguments) {
        super.initArgs(arguments);
        page = arguments.getInt("page");
    }

    @Override
    protected View onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lazy, container, false);
    }

    @Override
    protected void initView(View view) {
        tv_state_show = view.findViewById(R.id.tv_state_show);
        tv_page = view.findViewById(R.id.tv_page);
        tv_page.setText("第"+page+"页");
    }

    @Override
    protected void initData() {

        loge("开始加载！！！！");

        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv_state_show.setText("已加载完！！！！");
            }
        }, 2000);
    }
}
