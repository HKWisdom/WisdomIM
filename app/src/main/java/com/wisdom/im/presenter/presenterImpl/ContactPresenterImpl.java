package com.wisdom.im.presenter.presenterImpl;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.wisdom.im.model.bean.Contact;
import com.wisdom.im.presenter.ContactPresenter;
import com.wisdom.im.utils.ThreadUtil;
import com.wisdom.im.view.ContactView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HKWisdom on 2017/3/31.
 */

public class ContactPresenterImpl implements ContactPresenter {

    private ContactView mContactView;
    private List<Contact> mContactList;

    @Override
    public List<Contact> getContactList() {
        return mContactList;
    }

    public ContactPresenterImpl(ContactView contactView) {
        mContactView = contactView;
    }

    @Override
    public void loadAllContacts() {
        ThreadUtil.runOnBgThread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取环信列表中所有的联系人的名字
                    List<String> userNames = EMClient.getInstance().contactManager().getAllContactsFromServer();
                    if (userNames != null && userNames.size() > 0) {
                        convert(userNames);

                        ThreadUtil.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mContactView.onLoadContactSuccess();
                            }
                        });
                    }

                } catch (HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mContactView.onLoadContactFailed();
                        }
                    });
                }
            }
        });
    }


    private void convert(List<String> userNames) {
        mContactList = new ArrayList<>();
        for (int i = 0; i < userNames.size(); i++) {
            Contact contact = new Contact();
            String name = userNames.get(i);
            contact.setFirstLatter(String.valueOf(name.charAt(0)));
            contact.setUsername(name);
            Boolean isShow = true;
            if (i > 0) {
                if(contact.getFirstLatter().equals(String.valueOf(userNames.get(i-1).charAt(0)))){
                    isShow = false;
                }
            }
            contact.setNeedShowFirstLatter(isShow);
            mContactList.add(contact);
        }
    }
}
