package com.zkp.gankio.base.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio
 * @time: 2019/5/20 11:40
 * @description:
 */
public abstract class AbstractSimpleFragment extends SupportFragment {

    private Unbinder mUnBinder;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert inflater != null;
        View view = inflater.inflate(getLayoutId(), container, false);
        mUnBinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 有些初始化必须在onCreateView中，例如setAdapter,
     * 否则，会弹出 No adapter attached; skipping layout
     */
    protected abstract void initView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != null && mUnBinder != Unbinder.EMPTY) {
            mUnBinder.unbind();
            mUnBinder = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initEventAndData();
    }

    /**
     * 初始化数据
     */
    protected abstract void initEventAndData();

}
