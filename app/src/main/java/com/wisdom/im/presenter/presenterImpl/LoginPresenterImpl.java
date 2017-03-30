package com.wisdom.im.presenter.presenterImpl;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.wisdom.im.presenter.LoginPresenter;
import com.wisdom.im.utils.StringUtil;
import com.wisdom.im.utils.ThreadUtil;
import com.wisdom.im.view.LoginView;

/**
 * Created by HKWisdom on 2017/3/30.
 */

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView mLoginView;

    public LoginPresenterImpl(LoginView loginView) {
        mLoginView = loginView;
    }

    @Override
    public void login(String name, String password) {
        if (StringUtil.isValidUsername(name)) {
            if (StringUtil.isValidPassword(password)) {
                loginHuanXin(name, password);
            }else {
                mLoginView.onPasswordFailed();
            }
        }else {
            mLoginView.onUsernameFailed();
        }

    }

    private void loginHuanXin(String name, String password) {
        EMClient.getInstance().login(name,password,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                ThreadUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoginView.onLoginSuccess();
                    }
                });

            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, final String message) {
                ThreadUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoginView.onLoginFailed(message);
                    }
                });

            }
        });
    }
}
