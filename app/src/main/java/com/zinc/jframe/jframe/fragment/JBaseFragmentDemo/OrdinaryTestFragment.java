package com.zinc.jframe.jframe.fragment.JBaseFragmentDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zinc.jframe.R;
import com.zinc.libjframe.utils.FragmentCompat;
import com.zinc.libjframe.view.activity.JBaseActivity;
import com.zinc.libjframe.view.fragment.JBaseFragment;

/**
 * @author Jiang zinc
 * @date 创建时间：2018/4/21
 * @description
 */

public class OrdinaryTestFragment extends JBaseFragment {

    ImageView iv_pic;

    @Override
    protected View onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ordinary, container, false);
    }

    @Override
    protected void initView(View view) {
        iv_pic = view.findViewById(R.id.iv_pic);
        iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() instanceof JBaseActivity) {
                    FragmentCompat.Flow.toggle(getActivity().getSupportFragmentManager(), ID_FRAME_LAYOUT_CONTAINER, OrdinaryTestFragment.this, new ImageFragment());
                }
            }
        });
    }

}
