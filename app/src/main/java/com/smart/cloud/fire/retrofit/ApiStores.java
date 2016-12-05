package com.smart.cloud.fire.retrofit;

import com.smart.cloud.fire.global.AlarmCameraInfo;
import com.smart.cloud.fire.global.AlarmMsg;
import com.smart.cloud.fire.global.CameraMap;
import com.smart.cloud.fire.global.Electric;
import com.smart.cloud.fire.global.ElectricInfo;
import com.smart.cloud.fire.global.ElectricValue;
import com.smart.cloud.fire.global.LoginServer;
import com.smart.cloud.fire.global.PostResult;
import com.smart.cloud.fire.global.SmokeSummary;
import com.smart.cloud.fire.global.TemperatureTime;
import com.smart.cloud.fire.global.VersionXml;
import com.smart.cloud.fire.mvp.fragment.ConfireFireFragment.ConfireFireModel;
import com.smart.cloud.fire.mvp.fragment.MapFragment.Camera;
import com.smart.cloud.fire.mvp.fragment.MapFragment.HttpAreaResult;
import com.smart.cloud.fire.mvp.fragment.MapFragment.HttpError;
import com.smart.cloud.fire.mvp.login.model.LoginModel;
import com.smart.cloud.fire.mvp.register.model.RegisterModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiStores {
    //登录技威服务器
    @FormUrlEncoded
    @POST("Users/LoginCheck.ashx")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<LoginModel> loginYooSee(@Field("User") String User, @Field("Pwd") String Pwd,
                                       @Field("VersionFlag") String VersionFlag, @Field("AppOS") String AppOS,
                                       @Field("AppVersion") String AppVersion);
    //登录本地服务器
    @GET("login")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<LoginModel> login(@Query("userId") String userId);

    //获取短信验证码
    @FormUrlEncoded
    @POST("Users/PhoneCheckCode.ashx")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<RegisterModel> getMesageCode(@Field("CountryCode") String countryCode, @Field("PhoneNO") String phoneNO
            , @Field("AppVersion") String appVersion);

    //检查短信验证码
    @FormUrlEncoded
    @POST("Users/PhoneVerifyCodeCheck.ashx")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<RegisterModel> verifyPhoneCode(@Field("CountryCode") String countryCode,@Field("PhoneNO") String phoneNO
            ,@Field("VerifyCode") String verifyCode);

    //注册
    @FormUrlEncoded
    @POST("Users/RegisterCheck.ashx")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<RegisterModel> register(@Field("VersionFlag") String versionFlag,@Field("Email") String email
            ,@Field("CountryCode") String countryCode,@Field("PhoneNO") String phoneNO
            ,@Field("Pwd") String pwd,@Field("RePwd") String rePwd
            ,@Field("VerifyCode") String verifyCode,@Field("IgnoreSafeWarning") String ignoreSafeWarning);

    //获取用户所有的烟感
    @GET("getAllSmoke")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<HttpError> getAllSmoke(@Query("userId") String userId, @Query("privilege") String privilege,@Query("page") String page);

    //获取用户所有的摄像头
    @GET("getAllCamera")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<HttpError> getAllCamera(@Query("userId") String userId, @Query("privilege") String privilege,@Query("page") String page);

    //获取所有的店铺类型
    @GET("getPlaceTypeId")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<HttpError> getPlaceTypeId(@Query("userId") String userId, @Query("privilege") String privilege,@Query("page") String page);

    //获取所有的区域类型
    @GET("getAreaId")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<HttpAreaResult> getAreaId(@Query("userId") String userId, @Query("privilege") String privilege, @Query("page") String page);

    //根据条件查询用户烟感
    @GET("getNeedSmoke")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<HttpError> getNeedSmoke(@Query("userId") String userId, @Query("privilege") String privilege,
                                            @Query("areaId") String areaId,@Query("page") String page,
                                            @Query("placeTypeId") String placeTypeId);

    //处理报警消息
    @GET("dealAlarm")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<HttpError> dealAlarm(@Query("userId") String userId, @Query("smokeMac") String smokeMac);

    //获取单个烟感信息
    @GET("getOneSmoke")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<ConfireFireModel> getOneSmoke(@Query("userId") String userId, @Query("smokeMac") String smokeMac,
                                             @Query("privilege") String privilege);

    //添加烟感
    @GET("addSmoke")
    Observable<ConfireFireModel> addSmoke(@Query("userId") String userId, @Query("smokeName") String smokeName,
                                          @Query("privilege") String privilege, @Query("smokeMac") String smokeMac,
                                          @Query("address") String address, @Query("longitude") String longitude,
                                          @Query("latitude") String latitude, @Query("placeAddress") String placeAddress,
                                          @Query("placeTypeId") String placeTypeId, @Query("principal1") String principal1,
                                          @Query("principal1Phone") String principal1Phone, @Query("principal2") String principal2,
                                          @Query("principal2Phone") String principal2Phone, @Query("areaId") String areaId,
                                          @Query("repeater") String repeater,@Query("camera") String camera,@Query("deviceType") String deviceType);

    //条件查询获取用户报警消息
    @GET("getNeedAlarm")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<HttpError> getNeedAlarm(@Query("userId") String userId, @Query("privilege") String privilege
            ,@Query("startTime") String startTime,@Query("endTime") String endTime
            ,@Query("areaId") String areaId,@Query("placeTypeId") String placeTypeId
            ,@Query("page") String page);

    //绑定烟感与摄像头
    @GET("bindCameraSmoke")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<HttpError> bindCameraSmoke(@Query("cameraId") String cameraId, @Query("smoke") String smoke);


    @GET("getCid")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<HttpError> bindAlias(@Query("alias") String alias, @Query("cid") String cid,@Query("projectName") String projectName);

    //一键报警
    @GET("textAlarm")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<HttpError> textAlarm(@Query("userId") String userId, @Query("privilege") String privilege,
                                    @Query("smokeMac") String smokeMac,@Query("info") String info);

    //一键报警确认回复
    @GET("textAlarmAck")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<HttpError> textAlarmAck(@Query("userId") String userId, @Query("alarmSerialNumber") String alarmSerialNumber);


    @GET("getNeedLossSmoke")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<HttpError> getNeedLossSmoke(@Query("userId") String userId, @Query("privilege") String privilege,
                                           @Query("areaId") String areaId,@Query("page") String page,
                                           @Query("placeTypeId") String placeTypeId);

    @GET("getSmokeSummary")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<SmokeSummary> getSmokeSummary(@Query("userId") String userId, @Query("privilege") String privilege,
                                             @Query("areaId") String areaId);

    @GET("getAllElectricInfo")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<ElectricInfo<Electric>> getAllElectricInfo(@Query("userId") String userId, @Query("privilege") String privilege,
                                                          @Query("page") String page);
//    getOneElectricInfo?userId=13428282520&privilege=2&smokeMac=32110533
    @GET("getOneElectricInfo")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<ElectricInfo<ElectricValue>> getOneElectricInfo(@Query("userId") String userId, @Query("privilege") String privilege,
                                                               @Query("smokeMac") String smokeMac);

//    getElectricTypeInfo?userId=13428282520&privilege=2&smokeMac=32110533&electricType=6&electricNum=1&page=
    @GET("getElectricTypeInfo")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<TemperatureTime> getElectricTypeInfo(@Query("userId") String userId, @Query("privilege") String privilege,
                                                    @Query("smokeMac") String smokeMac, @Query("electricType") String electricType,
                                                    @Query("electricNum") String electricNum, @Query("page") String page);
//    getNeedElectricInfo?userId=13622215085&privilege=2&areaId=14&placeTypeId=2&page
    @GET("getNeedElectricInfo")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<ElectricInfo<Electric>> getNeedElectricInfo(@Query("userId") String userId, @Query("privilege") String privilege,
                                                    @Query("areaId") String areaId, @Query("placeTypeId") String placeTypeId,
                                                    @Query("page") String page);

    @FormUrlEncoded
    @POST("register")
    Observable<PostResult> registerServerIp(@Field("userId") String userId, @Field("userName") String userName
            , @Field("phone") String phone, @Field("email") String email, @Field("privilege") String pwd);

    //登录本地服务器
    @GET("login")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<LoginServer> loginServer(@Query("userId") String userId, @Query("phone") String phone, @Query("email") String email);

//    http://192.168.4.111:51091/camera/managerGetAllCamera?userId=13428282520&privilege=3&page=1
    @GET("managerGetAllCamera")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<CameraMap> managerGetAllCamera(@Query("userId") String userId, @Query("privilege") String privilege, @Query("page") String page);

    //    9 获取摄像头信息
//    9 get:	  http://192.168.4.111:51091/camera/getOneCamera?cameraId=3121164
    @GET("getOneCamera")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<AlarmCameraInfo> getOneCamera(@Query("cameraId") String cameraId);

//    11 管理员获取摄像头详细信息
//    11  get:	  http://192.168.4.111:51091/camera/managerGetAllCameraDetail?userId=13428282520&privilege=3&page=1
    @GET("managerGetAllCameraDetail")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<Camera> managerGetAllCameraDetail(@Query("userId") String userId,@Query("privilege") String privilege,@Query("page") String page);

    //    http://192.168.4.111:51091/camera/getAllAlarm?userId=13428282520&privilege=1&page=
    @GET("getAllAlarm")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=utf-8")
    Observable<AlarmMsg> getAllAlarm(@Query("userId") String userId, @Query("privilege") String privilege, @Query("page") String page);

    @GET("update_new_bees.xml")
    Observable<VersionXml> checkVersion();

    @FormUrlEncoded
    @POST("addCamera")
    Observable<PostResult> addCamera(@Field("cameraId") String cameraId, @Field("cameraName") String cameraName
            , @Field("cameraPwd") String cameraPwd, @Field("cameraAddress") String cameraAddress, @Field("longitude") String longitude
            ,@Field("latitude") String latitude, @Field("principal1") String principal1, @Field("principal1Phone") String principal1Phone,
                                     @Field("principal2") String principal2, @Field("principal2Phone") String principal2Phone, @Field("areaId") String areaId,
                                     @Field("placeTypeId") String placeTypeId);
    @FormUrlEncoded
    @POST("bindUserIdCameraId")
    Observable<PostResult> bindUserIdCameraId(@Field("userId") String userId, @Field("cameraId") String cameraId);
}
