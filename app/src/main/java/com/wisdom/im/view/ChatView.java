package com.wisdom.im.view;

/**
 * Created by HKWisdom on 2017/4/1.
 */

public interface ChatView {
    void startSendMessage(String content);

    void onSendMessageSuccess();

    void onSendMessageFailed();

}
