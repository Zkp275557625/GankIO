package com.zkp.gankio.db;

import com.zkp.gankio.app.App;
import com.zkp.gankio.db.entity.Article;
import com.zkp.gankio.db.entity.Category;
import com.zkp.gankio.db.greendao.DaoSession;

import java.util.List;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.db
 * @time: 2019/5/21 10:39
 * @description:
 */
public class DbHelperImp implements DbHelper {

    private DaoSession daoSession;

    public DbHelperImp() {
        daoSession = App.getDaoSession();
    }

    @Override
    public List<Article> addArticle(Article article) {
        daoSession.getArticleDao().insert(article);
        return daoSession.getArticleDao().loadAll();
    }

    @Override
    public void deleteArticle(String articleId) {
        daoSession.getArticleDao().deleteByKey(articleId);
    }

    @Override
    public Article loadArticle(String articleId) {
        return daoSession.getArticleDao().load(articleId);
    }

    @Override
    public List<Article> loadArticles() {
        return daoSession.getArticleDao().loadAll();
    }

    @Override
    public List<Category> loadCategories() {
        return daoSession.getCategoryDao().loadAll();
    }

    @Override
    public void addCategories(List<Category> categoryList) {
        List<Category> categories = loadCategories();
        if (categories != null && categories.size() > 0) {
            return;
        }
        for (int i = 0; i < categoryList.size(); i++) {
            daoSession.getCategoryDao().insert(categoryList.get(i));
        }
    }
}
