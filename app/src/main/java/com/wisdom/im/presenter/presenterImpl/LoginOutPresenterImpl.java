package com.wisdom.im.presenter.presenterImpl;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.wisdom.im.presenter.LoginOutPresenter;
import com.wisdom.im.utils.ThreadUtil;
import com.wisdom.im.view.LoginOutView;

/**
 * Created by HKWisdom on 2017/3/31.
 */

public class LoginOutPresenterImpl implements LoginOutPresenter {

    private LoginOutView mLoginOutView;

    public LoginOutPresenterImpl(LoginOutView loginOutView) {
        mLoginOutView = loginOutView;
    }

    @Override
    public void loginOut() {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                ThreadUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoginOutView.onLoginOutSuccess();
                    }
                });


            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {

                ThreadUtil.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mLoginOutView.onLoginOutFailed();
                    }
                });
            }
        });
    }
}
