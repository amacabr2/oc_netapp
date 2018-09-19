package com.antho.opnetapp.controllers.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antho.opnetapp.R;
import com.antho.opnetapp.models.GithubUser;
import com.antho.opnetapp.streams.GithubStreams;
import com.antho.opnetapp.views.GithubUserAdapter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class MainFragment extends Fragment {

    // FOR DESIGN
    @BindView(R.id.fragment_main_swipe_container)
    private SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.fragment_main_recycler_view)
    private RecyclerView recyclerView;

    private Disposable disposable;

    private List<GithubUser> githubUsers;
    private GithubUserAdapter adapter;

    public MainFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        this.configureSwipeRefreshLayout();
        this.configureRecyclerView();
        this.executeHttpRequestWithRetrofit();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    private void configureRecyclerView() {
        this.githubUsers = new ArrayList<>();
        this.adapter = new GithubUserAdapter(this.githubUsers, Glide.with(this));
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void configureSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequestWithRetrofit();
            }
        });
    }

    // ------------------------------
    //  HTTP (RxJAVA)
    // ------------------------------

    private void executeHttpRequestWithRetrofit() {
        this.disposable = GithubStreams.streamFetchUserFollowing("JakeWharton").subscribeWith(new DisposableObserver<List<GithubUser>>() {
            @Override
            public void onNext(List<GithubUser> users) {
                Log.e("TAG","On Next");
                updateUI(users);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","On Error"+Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG","On Complete !!");
            }
        });
    }

    // Dispose subscription
    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) {
            this.disposable.dispose();
        }
    }

    // ------------------
    //  UPDATE UI
    // ------------------

    private void updateUI(List<GithubUser> users) {
        swipeRefreshLayout.setRefreshing(false);
        githubUsers.clear();
        githubUsers.addAll(users);
        adapter.notifyDataSetChanged();
    }
}
