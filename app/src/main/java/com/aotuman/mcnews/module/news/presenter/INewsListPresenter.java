package com.aotuman.mcnews.module.news.presenter;


import com.aotuman.mcnews.base.BasePresenter;

/**
 * ClassName: INewsListPresenter<p>
 * Author: oubowu<p>
 * Fuction: 新闻列表代理接口<p>
 * CreateDate: 2016/2/18 14:39<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
public interface INewsListPresenter extends BasePresenter {

    void refreshData();

    void loadMoreData();

}
