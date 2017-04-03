package com.wisdom.im.presenter.presenterImpl;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.wisdom.im.utils.ThreadUtil;
import com.wisdom.im.view.ChatView;

import java.util.List;

/**
 * Created by HKWisdom on 2017/4/1.
 */

public class ChatPresenterImpl implements ChatPresenter {
    private ChatView mChatView;

    public ChatPresenterImpl(ChatView chatView) {
        mChatView = chatView;
    }



    @Override
    public void sendMessage(final String content, String username) {
        final EMMessage message = EMMessage.createTxtSendMessage(content, username);
        //将消息添加到列表中
        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mChatView.startSendMessage(message);
            }
        });

        //发送消息的回调
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                ThreadUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mChatView.onSendMessageSuccess();
                    }
                });
            }

            @Override
            public void onError(int i, String s) {
                ThreadUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mChatView.onSendMessageFailed();
                    }
                });
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);

    }

    @Override
    public void loadHistoryMsg(final String username) {
        ThreadUtil.runOnBgThread(new Runnable() {
            @Override
            public void run() {
                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
                if (conversation == null) {
                    return;
                }
                //获取此会话的所有消息
                final List<EMMessage> messagesList = conversation.getAllMessages();
                if (messagesList != null && messagesList.size() > 0) {

                    ThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mChatView.loadHistoryMsgSuccess(messagesList);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void loadMoreMsg(final String username, final String msgId) {
        ThreadUtil.runOnBgThread(new Runnable() {
            @Override
            public void run() {
                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(username);
                if (conversation == null) {
                    return;
                }
                //获取此会话的所有消息
                final List<EMMessage> messagesList = conversation.loadMoreMsgFromDB(msgId,20);
                if (messagesList != null && messagesList.size() > 0) {

                    ThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mChatView.loadMOreMsgSuccess(messagesList);
                        }
                    });
                }
            }
        });
    }
}
