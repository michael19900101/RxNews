package com.aotuman.mcnews;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.aotuman.mcnews.bean.NeteastNewsSummary;
import com.aotuman.mcnews.common.DataLoadType;
import com.aotuman.mcnews.module.news.presenter.INewsListPresenterImpl;
import com.aotuman.mcnews.module.news.view.INewsListView;

import java.util.List;


public class MainActivity extends AppCompatActivity implements INewsListView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String mNewsId = "T1348649580692";
        String mNewsType = "list";
        new INewsListPresenterImpl(this, mNewsId, mNewsType);
    }

    @Override
    public void toast(String msg) {
        Log.e("aaaa","toast");
    }

    @Override
    public void showProgress() {
        Log.e("aaaa","showProgress");
    }

    @Override
    public void hideProgress() {
        Log.e("aaaa","hideProgress");
    }

    @Override
    public void updateNewsList(List<NeteastNewsSummary> data, @NonNull String errorMsg,
                               @DataLoadType.DataLoadTypeChecker int type) {

        Log.e("aaaa","data:"+data.size());
    }
}
