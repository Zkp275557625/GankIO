package com.zkp.gankio.base.presenter;

import com.zkp.gankio.base.view.IView;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio
 * @time: 2019/5/20 10:59
 * @description:
 */
public interface IPresenter<T extends IView> {

    /**
     * 绑定View
     *
     * @param view view
     */
    void attachView(T view);

    /**
     * 解绑View
     */
    void detachView();

    /**
     * 重新加载
     */
    void reload();

    /**
     * 注册EventBus
     */
    void registerEventBus();

    /**
     * 取消注册EventBus
     */
    void unregisterEventBus();

}
