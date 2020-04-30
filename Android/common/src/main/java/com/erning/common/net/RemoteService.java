package com.erning.common.net;

import com.erning.common.net.bean.result.JsonRst;

import java.lang.ref.Reference;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by erning on 2018/3/22.
 */
public interface RemoteService {

    @POST("login")//登录
    Call<JsonRst> login(@Query("phone") String phone, @Query("password")String password);

    @POST("register")//注册
    Call<JsonRst> register(@Query("phone") String phone, @Query("password")String password);

    @POST("forget")//找回密码
    Call<JsonRst> forget(@Query("phone")String phone, @Query("code")String code, @Query("pwd")String pwd);

    @POST("getUserInfo")//获取用户信息
    Call<JsonRst> getUserInfo(@Query("id")String id);

    @POST("getProduceList")//获取菜单
    Call<JsonRst> getProduceList(@Query("page")int page,@Query("limit")int limit);
}