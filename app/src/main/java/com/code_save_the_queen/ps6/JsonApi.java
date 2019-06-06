package com.code_save_the_queen.ps6;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;



public interface JsonApi {

    @POST("increment")
    Call<String> createPost(@Body Post post);

    @POST("admin/refuse")
    Call<String> deletepost(@Body Delete delete);

    @POST("admin/delay")
    Call<String> delaypost(@Body Delete delete);



}
