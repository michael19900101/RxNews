package com.aotuman.mcnews.module.news.view;


import com.aotuman.mcnews.base.BaseView;
import com.aotuman.mcnews.bean.NeteastNewsDetail;

/**
 * ClassName: INewsDetailView<p>
 * Author: oubowu<p>
 * Fuction: 新闻详情视图接口<p>
 * CreateDate: 2016/2/19 14:52<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
public interface INewsDetailView extends BaseView {

    void initNewsDetail(NeteastNewsDetail data);

}
