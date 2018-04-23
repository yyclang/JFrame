package com.zinc.libjframe.view.fragment.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.zinc.libjframe.R;
import com.zinc.libjframe.utils.UIUtils;
import com.zinc.libjframe.widget.BallSpinFadeLoader;

/**
 * @author Jiang zinc
 * @date 创建时间：2018/4/23
 * @description
 */

public class JCommonLoadingFragment extends JBaseDialogFragment {

    public static JCommonLoadingFragment newInstance() {
        JCommonLoadingFragment fragment = new JCommonLoadingFragment();

        Bundle bundle = new Bundle();

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initArgs(Bundle arguments) {

    }

    @Override
    protected void initWindows(Window window) {
        //空实现，去除父类动画效果
    }

    @Override
    protected View onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.j_fragment_loading, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void initView(View view) {

    }

}
