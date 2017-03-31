package com.wisdom.im.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wisdom.im.R;
import com.wisdom.im.model.bean.Friend;
import com.wisdom.im.presenter.AddFriendPresenter;
import com.wisdom.im.presenter.presenterImpl.AddFriendPresenterImpl;
import com.wisdom.im.ui.adapter.FriendAdapter;
import com.wisdom.im.view.AddFriendView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by HKWisdom on 2017/3/31.
 */
public class AddFriendActivity extends BaseActivity implements AddFriendView {

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_add_friend)
    ImageView mIvAddFriend;
    @BindView(R.id.et_keyword)
    EditText mEtKeyword;
    @BindView(R.id.btn_search_friend)
    ImageView mBtnSearchFriend;
    @BindView(R.id.rv_search_friend)
    RecyclerView mRvSearchFriend;
    private AddFriendPresenter mAddFriendPresenter;
    private FriendAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_add_friend;
    }

    @Override
    protected void init() {
        mTvTitle.setText("添加好友");
        mIvBack.setVisibility(View.VISIBLE);
        mAddFriendPresenter = new AddFriendPresenterImpl(this);
        mRvSearchFriend.setHasFixedSize(true);
        mRvSearchFriend.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FriendAdapter(this);
        mRvSearchFriend.setAdapter(mAdapter);
    }

    @OnClick({R.id.iv_back, R.id.btn_search_friend})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_search_friend:
                search();
                break;
        }
    }


    private void search() {
        String content = mEtKeyword.getText().toString().trim();
        mAddFriendPresenter.searchFriends(content);
    }

    @Override
    public void onSearchFriendSuccess() {
        List<Friend> list = mAddFriendPresenter.getFriendList();
        mAdapter.setFriendList(list);
    }

    @Override
    public void onSearchFriendFailed() {
        Toast.makeText(this, "搜索失败", Toast.LENGTH_SHORT).show();
    }



}
