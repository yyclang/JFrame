package com.zinc.jframe.jframe.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.zinc.jframe.R;
import com.zinc.jframe.jframe.fragment.JLazyFragmentDemo.LazyTestFragment;
import com.zinc.libjframe.view.activity.JBaseActivity;

/**
 * @author Jiang zinc
 * @date 创建时间：2018/4/22
 * @description
 */

public class ViewPageActivity extends JBaseActivity {

    private ViewPager viewPager;

    @Override
    protected int getLayout() {
        return R.layout.activity_view_page;
    }

    @Override
    protected void initIntent(Intent intent) {

    }

    @Override
    protected void initView() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(5);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public Fragment getItem(int position) {
                return LazyTestFragment.newInstance(position);
            }


        });

    }
}
