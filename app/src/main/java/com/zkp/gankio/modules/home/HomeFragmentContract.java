package com.zkp.gankio.modules.home;

import com.zkp.gankio.base.presenter.IPresenter;
import com.zkp.gankio.base.view.IView;
import com.zkp.gankio.beans.BannerBean;
import com.zkp.gankio.beans.TodayGankBean;
import com.zkp.gankio.db.entity.Article;
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

        /**
         * 从数据库中加载所有收藏文章信息成功
         *
         * @param articleList
         */
        void loadArticlesSuccess(List<Article> articleList);

        /**
         * 收藏文章成功
         */
        void collectArticleSuccess();

        /**
         * 取消收藏文章成功
         */
        void unCollectArticleSuccess();

    }

    public interface Presenter extends IPresenter<View> {
        /**
         * 获取首页今日干货
         */
        void getTodayGank();

        /**
         * 向数据库中添加分类数据
         * @param categoryList
         */
        void addCategories(List<Category> categoryList);

        /**
         * 从数据库中加载所有收藏的文章信息
         *
         * @return
         */
        void loadArticles();

        /**
         * 收藏文章
         *
         * @param article
         */
        void collectArticle(Article article);

        /**
         * 取消收藏文章
         *
         * @param articleId
         */
        void unCollectArticle(String articleId);

    }

}
