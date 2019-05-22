package com.zkp.gankio.db;

import com.zkp.gankio.db.entity.Article;
import com.zkp.gankio.db.entity.Category;

import java.util.List;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.db
 * @time: 2019/5/21 10:31
 * @description:
 */
public interface DbHelper {

    /**
     * 向数据库中添加一条收藏数据
     *
     * @param article
     * @return
     */
    List<Article> addArticle(Article article);

    /**
     * 删除数据库中已收藏的文章信息
     *
     * @param articleId
     */
    void deleteArticle(String articleId);

    /**
     * 从数据库中加载收藏的文章信息
     *
     * @param articleId
     * @return
     */
    Article loadArticle(String articleId);

    /**
     * 从数据库中加载所有收藏的文章信息
     *
     * @return
     */
    List<Article> loadArticles();

    /**
     * 从数据库中加载所有的分类信息
     *
     * @return
     */
    List<Category> loadCategories();

    /**
     * 向数据库中添加分类信息
     *
     * @param categoryList
     */
    void addCategories(List<Category> categoryList);

}
