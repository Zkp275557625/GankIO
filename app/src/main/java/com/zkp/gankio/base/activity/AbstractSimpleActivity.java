package com.zkp.gankio.base.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio
 * @time: 2019/5/20 11:05
 * @description:
 */
public abstract class AbstractSimpleActivity extends SupportActivity {

    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        mUnBinder = ButterKnife.bind(this);
        onViewCreated();
        initToolbar();
        initView();
        initEventAndData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnBinder != null && mUnBinder != Unbinder.EMPTY) {
            mUnBinder.unbind();
            mUnBinder = null;
        }
    }

    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 在initEventAndData()之前执行
     */
    protected abstract void onViewCreated();

    /**
     * 初始化ToolBar
     */
    protected abstract void initToolbar();

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initEventAndData();

}
