package com.antho.opnetapp.streams;

import com.antho.opnetapp.models.GithubUser;
import com.antho.opnetapp.services.GithubService;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GithubStreams {

    public static Observable<List<GithubUser>> streamFetchUserFollowing(String username){
        GithubService gitHubService = GithubService.retrofit.create(GithubService.class);
        return gitHubService.getFollowing(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
