package com.zkp.gankio.modules.category.list;

import com.zkp.gankio.base.presenter.BasePresenter;
import com.zkp.gankio.beans.CategoryBean;
import com.zkp.gankio.http.ApiService;
import com.zkp.gankio.http.AppConfig;
import com.zkp.gankio.http.HttpUtil;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.category.list
 * @time: 2019/5/21 14:25
 * @description:
 */
public class CategoryListPresenter extends BasePresenter<CategoryListFragmentContract.View> implements CategoryListFragmentContract.Presenter {

    private String catrgory;
    private int currentPage = 1;

    @Override
    public void getCategory(String category, int page, boolean isFresh) {
        if (mView != null) {
            mView.showLoading();
            HttpUtil.request(HttpUtil.createApi(AppConfig.BASE_URL, ApiService.class).getCategory(category, page), new HttpUtil.IResponseListener<CategoryBean>() {
                @Override
                public void onSuccess(CategoryBean data) {
                    if (!data.isError()) {
                        mView.getCategorySuccess(data, isFresh);
                    } else {
                        mView.getCategoryError("获取" + category + "数据失败");
                    }
                    mView.hideLoading();
                }

                @Override
                public void onFail(String errMsg) {
                    mView.getCategoryError(errMsg);
                    mView.hideLoading();
                }
            });
        }
    }

    @Override
    public void refresh(String category) {
        this.currentPage = 1;
        this.catrgory = category;
        getCategory(category, currentPage, true);
    }

    @Override
    public void loadMore() {
        currentPage++;
        getCategory(catrgory, currentPage, false);
    }
}
