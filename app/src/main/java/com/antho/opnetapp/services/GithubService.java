package com.antho.opnetapp.services;

import com.antho.opnetapp.models.GithubUser;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    @GET("users/{username}/following")
    Observable<List<GithubUser>> getFollowing(@Path("username") String username);
}
