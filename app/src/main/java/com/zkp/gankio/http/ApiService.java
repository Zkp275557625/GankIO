package com.zkp.gankio.http;

import com.zkp.gankio.beans.BannerBean;
import com.zkp.gankio.beans.CategoryBean;
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
     * 获取首页banner
     *
     * @return
     */
    @GET("/api/data/福利/10/1")
    Observable<BannerBean> getBanner();

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

}
