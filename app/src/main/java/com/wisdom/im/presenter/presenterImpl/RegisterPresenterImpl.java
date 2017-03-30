package com.wisdom.im.presenter.presenterImpl;

import com.wisdom.im.presenter.RegisterPresenter;
import com.wisdom.im.utils.StringUtil;
import com.wisdom.im.view.RegisterView;

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
                if (!(password.equals(confirmWord))) {
                    //开始注册
                    mRegisterView.startRegister();
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
}
