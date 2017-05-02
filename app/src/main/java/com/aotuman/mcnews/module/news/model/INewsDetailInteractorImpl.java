package com.aotuman.mcnews.module.news.model;

import com.aotuman.mcnews.base.BaseSubscriber;
import com.aotuman.mcnews.bean.NeteastNewsDetail;
import com.aotuman.mcnews.callback.RequestCallback;
import com.aotuman.mcnews.http.manager.RetrofitManager;
import com.aotuman.mcnews.http.service.HostType;

import java.util.Map;

import rx.Subscription;
import rx.functions.Func1;

/**
 * ClassName: INewsDetailInteractorImpl<p>
 * Author: oubowu<p>
 * Fuction: 新闻详情的Model层接口实现<p>
 * CreateDate: 2016/2/19 21:02<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
public class INewsDetailInteractorImpl implements INewsDetailInteractor<NeteastNewsDetail> {

    @Override
    public Subscription requestNewsDetail(final RequestCallback<NeteastNewsDetail> callback, final String id) {
        return RetrofitManager.getInstance(HostType.NETEASE_NEWS_VIDEO).getNewsDetailObservable(id)
                .map(new Func1<Map<String, NeteastNewsDetail>, NeteastNewsDetail>() {
                    @Override
                    public NeteastNewsDetail call(Map<String, NeteastNewsDetail> map) {
                        return map.get(id);
                    }
                }).subscribe(new BaseSubscriber<>(callback));
    }

}
