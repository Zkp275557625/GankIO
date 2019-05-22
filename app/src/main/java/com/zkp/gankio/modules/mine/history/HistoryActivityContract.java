package com.zkp.gankio.modules.mine.history;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zkp.gankio.base.presenter.IPresenter;
import com.zkp.gankio.base.view.IView;
import com.zkp.gankio.beans.BannerBean;
import com.zkp.gankio.beans.HistoryContentBean;
import com.zkp.gankio.beans.HistoryGankBean;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.mine.history
 * @time: 2019/5/22 13:57
 * @description:
 */
public class HistoryActivityContract {

    public interface View extends IView {

        /**
         * 获取图片成功
         *
         * @param data
         * @param isFresh
         */
        void getImagesSuccess(BannerBean data, boolean isFresh);

        /**
         * 获取图片失败
         *
         * @param errMsg
         */
        void getImagesError(String errMsg);

        /**
         * 获取发过干货的日期成功
         *
         * @param historyGankBean
         */
        void getHistoryGankSuccess(HistoryGankBean historyGankBean);

        /**
         * 获取发过干货的日期失败
         *
         * @param errMsg
         */
        void getHistoryGankError(String errMsg);

        /**
         * 获取历史干货内容成功
         *
         * @param data
         * @param helper
         */
        void getHistoryGankContentSuccess(HistoryContentBean data, BaseViewHolder helper);

        /**
         * 获取历史干货内容失败
         *
         * @param errMsg
         */
        void getHistoryGankContentError(String errMsg);

    }

    public interface Presenter extends IPresenter<View> {
        /**
         * 获取图片做item背景
         *
         * @param page
         * @param isFresh
         */
        void getImages(int page, boolean isFresh);

        /**
         * 获取发过干货的日期
         */
        void getHistoryGank();

        /**
         * 获取历史干货的内容
         *
         * @param date
         * @param helper
         */
        void getHistoryGankContent(String date, BaseViewHolder helper);

        /**
         * 刷新
         */
        void refresh();

        /**
         * 加载更多
         */
        void loadMore();
    }

}
