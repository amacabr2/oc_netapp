package com.antho.opnetapp.controllers.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antho.opnetapp.R;
import com.antho.opnetapp.models.GithubUser;
import com.antho.opnetapp.streams.GithubStreams;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class MainFragment extends Fragment {

    // FOR DESIGN
    @BindView(R.id.fragment_main_textview)
    TextView textView;

    private Disposable disposable;

    public MainFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    // -----------------
    // ACTIONS
    // -----------------

    @OnClick(R.id.fragment_main_button)
    public void submit(View view) {
        this.executeHttpRequestWithRetrofit();
    }

    // ------------------------------
    //  Reactive X
    // ------------------------------

    private void executeHttpRequestWithRetrofit() {
        this.updateUIWhenStartingHTTPRequest();

        this.disposable = GithubStreams.streamFetchUserFollowing("JakeWharton").subscribeWith(new DisposableObserver<List<GithubUser>>() {
            @Override
            public void onNext(List<GithubUser> users) {
                Log.e("TAG","On Next");
                updateUIWithListUsers(users);
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

    // Create Observable
    private Observable<String> getObservable(){
        return Observable.just("Cool !");
    }

    // Create Subscriber
    private DisposableObserver<String> getSubscriber(){
        return new DisposableObserver<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onNext(String item) {
                textView.setText("Observable emits : "+item);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG","On Error"+Log.getStackTraceString(e));
            }

            @Override
            public void onComplete() {
                Log.e("TAG","On Complete !!");
            }
        };
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
