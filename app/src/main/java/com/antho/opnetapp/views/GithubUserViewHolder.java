package com.antho.opnetapp.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.antho.opnetapp.R;
import com.antho.opnetapp.models.GithubUser;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GithubUserViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.fragment_main_item_title)
    private TextView textView;

    @BindView(R.id.fragment_main_item_website)
    private TextView textViewWebsite;

    @BindView(R.id.fragment_main_item_image)
    private ImageView imageView;

    public GithubUserViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateWithGithubUser(GithubUser githubUser, RequestManager glide) {
        textView.setText(githubUser.getLogin());
        textViewWebsite.setText(githubUser.getHtmlUrl());
        glide.load(githubUser.getAvatarUrl()).apply(RequestOptions.circleCropTransform()).into(imageView);
    }
}
