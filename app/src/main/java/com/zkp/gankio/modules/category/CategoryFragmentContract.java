package com.zkp.gankio.modules.category;

import com.zkp.gankio.base.presenter.IPresenter;
import com.zkp.gankio.base.view.IView;
import com.zkp.gankio.db.entity.Category;

import java.util.List;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.category
 * @time: 2019/5/21 11:39
 * @description:
 */
public class CategoryFragmentContract {

    public interface View extends IView {
        /**
         * 从数据库中加载分类数据成功
         *
         * @param categoryList
         */
        void loadCategoriesSuccess(List<Category> categoryList);
    }

    public interface Presenter extends IPresenter<View> {
        /**
         * 从数据库中加载分类数据
         */
        void loadCategories();
    }

}
