package com.wisdom.im.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.wisdom.im.R;
import com.wisdom.im.model.bean.Contact;
import com.wisdom.im.ui.fragment.ContactFragment;
import com.wisdom.im.utils.ThreadUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HKWisdom on 2017/3/31.
 */

public class ContactAdapter extends RecyclerView.Adapter implements EMContactListener {

    private Context mContext;
    private List<Contact> mContactList;
    private ContactFragment mContactFragment;


    public ContactAdapter(Context context, ContactFragment contactFragment) {
        mContext = context;
        this.mContactFragment = contactFragment;
        EMClient.getInstance().contactManager().setContactListener(this);
    }

    public void setContactList(List<Contact> contactList) {
        mContactList = contactList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_contact, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.bindView(mContactList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mContactList != null) {
            return mContactList.size();
        }
        return 0;
    }

    @Override
    public void onContactAdded(String username) {
        //添加
        addContact(username);
    }

    @Override
    public void onContactDeleted(String s) {
        //删除
        int position = -1;
        for (int i = 0; i < mContactList.size(); i++) {
            if (mContactList.get(i).getUsername().equalsIgnoreCase(s)) {
                position = i;
            }
        }

        if (position != -1) {
            mContactList.remove(position);
        }

        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onContactInvited(String s, String s1) {

    }

    @Override
    public void onFriendRequestAccepted(String s) {

    }

    @Override
    public void onFriendRequestDeclined(String s) {

    }


    private void addContact(String username) {
        Contact contact = new Contact();
        contact.setUsername(username);
        contact.setFirstLatter(String.valueOf(username.toString().charAt(0)));
        contact.setNeedShowFirstLatter(false);
        mContactList.add(contact);
        Collections.sort(mContactList, new Comparator<Contact>() {
            @Override
            public int compare(Contact o1, Contact o2) {

                return o1.getUsername().charAt(0) - o2.getUsername().charAt(0);
            }
        });

        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.firstlatter)
        TextView mFirstlatter;
        @BindView(R.id.username)
        TextView mUsername;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mContactFragment.AlertDeleteContact(mUsername.getText().toString().trim());
                    return false;
                }
            });
        }

        public void bindView(Contact contact) {
            mFirstlatter.setText(contact.getFirstLatter());
            mUsername.setText(contact.getUsername());
            if (contact.isNeedShowFirstLatter()) {
                mFirstlatter.setVisibility(View.VISIBLE);
            } else {
                mFirstlatter.setVisibility(View.GONE);
            }

        }
    }
}
