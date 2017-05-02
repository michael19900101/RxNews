package com.aotuman.mcnews.module.news.view;


import com.aotuman.mcnews.base.BaseView;
import com.aotuman.mcnews.greendao.NewsChannelTable;

import java.util.List;

/**
 * ClassName: INewsView<p>
 * Author: oubowu<p>
 * Fuction: 新闻视图接口<p>
 * CreateDate: 2016/2/17 20:25<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
public interface INewsView extends BaseView {

    void initViewPager(List<NewsChannelTable> newsChannels);

    void initRxBusEvent();

}
