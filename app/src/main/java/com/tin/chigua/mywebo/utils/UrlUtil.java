package com.tin.chigua.mywebo.utils;

/**
 * Created by hasee on 5/8/2017.
 */

public class UrlUtil {

    private static final String COMMON_URL = "https://api.weibo.com/2/";

    //公共微博
    public static final String PUBLIC_TIMELINE = COMMON_URL + "statuses/public_timeline.json";
    //用户关注微博
    public static final String HOME_TIMELINE = COMMON_URL + "statuses/home_timeline.json";
    //获取表情图片
    public static final String EMOJIS = COMMON_URL + "emotions.json";

}
