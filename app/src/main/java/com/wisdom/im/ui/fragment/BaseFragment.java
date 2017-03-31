package com.wisdom.im.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by HKWisdom on 2017/3/31.
 */

public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getContext(),getLayoutRestId(),null);
        ButterKnife.bind(this,rootView);
        init();
        return rootView;
    }

    protected abstract void init();

    public abstract int getLayoutRestId();


}
