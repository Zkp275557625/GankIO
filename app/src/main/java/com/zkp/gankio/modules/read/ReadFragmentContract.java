package com.zkp.gankio.modules.read;

import com.zkp.gankio.base.presenter.IPresenter;
import com.zkp.gankio.base.view.IView;
import com.zkp.gankio.beans.ReadCategoryMainBean;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.read
 * @time: 2019/5/21 15:43
 * @description:
 */
public class ReadFragmentContract {

    public interface View extends IView {

        /**
         * 获取闲读主分类成功
         *
         * @param data
         */
        void getReadCategoryMainSuccess(ReadCategoryMainBean data);

        /**
         * 获取闲读主分类失败
         *
         * @param errMsg
         */
        void getReadCategoryMainError(String errMsg);
    }

    public interface Presenter extends IPresenter<View> {
        /**
         * 获取闲读主分类
         */
        void getReadCategoryMain();
    }
}
