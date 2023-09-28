package com.yxf.bindercode.hicar.api;

import com.yxf.bindercode.hicar.api.retrofit.annotation.Field;
import com.yxf.bindercode.hicar.api.retrofit.annotation.GET;
import com.yxf.bindercode.hicar.api.retrofit.annotation.POST;
import com.yxf.bindercode.hicar.api.retrofit.annotation.Query;

import okhttp3.Call;

public interface ProjectService {
    @GET(value = "/project/list/1/json")
    Call getProjectInfo(@Query("cid") String cid);

    @POST("/user/login")
    Call login(@Field("username") String username, @Field("password") String password);
}
