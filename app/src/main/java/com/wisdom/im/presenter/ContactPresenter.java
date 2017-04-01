package com.wisdom.im.presenter;

import com.wisdom.im.model.bean.Contact;

import java.util.List;

/**
 * Created by HKWisdom on 2017/3/31.
 */

public interface ContactPresenter {

    List<Contact> getContactList();

    void loadAllContacts();

    void deleteContact(String username);

}
