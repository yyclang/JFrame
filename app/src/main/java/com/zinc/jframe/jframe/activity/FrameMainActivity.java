package com.zinc.jframe.jframe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.zinc.jframe.R;
import com.zinc.jframe.jframe.fragment.JLoadMoreFragmentDemo.LoadMoreTestFragment;
import com.zinc.libjframe.config.JFrameManager;
import com.zinc.libjframe.view.activity.JBaseActivity;

/**
 * @author Jiang zinc
 * @date 创建时间：2018/4/21
 * @description
 */

public class FrameMainActivity extends JBaseActivity implements View.OnClickListener {

    private TextView tvOrdinary;
    private TextView tvLazy;
    private TextView tvList;
    private TextView tvLoadMore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        JFrameManager.getInstance().setIsDebug(true);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_frame_activity;
    }

    @Override
    protected void initView() {
        tvOrdinary = (TextView) findViewById(R.id.tv_ordinary);
        tvLazy = (TextView) findViewById(R.id.tv_lazy);
        tvList = (TextView) findViewById(R.id.tv_list);
        tvLoadMore = (TextView) findViewById(R.id.tv_load_more);

        tvOrdinary.setOnClickListener(this);
        tvLazy.setOnClickListener(this);
        tvList.setOnClickListener(this);
        tvLoadMore.setOnClickListener(this);
    }

    @Override
    protected void initIntent(Intent intent) {

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, FrameContainerActivity.class);
        switch (v.getId()) {

            case R.id.tv_ordinary:
                intent.putExtra("type", "ordinary");
                break;

            case R.id.tv_lazy:
                intent = new Intent(this, ViewPageActivity.class);
                intent.putExtra("type", "lazy");
                break;

            case R.id.tv_list:
                intent.putExtra("type", "list");
                break;

            case R.id.tv_load_more:
                intent.putExtra("type", "loadMore");
                break;
        }
        startActivity(intent);
    }
}
