package com.wisdom.im.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wisdom.im.R;
import com.wisdom.im.model.bean.Friend;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HKWisdom on 2017/3/31.
 */

public class FriendAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Friend> mFriendList;

    public FriendAdapter(Context context) {
        mContext = context;
    }

    public void setFriendList(List<Friend> friendList) {
        mFriendList = friendList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_friend, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bindView(mFriendList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mFriendList != null) {
            return mFriendList.size();
        }
        return 0;
    }

     class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_add_friend_username)
        TextView mTvAddFriendUsername;
        @BindView(R.id.tv_add_friend_creat_time)
        TextView mTvAddFriendCreatTime;
        @BindView(R.id.btn_add_friend)
        Button mBtnAddFriend;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

         public void bindView(Friend friend) {
             mTvAddFriendUsername.setText(friend.getUsername());
             mTvAddFriendCreatTime.setText(friend.getCreateTime());
             if (friend.isAdded()) {
                 mBtnAddFriend.setText("已添加好友");
                 mBtnAddFriend.setEnabled(false);
             }else {
                 mBtnAddFriend.setText("添加");
             }
         }
     }
}
