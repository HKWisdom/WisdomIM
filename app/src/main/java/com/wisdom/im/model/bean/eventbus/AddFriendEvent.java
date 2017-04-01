package com.wisdom.im.model.bean.eventbus;

/**
 * Created by HKWisdom on 2017/4/1.
 */

public class AddFriendEvent {
    private String username;
    private String reason;

    public AddFriendEvent(String username, String reason) {
        this.username = username;
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
