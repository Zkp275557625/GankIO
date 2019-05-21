package com.zkp.gankio.modules.read.child;

import com.zkp.gankio.base.presenter.IPresenter;
import com.zkp.gankio.base.view.IView;
import com.zkp.gankio.beans.ReadCategoryChildBean;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.read.child
 * @time: 2019/5/21 16:34
 * @description:
 */
public class ReadChildFragmentContract {

    public interface View extends IView {
        /**
         * 获取闲读子分类成功
         *
         * @param data
         */
        void getReadCategoryChildSuccess(ReadCategoryChildBean data);

        /**
         * 获取闲读子分类失败
         *
         * @param errMsg
         */
        void getReadCategoryChildError(String errMsg);
    }

    public interface Presenter extends IPresenter<View> {
        /**
         * 获取闲读子分类
         *
         * @param enName
         */
        void getReadCategoryChild(String enName);
    }

}
