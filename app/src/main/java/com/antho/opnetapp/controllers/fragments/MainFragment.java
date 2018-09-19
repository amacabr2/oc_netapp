package com.antho.opnetapp.controllers.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antho.opnetapp.R;
import com.antho.opnetapp.models.GithubUser;
import com.antho.opnetapp.tasks.NetworkAsyncTask;
import com.antho.opnetapp.utils.GithubCalls;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends Fragment implements NetworkAsyncTask.Listeners, GithubCalls.Callbacks {

    // FOR DESIGN
    @BindView(R.id.fragment_main_textview)
    TextView textView;

    public MainFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    // -----------------
    // ACTIONS
    // -----------------

    @OnClick(R.id.fragment_main_button)
    public void submit(View view) {
        //this.executeHttpRequest();
        this.executeHttpRequestWithRetrofit();
    }

    // -----------------
    // HTTP REQUEST
    // -----------------

    @Override
    public void onPreExecute() {
        this.updateUIWhenStartingHTTPRequest();
    }

    @Override
    public void doInBackground() { }

    @Override
    public void onPostExecute(String json) {
        this.updateUIWhenStopingHTTPRequest(json);
    }

    // -----------------
    // HTTP REQUEST (Retrofit)
    // -----------------

    @Override
    public void onResponse(@Nullable List<GithubUser> users) {
        if (users != null) {
            this.updateUIWithListUsers(users);
        }
    }

    @Override
    public void onFailure() {
        this.updateUIWhenStopingHTTPRequest("An error happened");
    }

    private void executeHttpRequest() {
        new NetworkAsyncTask(this).execute("https://api.github.com/users/JakeWharton/following");
    }

    private void executeHttpRequestWithRetrofit() {
        this.updateUIWhenStartingHTTPRequest();
        GithubCalls.fetchUsersFollowing(this, "JakeWharton");
    }

    // ------------------
    //  UPDATE UI
    // ------------------

    @SuppressLint("SetTextI18n")
    private void updateUIWhenStartingHTTPRequest(){
        this.textView.setText("Downloading...");
    }

    private void updateUIWhenStopingHTTPRequest(String response){
        this.textView.setText(response);
    }

    private void updateUIWithListUsers(List<GithubUser> users) {
        StringBuilder stringBuilder = new StringBuilder();
        for (GithubUser user : users){
            stringBuilder.append("-").append(user.getLogin()).append("\n");
        }
        updateUIWhenStopingHTTPRequest(stringBuilder.toString());
    }
}
