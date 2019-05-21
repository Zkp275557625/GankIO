package com.zkp.gankio.modules.home.detail;

import com.zkp.gankio.base.presenter.BasePresenter;
import com.zkp.gankio.db.DbHelper;
import com.zkp.gankio.db.DbHelperImp;
import com.zkp.gankio.db.entity.Article;
import com.zkp.gankio.utils.RxUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.home.detail
 * @time: 2019/5/21 9:56
 * @description:
 */
public class ArticlePresenter extends BasePresenter<ArticleActivityContract.View> implements ArticleActivityContract.Presenter {

    private DbHelper dbHelper;

    public ArticlePresenter() {
        dbHelper = new DbHelperImp();
    }

    @Override
    public void loadArticle(String articleId) {
        addSubscribe(Observable.create((ObservableOnSubscribe<Article>) e -> {
            Article article = dbHelper.loadArticle(articleId);
            if (article == null) {
                article = new Article();
            }
            e.onNext(article);
        }).compose(RxUtils.schedulerTransformer())
                .filter(article -> mView != null)
                .subscribe(article -> {
                            mView.loadArticleSuccess(article);
                        }
                ));
    }

    @Override
    public void collectArticle(Article article) {
        addSubscribe(Observable.create((ObservableOnSubscribe<List<Article>>) e -> {
            List<Article> articleList = dbHelper.addArticle(article);
            e.onNext(articleList);
        }).compose(RxUtils.schedulerTransformer())
                .filter(articleList -> mView != null)
                .subscribe(articleList -> {
                            mView.collectArticleSuccess();
                        }
                ));
    }

    @Override
    public void unCollectArticle(String articleId) {
        addSubscribe(Observable.create((ObservableOnSubscribe<String>) e -> {
            dbHelper.deleteArticle(articleId);
            String str = "success";
            e.onNext(str);
        }).compose(RxUtils.schedulerTransformer())
                .filter(str -> mView != null)
                .subscribe(str -> {
                            mView.unCollectArticleSuccess();
                        }
                ));
    }
}
