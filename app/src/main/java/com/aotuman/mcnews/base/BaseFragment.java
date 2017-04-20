package com.aotuman.mcnews.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aotuman.mcnews.annotation.ActivityFragmentInject;

/**
 * Created by aotuman on 2017/4/20.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment
        implements BaseView, View.OnClickListener {

    protected View mFragmentRootView;
    protected int mContentViewId;
    // 将代理类通用行为抽出来
    protected T mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == mFragmentRootView) {
            if (getClass().isAnnotationPresent(ActivityFragmentInject.class)) {
                ActivityFragmentInject annotation = getClass()
                        .getAnnotation(ActivityFragmentInject.class);
                mContentViewId = annotation.contentViewId();
            }else {
                throw new RuntimeException(
                        "Class must add annotations of ActivityFragmentInitParams.class");
            }
            mFragmentRootView = inflater.inflate(mContentViewId, container, false);
            initView(mFragmentRootView);
        }
        return mFragmentRootView;
    }

    protected abstract void initView(View fragmentRootView);

    /**
     * 继承BaseView抽出显示信息通用行为
     *
     * @param msg
     */
    @Override
    public void toast(String msg) {
        showSnackbar(msg);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onClick(View v) {

    }

    protected void showSnackbar(String msg) {
        Snackbar.make(mFragmentRootView, msg, Snackbar.LENGTH_SHORT).show();
    }
}
