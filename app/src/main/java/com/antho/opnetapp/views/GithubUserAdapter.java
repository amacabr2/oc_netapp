package com.antho.opnetapp.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.antho.opnetapp.R;
import com.antho.opnetapp.models.GithubUser;
import com.bumptech.glide.RequestManager;

import java.util.List;

public class GithubUserAdapter extends RecyclerView.Adapter<GithubUserViewHolder> {

    public interface Listener {
        void onClickDeleteButton(int position);
    }

    private final Listener callback;

    private List<GithubUser> githubUsers;
    private RequestManager glide;

    public GithubUserAdapter(List<GithubUser> githubUsers, RequestManager glide, Listener callback) {
        this.githubUsers = githubUsers;
        this.glide = glide;
        this.callback = callback;
    }

    @NonNull
    @Override
    public GithubUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_main_item, parent, false);
        return new GithubUserViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return this.githubUsers.size();
    }

    @Override
    public void onBindViewHolder(@NonNull GithubUserViewHolder holder, int position) {
        holder.updateWithGithubUser(this.githubUsers.get(position), this.glide, this.callback);
    }

    public GithubUser getUser(int position) {
        return this.githubUsers.get(position);
    }
}
