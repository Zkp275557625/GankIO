package com.zkp.gankio.modules.home;

import com.zkp.gankio.base.presenter.BasePresenter;
import com.zkp.gankio.beans.TodayGankBean;
import com.zkp.gankio.db.DbHelper;
import com.zkp.gankio.db.DbHelperImp;
import com.zkp.gankio.db.entity.Article;
import com.zkp.gankio.db.entity.Category;
import com.zkp.gankio.http.ApiService;
import com.zkp.gankio.http.AppConfig;
import com.zkp.gankio.http.HttpUtil;
import com.zkp.gankio.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.home
 * @time: 2019/5/20 14:54
 * @description:
 */
public class HomePresenter extends BasePresenter<HomeFragmentContract.View> implements HomeFragmentContract.Presenter {

    private DbHelper dbHelper;

    public HomePresenter() {
        dbHelper = new DbHelperImp();
    }

    @Override
    public void getTodayGank() {
        if (mView != null) {
            mView.showLoading();

            HttpUtil.request(HttpUtil.createApi(AppConfig.BASE_URL, ApiService.class).getTodatGank(), new HttpUtil.IResponseListener<TodayGankBean>() {
                @Override
                public void onSuccess(TodayGankBean data) {
                    if (!data.isError()) {
                        mView.getTodayGankSuceess(data);
                    } else {
                        mView.getTodayGankError("获取今日干货失败");
                    }
                    mView.hideLoading();
                }

                @Override
                public void onFail(String errMsg) {
                    mView.getTodayGankError("获取今日干货失败");
                    mView.hideLoading();
                }
            });
        }
    }

    @Override
    public void addCategories(List<Category> categoryList) {
        addSubscribe(Observable.create((ObservableOnSubscribe<List<Category>>) e -> {
            dbHelper.addCategories(categoryList);
            e.onNext(categoryList);
        }).compose(RxUtils.schedulerTransformer())
                .filter(articleList -> mView != null)
                .subscribe(articleList -> {
                            mView.addCategoriesSuccess();
                        }
                ));
    }

    @Override
    public void loadArticles() {
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
                                mView.loadArticlesSuccess(articleList);
                                mView.hideLoading();
                            }
                    ));
        }
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
