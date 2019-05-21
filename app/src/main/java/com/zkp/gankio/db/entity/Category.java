package com.zkp.gankio.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.db.entity
 * @time: 2019/5/21 11:21
 * @description:
 */
@Entity
public class Category {

    @Id(autoincrement = true)
    private Long id;

    private String categoryName;

    @Generated(hash = 686479975)
    public Category(Long id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    @Generated(hash = 1150634039)
    public Category() {
    }

    public Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName == null ? "" : categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
