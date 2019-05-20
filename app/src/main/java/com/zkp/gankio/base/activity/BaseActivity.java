package com.zkp.gankio.base.activity;

import android.support.v4.app.Fragment;

import com.classic.common.MultipleStatusView;
import com.zkp.gankio.R;
import com.zkp.gankio.base.presenter.IPresenter;
import com.zkp.gankio.base.view.IView;
import com.zkp.gankio.utils.CommonUtils;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio
 * @time: 2019/5/20 11:20
 * @description:
 */
public abstract class BaseActivity<P extends IPresenter> extends AbstractSimpleActivity implements IView {

    @Inject
    protected P mPresenter;

    private MultipleStatusView mMultipleStatusView;

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        hideLoading();
        super.onDestroy();
    }

    @Override
    protected void onViewCreated() {
        mMultipleStatusView = findViewById(R.id.custom_multiple_status_view);
        if (mMultipleStatusView != null) {
            mMultipleStatusView.setOnRetryClickListener(v -> mPresenter.reload());
        }
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onBackPressedSupport() {
        CommonUtils.hideKeyBoard(this, this.getWindow().getDecorView().getRootView());
        super.onBackPressedSupport();
    }

    @Override
    public void showLoading() {
        if (mMultipleStatusView == null) {
            return;
        }
        mMultipleStatusView.showLoading();
    }

    @Override
    public void hideLoading() {
        if (mMultipleStatusView == null) {
            return;
        }
        mMultipleStatusView.showContent();
    }

    @Override
    public void showError() {
        if (mMultipleStatusView == null) {
            return;
        }
        mMultipleStatusView.showError();
    }

    @Override
    public void showNoNetwork() {
        if (mMultipleStatusView == null) {
            return;
        }
        mMultipleStatusView.showNoNetwork();
    }

    @Override
    public void showEmpty() {
        if (mMultipleStatusView == null) {
            return;
        }
        mMultipleStatusView.showEmpty();
    }

    @Override
    public void showContent() {
        if (mMultipleStatusView == null) {
            return;
        }
        mMultipleStatusView.showContent();
    }

}
