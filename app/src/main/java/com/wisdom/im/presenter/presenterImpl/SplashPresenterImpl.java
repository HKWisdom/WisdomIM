package com.wisdom.im.presenter.presenterImpl;

import com.wisdom.im.presenter.SplashPresenter;
import com.wisdom.im.view.SplashView;

/**
 * Created by HKWisdom on 2017/3/30.
 */

public class SplashPresenterImpl implements SplashPresenter {

    private SplashView mSplashView;

    public SplashPresenterImpl(SplashView splashView) {
        mSplashView = splashView;
    }

    @Override
    public void checkLoginState() {
        boolean state = getLoginState();
        if (state) {
            mSplashView.onHasLogin();
        }else {
            mSplashView.onNoHasLogin();
        }
    }

    private boolean getLoginState() {
        return false;
    }

}
