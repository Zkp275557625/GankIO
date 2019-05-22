package com.zkp.gankio.modules.mine.history;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zkp.gankio.base.presenter.BasePresenter;
import com.zkp.gankio.beans.BannerBean;
import com.zkp.gankio.beans.HistoryContentBean;
import com.zkp.gankio.beans.HistoryGankBean;
import com.zkp.gankio.http.ApiService;
import com.zkp.gankio.http.AppConfig;
import com.zkp.gankio.http.HttpUtil;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.mine.history
 * @time: 2019/5/22 13:58
 * @description:
 */
public class HistoryPresenter extends BasePresenter<HistoryActivityContract.View> implements HistoryActivityContract.Presenter {

    private int currentPage = 1;

    @Override
    public void getHistoryGank() {
        if (mView != null) {
            mView.showLoading();
            HttpUtil.request(HttpUtil.createApi(AppConfig.BASE_URL, ApiService.class).getHistoryGank(), new HttpUtil.IResponseListener<HistoryGankBean>() {
                @Override
                public void onSuccess(HistoryGankBean data) {
                    if (!data.isError()) {
                        mView.getHistoryGankSuccess(data);
                    } else {
                        mView.getHistoryGankError("获取日期失败");
                    }
                    mView.hideLoading();
                }

                @Override
                public void onFail(String errMsg) {
                    mView.getHistoryGankError(errMsg);
                    mView.hideLoading();
                }
            });
        }
    }

    @Override
    public void getHistoryGankContent(String date, BaseViewHolder helper) {
        if (mView != null) {
            HttpUtil.request(HttpUtil.createApi(AppConfig.BASE_URL, ApiService.class).getHistoryContent(date), new HttpUtil.IResponseListener<HistoryContentBean>() {
                @Override
                public void onSuccess(HistoryContentBean data) {
                    if (!data.isError()) {
                        mView.getHistoryGankContentSuccess(data, helper);
                    } else {
                        mView.getHistoryGankContentError("获取历史干货内容失败");
                    }
                }

                @Override
                public void onFail(String errMsg) {
                    mView.getHistoryGankContentError(errMsg);
                }
            });
        }
    }

    @Override
    public void getImages(int page, boolean isFresh) {
        HttpUtil.request(HttpUtil.createApi(AppConfig.BASE_URL, ApiService.class).getBanner(page), new HttpUtil.IResponseListener<BannerBean>() {
            @Override
            public void onSuccess(BannerBean data) {
                if (!data.isError()) {
                    mView.getImagesSuccess(data, isFresh);
                } else {
                    mView.getImagesError("获取图片失败");
                }
                mView.hideLoading();
            }

            @Override
            public void onFail(String errMsg) {
                mView.getImagesError(errMsg);
                mView.hideLoading();
            }
        });
    }

    @Override
    public void refresh() {
        currentPage = 1;
        getImages(currentPage, true);
    }

    @Override
    public void loadMore() {
        currentPage++;
        getImages(currentPage, false);
    }
}
