package com.wisdom.im.model.bean;

/**
 * Created by lidongzhi on 2017/3/30.
 */

public class Friend {
    private String username;
    private String createTime;
    private boolean isAdded;//已被当前登录用户添加为好友

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
