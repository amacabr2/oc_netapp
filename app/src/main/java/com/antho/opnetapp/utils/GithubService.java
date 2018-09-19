package com.antho.opnetapp.utils;

import com.antho.opnetapp.models.GithubUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {

    public static final Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    @GET("users/{username}/following")
    Call<List<GithubUser>> getFollowing(@Path("username") String username);
}
