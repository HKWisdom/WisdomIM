package com.wisdom.im.presenter.presenterImpl;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.wisdom.im.app.MyApplication;
import com.wisdom.im.model.bean.User;
import com.wisdom.im.presenter.RegisterPresenter;
import com.wisdom.im.utils.StringUtil;
import com.wisdom.im.utils.ThreadUtil;
import com.wisdom.im.view.RegisterView;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by HKWisdom on 2017/3/30.
 */

public class RegisterPresenterImpl implements RegisterPresenter {

    private RegisterView mRegisterView;

    public RegisterPresenterImpl(RegisterView registerView) {
        mRegisterView = registerView;
    }

    @Override
    public void register(String username, String password, String confirmWord) {
        if (StringUtil.isValidUsername(username)) {
            if (StringUtil.isValidPassword(password)) {
                if ((password.equals(confirmWord))) {
                    //开始注册
                    mRegisterView.startRegister();

                    //注册Bmob
                    initBmob(username,password);
                }else {
                    mRegisterView.on2PasswordNotEquals();
                }
            }else {
                mRegisterView.onPasswordError();
            }
        }else {
            mRegisterView.onUsernameError();
        }
    }

    private void initBmob(final String username, final String password) {
        User user = new User();
        user.setFaceScore("10");
        user.setHeight("180");
        user.setUsername(username);
        user.setPassword(password);
        user.signUp(MyApplication.mContext, new SaveListener() {
            @Override
            public void onSuccess() {

                //注册环信
                registerHuanXin(username,password);

                mRegisterView.onBmobSuccess();
            }

            @Override
            public void onFailure(int i, String s) {
                mRegisterView.onBmobFailed();
            }
        });
    }

    private void registerHuanXin(final String username, final String password) {

        ThreadUtil.runOnBgThread(new Runnable() {
            @Override
            public void run() {
                try {
                    //注册失败会抛出HyphenateException
                    EMClient.getInstance().createAccount(username, password);//同步方法
                    ThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRegisterView.onHuanXinSuccess();
                        }
                    });
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    ThreadUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRegisterView.onHuanXinFailed();
                        }
                    });

                }
            }
        });
    }
}
