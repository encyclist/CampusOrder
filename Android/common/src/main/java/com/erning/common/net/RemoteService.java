package com.erning.common.net;

import com.erning.common.net.bean.result.BaseResultModel;
import com.erning.common.net.bean.result.JsonRst;

import java.util.List;
import java.util.TreeMap;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by erning on 2018/3/22.
 */
@SuppressWarnings("SpellCheckingInspection")
public interface RemoteService {

    // --------------------------------公共--------------------------------------
    @GET("https://restapi.amap.com/v3/place/around") // 搜索周边建筑
    Call<String> searchPoi(@Query("key") String key, @Query("location")String location, @Query("offset")int offset, @Query("page")int page);

    @FormUrlEncoded
    @POST("common/common/sendSms")//验证码 1登录2注册3找回密码4换绑手机号5注销账户
    Call<BaseResultModel> sms(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("common/common/getAreaTree")//获取区域列表（树状）
    Call<JsonRst> getAreaTree(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("common/common/getArea")//获取区域列表
    Call<JsonRst> getArea(@FieldMap TreeMap<String, String> map);

    @Multipart
    @POST("common/upload/imageMuti")//上传图片s
    Call<JsonRst> updateImages(@Part List<MultipartBody.Part> file);

    @Multipart
    @POST("common/upload/image")//上传图片
    Call<JsonRst> updateImage(@Part List<MultipartBody.Part> file);

    @Multipart
    @POST("common/upload/file")//上传文件
    Call<JsonRst> updateFile(@Part List<MultipartBody.Part> file);

    @FormUrlEncoded
    @POST("api/qrcode/detail")//获取二维码详情
    Call<JsonRst> getQRDetail(@FieldMap TreeMap<String, String> map);

    // --------------------------------账户管理--------------------------------------
    @FormUrlEncoded
    @POST("api/auth/login")//登录 0账号密码登录1手机验证码登录
    Call<JsonRst> login(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/auth/register")//注册
    Call<JsonRst> register(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/auth/forget")//忘记密码
    Call<BaseResultModel> forget(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/user/changeMobile")//会员换绑手机号
    Call<BaseResultModel> changePhone(@FieldMap TreeMap<String, String> map);

    // --------------------------------通讯录--------------------------------------

    @FormUrlEncoded
    @POST("api/user/search")//搜索新朋友
    Call<JsonRst> searchNewFriend(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/search")//搜索好友
    Call<JsonRst> searchFriend(@FieldMap TreeMap<String, String> map);

    @Deprecated()
    @FormUrlEncoded
    @POST("api/friend/applySubmit")//添加好友（申请）
    Call<BaseResultModel> addFriend(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/applyAgree")//添加好友（同意）
    Call<BaseResultModel> addFriendAgree(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/applyRefuse")//添加好友（拒绝）
    Call<BaseResultModel> addFriendRefuse(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/getNewFriendApplyCount")//获取好友申请数量
    Call<JsonRst> getNewFriendApplyCount(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/user/detail")//添加好友前查看用户详情
    Call<JsonRst> userDetail(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/detail")//好友详情
    Call<JsonRst> friendDetail(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/applyPreview")//添加好友页面获取申请理由
    Call<JsonRst> addFriendReason(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/lists")//获取好友列表（大写字母分组）
    Call<JsonRst> friendList(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/apply")//获取好友申请列表（带分页）
    Call<JsonRst> friendApplyList(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/applyDetail")//好友申请记录详情
    Call<JsonRst> friendapplyDetail(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/setRemark")//添加好友备注或说明
    Call<JsonRst> setRemark(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/black")//获取好友黑名单列表
    Call<JsonRst> getBlackList(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/setBlack")//设置好友黑名单或者从黑名单移除
    Call<BaseResultModel> setBlack(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/delete")//删除好友
    Call<BaseResultModel> delete(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/setTimelineRole")//设置好友动态权限
    Call<BaseResultModel> setTimelineRole(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/unsee")//获取我不要看的朋友圈的列表
    Call<JsonRst> getUnseeList(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/forbid")//获取禁止看我朋友圈列表
    Call<JsonRst> getForbidList(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/setForbid")//设置禁止看我朋友圈的人员列表（批量）
    Call<JsonRst> setForbid(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/setUnsee")//设置我不看的朋友圈的人员列表（批量）
    Call<JsonRst> setUnsee(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/common/search")//获取搜索好友和群组列表（带分页）
    Call<JsonRst> aggregationSearch(@FieldMap TreeMap<String, String> map);

    // --------------------------------聊天--------------------------------------

    @FormUrlEncoded
    @POST("api/friend/setChating")//设置正在聊天对话框中
    Call<JsonRst> setChating(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/setUnChating")//设置离开聊天对话框
    Call<JsonRst> setUnChating(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/msg/clear")//清空聊天记录
    Call<BaseResultModel> clear(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/setNoDisturb")//免打扰
    Call<BaseResultModel> setFriendNoDisturb(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/setTop")//置顶
    Call<BaseResultModel> setFriendTop(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/msg/lists")//搜索聊天内容
    Call<JsonRst> searchChatContent(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/friend/sameGroup")//获取我和好友的共同群聊列表
    Call<JsonRst> getSameGroup(@FieldMap TreeMap<String, String> map);

    // --------------------------------个人中心--------------------------------------

    @FormUrlEncoded
    @POST("api/user/info")//获取用户基本信息
    Call<JsonRst> userInfo(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/user/setAvatar")//设置头像
    Call<BaseResultModel> setAvatar(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/user/setNickname")//设置昵称
    Call<BaseResultModel> setNickname(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/user/setGender")//设置性别
    Call<BaseResultModel> setGender(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/user/setAddress")//设置地区
    Call<BaseResultModel> setAddress(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/user/setDescribe")//设置会员个性签名
    Call<BaseResultModel> setDescribe(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/user/changePassword")//修改密码
    Call<BaseResultModel> changePassword(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/user/setVerify")//设置被加好友时验证 need_verify0否1是
    Call<BaseResultModel> setVerify(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/user/setAddWay")//设置好友添加方式
    Call<BaseResultModel> setAddWay(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/user/setRecommend")//设置通讯录推荐
    Call<BaseResultModel> setRecommend(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/user/setNotice")//设置动态更新提醒
    Call<BaseResultModel> setDynamicNotice(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/user/getQrcode")//设置动态更新提醒
    Call<JsonRst> getQrcode(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/user/setCover")//设置朋友圈封面图
    Call<BaseResultModel> setCover(@FieldMap TreeMap<String, String> map);

    // --------------------------------动态--------------------------------------

    @FormUrlEncoded
    @POST("api/timeline/my")//我的朋友圈
    Call<JsonRst> getMyTimelineList(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/timeline/friend")//获取指定好友动态列表
    Call<JsonRst> getFriendTimelineList(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/timeline/lists")//获取好友动态列表
    Call<JsonRst> getTimelineList(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/timeline/create")//创建好友动态
    Call<BaseResultModel> releaseTimeline(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/timeline/cancelZan")//好友动态取消点赞
    Call<BaseResultModel> cancelLike(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/timeline/setZan")//好友动态点赞
    Call<BaseResultModel> setLike(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/timeline/detail")//好友动态详情
    Call<JsonRst> timelineDetail(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/timeline/addComment")//评论好友动态
    Call<BaseResultModel> addComment(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/timeline/msg")//获取好友动态消息列表
    Call<JsonRst> timelineMsg(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/timeline/clearMsg")//清空好友动态消息列表
    Call<BaseResultModel> clearMsg(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/timeline/deleteComment")//删除好友动态评论
    Call<BaseResultModel> deleteComment(@FieldMap TreeMap<String, String> map);

    // --------------------------------群聊--------------------------------------

    @FormUrlEncoded
    @POST("api/group/create")//发起群聊
    Call<JsonRst> createGroup(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/group/lists")//获取群聊
    Call<JsonRst> getGroupList(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/group/detail")//获取群聊详情
    Call<JsonRst> getGroupDetail(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/group/setTop")//设置群聊置顶
    Call<BaseResultModel> setGroupTop(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/group/setNoDisturb")//设置群聊免打扰
    Call<BaseResultModel> setGroupNoDisturb(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/group/change")//变更群聊群主
    Call<BaseResultModel> changeGroupOwner(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/group/quit")//退出群聊
    Call<BaseResultModel> quitGroup(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/group/setName")//设置群聊名称
    Call<BaseResultModel> setGroupName(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/group/invite")//邀请加入群聊
    Call<BaseResultModel> inviteGroup(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/group/setUnChating")//设置离开聊天对话框
    Call<JsonRst> setGroupUnChating(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/group/setChating")//设置正在群聊聊天中
    Call<JsonRst> setGroupChating(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/group/getQrcode")//获取指定群组的二维码
    Call<JsonRst> getGroupQR(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/group/setReview")//设置群进入是否开启审核
    Call<BaseResultModel> setGroupReview(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/group/getApplyLists")//获取进群申请记录列表
    Call<JsonRst> getGroupApplyLists(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/group/getApplyDetail")//查看进群申请记录详情
    Call<JsonRst> getGroupApplyDetail(@FieldMap TreeMap<String, String> map);

    @FormUrlEncoded
    @POST("api/group/setApplyReject")//驳回进群申请记录
    Call<BaseResultModel> setApplyReject(@FieldMap TreeMap<String, String> map);
}