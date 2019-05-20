package com.zkp.gankio.base.presenter;

import com.zkp.gankio.base.view.IView;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio
 * @time: 2019/5/20 11:02
 * @description:
 */
public class BasePresenter<T extends IView> implements IPresenter<T> {

    protected T mView;

    @Override
    public void attachView(T view) {
        this.mView = view;
        registerEventBus();
    }

    @Override
    public void detachView() {
        this.mView = null;
        unregisterEventBus();
    }

    @Override
    public void reload() {

    }

    @Override
    public void registerEventBus() {

    }

    @Override
    public void unregisterEventBus() {

    }
}
