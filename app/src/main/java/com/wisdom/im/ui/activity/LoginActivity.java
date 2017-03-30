package com.wisdom.im.ui.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wisdom.im.R;
import com.wisdom.im.presenter.LoginPresenter;
import com.wisdom.im.presenter.presenterImpl.LoginPresenterImpl;
import com.wisdom.im.view.LoginView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by HKWisdom on 2017/3/30.
 */
public class LoginActivity extends BaseActivity implements LoginView{

    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_new_user)
    TextView mTvNewUser;

    private LoginPresenter mLoginPresenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        mLoginPresenter = new LoginPresenterImpl(this);
    }

    @OnClick({R.id.btn_login, R.id.tv_new_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;

            case R.id.tv_new_user:
                goToActivity(RegisterActivity.class);
                break;
        }
    }

    private void login() {
        showDialog("正在登录...");
        String username = mUsername.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        mLoginPresenter.login(username,password);
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        hideDialog();
        goToActivity(MainActivity.class);
    }

    @Override
    public void onLoginFailed(String message) {
        Toast.makeText(this, "登录失败" + message, Toast.LENGTH_SHORT).show();
        hideDialog();
    }

    @Override
    public void onPasswordFailed() {
        Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUsernameFailed() {
        Toast.makeText(this, "用户名格式不对", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressDialog() {
        showDialog("正在登录...");
    }
}
