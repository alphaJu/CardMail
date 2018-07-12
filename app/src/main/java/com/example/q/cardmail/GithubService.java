package com.example.q.cardmail;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;

import java.util.List;

public interface GithubService {
    @GET("/memo/get-data")
    Call<List<MemoGet>> getUserRepositories();

    @POST("/memo/insert-data")
    Call<MemoPut> postUserRepositories(@Body MemoPut mp);

    @PUT("/memo/update-data")
    Call<MemoPut> putUserRepositories(@Body MemoPut mp);

    @HTTP(path = "/memo/delete-data", method = "DELETE", hasBody = true)
    Call<MemoPut> deleteUserRepositories(@Body MemoPut mp);

    @GET("/member/get-data")
    Call<List<UserGet>> getUserData();

    @PUT("/member/insert-data")
    Call<UserPut> putUserData(@Body UserPut up);
}
