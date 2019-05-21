package com.zkp.gankio.modules.home.detail;

import com.zkp.gankio.base.presenter.IPresenter;
import com.zkp.gankio.base.view.IView;
import com.zkp.gankio.db.entity.Article;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.home.detail
 * @time: 2019/5/21 9:54
 * @description:
 */
public class ArticleActivityContract {

    public interface View extends IView {

        /**
         * 加载文章信息成功
         *
         * @param article
         */
        void loadArticleSuccess(Article article);

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
         * 加载文章信息
         * 用于判断当前文章有没有被收藏
         *
         * @param articleId
         */
        void loadArticle(String articleId);

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
