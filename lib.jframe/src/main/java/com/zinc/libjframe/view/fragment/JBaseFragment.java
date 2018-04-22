package com.zinc.libjframe.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zinc.libjframe.R;
import com.zinc.libjframe.config.JFrameManager;
import com.zinc.libjframe.utils.FragmentCompat;
import com.zinc.libjframe.widget.StateLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiang zinc
 * @date 创建时间：2018/4/21
 * @description 对fragment的最基础封装
 */

public abstract class JBaseFragment extends Fragment {

    protected Bundle mSaveInstanceState;

    //装载FrameLayout的布局容器
    protected final static int COMMON_FRAME_LAYOUT = R.layout.j_common_frame_layout;
    //装载JRecycleView的布局容器
    protected final static int COMMON_RECYCLE_VIEW_LAYOUT = R.layout.j_common_recycle_view;
    //装载FrameLayout的布局容器中的FrameLayout的id
    protected final static int ID_FRAME_LAYOUT_CONTAINER = R.id.frame_layout_container;
    //装载JRecycleView的布局容器中的JRecycleView的id
    protected final static int ID_RECYCLE_VIEW = R.id.recycle_view;

    @Override
    public void onAttach(Context context) {
        logi("onAttach------start");
        super.onAttach(context);

        Bundle arguments = getArguments();
        if (arguments != null) {
            initArgs(arguments);
        }
        logi("onAttach------end");
    }

    //初始化参数
    protected void initArgs(Bundle arguments) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        logi("onCreate------start");
        super.onCreate(savedInstanceState);

        this.mSaveInstanceState = savedInstanceState;

        logi("onCreate------end");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        logi("onCreateView------start");
        View fragmentView = onCreateFragmentView(inflater, container, savedInstanceState);
//        if (isSupportStateLayout()) {
//            fragmentView = wrapFragmentView(fragmentView);
//        }
        logi("onCreateView------end");
        return fragmentView;
    }

    /**
     * {@link JBaseFragment#onCreateView(LayoutInflater, ViewGroup, Bundle)} 与其相同 只是用于父类JFragment中包装
     */
    protected abstract View onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    //是否要开启多状态
    protected boolean isSupportStateLayout() {
        return false;
    }

    //包装fragment，添加多状态
    protected View wrapFragmentView(View view) {
        StateLayout stateLayout = new StateLayout(getActivity());
        stateLayout.setContentView(view);
        stateLayout.setRetryView(JFrameManager.getInstance().getRetryViewLayout());
        stateLayout.setEmptyView(JFrameManager.getInstance().getEmptyViewLayout());
        stateLayout.setLoadingView(JFrameManager.getInstance().getLoadingViewLayout());
        return stateLayout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        logi("onViewCreated------start");
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        logi("onViewCreated------end");
    }

    protected abstract void initView(View view);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        logi("onActivityCreated------start");
        super.onActivityCreated(savedInstanceState);
        logi("onActivityCreated------end");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        logi("onViewStateRestored------start");
        super.onViewStateRestored(savedInstanceState);
        logi("onViewStateRestored------end");
    }

    @Override
    public void onStart() {
        logi("onStart------start");
        super.onStart();
        logi("onStart------end");
    }

    @Override
    public void onResume() {
        logi("onResume------start");
        super.onResume();
        logi("onResume------end");
    }

    @Override
    public void onPause() {
        logi("onPause------start");
        super.onPause();
        logi("onPause------end");
    }

    @Override
    public void onStop() {
        logi("onStop------start");
        super.onStop();
        logi("onStop------end");
    }

    @Override
    public void onDestroyView() {
        logi("onDestroyView------start");
        super.onDestroyView();
        logi("onDestroyView------end");
    }

    @Override
    public void onDestroy() {
        logi("onDestroy------start");
        super.onDestroy();
        logi("onDestroy------end");
    }

    @Override
    public void onDetach() {
        logi("onDetach------start");
        super.onDetach();
        logi("onDetach------end");
    }

    protected String getUniqueId() {
        return Integer.toHexString(System.identityHashCode(this));
    }

    protected String getFragmentTag() {
        return getClass().getSimpleName() + "{" + getUniqueId() + "}";
    }

    protected void logi(String msg) {
        if (JFrameManager.getInstance().isDebug()) {
            Log.i(getFragmentTag(), msg);
        }
    }

    protected void logw(String msg) {
        if (JFrameManager.getInstance().isDebug()) {
            Log.e(getFragmentTag(), msg);
        }
    }

    protected void loge(String msg) {
        if (JFrameManager.getInstance().isDebug()) {
            Log.e(getFragmentTag(), msg);
        }
    }

    /**
     * 判断当前fragment是否消费回退事件
     *
     * @param fragmentManager 上级fragment 的管理
     * @return
     */
    public boolean onConsumeBackEvent(FragmentManager fragmentManager) {
        boolean consume = FragmentCompat.isConsumeBackEvent(getChildFragmentManager());
        if (consume) {
            //子fragment消费成功以后，判断当前fragment是否还有子fragment，没有的话，看情况决定是否关闭
            if (getChildFragmentManager().getBackStackEntryCount() == 0 && noChild2Finish()) {
                return fragmentManager.popBackStackImmediate();
            }
            return true;
        } else {
            //子fragment未消费回退事件,则由当前fragment进行消费
            return fragmentManager.popBackStackImmediate();
        }
    }

    //当fragmentmanager内的子fragment数为0时，判断是否关闭当前fragment
    protected boolean noChild2Finish() {
        return true;
    }

    //打印fragment的信息，主要用于调试
    protected void printFragmentLog(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                Log.e("fragment_list", fragment != null ? fragment.toString() + ",isHidden:" + fragment.isHidden() : "null");
            }
        }
    }

    //单个fragment
    public void addLayerFragment(int containerId, Fragment fragment) {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(fragment);
        addLayerFragment(containerId, fragments, 0);
    }

    //list方法
    public void addLayerFragment(int containerId, List<Fragment> fragments) {
        addLayerFragment(containerId, fragments, 0);
    }

    public void addLayerFragment(int containerId, List<Fragment> fragments, int showPosition) {
        if (this.mSaveInstanceState == null) {
            FragmentCompat.Layer.init(getChildFragmentManager(), containerId, showPosition, fragments);
        } else {
            FragmentCompat.Layer.restoreInstance(getChildFragmentManager(), fragments);
        }
    }

    //切换fragment
    public void toggleLayerFragment(Fragment from, Fragment to) {
        FragmentCompat.Layer.toggle(getChildFragmentManager(), from, to);
    }

}
