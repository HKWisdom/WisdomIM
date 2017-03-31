package com.wisdom.im.presenter.presenterImpl;

import com.hyphenate.chat.EMClient;
import com.wisdom.im.app.MyApplication;
import com.wisdom.im.model.bean.Friend;
import com.wisdom.im.model.bean.User;
import com.wisdom.im.presenter.AddFriendPresenter;
import com.wisdom.im.utils.ThreadUtil;
import com.wisdom.im.view.AddFriendView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by HKWisdom on 2017/3/31.
 */

public class AddFriendPresenterImpl implements AddFriendPresenter {

    private static final String TAG = "AddFriendPresenterImpl";
    private AddFriendView mAddFriendView;

    public AddFriendPresenterImpl(AddFriendView addFriendView) {
        mAddFriendView = addFriendView;
    }

    @Override
    public void searchFriends(String content) {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereStartsWith("username",content);
        bmobQuery.addWhereNotEqualTo("username", EMClient.getInstance().getCurrentUser());
        //执行查询
        bmobQuery.findObjects(MyApplication.mContext, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                //转换集合
                    convert(list);
                    ThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAddFriendView.onSearchFriendSuccess();
                        }
                    });
            }

            @Override
            public void onError(int i, String s) {
                ThreadUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAddFriendView.onSearchFriendFailed();
                    }
                });
            }
        });
    }

    private List<Friend> mFriendList;

    @Override
    public List<Friend> getFriendList() {
        return mFriendList;
    }

    private void convert(List<User> list) {
        mFriendList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            Friend friend = new Friend();
            friend.setUsername(user.getUsername());
            friend.setCreateTime(user.getCreatedAt());
           mFriendList.add(friend);
        }
    }
}
