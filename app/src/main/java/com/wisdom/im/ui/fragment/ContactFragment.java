package com.wisdom.im.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wisdom.im.R;
import com.wisdom.im.model.bean.Contact;
import com.wisdom.im.presenter.ContactPresenter;
import com.wisdom.im.presenter.presenterImpl.ContactPresenterImpl;
import com.wisdom.im.ui.adapter.ContactAdapter;
import com.wisdom.im.view.ContactView;

import java.util.List;

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
    public ContactPresenter mContactPresenter;
    private ContactAdapter mContactAdapter;

    @Override
    protected void init() {
        mTvTitle.setText("联系人");
        mIvAddFriend.setVisibility(View.VISIBLE);
        mContactPresenter = new ContactPresenterImpl(this);
        mRvContact.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContact.setHasFixedSize(true); //自动刷新时固定item宽高，减少测量、布局
        mContactAdapter = new ContactAdapter(getContext());
        mRvContact.setAdapter(mContactAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContactPresenter.loadAllContacts();
    }

    @Override
    public int getLayoutRestId() {
        return R.layout.fragment_contact;
    }


    @OnClick(R.id.iv_add_friend)
    public void onClick() {

    }

    @Override
    public void onLoadContactSuccess() {
        List<Contact> contactList = mContactPresenter.getContactList();
        mContactAdapter.setContactList(contactList);
        Toast.makeText(getContext(), "获取联系人成功"+contactList.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadContactFailed() {
        Toast.makeText(getContext(), "获取联系人失败", Toast.LENGTH_SHORT).show();

    }
}
