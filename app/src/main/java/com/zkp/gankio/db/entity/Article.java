package com.zkp.gankio.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.db
 * @time: 2019/5/21 10:23
 * @description:
 */
@Entity
public class Article {

    @Id
    private String articleId;

    private String articleLink;

    private String articleTitle;

    private String articleAuthor;

    private String articleTyppe;

    @Generated(hash = 127824941)
    public Article(String articleId, String articleLink, String articleTitle,
            String articleAuthor, String articleTyppe) {
        this.articleId = articleId;
        this.articleLink = articleLink;
        this.articleTitle = articleTitle;
        this.articleAuthor = articleAuthor;
        this.articleTyppe = articleTyppe;
    }

    @Generated(hash = 742516792)
    public Article() {
    }

    public String getArticleId() {
        return articleId == null ? "" : articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getArticleLink() {
        return articleLink == null ? "" : articleLink;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }

    public String getArticleTitle() {
        return articleTitle == null ? "" : articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleAuthor() {
        return articleAuthor == null ? "" : articleAuthor;
    }

    public void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor;
    }

    public String getArticleTyppe() {
        return articleTyppe == null ? "" : articleTyppe;
    }

    public void setArticleTyppe(String articleTyppe) {
        this.articleTyppe = articleTyppe;
    }
}
