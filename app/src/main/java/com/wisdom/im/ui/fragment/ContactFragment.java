package com.wisdom.im.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wisdom.im.R;
import com.wisdom.im.view.ContactView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by HKWisdom on 2017/3/31.
 */
public class ContactFragment extends BaseFragment implements ContactView {
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_add_friend)
    ImageView mIvAddFriend;
    @BindView(R.id.rv_contact)
    RecyclerView mRvContact;
    @BindView(R.id.popup_latter)
    TextView mPopupLatter;

    @Override
    protected void init() {
        mTvTitle.setText("联系人");
        mIvAddFriend.setVisibility(View.VISIBLE);
    }

    @Override
    public int getLayoutRestId() {
        return R.layout.fragment_contact;
    }


    @OnClick(R.id.iv_add_friend)
    public void onClick() {

    }
}
