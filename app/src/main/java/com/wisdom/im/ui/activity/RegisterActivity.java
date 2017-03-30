package com.wisdom.im.ui.activity;

import android.widget.Button;
import android.widget.EditText;

import com.wisdom.im.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by HKWisdom on 2017/3/30.
 */
public class RegisterActivity extends BaseActivity {


    @BindView(R.id.username)
    EditText mUsername;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.confirm_password)
    EditText mConfirmPassword;
    @BindView(R.id.btn_register)
    Button mBtnRegister;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {

    }


    @OnClick(R.id.btn_register)
    public void onClick() {
        finish();
    }
}
