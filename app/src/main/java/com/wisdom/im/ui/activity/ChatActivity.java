package com.wisdom.im.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMMessage;
import com.wisdom.im.R;
import com.wisdom.im.presenter.presenterImpl.ChatPresenter;
import com.wisdom.im.presenter.presenterImpl.ChatPresenterImpl;
import com.wisdom.im.ui.adapter.ChatAdapter;
import com.wisdom.im.view.ChatView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by HKWisdom on 2017/4/1.
 */
public class ChatActivity extends BaseActivity implements View.OnClickListener, ChatView {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_add_friend)
    ImageView mIvAddFriend;
    @BindView(R.id.rv_chat)
   public RecyclerView mRvChat;
    @BindView(R.id.et_msg_content)
    EditText mEtMsgContent;
    @BindView(R.id.btn_send_msg)
    Button mBtnSendMsg;
    private ChatPresenter mChatPresenter;
    private String mUsername;
    private ChatAdapter mChatAdapter;

    @Override
    protected void init() {
        mChatPresenter = new ChatPresenterImpl(this);
        Intent intent = getIntent();
        if (intent != null) {
            mUsername = intent.getStringExtra("username");
            mTvTitle.setText(mUsername);
        }

        mChatAdapter = new ChatAdapter(this);
        mRvChat.setLayoutManager(new LinearLayoutManager(this));
        mRvChat.setHasFixedSize(true);
        mRvChat.setAdapter(mChatAdapter);

        mIvBack.setVisibility(View.VISIBLE);
        mIvBack.setOnClickListener(this);

       // mRvChat.addOnScrollListener(mOnScrollListener);
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            //获取到第一个条目的索引
            int firstVisibleItemPosition = ((LinearLayoutManager) mRvChat.getLayoutManager()).findFirstVisibleItemPosition();
            //得到第一个条目的id
            String msgId = mChatAdapter.getEMMessageList().get(firstVisibleItemPosition).getMsgId();
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && firstVisibleItemPosition == 0) {
                //加载更多
                mChatPresenter.loadMoreMsg(mUsername,msgId);
            }
        }
    };

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_chat;
    }


    @OnClick(R.id.btn_send_msg)
    public void onClick() {
        send();//发送消息
    }

    private void send() {
        String content = mEtMsgContent.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "发送内容不能为空", Toast.LENGTH_SHORT).show();
        } else {
            mChatPresenter.sendMessage(content, mUsername);
            mEtMsgContent.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void startSendMessage(EMMessage content) {
        mChatAdapter.addMsg(content);
    }

    @Override
    public void onSendMessageSuccess() {
        Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
        mChatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSendMessageFailed() {
        Toast.makeText(this, "发送失败", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void loadHistoryMsgSuccess(List<EMMessage> messagesList) {
      //  mChatAdapter.receiveMsg(messagesList);
    }

    @Override
    public void loadMOreMsgSuccess(List<EMMessage> messagesList) {
      //  mChatAdapter.receiveOldMsg(messagesList);
    }

}
