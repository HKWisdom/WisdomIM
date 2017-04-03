package com.wisdom.im.presenter.presenterImpl;

/**
 * Created by HKWisdom on 2017/4/1.
 */

public interface ChatPresenter {
    void sendMessage(String content,String username);

    void loadHistoryMsg(String name);

    void loadMoreMsg(String username, String msgId);
}
