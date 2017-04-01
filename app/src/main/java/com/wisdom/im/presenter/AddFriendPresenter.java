package com.wisdom.im.presenter;

import com.wisdom.im.model.bean.Friend;

import java.util.List;

/**
 * Created by HKWisdom on 2017/3/31.
 */

public interface AddFriendPresenter {

    void searchFriends(String content);

    List<Friend> getFriendList();

    void onDestroy();
}
