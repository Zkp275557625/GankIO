package com.zkp.gankio.modules.mine.collect;

import com.zkp.gankio.base.presenter.IPresenter;
import com.zkp.gankio.base.view.IView;
import com.zkp.gankio.db.entity.Article;

import java.util.List;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.mine.collect
 * @time: 2019/5/22 10:02
 * @description:
 */
public class CollectActivityContract {

    public interface View extends IView {

        /**
         * 从数据库中加载所有收藏文章信息成功
         *
         * @param articleList
         * @param isFresh     是否刷新
         */
        void loadArticlesSuccess(List<Article> articleList, boolean isFresh);

        /**
         * 取消收藏文章成功
         */
        void unCollectArticleSuccess();
    }

    public interface Presenter extends IPresenter<View> {

        /**
         * 从数据库中加载所有收藏的文章信息
         *
         * @param isFresh 是否刷新
         * @return
         */
        void loadArticles(boolean isFresh);

        /**
         * 刷新
         */
        void refresh();

        /**
         * 取消收藏文章
         *
         * @param articleId
         */
        void unCollectArticle(String articleId);

    }

}
