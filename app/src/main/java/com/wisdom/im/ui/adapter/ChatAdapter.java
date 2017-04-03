package com.wisdom.im.ui.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.util.DateUtils;
import com.wisdom.im.R;
import com.wisdom.im.ui.activity.ChatActivity;
import com.wisdom.im.utils.ThreadUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HKWisdom on 2017/4/3.
 */

public class ChatAdapter extends RecyclerView.Adapter implements EMMessageListener {

    private static final int TYPE_SEND = 0;
    private static final int TYPE_RECEIVE = 1;
    private Context mContext;
    private List<EMMessage> mEMMessageList = new ArrayList<>();

    public ChatAdapter(Context context) {
        mContext = context;
        EMClient.getInstance().chatManager().addMessageListener(this);
    }

    public List<EMMessage> getEMMessageList() {
        return mEMMessageList;
    }

    @Override
    public int getItemViewType(int position) {
        EMMessage emMessage = mEMMessageList.get(position);
        if (emMessage.getFrom().equalsIgnoreCase(EMClient.getInstance().getCurrentUser())) {
            return TYPE_SEND;
        }else {
            return TYPE_RECEIVE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_SEND:
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_send_msg, parent, false);
                SendViewHolder sendViewHolder = new SendViewHolder(view);
                return sendViewHolder;
            case TYPE_RECEIVE:
                View root = LayoutInflater.from(mContext).inflate(R.layout.item_receive_msg, parent, false);
                ReceiveViewHolder receiveViewHolder = new ReceiveViewHolder(root);
                return receiveViewHolder;
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType){
            case TYPE_SEND:
                SendViewHolder sendViewHolder = (SendViewHolder) holder;
                sendViewHolder.bindView(mEMMessageList.get(position),position);
                break;
            case TYPE_RECEIVE:
                ReceiveViewHolder receiveViewHolder = (ReceiveViewHolder) holder;
                receiveViewHolder.bindView(mEMMessageList.get(position),position);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mEMMessageList != null) {
            return mEMMessageList.size();
        }
        return 0;
    }

    public void addMsg(EMMessage emMessage) {
        mEMMessageList.add(emMessage);
        notifyDataSetChanged();
        ((ChatActivity) mContext).mRvChat.scrollToPosition(getItemCount() - 1);
    }

    private void receive(List<EMMessage> list) {
        mEMMessageList.addAll(list);
        notifyDataSetChanged();
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((ChatActivity) mContext).mRvChat.scrollToPosition(getItemCount() - 1);
            }
        });
    }

    @Override
    public void onMessageReceived(List<EMMessage> list) {
        //收到消息
        receive(list);
        Log.d("receive", "onMessageReceived: " + "接收到消息");
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {
        //收到透传消息
    }

    @Override
    public void onMessageRead(List<EMMessage> list) {
        //收到已读回执
    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {
        //收到已送达回执
    }

    @Override
    public void onMessageChanged(EMMessage emMessage, Object o) {
        //消息状态变动
    }

    public void receiveMsg(List<EMMessage> messagesList) {
        mEMMessageList.addAll(messagesList);
        notifyDataSetChanged();
        ((ChatActivity) mContext).mRvChat.scrollToPosition(getItemCount() - 1);
    }

    public void receiveOldMsg(List<EMMessage> messagesList) {
        mEMMessageList.addAll(0,messagesList);
        notifyDataSetChanged();
        ((ChatActivity) mContext).mRvChat.scrollToPosition(getItemCount() - 1);
    }

    class SendViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_send_msg_time)
        TextView mTvSendMsgTime;
        @BindView(R.id.iv_send_icon)
        ImageView mIvSendIcon;
        @BindView(R.id.iv_send_progress)
        ImageView mIvSendProgress;
        @BindView(R.id.tv_send_msg_content)
        TextView mTvSendMsgContent;

        SendViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(EMMessage emMessage,int position) {
            long msgTime = emMessage.getMsgTime();
            mTvSendMsgTime.setText(DateUtils.getTimestampString(new Date(msgTime)));
            //和上一条消息的间隔时间小于20s，就不显示了
            if(position == 0 || msgTime - mEMMessageList.get(position - 1).getMsgTime() > 20000){
                mTvSendMsgTime.setVisibility(View.VISIBLE);
            }else{
                mTvSendMsgTime.setVisibility(View.GONE);
            }
            EMMessageBody body = emMessage.getBody();
            if (body instanceof EMTextMessageBody) {
                mTvSendMsgContent.setText(((EMTextMessageBody) body).getMessage());
            }else {
                mTvSendMsgContent.setText("非文本消息");
            }
            switch (emMessage.status()){
                case INPROGRESS:
                    mIvSendProgress.setImageResource(R.drawable.ani_send_progress);
                    AnimationDrawable animationDrawable = (AnimationDrawable) mIvSendProgress.getDrawable();
                    //转圈圈
                    animationDrawable.start();
                    break;
                case SUCCESS:
                    mIvSendProgress.setVisibility(View.GONE);
                    break;
                case FAIL:
                    mIvSendProgress.setImageResource(R.drawable.msg_error);
                    break;
            }
        }
    }

    class ReceiveViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_receive_msg_time)
        TextView mTvReceiveMsg;
        @BindView(R.id.iv_receive_icon)
        ImageView mIvSendIcon;
        @BindView(R.id.tv_receive_msg_content)
        TextView mTvReceiveMsgContent;

        ReceiveViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(EMMessage emMessage,int position) {
            long msgTime = emMessage.getMsgTime();
            mTvReceiveMsg.setText(DateUtils.getTimestampString(new Date(msgTime)));
            //和上一条消息的间隔时间小于20s，就不显示了
            if(position == 0 || msgTime - mEMMessageList.get(position - 1).getMsgTime() > 20000){
                mTvReceiveMsg.setVisibility(View.VISIBLE);
            }else{
                mTvReceiveMsg.setVisibility(View.GONE);
            }
            EMMessageBody body = emMessage.getBody();
            if (body instanceof EMTextMessageBody) {
                mTvReceiveMsgContent.setText(((EMTextMessageBody) body).getMessage());
            }else {
                mTvReceiveMsgContent.setText("非文本消息");
            }
        }
    }

    public void onDestroy(){
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }
}
