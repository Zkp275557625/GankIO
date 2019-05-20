package com.zkp.gankio.modules.home;

import com.zkp.gankio.base.presenter.BasePresenter;
import com.zkp.gankio.beans.BannerBean;
import com.zkp.gankio.beans.TodayGankBean;
import com.zkp.gankio.http.ApiService;
import com.zkp.gankio.http.AppConfig;
import com.zkp.gankio.http.HttpUtil;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.home
 * @time: 2019/5/20 14:54
 * @description:
 */
public class HomePresenter extends BasePresenter<HomeFragmentContract.View> implements HomeFragmentContract.Presenter {

    @Override
    public void getBanner() {
        if (mView != null) {
            mView.showLoading();

            HttpUtil.request(HttpUtil.createApi(AppConfig.BASE_URL, ApiService.class).getBanner(), new HttpUtil.IResponseListener<BannerBean>() {
                @Override
                public void onSuccess(BannerBean data) {
                    if (!data.isError()) {
                        mView.getBannerSuccess(data);
                    } else {
                        mView.getBannerError("获取banner失败");
                    }
                    mView.hideLoading();
                }

                @Override
                public void onFail(String errMsg) {
                    mView.getBannerError("获取banner失败");
                    mView.hideLoading();
                }
            });
        }
    }

    @Override
    public void getTodayGank() {
        if (mView != null) {
            mView.showLoading();

            HttpUtil.request(HttpUtil.createApi(AppConfig.BASE_URL, ApiService.class).getTodatGank(), new HttpUtil.IResponseListener<TodayGankBean>() {
                @Override
                public void onSuccess(TodayGankBean data) {
                    if (!data.isError()) {
                        mView.getTodayGankSuceess(data);
                    } else {
                        mView.getTodayGankError("获取今日干货失败");
                    }
                    mView.hideLoading();
                }

                @Override
                public void onFail(String errMsg) {
                    mView.getTodayGankError("获取今日干货失败");
                    mView.hideLoading();
                }
            });
        }
    }
}
