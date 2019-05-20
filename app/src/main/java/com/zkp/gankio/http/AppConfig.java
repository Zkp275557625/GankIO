package com.zkp.gankio.http;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.http
 * @time: 2019/5/20 13:47
 * @description:
 */
public class AppConfig {

    /**
     * 读取超时 默认设置为10s
     */
    public static final int TIMEOUT_READ = 10;
    /**
     * 连接超时 默认设置为10s
     */
    public static final int TIMEOUT_CONNECTION = 10;

    /**
     * 连续两次点击时间间隔<2000,则退出应用
     */
    public static final long EXIT_TIME = 2000;

    public static final String CURRENT_FRAGMENT_KEY = "current_fragment";

    /**
     * 干货集中营 baseUrl
     */
    public static final String BASE_URL = "http://gank.io";

    /**
     * 首页
     */
    public static final int TYPE_HOME = 0;
    /**
     * 分类
     */
    public static final int TYPE_CATRGORY = 1;
    /**
     * 福利(图片)
     */
    public static final int TYPE_IMAGES = 2;
    /**
     * 我的
     */
    public static final int TYPE_MINE = 3;

}
