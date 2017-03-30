package com.wisdom.im.view;

/**
 * Created by HKWisdom on 2017/3/30.
 */

public interface RegisterView {


    void onUsernameError();

    void onPasswordError();

    void on2PasswordNotEquals();

    void startRegister();
}
