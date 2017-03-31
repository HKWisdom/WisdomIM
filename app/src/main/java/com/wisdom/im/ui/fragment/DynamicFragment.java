package com.wisdom.im.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wisdom.im.R;
import com.wisdom.im.presenter.LoginOutPresenter;
import com.wisdom.im.presenter.presenterImpl.LoginOutPresenterImpl;
import com.wisdom.im.view.LoginOutView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by HKWisdom on 2017/3/31.
 */
public class DynamicFragment extends BaseFragment implements LoginOutView{
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_add_friend)
    ImageView mIvAddFriend;
    @BindView(R.id.btn_logout)
    Button mBtnLogout;
    private LoginOutPresenter mLoginOutPresenter;
    @Override
    protected void init() {
        mLoginOutPresenter = new LoginOutPresenterImpl(this);
    }

    @Override
    public int getLayoutRestId() {
        return R.layout.fragment_dynamic;
    }


    @OnClick({R.id.iv_back, R.id.btn_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.btn_logout:
                mLoginOutPresenter.loginOut();
                break;
        }
    }

    @Override
    public void onLoginOutSuccess() {
        Toast.makeText(getContext(), "退出成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginOutFailed() {
        Toast.makeText(getContext(), "退出失败", Toast.LENGTH_SHORT).show();
    }
}
