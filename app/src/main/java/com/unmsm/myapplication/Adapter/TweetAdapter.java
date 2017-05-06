package com.unmsm.myapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetView;
import com.unmsm.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rubymobile on 05/05/2017.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.MyHolder>{

    List<Tweet> data = new ArrayList<>();
    Context context;

    public TweetAdapter(List<Tweet> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_tweet, parent, false);
        return new TweetAdapter.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.tweet.setTweet(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        TweetView tweet;

        public MyHolder(View itemView) {
            super(itemView);
            tweet = (TweetView) itemView.findViewById(R.id.tweet);
        }
    }
}