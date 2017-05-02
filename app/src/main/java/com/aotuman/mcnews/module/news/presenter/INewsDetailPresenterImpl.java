package com.aotuman.mcnews.module.news.presenter;


import com.aotuman.mcnews.base.BasePresenterImpl;
import com.aotuman.mcnews.bean.NeteastNewsDetail;
import com.aotuman.mcnews.module.news.model.INewsDetailInteractor;
import com.aotuman.mcnews.module.news.model.INewsDetailInteractorImpl;
import com.aotuman.mcnews.module.news.view.INewsDetailView;

/**
 * ClassName: INewsDetailPresenterImpl<p>
 * Author: oubowu<p>
 * Fuction: 新闻详情代理接口实现<p>
 * CreateDate: 2016/2/19 21:11<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
public class INewsDetailPresenterImpl extends BasePresenterImpl<INewsDetailView, NeteastNewsDetail>
        implements INewsDetailPresenter {

    public INewsDetailPresenterImpl(INewsDetailView newsDetailView, String postId) {
        super(newsDetailView);
        INewsDetailInteractor<NeteastNewsDetail> newsDetailInteractor = new INewsDetailInteractorImpl();
        mSubscription = newsDetailInteractor.requestNewsDetail(this, postId);
    }

    @Override
    public void requestSuccess(NeteastNewsDetail data) {
        mView.initNewsDetail(data);
    }

    @Override
    public void requestError(String msg) {
        super.requestError(msg);
        mView.toast(msg);
    }
}
