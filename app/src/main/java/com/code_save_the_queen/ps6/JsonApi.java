package com.code_save_the_queen.ps6;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface JsonApi {

    @POST("increment")
    Call<Post> createPost(@Body Post post);


}
