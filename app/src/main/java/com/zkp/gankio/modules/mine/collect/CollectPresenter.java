package com.zkp.gankio.modules.mine.collect;

import com.zkp.gankio.base.presenter.BasePresenter;
import com.zkp.gankio.db.DbHelper;
import com.zkp.gankio.db.DbHelperImp;
import com.zkp.gankio.db.entity.Article;
import com.zkp.gankio.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.mine.collect
 * @time: 2019/5/22 10:03
 * @description:
 */
public class CollectPresenter extends BasePresenter<CollectActivityContract.View> implements CollectActivityContract.Presenter {

    private DbHelper dbHelper;

    public CollectPresenter() {
        dbHelper = new DbHelperImp();
    }

    @Override
    public void loadArticles(boolean isFresh) {
        if (mView != null) {
            mView.showLoading();
            addSubscribe(Observable.create((ObservableOnSubscribe<List<Article>>) e -> {
                List<Article> articleList = dbHelper.loadArticles();
                if (articleList == null) {
                    articleList = new ArrayList<>();
                }
                e.onNext(articleList);
            }).compose(RxUtils.schedulerTransformer())
                    .filter(articleList -> mView != null)
                    .subscribe(articleList -> {
                                if (articleList.size() == 0) {
                                    mView.showEmpty();
                                }else {
                                    mView.loadArticlesSuccess(articleList, isFresh);
                                    mView.hideLoading();
                                }
                            }
                    ));
        }
    }

    @Override
    public void refresh() {
        loadArticles(true);
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
