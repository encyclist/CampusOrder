package com.erning.common.net;

import com.erning.common.net.bean.JsonRst;
import com.erning.common.net.bean.Order;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by erning on 2018/3/22.
 */
public interface RemoteService {

    @POST("student/login")//登录
    Call<JsonRst> login(@Query("tel") String phone, @Query("password")String password);

    @POST("student/addStudent")//注册
    Call<JsonRst> register(@Query("tel") String phone, @Query("password")String password);

    @POST("student/updateStudent")//找回密码
    Call<JsonRst> forget(@Query("tel")String phone, @Query("yzm")String code, @Query("password")String pwd);

    @POST("getUserInfo")//获取用户信息
    Call<JsonRst> getUserInfo(@Query("id")String id);

    @POST("product/selectProduct")//获取菜单
    Call<JsonRst> getProduceList(@Query("pageNum")int page,@Query("pageSize")int limit);

    @POST("order/addOrder")//下单
    Call<JsonRst> addOrder(@Body Order order);
}