package com.wisdom.im.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
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
import com.wisdom.im.ui.activity.AddFriendActivity;
import com.wisdom.im.ui.adapter.ContactAdapter;
import com.wisdom.im.view.ContactView;
import com.wisdom.im.widget.SliderBar;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by HKWisdom on 2017/3/31.
 */
public class ContactFragment extends BaseFragment implements ContactView, SliderBar.OnIndexChangeListener, SwipeRefreshLayout.OnRefreshListener {
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
    @BindView(R.id.sliderbar)
    SliderBar mSliderbar;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private ContactAdapter mContactAdapter;

    @Override
    protected void init() {
        mTvTitle.setText("联系人");
        mIvAddFriend.setVisibility(View.VISIBLE);
        mContactPresenter = new ContactPresenterImpl(this);
        mRvContact.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContact.setHasFixedSize(true); //自动刷新时固定item宽高，减少测量、布局
        mContactAdapter = new ContactAdapter(getContext(),this);
        mRvContact.setAdapter(mContactAdapter);
        mSliderbar.setOnIndexChangeListener(this);
        mSwipeRefresh.setOnRefreshListener(this);
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
        Intent intent = new Intent(getContext(), AddFriendActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoadContactSuccess() {
        List<Contact> contactList = mContactPresenter.getContactList();
        mContactAdapter.setContactList(contactList);
        //缓存联系人
        Toast.makeText(getContext(), "添加成功" + contactList.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadContactFailed() {
        Toast.makeText(getContext(), "获取联系人失败", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDeleteContactSuccess() {
        Toast.makeText(getContext(), "删除联系人成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteContactFailed() {
        Toast.makeText(getContext(), "删除联系人失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onIndexChange(String latter) {
        mPopupLatter.setVisibility(View.VISIBLE);
        mPopupLatter.setText(latter);
        int position = getPositionByLatter(latter);
        if (position != -1) {
            mRvContact.smoothScrollToPosition(position);
        }
    }

    @Override
    public void onFinish() {
        mPopupLatter.setVisibility(View.INVISIBLE);
    }

    private int getPositionByLatter(String latter) {
        List<Contact> contactList = mContactPresenter.getContactList();
        int position = -1;
        for (int i = 0; i < contactList.size(); i++) {
            Contact contact = contactList.get(i);
            if (latter.equalsIgnoreCase(contact.getFirstLatter())) {
                position = i;
            }
        }
        return position;
    }

    @Override
    public void onRefresh() {
        mContactPresenter.loadAllContacts();
        mSwipeRefresh.setRefreshing(false);
    }

    public void AlertDeleteContact(final String username) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("删除联系人");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //执行删除联系人的操作
                mContactPresenter.deleteContact(username);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();
            }
        });
        builder.show();
    }
}
