package com.unmsm.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.unmsm.myapplication.Activities.UserDetail;
import com.unmsm.myapplication.Fragment.LinkedAccountsFragment;
import com.unmsm.myapplication.Network.Models.LinkedAccountItem;
import com.unmsm.myapplication.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by NriKe on 16/05/2017.
 */

public class LinkedAccountsAdapter extends RecyclerView.Adapter<LinkedAccountsAdapter.MyHolder>{

    List<LinkedAccountItem> data;
    Context context;
    Fragment fragment;

    public LinkedAccountsAdapter(List<LinkedAccountItem> data, Context context, Fragment fragment) {
        this.data = data;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_linked_account, parent, false);
        return new LinkedAccountsAdapter.MyHolder(view);
    }

    public void removeItem(int position){
        data.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        Picasso.with(context).load(data.get(position).getTwitter_image_url()).into(holder.iv_image);
        holder.tv_nickname.setText("@"+data.get(position).getTwitter_screen_name());
        holder.tv_username.setText(data.get(position).getTwitter_user_name());
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LinkedAccountsFragment)fragment).showDialog(data.get(position).getTwitter_user_id(), position);
            }
        });

        holder.detail_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open_details = new Intent(context, UserDetail.class);
                open_details.putExtra("USER_ID", String.valueOf(data.get(position).getTwitter_user_id()));
                context.startActivity(open_details);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        CircleImageView iv_image;
        TextView tv_nickname;
        TextView tv_delete;
        LinearLayout detail_user;
        TextView tv_username;

        public MyHolder(View v) {
            super(v);
            iv_image = (CircleImageView) v.findViewById(R.id.iv_image);
            tv_nickname = (TextView) v.findViewById(R.id.tv_nickname);
            tv_delete = (TextView) v.findViewById(R.id.tv_delete);
            detail_user = (LinearLayout) v.findViewById(R.id.detail_user);
            tv_username = (TextView) v.findViewById(R.id.tv_username);
        }
    }
}
