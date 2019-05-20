package com.zkp.gankio.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.classic.common.MultipleStatusView;
import com.zkp.gankio.R;
import com.zkp.gankio.base.presenter.IPresenter;
import com.zkp.gankio.base.view.IView;

import javax.inject.Inject;


/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio
 * @time: 2019/5/20 11:43
 * @description:
 */
public abstract class BaseFragment<P extends IPresenter> extends AbstractSimpleFragment implements IView {

    @Inject
    protected P mPresenter;

    private MultipleStatusView mMultipleStatusView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMultipleStatusView = view.findViewById(R.id.custom_multiple_status_view);
        if (mMultipleStatusView != null) {
            mMultipleStatusView.setOnRetryClickListener(v -> mPresenter.reload());
        }
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mPresenter != null) {
            mPresenter = null;
        }
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        hideLoading();
        super.onDestroyView();
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
