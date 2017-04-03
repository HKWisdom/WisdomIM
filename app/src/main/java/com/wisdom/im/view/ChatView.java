package com.wisdom.im.view;

import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * Created by HKWisdom on 2017/4/1.
 */

public interface ChatView {
    void startSendMessage(EMMessage content);

    void onSendMessageSuccess();

    void onSendMessageFailed();

    void loadHistoryMsgSuccess(List<EMMessage> messagesList);

    void loadMOreMsgSuccess(List<EMMessage> messagesList);
}
