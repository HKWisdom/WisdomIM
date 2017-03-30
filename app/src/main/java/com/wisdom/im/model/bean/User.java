package com.wisdom.im.model.bean;

import cn.bmob.v3.BmobUser;

/**
 * Created by HKWisdom on 2017/3/30.
 */

public class User extends BmobUser{
    private String faceScore;
    private String height;

    public String getFaceScore() {
        return faceScore;
    }

    public void setFaceScore(String faceScore) {
        this.faceScore = faceScore;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
