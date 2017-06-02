package com.unmsm.myapplication.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.unmsm.myapplication.Network.Models.ResultResponse;
import com.unmsm.myapplication.R;

import java.util.List;

/**
 * Created by rubymobile on 02/06/17.
 */

public class ResultadosAdapter extends RecyclerView.Adapter<ResultadosAdapter.Myholder>{

    Context context;
    List<ResultResponse> data;

    public ResultadosAdapter(Context context, List<ResultResponse> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public Myholder onCreateViewHolder(ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_resultados, parent, false);
        return new ResultadosAdapter.Myholder(view);
    }

    @Override
    public void onBindViewHolder(Myholder myholder, int i) {
        myholder.tv_name.setText(data.get(i).getOwner_name() + " - @" + data.get(i).getOwner_screen());

        myholder.tv_tweet.setText(data.get(i).getTweet());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Myholder extends RecyclerView.ViewHolder{

        TextView tv_name;
        TextView tv_tweet;

        public Myholder(View itemView) {
            super(itemView);

            tv_tweet = (TextView) itemView.findViewById(R.id.tv_tweet);
            tv_name =(TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
