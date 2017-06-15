package com.tin.chigua.mywebo.constant;

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

    public static final String REFRESH_TOKEN = "https://api.weibo.com/oauth2/" +
            "access_token?client_id=YOUR_CLIENT_ID&client_secret=YOUR_CLIENT_SECRET" +
            "&grant_type=refresh_token&redirect_uri=YOUR_REGISTERED_REDIRECT_URI&refresh_token=";

}
