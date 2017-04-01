package com.wisdom.im.presenter.presenterImpl;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.wisdom.im.greendao.DataBaseManager;
import com.wisdom.im.model.bean.Friend;
import com.wisdom.im.model.bean.User;
import com.wisdom.im.model.bean.eventbus.AddFriendEvent;
import com.wisdom.im.presenter.AddFriendPresenter;
import com.wisdom.im.utils.ThreadUtil;
import com.wisdom.im.view.AddFriendView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

import static com.wisdom.im.app.MyApplication.mContext;

/**
 * Created by HKWisdom on 2017/3/31.
 */

public class AddFriendPresenterImpl implements AddFriendPresenter {

    private static final String TAG = "AddFriendPresenterImpl";
    private AddFriendView mAddFriendView;

    public AddFriendPresenterImpl(AddFriendView addFriendView) {
        mAddFriendView = addFriendView;
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.Async)
    public void onAddFriendEvent(AddFriendEvent event) {
        //参数为要添加的好友的username和添加理由
        try {
            EMClient.getInstance().contactManager().addContact(event.getUsername(), event.getReason());
            ThreadUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAddFriendView.onAddFriendSuccess();
                }
            });
        } catch (HyphenateException e) {
            e.printStackTrace();
            ThreadUtil.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAddFriendView.onAddFriendFailed();
                }
            });
        }
    }

    @Override
    public void searchFriends(String content) {
        BmobQuery<User> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereStartsWith("username", content);
        bmobQuery.addWhereNotEqualTo("username", EMClient.getInstance().getCurrentUser());
        //执行查询
        bmobQuery.findObjects(mContext, new FindListener<User>() {
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
        List<String> names = DataBaseManager.getInstance(mContext).queryAllContact();
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            Friend friend = new Friend();
            friend.setUsername(user.getUsername());
            friend.setCreateTime(user.getCreatedAt());
            if (names.contains(friend.getUsername())) {
                friend.setAdded(true);
            } else {
                friend.setAdded(false);
            }
            mFriendList.add(friend);
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }
}
