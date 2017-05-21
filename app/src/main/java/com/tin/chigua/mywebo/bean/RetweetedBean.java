package com.tin.chigua.mywebo.bean;

import java.util.List;

/**
 * Created by hasee on 5/11/2017.
 */
public class RetweetedBean {

    private List<StatusesBean> retweeted_status;
    private List<UserBean> retweeted_user;

    public List<UserBean> getRetweeted_user() {
        return retweeted_user;
    }

    public void setRetweeted_user(List<UserBean> retweeted_user) {
        this.retweeted_user = retweeted_user;
    }

    public List<StatusesBean> getRetweeted_status() {
        return retweeted_status;
    }

    public void setRetweeted_status(List<StatusesBean> retweeted_status) {
        this.retweeted_status = retweeted_status;
    }

    @Override
    public String toString() {
        return "Retweeted{" +
                "retweeted_status=" + retweeted_status +
                ", retweeted_user=" + retweeted_user +
                '}';
    }
}
