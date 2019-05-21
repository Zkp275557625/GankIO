package com.zkp.gankio.modules.category;

import com.zkp.gankio.base.presenter.BasePresenter;
import com.zkp.gankio.db.DbHelper;
import com.zkp.gankio.db.DbHelperImp;
import com.zkp.gankio.db.entity.Category;
import com.zkp.gankio.utils.RxUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.category
 * @time: 2019/5/21 11:39
 * @description:
 */
public class CategoryPresenter extends BasePresenter<CategoryFragmentContract.View> implements CategoryFragmentContract.Presenter {

    private DbHelper dbHelper;

    public CategoryPresenter() {
        this.dbHelper = new DbHelperImp();
    }

    @Override
    public void loadCategories() {
        addSubscribe(Observable.create((ObservableOnSubscribe<List<Category>>) e -> {
            List<Category> categoryList = dbHelper.loadCategories();
            e.onNext(categoryList);
        }).compose(RxUtils.schedulerTransformer())
                .filter(categoryList -> mView != null)
                .subscribe(categoryList -> {
                            mView.loadCategoriesSuccess(categoryList);
                        }
                ));
    }
}
