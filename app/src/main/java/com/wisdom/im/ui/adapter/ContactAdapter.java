package com.wisdom.im.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wisdom.im.R;
import com.wisdom.im.model.bean.Contact;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by HKWisdom on 2017/3/31.
 */

public class ContactAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<Contact> mContactList;

    public ContactAdapter(Context context) {
        mContext = context;
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

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.firstlatter)
        TextView mFirstlatter;
        @BindView(R.id.username)
        TextView mUsername;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindView(Contact contact) {
            mFirstlatter.setText(contact.getFirstLatter());
            mUsername.setText(contact.getUsername());
        }
    }
}
