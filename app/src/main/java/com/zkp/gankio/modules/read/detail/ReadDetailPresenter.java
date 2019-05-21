package com.zkp.gankio.modules.read.detail;

import com.zkp.gankio.base.presenter.BasePresenter;
import com.zkp.gankio.beans.ReadDetailBean;
import com.zkp.gankio.http.ApiService;
import com.zkp.gankio.http.AppConfig;
import com.zkp.gankio.http.HttpUtil;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.read.detail
 * @time: 2019/5/21 17:07
 * @description:
 */
public class ReadDetailPresenter extends BasePresenter<ReadDetailFragmentContract.View> implements ReadDetailFragmentContract.Presenter {

    private String id;
    private int currentPage = 1;

    @Override
    public void getReadDetailList(String id, int page, boolean isFresh) {
        if (mView != null) {
            mView.showLoading();
            HttpUtil.request(HttpUtil.createApi(AppConfig.BASE_URL, ApiService.class).getReadDetailList(id, page), new HttpUtil.IResponseListener<ReadDetailBean>() {
                @Override
                public void onSuccess(ReadDetailBean data) {
                    if (!data.isError()) {
                        mView.getReadDetailListSuccess(data, isFresh);
                    } else {
                        mView.getReadDetailListError("获取闲读列表失败");
                    }
                    mView.hideLoading();
                }

                @Override
                public void onFail(String errMsg) {
                    mView.getReadDetailListError(errMsg);
                    mView.hideLoading();
                }
            });
        }
    }

    @Override
    public void refresh(String id) {
        this.id = id;
        currentPage = 1;
        getReadDetailList(id, currentPage, true);
    }

    @Override
    public void loadMore() {
        currentPage++;
        getReadDetailList(id, currentPage, false);
    }
}
