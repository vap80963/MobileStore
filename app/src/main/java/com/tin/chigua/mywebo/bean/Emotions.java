package com.tin.chigua.mywebo.bean;

import java.io.Serializable;

/**
 * Created by hasee on 5/17/2017.
 */

public class Emotions implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * category : 休闲
     * common : true
     * hot : false
     * icon : http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/eb/smile.gif
     * phrase : [呵呵]
     * picid : null
     * type : face
     * url : http://img.t.sinajs.cn/t35/style/images/common/face/ext/normal/eb/smile.gif
     * value : [呵呵]
     */

    private String category;
    private boolean common;
    private boolean hot;
    private String icon;
    private String phrase;
    private Object picid;
    private String type;
    private String url;
    private String value;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isCommon() {
        return common;
    }

    public void setCommon(boolean common) {
        this.common = common;
    }

    public boolean isHot() {
        return hot;
    }

    public void setHot(boolean hot) {
        this.hot = hot;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public Object getPicid() {
        return picid;
    }

    public void setPicid(Object picid) {
        this.picid = picid;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
