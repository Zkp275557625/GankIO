package com.zkp.gankio.modules.home;

import com.zkp.gankio.base.presenter.IPresenter;
import com.zkp.gankio.base.view.IView;
import com.zkp.gankio.beans.BannerBean;
import com.zkp.gankio.beans.TodayGankBean;
import com.zkp.gankio.db.entity.Category;

import java.util.List;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.home
 * @time: 2019/5/20 14:53
 * @description:
 */
public class HomeFragmentContract {

    public interface View extends IView {
        /**
         * 获取首页今日干货成功
         *
         * @param data TodayGankBean
         */
        void getTodayGankSuceess(TodayGankBean data);

        /**
         * 获取首页今日干货失败
         *
         * @param errMsg String
         */
        void getTodayGankError(String errMsg);

        /**
         * 向数据库中添加分类数据成功
         */
        void addCategoriesSuccess();

    }

    public interface Presenter extends IPresenter<View> {
        /**
         * 获取首页今日干货
         */
        void getTodayGank();

        /**
         * 向数据库中添加分类数据
         */
        void addCategories(List<Category> categoryList);

    }

}
