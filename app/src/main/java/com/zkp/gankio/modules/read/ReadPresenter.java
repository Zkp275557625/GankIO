package com.zkp.gankio.modules.read;

import com.zkp.gankio.base.presenter.BasePresenter;
import com.zkp.gankio.beans.ReadCategoryMainBean;
import com.zkp.gankio.http.ApiService;
import com.zkp.gankio.http.AppConfig;
import com.zkp.gankio.http.HttpUtil;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.read
 * @time: 2019/5/21 15:44
 * @description:
 */
public class ReadPresenter extends BasePresenter<ReadFragmentContract.View> implements ReadFragmentContract.Presenter {

    @Override
    public void getReadCategoryMain() {
        if (mView != null) {
            mView.showLoading();
            HttpUtil.request(HttpUtil.createApi(AppConfig.BASE_URL, ApiService.class).getReadCategoryMain(), new HttpUtil.IResponseListener<ReadCategoryMainBean>() {
                @Override
                public void onSuccess(ReadCategoryMainBean data) {
                    if (!data.isError()) {
                        mView.getReadCategoryMainSuccess(data);
                    } else {
                        mView.getReadCategoryMainError("获取闲读主分类失败");
                    }
                    mView.hideLoading();
                }

                @Override
                public void onFail(String errMsg) {
                    mView.getReadCategoryMainError(errMsg);
                    mView.hideLoading();
                }
            });
        }
    }
}
