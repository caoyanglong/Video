package com.day.l.video.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CYL on 16-9-10.
 * email:670654904@qq.com
 */
public class Constants {
    public final static String USER_LOGIN = "user_login";
    public final static String USER_PASS = "user_pass";
    public final static String SUPERIO = "superior";
    public final static String TOKEN = "token";
    public final static String MONEY_KEY = "money";
    public final static String EXPIRATION_TIME_KEY = "expiration_time";
    public final static String FULL_NAME = "full_name";
    public final static String MOBILE_KEY = "mobile";
    public final static String ALIPAY_KEY = "alipay";
    public final static String AVATAR_KEY = "avatar";

    public final static String SYSTEM_KEY = "system";
    public final static String VERIOSN_KEY = "version";
    public final static String RED_BAGS_ID = "redbag_id";
    public final static String TYPE_KEY = "type";
    public final static String APPLICATION_KEY = "application";
    public final static String GAME_KEY = "game";
    public final static String MESSAGE_ID_KEY = "message_id";
    /**
     * 注册
     */
    public final static String REGISTER_API = "http://115.28.9.92/eliu/index.php/App/Token/register";
    /**
     * 登陆
     */
    public final static String LONGIN_API = "http://115.28.9.92/eliu/index.php/App/Token/login";
    /**
     * 刷新token
     */
    public final static String REFRESH_TOKEN_API = "http://115.28.9.92/eliu/index.php/App/Token/isExpaireDate";
    /**
     * 启动页面
     */
    public final static String SPLASH_ICON_API = "http://115.28.9.92/eliu/index.php/App/Start/index";
    /**
     * 商家header 页面广告
     */
    public final static String HEADER_AD_ICON_API = "http://115.28.9.92/eliu/index.php/App/Index/slide";
    /**
     * 中间广告数据
     */
    public final static String CENTER_AD_ICON_API = "http://115.28.9.92/eliu/index.php/App/Index/business";
    /**
     * 用户个人资料的信息
     */
    public final static String USER_INFO_API = "http://115.28.9.92/eliu/index.php/App/User/user_info";
    /**
     * 我的获取红包列表
     */
    public final static String MY_RED_BAGS_LIST_API = "http://115.28.9.92/eliu/index.php/App/Redbag/user_gains";
    /**
     * 我的体现列表
     */
    public final static String MY_CASH_LIST_API = "http://115.28.9.92/eliu/index.php/App/Cash/index";
    /**
     * 红包列表
     */
    public final static String RED_BAG_LIST_API = "http://115.28.9.92/eliu/index.php/App/Redbag/index";
    /**
     * 惊喜页面
     */
    public final static String SURPRISE_API = "http://115.28.9.92/eliu/index.php/App/Surprise/index";
    /**
     * 修改个人资料
     */
    public final static String SET_USER_INFO_API = "http://115.28.9.92/eliu/index.php/App/User/set_info";
    /**
     * 我的消息列表
     */
    public final static String MESSAGE_LIST_API = "http://115.28.9.92/eliu/index.php/App/Message/index";
    /**
     * 上传头像到服务器
     */
    public final static String UPLOAD_HEAD_ICON_API = "http://115.28.9.92/eliu/index.php/App/User/avatar_upload";
    /**
     * 应用列表
     */
    public final static String APP_LIST_API = "http://115.28.9.92/eliu/index.php/App/Application/index";
    /**
     * 游戏列表
     */
    public final static String GAME_LIST_API = "http://115.28.9.92/doc/index.php?s=/home/page/index/page_id/12";
    /**
     * 更新版本
     */
    public final static String UPDATE_VERSION_API = "http://115.28.9.92/eliu/index.php/App/Version/up";
    /**
     * 提现申请
     */
    public final static String APPLY_CASH_PAI = "http://115.28.9.92/eliu/index.php/App/Cash/do_cash";
    /**
     * 检测token 是否有效
     */
    public final static String CHECK_TOKEN_API = "http://115.28.9.92/eliu/index.php/App/Token/check_token";
    /**
     * 分享获取红包的接口
     */
    public final static String GET_SHARE_RED_BAGS_API = "http://115.28.9.92/eliu/index.php/App/Redbag/share";

    /**
     * 删除我的消息接口
     */
    public final static String DELETE_MESSAGE_API = "http://115.28.9.92/eliu/index.php/App/Message/del_mess";
    /***
     * 消息阅读
     */
    public final static String READ_MESSAGE_API = "http://115.28.9.92/eliu/index.php/App/Message/read_mess";
    /**
     * webview 需要加载的地址
     */
    public final static String LOAD_URL = "load_url";


    /******************************************************************************************************/
    public static final String AppID_KEY = "AppID";
    public static final String TimeStamp_KEY = "TimeStamp";
    public static final String JSON_KEY = "JSON_KEY";
    public static final String INDEX_KEY = "index";
    public static final String VIDEO_RECORDS = "video_records";

    public static final String IEMI_KEY = "IEME";
    public static final String ID = "ID";
    public static final String VIDEO_NAME = "VIDEO_NAME";
    public static final String TimeStamp = "TimeStamp";
    /**
     * 获取token api
     */
    public static final String GET_TOKEN_API = "/API/v.asmx/getMyTokens";
    /**
     * 获取视频数据
     */
    public static final String GET_VIDEO_DATA_API = "/API/v.asmx/getHomeData";
    /**
     * 解析视频地址的接口
     */
    public static final String GET_VIDEOS_URL_API = "/API/v.asmx/getVideos";
    /**
     * 视频详情
     */
    public static final String GET_VIDEO_DETAIL_INFO_API = "/API/v.asmx/getVideoInfos";
    /**
     * 获取视频列表
     */
    public static final String GET_VIDEO_LIST_DATA_API = "/API/v.asmx/getVideoData";



    public static final String APPID = "f8cc1b8da74147bbbd50619700a7fef3";

    public static final String keyConstants = "000000000000000000000000";
    public static final String PRIVATEKEY = "7C26642A4F204DF48791F8C2B776D046";
    public final static String PAGE_URL_KEY = "pageUrl";
    public static final String ImageHost = "http://api.v.eeliu.com";
    /**
     * 第三方的解析平台
     */
    public static final String PLUGIN_API = "http://jiexi888.duapp.com/?url=";


    public static String getImageUrl(String iconUrl){
        if(!iconUrl.contains("http")){
            iconUrl = ImageHost+iconUrl;
        }
        return iconUrl;
    }
    public static List<String> VideoHostList = new ArrayList<>();
    static {
        VideoHostList.add("http://cache1.video.v.zhuovi.cn");
        VideoHostList.add("http://cache2.video.v.zhuovi.cn");
        VideoHostList.add("http://cache3.video.v.zhuovi.cn");
        VideoHostList.add("http://cache4.video.v.zhuovi.cn");
        VideoHostList.add("http://cache5.video.v.zhuovi.cn");
        VideoHostList.add("http://api.v.zhuovi.net");
        VideoHostList.add("http://api.v.zhuovi.cn");
    }
    /*********************************************************************************************************/


}
