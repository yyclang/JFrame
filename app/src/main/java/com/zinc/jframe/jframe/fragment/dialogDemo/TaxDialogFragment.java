package com.zinc.jframe.jframe.fragment.dialogDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zinc.jframe.R;
import com.zinc.libjframe.view.fragment.dialog.JBaseDialogFragment;
import com.zinc.libjframe.view.fragment.dialog.JCommonLoadingFragment;

/**
 * @author Jiang zinc
 * @date 创建时间：2018/4/23
 * @description
 */

public class TaxDialogFragment extends JBaseDialogFragment {

    private String name;

    public static TaxDialogFragment newInstance(String name) {
        TaxDialogFragment fragment = new TaxDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putString("name", name);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void initArgs(Bundle arguments) {
        this.name = arguments.getString("name");
    }

    @Override
    protected View onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tax_dialog, container, false);
    }

    @Override
    protected void initView(View view) {
        ((TextView) view.findViewById(R.id.tv_title)).setText(name + "缴税明细");
        view.findViewById(R.id.tv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
