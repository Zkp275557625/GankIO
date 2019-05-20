package com.zkp.gankio.beans;

import java.util.List;

/**
 * @author: zkp
 * @project: GankIO
 * @package: com.zkp.gankio.beans
 * @time: 2019/5/20 15:41
 * @description:
 */
public class BaseGankBean {

    /**
     * _id : 5c0622429d2122308e7445cf
     * createdAt : 2018-12-04T06:44:18.364Z
     * desc : 一个基于ijkplayer的完整视频播放器封装，支持自定义，拓展性强，已经用于实际开发中
     * images : ["https://ww1.sinaimg.cn/large/0073sXn7ly1g1p96mf7zlj308c0pfjtm","https://ww1.sinaimg.cn/large/0073sXn7ly1g1p96mlxyxj308c0go748","https://ww1.sinaimg.cn/large/0073sXn7ly1g1p96mw2gaj30k30bv406","https://ww1.sinaimg.cn/large/0073sXn7ly1g1p96n3bp6j308c0et0tm","https://ww1.sinaimg.cn/large/0073sXn7ly1g1p96n9715j308c0goq3f"]
     * publishedAt : 2019-04-10T00:00:00.0Z
     * source : web
     * type : Android
     * url : https://github.com/yangchong211/YCVideoPlayer
     * used : true
     * who : fingdo
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "BaseGankBean{" +
                "_id='" + _id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who='" + who + '\'' +
                ", images=" + images +
                '}';
    }
}
