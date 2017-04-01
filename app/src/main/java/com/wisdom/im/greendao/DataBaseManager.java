package com.wisdom.im.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.wisdom.im.app.MyApplication;
import com.wisdom.im.model.bean.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HKWisdom on 2017/3/31.
 */

public class DataBaseManager {
    private static DataBaseManager sDataBaseManager;
    private ContactDao mContactDao;

    private DataBaseManager() {
    }

    public static DataBaseManager getInstance(Context context) {
        if (sDataBaseManager == null) {
            synchronized (DataBaseManager.class) {
                if (sDataBaseManager == null) {
                    sDataBaseManager = new DataBaseManager();
                    sDataBaseManager.init(context);
                }
            }
        }
        return sDataBaseManager;
    }

    private void init(Context context) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context,"wisdom");
        SQLiteDatabase writableDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        DaoSession daoSession = daoMaster.newSession();
        mContactDao = daoSession.getContactDao();
    }

    public ContactDao getContactDao() {
        return mContactDao;
    }

    /**
     * 查询所有联系人的姓名
     */
    public List<String> queryAllContact() {
        List<String> names = new ArrayList<>();
        ContactDao contactDao =  DataBaseManager.getInstance(MyApplication.mContext).getContactDao();
        List<Contact> contactList = contactDao.queryBuilder().list();
        for (int i = 0; i < contactList.size(); i++) {
            names.add(contactList.get(i).getUsername());
        }
        return names;
    }

    public void insertContact(List<Contact> mContactList) {
        for (Contact contact : mContactList) {
            mContactDao.insert(contact);
        }
    }

    public void deleteContact() {
        mContactDao.deleteAll();
    }
}
