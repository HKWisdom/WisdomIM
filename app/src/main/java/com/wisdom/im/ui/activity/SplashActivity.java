package com.wisdom.im.ui.activity;

import android.os.Handler;

import com.wisdom.im.R;
import com.wisdom.im.presenter.SplashPresenter;
import com.wisdom.im.presenter.presenterImpl.SplashPresenterImpl;
import com.wisdom.im.view.SplashView;

/**
 * Created by HKWisdom on 2017/3/30.
 */

public class SplashActivity extends BaseActivity implements SplashView {

    private SplashPresenter mSplashPresenter;
    private Handler mHandler;
    private long DELAY_TIME = 2000;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        mHandler = new Handler();
        mSplashPresenter = new SplashPresenterImpl(this);
        mSplashPresenter.checkLoginState();
    }

    @Override
    public void onHasLogin() {
        goToActivity(MainActivity.class);
        finish();
    }

    @Override
    public void onNoHasLogin() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                goToActivity(LoginActivity.class);
                finish();
            }
        },DELAY_TIME);
    }
}
