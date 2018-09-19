package com.antho.opnetapp.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.antho.opnetapp.models.GithubUser;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubCalls {

    public interface Callbacks {
        void onResponse(@Nullable List<GithubUser> users);
        void onFailure();
    }

    public static void fetchUsersFollowing(Callbacks callbacks, String username) {
        final WeakReference<Callbacks> callbacksWeakReference = new WeakReference<>(callbacks);
        GithubService githubService = GithubService.retrofit.create(GithubService.class);
        Call<List<GithubUser>> call = githubService.getFollowing(username);

        call.enqueue(new Callback<List<GithubUser>>() {
            @Override
            public void onResponse(@NonNull Call<List<GithubUser>> call, @NonNull Response<List<GithubUser>> response) {
                if (callbacksWeakReference.get() != null) {
                    callbacksWeakReference.get().onResponse(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GithubUser>> call, @NonNull Throwable t) {
                if (callbacksWeakReference.get() != null) {
                    callbacksWeakReference.get().onFailure();
                }
            }
        });
    }
}
