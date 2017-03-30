package com.wisdom.im.ui.activity;

import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wisdom.im.R;
import com.wisdom.im.presenter.RegisterPresenter;
import com.wisdom.im.presenter.presenterImpl.RegisterPresenterImpl;
import com.wisdom.im.view.RegisterView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by HKWisdom on 2017/3/30.
 */
public class RegisterActivity extends BaseActivity implements RegisterView {


    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.confirm_password)
    EditText mConfirmPassword;
    @BindView(R.id.btn_register)
    Button mBtnRegister;
    RegisterPresenter mRegisterPresenter;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {
        mRegisterPresenter = new RegisterPresenterImpl(this);
        mConfirmPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                register();
                return true;
            }
        });
    }


    @OnClick(R.id.btn_register)
    public void onClick() {
        register();
    }

    public void register() {
        String mUsername1 = mUsername.getText().toString().trim();
        String mPassword1 = mPassword.getText().toString().trim();
        String mConfirmPassword1 = mConfirmPassword.getText().toString().trim();
        //隐藏软键盘
        hideSoftKeyBoard();
        mRegisterPresenter.register(mUsername1, mPassword1, mConfirmPassword1);
    }

    @Override
    public void onUsernameError() {
        Toast.makeText(this, "用户名格式不对", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPasswordError() {
        Toast.makeText(this, "密码名格式不对", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void on2PasswordNotEquals() {
        Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startRegister() {
        showDialog("正在注册");
    }
}
