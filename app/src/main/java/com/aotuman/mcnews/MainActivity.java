package com.aotuman.mcnews;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.aotuman.mcnews.base.BaseFragment;
import com.aotuman.mcnews.base.BaseFragmentAdapter;
import com.aotuman.mcnews.base.TestFragment;
import com.aotuman.mcnews.module.news.ui.NewsListFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);

        initViewPager();
        tabLayout.setupWithViewPager(viewPager);

    }

    private void initViewPager(){
        List<BaseFragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        String mNewsId = "T1348649580692";
        String mNewsType = "list";
        NewsListFragment fragment = NewsListFragment
                .newInstance(mNewsId, mNewsType,0);
        fragments.add(fragment);
        titles.add("新闻");

        for(int i = 1;i < 7;i++){
            fragments.add(new TestFragment());
            titles.add("测试"+i);
        }

        if (viewPager.getAdapter() == null) {
            // 初始化ViewPager
            BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(),
                    fragments, titles);
            viewPager.setAdapter(adapter);
        }
    }
}
