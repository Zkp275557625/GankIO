package com.zkp.gankio.modules.category.list;

import com.zkp.gankio.base.presenter.IPresenter;
import com.zkp.gankio.base.view.IView;
import com.zkp.gankio.beans.CategoryBean;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.category.list
 * @time: 2019/5/21 14:24
 * @description:
 */
public class CategoryListFragmentContract {

    public interface View extends IView {
        /**
         * 获取分类数据成功
         *
         * @param data
         * @param isFresh
         */
        void getCategorySuccess(CategoryBean data, boolean isFresh);

        /**
         * 获取分类数据失败
         *
         * @param errMsg
         */
        void getCategoryError(String errMsg);
    }

    public interface Presenter extends IPresenter<View> {

        /**
         * 获取分类数据
         *
         * @param category 分类
         * @param page     页码 从1开始
         * @param isFresh  是否刷新
         */
        void getCategory(String category, int page, boolean isFresh);

        /**
         * 刷新
         * @param category 分类
         */
        void refresh(String category);

        /**
         * 加载更多
         */
        void loadMore();

    }

}
