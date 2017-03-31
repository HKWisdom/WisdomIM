package com.wisdom.im.ui.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.roughike.bottombar.OnTabSelectListener;
import com.wisdom.im.R;
import com.wisdom.im.ui.fragment.ContactFragment;
import com.wisdom.im.ui.fragment.ConversationFragment;
import com.wisdom.im.ui.fragment.DynamicFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    @BindView(R.id.bottom_bar)
    com.roughike.bottombar.BottomBar mBottomBar;
    List<Fragment> mFragmentList;
    private FragmentManager mSupportFragmentManager;
    private Fragment tempFragment;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }


    @Override
    protected void init() {
        mFragmentList = new ArrayList<>();
        initFragment();
        mSupportFragmentManager = getSupportFragmentManager();
        tempFragment = mFragmentList.get(0);

        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_conversation:
                        switchContent(tempFragment,mFragmentList.get(0), mSupportFragmentManager.beginTransaction());
                        tempFragment = mFragmentList.get(0);
                        break;

                    case R.id.tab_contact:
                        switchContent(tempFragment,mFragmentList.get(1), mSupportFragmentManager.beginTransaction());
                        tempFragment = mFragmentList.get(1);
                        break;

                    case R.id.tab_dynamic:
                        switchContent(tempFragment,mFragmentList.get(2), mSupportFragmentManager.beginTransaction());
                        tempFragment = mFragmentList.get(2);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * 判断是否添加了界面，以保存当前状态
     */
    public void switchContent(Fragment from, Fragment to,
                              FragmentTransaction transaction) {

        if (!to.isAdded()) { // 先判断是否被add过

            transaction.hide(from).add(R.id.fragment_container, to)
                    .commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }

    }

    private void initFragment() {
        mFragmentList.add(new ConversationFragment());
        mFragmentList.add(new ContactFragment());
        mFragmentList.add(new DynamicFragment());
    }
}
