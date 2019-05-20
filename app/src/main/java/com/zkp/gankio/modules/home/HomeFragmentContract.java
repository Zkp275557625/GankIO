package com.zkp.gankio.modules.home;

import com.zkp.gankio.base.presenter.IPresenter;
import com.zkp.gankio.base.view.IView;
import com.zkp.gankio.beans.BannerBean;
import com.zkp.gankio.beans.TodayGankBean;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.modules.home
 * @time: 2019/5/20 14:53
 * @description:
 */
public class HomeFragmentContract {

    public interface View extends IView {

        /**
         * 获取首页banner成功
         *
         * @param data BannerBean
         */
        void getBannerSuccess(BannerBean data);

        /**
         * 获取首页Banner失败
         *
         * @param errMsg String
         */
        void getBannerError(String errMsg);

        /**
         * 获取首页今日干货成功
         *
         * @param data TodayGankBean
         */
        void getTodayGankSuceess(TodayGankBean data);

        /**
         * 获取首页今日干货失败
         *
         * @param errMsg String
         */
        void getTodayGankError(String errMsg);

    }

    public interface Presenter extends IPresenter<View> {

        /**
         * 获取首页Banner
         */
        void getBanner();

        /**
         * 获取首页今日干货
         */
        void getTodayGank();

    }

}
