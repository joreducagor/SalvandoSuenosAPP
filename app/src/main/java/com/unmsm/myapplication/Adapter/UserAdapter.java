package com.unmsm.myapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.User;
import com.unmsm.myapplication.Activities.MainActivity;
import com.unmsm.myapplication.Activities.UserDetail;
import com.unmsm.myapplication.Network.Models.UserShowModel;
import com.unmsm.myapplication.R;

import java.util.List;

/**
 * Created by rubymobile on 5/3/17.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyHolder>{

    List<User> data;
    Context context;

    public UserAdapter(List<User> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_user, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        Picasso.with(context).load(data.get(position).profileImageUrl).into(holder.iv_image);

        holder.tv_nickname.setText(data.get(position).name);

        holder.tv_description.setText(data.get(position).description);

        holder.user_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("user_id", String.valueOf(data.get(position).getId()));
                Intent open_details = new Intent(context, UserDetail.class);
                open_details.putExtra("USER_ID", data.get(position).getId());
                context.startActivity(open_details);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        ImageView iv_image;
        TextView tv_nickname;
        TextView tv_description;
        LinearLayout user_row;

        public MyHolder(final View itemView) {
            super(itemView);

            iv_image = (ImageView) itemView.findViewById(R.id.iv_image);
            tv_nickname = (TextView) itemView.findViewById(R.id.tv_nickname);
            tv_description = (TextView) itemView.findViewById(R.id.tv_description);
            user_row = (LinearLayout) itemView.findViewById(R.id.detail_user);

        }
    }
}
