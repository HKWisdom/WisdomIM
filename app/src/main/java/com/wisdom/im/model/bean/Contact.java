package com.wisdom.im.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lidongzhi on 2017/3/30.
 */
@Entity
public class Contact {
    private String username;
    private String firstLatter;
    private boolean needShowFirstLatter;//是否显示首字母，当前小组第一个才能显示
    public Contact() {
    }

    @Generated(hash = 1558592194)
    public Contact(String username, String firstLatter, boolean needShowFirstLatter) {
        this.username = username;
        this.firstLatter = firstLatter;
        this.needShowFirstLatter = needShowFirstLatter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstLatter() {
        return firstLatter;
    }

    public void setFirstLatter(String firstLatter) {
        this.firstLatter = firstLatter;
    }

    public boolean isNeedShowFirstLatter() {
        return needShowFirstLatter;
    }

    public void setNeedShowFirstLatter(boolean needShowFirstLatter) {
        this.needShowFirstLatter = needShowFirstLatter;
    }

    public boolean getNeedShowFirstLatter() {
        return this.needShowFirstLatter;
    }
}
