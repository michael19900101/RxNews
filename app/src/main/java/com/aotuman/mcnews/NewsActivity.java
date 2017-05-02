package com.aotuman.mcnews;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.aotuman.mcnews.annotation.ActivityFragmentInject;
import com.aotuman.mcnews.base.BaseActivity;
import com.aotuman.mcnews.base.BaseFragment;
import com.aotuman.mcnews.base.BaseFragmentAdapter;
import com.aotuman.mcnews.greendao.NewsChannelTable;
import com.aotuman.mcnews.module.news.presenter.INewsPresenter;
import com.aotuman.mcnews.module.news.presenter.INewsPresenterImpl;
import com.aotuman.mcnews.module.news.ui.NewsListFragment;
import com.aotuman.mcnews.module.news.view.INewsView;
import com.aotuman.mcnews.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aotuman on 2017/5/2.
 */
@ActivityFragmentInject(contentViewId = R.layout.activity_main,
        toolbarTitle = R.string.app_name)
public class NewsActivity extends BaseActivity<INewsPresenter> implements INewsView{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolBar;

    @Override
    protected void initView() {
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        mPresenter = new INewsPresenterImpl(this);
    }

    @Override
    public void initViewPager(List<NewsChannelTable> newsChannels) {
        List<BaseFragment> fragments = new ArrayList<>();
        final List<String> title = new ArrayList<>();

        if (newsChannels != null) {
            // 有除了固定的其他频道被选中，添加
            for (NewsChannelTable news : newsChannels) {
                final NewsListFragment fragment = NewsListFragment
                        .newInstance(news.getNewsChannelId(), news.getNewsChannelType(),
                                news.getNewsChannelIndex());

                fragments.add(fragment);
                title.add(news.getNewsChannelName());
            }

            if (viewPager.getAdapter() == null) {
                // 初始化ViewPager
                BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(),
                        fragments, title);
                viewPager.setAdapter(adapter);
            } else {
                final BaseFragmentAdapter adapter = (BaseFragmentAdapter) viewPager.getAdapter();
                adapter.updateFragments(fragments, title);
            }
            viewPager.setCurrentItem(0, false);
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setScrollPosition(0, 0, true);
            // 根据Tab的长度动态设置TabLayout的模式
            ViewUtil.dynamicSetTabLayoutMode(tabLayout);

            setOnTabSelectEvent(viewPager, tabLayout);

        } else {
            toast("数据异常");
        }
    }

    @Override
    public void initRxBusEvent() {

    }
}
