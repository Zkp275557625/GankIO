package com.zkp.gankio.modules.read.detail;

import com.zkp.gankio.base.presenter.IPresenter;
import com.zkp.gankio.base.view.IView;
import com.zkp.gankio.beans.ReadDetailBean;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.read.detail
 * @time: 2019/5/21 17:06
 * @description:
 */
public class ReadDetailFragmentContract {

    public interface View extends IView {

        /**
         * 获取闲读列表成功
         *
         * @param data
         * @param isFresh 是否刷新
         */
        void getReadDetailListSuccess(ReadDetailBean data, boolean isFresh);

        /**
         * 获取闲读列表失败
         *
         * @param errMsg
         */
        void getReadDetailListError(String errMsg);
    }

    public interface Presenter extends IPresenter<View> {

        /**
         * 获取闲读列表
         *
         * @param id      子分类id
         * @param page    页码 从1开始
         * @param isFresh 是否刷新
         */
        void getReadDetailList(String id, int page, boolean isFresh);

        /**
         * 刷新
         *
         * @param id 子分类id
         */
        void refresh(String id);

        /**
         * 加载更多
         */
        void loadMore();
    }

}
