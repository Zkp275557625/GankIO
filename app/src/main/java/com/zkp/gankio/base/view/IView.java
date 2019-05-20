package com.zkp.gankio.base.view;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio
 * @time: 2019/5/20 10:56
 * @description:
 */
public interface IView {

    /**
     * 显示Loading
     */
    void showLoading();

    /**
     * 隐藏Loading
     */
    void hideLoading();

    /**
     * 显示errorView
     */
    void showError();

    /**
     * 显示无网络
     */
    void showNoNetwork();

    /**
     * 显示无内容
     */
    void showEmpty();

    /**
     * 显示页面内容
     */
    void showContent();

}
