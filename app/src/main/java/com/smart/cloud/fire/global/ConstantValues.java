package com.smart.cloud.fire.global;

/**
 * Created by Administrator on 2016/7/27.
 */
public class ConstantValues {
    //public static final String SERVER_IP_NEW ="http://192.168.4.111:51091/fireSystem/";
    //测试IP:
    public static final String SERVER_IP_NEW ="http://119.29.224.28:51091/fireSystem/";
    //测试IP:
    public static final String SERVER_IP_NEW_TEST="http://119.29.224.28:51090/fireSystem/";
    //正式IP:
   //public static final String SERVER_IP_NEW ="http://119.29.155.148:51091/fireSystem/";

    public static final String SERVER_PUSH ="http://119.29.155.148/GeTuiPush/";
    public static final String SERVER_YOOSEE_IP_ONE ="http://api1.cloudlinks.cn/";
    public static final String SERVER_YOOSEE_IP_TWO = "http://api2.cloudlinks.cn/";
    public static final String SERVER_YOOSEE_IP_THREE = "http://api3.cloud-links.net/";
    public static final String SERVER_YOOSEE_IP_FOUR = "http://api4.cloud-links.net/";
    public static final String[] fragmentStr ={"mAgencyMapFragment","mHomeFragment","mCategoryFragment","mMapFragment","mCollectFragment","mSettingFragment","mCallAlarmFragment"};
    public static final String PACKAGE_NAME = "com.smart.cloud.fire.utils.";
    public static final String FORGET_PASSWORD_URL = "http://cloudlinks.cn/pw/";
    //更新接口
    public static final String UPDATE_URL="http://119.29.155.148/download/update_cloudfire.xml";
    public static final String ERROR_URL = "http://182.254.234.243:8080/UploadError/UploadServlet";

    public static class Privilege{
        public static final int NORMAL_MAN = 1;
        public static final int AGENCY_MAN = 2;
        public static final int ADMINISTATOR = 3;
        public static final int POLICEMAEN = 5;
        public static final int SUPER_ADMIN = 4;
    }

    public static class Update{
        public static final String SAVE_PATH = ConstantValues.CACHE_FOLDER_NAME+"/apk";
        public static final String FILE_NAME = ConstantValues.CACHE_FOLDER_NAME+".apk";
        public static final String INSTALL_APK = "application/vnd.android.package-archive";
    }

    public static class Action{
        public static final String GET_GE_TUI_PUSH_CID = "GET_GE_TUI_PUSH_CID";
        public static final String RECEIVE_MSG = PACKAGE_NAME+"RECEIVE_MSG";
        public final static String ACTION_UPDATE = PACKAGE_NAME+"ACTION_UPDATE";

        public final static String REFRESH_CONTANTS = PACKAGE_NAME+"refresh.contants";
        public final static String ACTION_REFRESH_NEARLY_TELL = PACKAGE_NAME+"ACTION_REFRESH_NEARLY_TELL";
        public final static String GET_FRIENDS_STATE = PACKAGE_NAME+"GET_FRIENDS_STATE";

        public static final String REFRESH_ALARM_RECORD = PACKAGE_NAME+"REFRESH_ALARM_RECORD";
        public static final String RADAR_SET_WIFI_SUCCESS=PACKAGE_NAME+"RADAR_SET_WIFI_SUCCESS";
        public static final String RADAR_SET_WIFI_FAILED=PACKAGE_NAME+"RADAR_SET_WIFI_FAILED";
        public static final String MONITOR_NEWDEVICEALARMING=PACKAGE_NAME+"MONITOR_NEWDEVICEALARMING";
    }

    public static class DeviceFlag {
        public static final int UNSET_PASSWORD = 0;
        public static final int AP_MODE = 2;
    }

    public static class	DeviceState{
        public static final int OFFLINE = 0;
    }

    public static class DefenceState{
        public static final int DEFENCE_STATE_LOADING = 2;
        public static final int DEFENCE_STATE_WARNING_PWD = 3;
        public static final int DEFENCE_STATE_WARNING_NET = 4;
    }

    public static class P2P_SET{

        public static class ACK_RESULT{
            public static final int ACK_PWD_ERROR = 9999;
            public static final int ACK_NET_ERROR = 9998;
            public static final int ACK_INSUFFICIENT_PERMISSIONS=9996;
        }

        public static class DEVICE_UPDATE {
            public static final int UNKNOWN=-1;
        }

    }

    public static class APmodeState{
        public static final int LINK = 0;
        public static final int UNLINK = 1;
    }

    public static class ConnectType{
        public final static int P2PCONNECT=0;
        public final static int RTSPCONNECT=1;
    }

    public static final String CACHE_FOLDER_NAME = "smartcloudfire";
    public static class Image{
        public static final float USER_HEADER_ROUND_SCALE = 1f/32f;
    }

    public static class P2P {
        // 设备不支持
        public static final String RET_DEVICE_NOT_SUPPORT = PACKAGE_NAME
                + "RET_DEVICE_NOT_SUPPORT";

        // 检查密码
        public static final String ACK_RET_CHECK_PASSWORD = PACKAGE_NAME
                + "ACK_RET_CHECK_PASSWORD";

        // 获取设备各种设置
        public static final String ACK_RET_GET_NPC_SETTINGS = PACKAGE_NAME
                + "ACK_RET_GET_NPC_SETTINGS";

        // 设备时间相关
        public static final String ACK_RET_SET_TIME = PACKAGE_NAME
                + "ACK_RET_SET_TIME";
        public static final String ACK_RET_GET_TIME = PACKAGE_NAME
                + "ACK_RET_GET_TIME";
        public static final String RET_SET_TIME = PACKAGE_NAME + "RET_SET_TIME";
        public static final String RET_GET_TIME = PACKAGE_NAME + "RET_GET_TIME";

        // 视频格式相关
        public static final String ACK_RET_SET_VIDEO_FORMAT = PACKAGE_NAME
                + "ACK_RET_SET_VIDEO_FORMAT";
        public static final String RET_SET_VIDEO_FORMAT = PACKAGE_NAME
                + "RET_SET_VIDEO_FORMAT";
        public static final String RET_GET_VIDEO_FORMAT = PACKAGE_NAME
                + "RET_GET_VIDEO_FORMAT";

        // 音量大小相关
        public static final String ACK_RET_SET_VIDEO_VOLUME = PACKAGE_NAME
                + "ACK_RET_SET_VIDEO_VOLUME";
        public static final String RET_SET_VIDEO_VOLUME = PACKAGE_NAME
                + "RET_SET_VIDEO_VOLUME";
        public static final String RET_GET_VIDEO_VOLUME = PACKAGE_NAME
                + "RET_GET_VIDEO_VOLUME";

        // 修改设备密码相关
        public static final String ACK_RET_SET_DEVICE_PASSWORD = PACKAGE_NAME
                + "ACK_RET_SET_DEVICE_PASSWORD";
        public static final String RET_SET_DEVICE_PASSWORD = PACKAGE_NAME
                + "RET_SET_DEVICE_PASSWORD";

        // 设置网络类型相关
        public static final String ACK_RET_SET_NET_TYPE = PACKAGE_NAME
                + "ACK_RET_SET_NET_TYPE";
        public static final String RET_SET_NET_TYPE = PACKAGE_NAME
                + "RET_SET_NET_TYPE";
        public static final String RET_GET_NET_TYPE = PACKAGE_NAME
                + "RET_GET_NET_TYPE";

        // 设置WIFI相关
        public static final String ACK_RET_SET_WIFI = PACKAGE_NAME
                + "ACK_RET_SET_WIFI";
        public static final String ACK_RET_GET_WIFI = PACKAGE_NAME
                + "ACK_GET_SET_WIFI";
        public static final String RET_SET_WIFI = PACKAGE_NAME + "RET_SET_WIFI";
        public static final String RET_GET_WIFI = PACKAGE_NAME + "RET_GET_WIFI";

        // 设置绑定报警ID
        public static final String ACK_RET_SET_BIND_ALARM_ID = PACKAGE_NAME
                + "ACK_RET_SET_BIND_ALARM_ID";
        public static final String ACK_RET_GET_BIND_ALARM_ID = PACKAGE_NAME
                + "ACK_RET_GET_BIND_ALARM_ID";
        public static final String RET_SET_BIND_ALARM_ID = PACKAGE_NAME
                + "RET_SET_BIND_ALARM_ID";
        public static final String RET_GET_BIND_ALARM_ID = PACKAGE_NAME
                + "RET_GET_BIND_ALARM_ID";
        public static final String DELETE_BINDALARM_ID = PACKAGE_NAME
                + "DELETE_BINDALARM_ID";

        // 设置报警邮箱
        public static final String ACK_RET_SET_ALARM_EMAIL = PACKAGE_NAME
                + "ACK_RET_SET_ALARM_EMAIL";
        public static final String ACK_RET_GET_ALARM_EMAIL = PACKAGE_NAME
                + "ACK_RET_GET_ALARM_EMAIL";
        public static final String RET_SET_ALARM_EMAIL = PACKAGE_NAME
                + "RET_SET_ALARM_EMAIL";
        public static final String RET_GET_ALARM_EMAIL = PACKAGE_NAME
                + "RET_GET_ALARM_EMAIL";
        public static final String RET_GET_ALARM_EMAIL_WITHSMTP = PACKAGE_NAME
                + "RET_GET_ALARM_EMAIL_WITHSMTP";

        // 设置移动侦测
        public static final String ACK_RET_SET_MOTION = PACKAGE_NAME
                + "ACK_RET_SET_MOTION";
        public static final String RET_SET_MOTION = PACKAGE_NAME
                + "RET_SET_MOTION";
        public static final String RET_GET_MOTION = PACKAGE_NAME
                + "RET_GET_MOTION";

        // 设置蜂鸣器
        public static final String ACK_RET_SET_BUZZER = PACKAGE_NAME
                + "RET_SET_BUZZER";
        public static final String RET_SET_BUZZER = PACKAGE_NAME
                + "RET_SET_BUZZER";
        public static final String RET_GET_BUZZER = PACKAGE_NAME
                + "RET_GET_BUZZER";

        // 设置录像模式
        public static final String ACK_RET_SET_RECORD_TYPE = PACKAGE_NAME
                + "ACK_RET_SET_RECORD_TYPE";
        public static final String RET_SET_RECORD_TYPE = PACKAGE_NAME
                + "RET_SET_RECORD_TYPE";
        public static final String RET_GET_RECORD_TYPE = PACKAGE_NAME
                + "RET_GET_RECORD_TYPE";

        // 设置录像时长
        public static final String ACK_RET_SET_RECORD_TIME = PACKAGE_NAME
                + "ACK_RET_SET_RECORD_TIME";
        public static final String RET_SET_RECORD_TIME = PACKAGE_NAME
                + "RET_SET_RECORD_TIME";
        public static final String RET_GET_RECORD_TIME = PACKAGE_NAME
                + "RET_GET_RECORD_TIME";

        // 设置录像计划时间
        public static final String ACK_RET_SET_RECORD_PLAN_TIME = PACKAGE_NAME
                + "ACK_RET_SET_RECORD_PLAN_TIME";
        public static final String RET_SET_RECORD_PLAN_TIME = PACKAGE_NAME
                + "RET_SET_RECORD_PLAN_TIME";
        public static final String RET_GET_RECORD_PLAN_TIME = PACKAGE_NAME
                + "RET_GET_RECORD_PLAN_TIME";

        // 防区设置
        public static final String ACK_RET_SET_DEFENCE_AREA = PACKAGE_NAME
                + "ACK_RET_SET_DEFENCE_AREA";
        public static final String ACK_RET_GET_DEFENCE_AREA = PACKAGE_NAME
                + "ACK_RET_GET_DEFENCE_AREA";
        public static final String ACK_RET_CLEAR_DEFENCE_AREA = PACKAGE_NAME
                + "ACK_RET_CLEAR_DEFENCE_AREA";
        public static final String RET_CLEAR_DEFENCE_AREA = PACKAGE_NAME
                + "RET_CLEAR_DEFENCE_AREA";
        public static final String RET_SET_DEFENCE_AREA = PACKAGE_NAME
                + "RET_SET_DEFENCE_AREA";
        public static final String RET_GET_DEFENCE_AREA = PACKAGE_NAME
                + "RET_GET_DEFENCE_AREA";
        //预置位设置后返回
        public static final String MESG_TYPE_RET_ALARM_TYPE_MOTOR_PRESET_POS = PACKAGE_NAME
                + "MESG_TYPE_RET_ALARM_TYPE_MOTOR_PRESET_POS";

        // 远程设置
        public static final String RET_SET_REMOTE_DEFENCE = PACKAGE_NAME
                + "RET_SET_REMOTE_DEFENCE";
        public static final String RET_GET_REMOTE_DEFENCE = PACKAGE_NAME
                + "RET_GET_REMOTE_DEFENCE";

        public static final String RET_SET_REMOTE_RECORD = PACKAGE_NAME
                + "RET_SET_REMOTE_RECORD";
        public static final String RET_GET_REMOTE_RECORD = PACKAGE_NAME
                + "RET_GET_REMOTE_RECORD";

        // 设置初始密码
        public static final String ACK_RET_SET_INIT_PASSWORD = PACKAGE_NAME
                + "ACK_RET_SET_INIT_PASSWORD";
        public static final String RET_SET_INIT_PASSWORD = PACKAGE_NAME
                + "RET_SET_INIT_PASSWORD";
        public static final String RET_GET_VISTOR_PASSWORD = PACKAGE_NAME
                + "RET_GET_VISTOR_PASSWORD";

        // 查询录像文件
        public static final String ACK_RET_GET_PLAYBACK_FILES = PACKAGE_NAME
                + "ACK_RET_GET_PLAYBACK_FILES";
        public static final String RET_GET_PLAYBACK_FILES = PACKAGE_NAME
                + "RET_GET_PLAYBACK_FILES";

        // 回放改变进度条
        public static final String PLAYBACK_CHANGE_SEEK = PACKAGE_NAME
                + "PLAYBACK_CHANGE_SEEK";

        // 回放状态改变
        public static final String PLAYBACK_CHANGE_STATE = PACKAGE_NAME
                + "PLAYBACK_CHANGE_STATE";

        // 画布状态切换
        public static final String P2P_CHANGE_IMAGE_TRANSFER = PACKAGE_NAME
                + "P2P_CHANGE_IMAGE_TRANSFER";

        // 设备检查更新
        public static final String ACK_RET_GET_DEVICE_INFO = PACKAGE_NAME
                + "ACK_RET_GET_DEVICE_INFO";
        public static final String RET_GET_DEVICE_INFO = PACKAGE_NAME
                + "RET_GET_DEVICE_INFO";

        public static final String ACK_RET_CHECK_DEVICE_UPDATE = PACKAGE_NAME
                + "ACK_RET_CHECK_DEVICE_UPDATE";
        public static final String RET_CHECK_DEVICE_UPDATE = PACKAGE_NAME
                + "RET_CHECK_DEVICE_UPDATE";

        public static final String ACK_RET_DO_DEVICE_UPDATE = PACKAGE_NAME
                + "ACK_RET_DO_DEVICE_UPDATE";
        public static final String RET_DO_DEVICE_UPDATE = PACKAGE_NAME
                + "RET_DO_DEVICE_UPDATE";

        public static final String ACK_RET_CANCEL_DEVICE_UPDATE = PACKAGE_NAME
                + "ACK_RET_CANCEL_DEVICE_UPDATE";
        /*
         * p2p Connect
         */
        public static final String P2P_REJECT = PACKAGE_NAME + "P2P_REJECT";
        public static final String P2P_ACCEPT = PACKAGE_NAME + "P2P_ACCEPT";
        public static final String P2P_READY = PACKAGE_NAME + "P2P_READY";

        // p2p视频分辨率改变
        public static final String P2P_RESOLUTION_CHANGE = PACKAGE_NAME
                + "P2P_RESOLUTION_CHANGE";

        // P2P监控人数改变
        public static final String P2P_MONITOR_NUMBER_CHANGE = PACKAGE_NAME
                + "P2P_MONITOR_NUMBER_CHANGE";
        // 设置图像倒转
        public static final String RET_GET_IMAGE_REVERSE = PACKAGE_NAME
                + "RET_GET_IMAGE_REVERSE";
        public static final String ACK_VRET_SET_IMAGEREVERSE = PACKAGE_NAME
                + "ACK_VRET_SET_IMAGEREVERSE";
        // 人体红外开关
        public static final String ACK_RET_SET_INFRARED_SWITCH = PACKAGE_NAME
                + "ACK_RET_SET_INFRARED_SWITCH";
        public static final String RET_GET_INFRARED_SWITCH = PACKAGE_NAME
                + "RET_GET_INFRARED_SWITCH";
        // 有线报警输入开关
        public static final String ACK_RET_SET_WIRED_ALARM_INPUT = PACKAGE_NAME
                + "ACK_RET_GET_WIRED_ALARM_INPUT";
        public static final String RET_GET_WIRED_ALARM_INPUT = PACKAGE_NAME
                + "RET_GET_WIRED_ALARM_INPUT";
        // 无线报警输入开关
        public static final String ACK_RET_SET_WIRED_ALARM_OUT = PACKAGE_NAME
                + "ACK_RET_GET_WIRED_ALARM_OUT";
        public static final String RET_GET_WIRED_ALARM_OUT = PACKAGE_NAME
                + "RET_GET_WIRED_ALARM_OUT";
        // 自动升级
        public static final String ACK_RET_SET_AUTOMATIC_UPGRADE = PACKAGE_NAME
                + "ACK_RET_GET_AUTOMATIC_UPGRADE";
        public static final String RET_GET_AUTOMATIC_UPGRAD = PACKAGE_NAME
                + "RET_GET_AUTOMATIC_UPGRAD";
        // 设置访客密码
        public static final String ACK_RET_SET_VISITOR_DEVICE_PASSWORD = PACKAGE_NAME
                + "ACK_RET_SET_VISITOR_DEVICE_PASSWORD";
        public static final String RET_SET_VISITOR_DEVICE_PASSWORD = PACKAGE_NAME
                + "RET_SET_VISITOR_DEVICE_PASSWORD";
        // 设置时区
        public static final String ACK_RET_SET_TIME_ZONE = PACKAGE_NAME
                + "ACK_RET_SET_TIME_ZONE";
        public static final String RET_GET_TIME_ZONE = PACKAGE_NAME
                + "RET_GET_TIME_ZONE";
        // SD卡
        public static final String RET_GET_SD_CARD_CAPACITY = PACKAGE_NAME
                + "RET_GET_SD_CARD_CAPACITY";
        public static final String RET_GET_SD_CARD_FORMAT = PACKAGE_NAME
                + "RET_GET_SD_CARD_FORMAT";
        public static final String ACK_GET_SD_CARD_CAPACITY = PACKAGE_NAME
                + "ACK_GET_SD_CARD_CAPACITY";
        public static final String ACK_GET_SD_CARD_FORMAT = PACKAGE_NAME
                + "ACK_GET_SD_CARD_FORMAT";
        public static final String RET_GET_USB_CAPACITY = PACKAGE_NAME
                + "RET_GET_USB_CAPACITY";
        // 预录像
        public static final String RET_GET_PRE_RECORD = PACKAGE_NAME
                + "RET_GET_PRE_RECORD";
        public static final String ACK_RET_SET_PRE_RECORD = PACKAGE_NAME
                + "ACK_RET_SET_PRE_RECORD";
        public static final String RET_SET_PRE_RECORD = PACKAGE_NAME
                + "RET_SET_PRE_RECORD";
        // 传感器开关
        public static final String ACK_RET_GET_SENSOR_SWITCH = PACKAGE_NAME
                + "ACK_RET_GET_SENSOR_SWITCH";
        public static final String ACK_RET_SET_SENSOR_SWITCH = PACKAGE_NAME
                + "ACK_RET_SET_SENSOR_SWITCH";
        public static final String RET_GET_SENSOR_SWITCH = PACKAGE_NAME
                + "RET_GET_SENSOR_SWITCH";
        public static final String RET_SET_SENSOR_SWITCH = PACKAGE_NAME
                + "RET_SET_SENSOR_SWITCH";

        public static final String RET_SET_GPIO = PACKAGE_NAME + "RET_SET_GPIO";
        // 获取音频设备型号
        public static final String RET_GET_AUDIO_DEVICE_TYPE = PACKAGE_NAME
                + "RET_GET_AUDIO_DEVICE_TYPE";
        // SetLamp返回
        public static final String SET_LAMP_STATUS = PACKAGE_NAME
                + "SET_LAMP_STATUS";
        // SetLamp返回
        public static final String ACK_SET_LAMP_STATUS = PACKAGE_NAME
                + "ACK_SET_LAMP_STATUS";
        // GetLamp返回
        public static final String GET_LAMP_STATUS = PACKAGE_NAME
                + "GET_LAMP_STATUS";
        //查看/设置预置位返回
        public static final String RET_PRESET_MOTORPOS_STATUS=PACKAGE_NAME+"RET_PRESET_MOTORPOS_STATUS";
        //P2P图像开始显示
        public static final String RET_P2PDISPLAY=PACKAGE_NAME+"RET_P2PDISPLAY";

        public static final String ACK_GET_REMOTE_DEFENCE=PACKAGE_NAME+"ACK_GET_REMOTE_DEFENCE";
    }

    public static class WatchAction{
        public static final String IF_WATCH="if_watching";
        public static final String CAMERA_ID="camera_id";
    }

    public static class UserInfo{
        public static final String SETTAG = "set_tag";
    }

    public static class P2P_TYPE{
        public static final int P2P_TYPE_MONITOR = 1;
    }

    public static class ActivityInfo{
        public static final int ACTIVITY_MAINACTIVITY = 1;
        public static final int ACTIVITY_APMONITORACTIVITY=64;

    }

}
