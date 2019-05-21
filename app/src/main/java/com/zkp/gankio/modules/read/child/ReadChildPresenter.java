package com.zkp.gankio.modules.read.child;

import com.zkp.gankio.base.presenter.BasePresenter;
import com.zkp.gankio.beans.ReadCategoryChildBean;
import com.zkp.gankio.http.ApiService;
import com.zkp.gankio.http.AppConfig;
import com.zkp.gankio.http.HttpUtil;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.read.child
 * @time: 2019/5/21 16:35
 * @description:
 */
public class ReadChildPresenter extends BasePresenter<ReadChildFragmentContract.View> implements ReadChildFragmentContract.Presenter {

    @Override
    public void getReadCategoryChild(String enName) {
        if (mView != null) {
            mView.showLoading();
            HttpUtil.request(HttpUtil.createApi(AppConfig.BASE_URL, ApiService.class).getReadCategoryChild(enName), new HttpUtil.IResponseListener<ReadCategoryChildBean>() {
                @Override
                public void onSuccess(ReadCategoryChildBean data) {
                    if (!data.isError()) {
                        mView.getReadCategoryChildSuccess(data);
                    } else {
                        mView.getReadCategoryChildError("获取闲读子分类失败");
                    }
                    mView.hideLoading();
                }

                @Override
                public void onFail(String errMsg) {
                    mView.getReadCategoryChildError(errMsg);
                    mView.hideLoading();
                }
            });
        }
    }
}
