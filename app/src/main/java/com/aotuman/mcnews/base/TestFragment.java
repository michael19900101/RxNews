package com.aotuman.mcnews.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aotuman.mcnews.module.news.presenter.INewsListPresenter;

/**
 * Created by aotuman on 2017/4/20.
 */

public class TestFragment extends BaseFragment<INewsListPresenter> {

    @Override
    protected void initView(View fragmentRootView) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText("hello world");
        return textView;
    }
}
