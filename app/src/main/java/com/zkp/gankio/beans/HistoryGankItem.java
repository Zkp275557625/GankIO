package com.zkp.gankio.beans;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.beans
 * @time: 2019/5/22 14:11
 * @description:
 */
public class HistoryGankItem {

    /**
     * 干货历史的标题
     */
    private String title;

    /**
     * 福利图片Url
     */
    private String iamgeUrl;

    /**
     * 发布日期
     */
    private String time;

    public HistoryGankItem(String title, String iamgeUrl, String time) {
        this.title = title;
        this.iamgeUrl = iamgeUrl;
        this.time = time;
    }

    public HistoryGankItem(String title, String time) {
        this.title = title;
        this.time = time;
    }

    public HistoryGankItem(String time) {
        this.time = time;
    }

    public HistoryGankItem() {
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIamgeUrl() {
        return iamgeUrl == null ? "" : iamgeUrl;
    }

    public void setIamgeUrl(String iamgeUrl) {
        this.iamgeUrl = iamgeUrl;
    }

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
