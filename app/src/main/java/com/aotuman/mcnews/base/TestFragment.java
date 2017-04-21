package com.aotuman.mcnews.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aotuman.mcnews.module.news.presenter.INewsListPresenter;

/**
 * Created by aotuman on 2017/4/20.
 */

public class TestFragment extends BaseFragment<INewsListPresenter> {
    //控件是否已经初始化
    private boolean isCreateView = false;
    private String name;

    public TestFragment(String name) {
        this.name = name;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("jjjj","fragment["+name+"]   onCreate");
    }

    @Override
    protected void initView(View fragmentRootView) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.e("jjjj","fragment["+name+"]   onCreateView");
        TextView textView = new TextView(getActivity());
        textView.setText("hello world");
        isCreateView = true;
        return textView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.e("jjjj","fragment["+name+"]是否可见:"+isVisibleToUser+","+"view是否创建:"+isCreateView);
    }
}
