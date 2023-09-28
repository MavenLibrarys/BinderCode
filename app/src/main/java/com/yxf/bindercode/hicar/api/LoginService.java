package com.yxf.bindercode.hicar.api;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface LoginService {
    @POST("/user/login")
    @FormUrlEncoded
    Call<LoginBean> login(@Field("username") String username, @Field("password") String password);

    @POST("/user/register")
    @FormUrlEncoded
    Call register(@FieldMap Map<String, String> params);

    @GET("/user/lg/userinfo/json")
    Call getUserInfo();
}
