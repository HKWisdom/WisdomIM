package com.wisdom.im.view;

/**
 * Created by HKWisdom on 2017/3/30.
 */

public interface LoginView {
    void onLoginSuccess();

    void onLoginFailed(String message);

    void onPasswordFailed();

    void onUsernameFailed();

    void showProgressDialog();
}
