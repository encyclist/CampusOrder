package com.erning.common.net;

import com.erning.common.net.bean.JsonRst;
import com.erning.common.net.bean.Order;
import com.erning.common.net.bean.Page;

import java.lang.ref.Reference;

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
    Call<JsonRst> getProduceList(@Body Page page);

    @POST("order/addOrder")//下单
    Call<JsonRst> addOrder(@Body Order order);

    @POST("order/selectOrderById")//获取订单信息
    Call<JsonRst> selectOrderById(@Query("id")int id);

    @POST("order/deleteOrder")//取消/删除订单
    Call<JsonRst> deleteOrder(@Query("id")int id);

    @POST("order/payOrder")//支付订单
    Call<JsonRst> payOrder(@Query("id")int id);

    @POST("order/selectOrder")//获取订单列表
    Call<JsonRst> getOrderList(@Query("pageNum")int page,@Query("pageSize")int limit,@Query("student_id")int id);
}