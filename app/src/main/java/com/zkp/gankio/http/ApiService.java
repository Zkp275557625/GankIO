package com.zkp.gankio.http;

import com.zkp.gankio.beans.BannerBean;
import com.zkp.gankio.beans.CategoryBean;
import com.zkp.gankio.beans.HistoryContentBean;
import com.zkp.gankio.beans.HistoryGankBean;
import com.zkp.gankio.beans.ReadCategoryChildBean;
import com.zkp.gankio.beans.ReadCategoryMainBean;
import com.zkp.gankio.beans.ReadDetailBean;
import com.zkp.gankio.beans.TodayGankBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.http
 * @time: 2019/5/20 15:55
 * @description:
 */
public interface ApiService {

    /**
     * 获取今日干货
     *
     * @return
     */
    @GET("/api/today")
    Observable<TodayGankBean> getTodatGank();

    /**
     * 获取分类数据
     *
     * @param catrgory 分类
     * @param page     页码 page从1开始
     * @return
     */
    @GET("/api/data/{catrgory}/20/{page}")
    Observable<CategoryBean> getCategory(@Path("catrgory") String catrgory, @Path("page") int page);

    /**
     * 获取闲读主分类
     *
     * @return
     */
    @GET("/api/xiandu/categories")
    Observable<ReadCategoryMainBean> getReadCategoryMain();

    /**
     * 获取闲读子分类
     *
     * @param enName
     * @return
     */
    @GET("/api/xiandu/category/{en_name}")
    Observable<ReadCategoryChildBean> getReadCategoryChild(@Path("en_name") String enName);

    /**
     * 获取闲读列表
     *
     * @param id   子分类id
     * @param page 页码 从1开始
     * @return
     */
    @GET("/api/xiandu/data/id/{id}/count/20/page/{page}")
    Observable<ReadDetailBean> getReadDetailList(@Path("id") String id, @Path("page") int page);

    /**
     * 获取发过干货的日期列表
     *
     * @return
     */
    @GET("/api/day/history")
    Observable<HistoryGankBean> getHistoryGank();

    /**
     * 获取首页banner
     *
     * @param page 页码 从1开始
     * @return
     */
    @GET("/api/data/福利/20/{page}")
    Observable<BannerBean> getBanner(@Path(("page")) int page);

    /**
     * 获取历史干货的内容
     *
     * @param date 日期 2019/05/22
     * @return
     */
    @GET("/api/history/content/day/{date}")
    Observable<HistoryContentBean> getHistoryContent(@Path(("date")) String date);

}
