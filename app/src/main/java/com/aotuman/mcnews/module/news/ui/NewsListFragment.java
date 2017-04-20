package com.aotuman.mcnews.module.news.ui;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aotuman.mcnews.R;
import com.aotuman.mcnews.annotation.ActivityFragmentInject;
import com.aotuman.mcnews.base.BaseFragment;
import com.aotuman.mcnews.base.BaseSpacesItemDecoration;
import com.aotuman.mcnews.bean.NeteastNewsSummary;
import com.aotuman.mcnews.common.DataLoadType;
import com.aotuman.mcnews.module.news.presenter.INewsListPresenter;
import com.aotuman.mcnews.module.news.presenter.INewsListPresenterImpl;
import com.aotuman.mcnews.module.news.view.INewsListView;
import com.aotuman.mcnews.utils.MeasureUtil;

import java.util.List;

/**
 * ClassName: NewsListFragment<p>
 * Author: oubowu<p>
 * Fuction: 新闻列表界面<p>
 * CreateDate: 2016/2/17 20:50<p>
 * UpdateUser: <p>
 * UpdateDate: <p>
 */
@ActivityFragmentInject(contentViewId = R.layout.fragment_news_list,
        handleRefreshLayout = true)
public class NewsListFragment extends BaseFragment<INewsListPresenter> implements INewsListView {

    protected static final String NEWS_ID = "news_id";
    protected static final String NEWS_TYPE = "news_type";
    protected static final String POSITION = "position";

    protected String mNewsId;
    protected String mNewsType;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter<MyViewHolder> mAdapter;
    private List<NeteastNewsSummary> data;
    // 一般作为ViewPager承载的Fragment都会把位置索引传过来，这里放到基类，
    // 方便下面的initRefreshLayoutOrRecyclerViewEvent()方法处理订阅事件
    protected int mPosition;

    public static NewsListFragment newInstance(String newsId, String newsType, int position) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NEWS_ID, newsId);
        bundle.putString(NEWS_TYPE, newsType);
        bundle.putInt(POSITION, position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNewsId = getArguments().getString(NEWS_ID);
            mNewsType = getArguments().getString(NEWS_TYPE);
            mPosition = getArguments().getInt(POSITION);
        }

    }

    @Override
    protected void initView(View fragmentRootView) {
        mRecyclerView = (RecyclerView) fragmentRootView.findViewById(R.id.recycler_view);
        mPresenter = new INewsListPresenterImpl(this, mNewsId, mNewsType);
    }

    @Override
    public void updateNewsList(List<NeteastNewsSummary> data, @NonNull String errorMsg, @DataLoadType.DataLoadTypeChecker int type) {
        if (mAdapter == null) {
            initNewsList(data);
        }
    }

    private void initNewsList(final List<NeteastNewsSummary> data) {
        this.data = data;
        // mAdapter为空肯定为第一次进入状态
        mAdapter = new RecyclerView.Adapter<MyViewHolder>() {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                MyViewHolder holder = new MyViewHolder(LayoutInflater.from(getActivity())
                        .inflate(R.layout.item_news_summary, parent,false));
                return holder;
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                NeteastNewsSummary item = data.get(position);
                holder.getTv_news_summary_digest().setText(item.digest);
                holder.getTv_news_summary_ptime().setText(item.ptime);
                holder.getTv_news_summary_title().setText(item.title);
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        };

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new BaseSpacesItemDecoration(MeasureUtil.dip2px(getActivity(), 4)));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.getItemAnimator().setAddDuration(250);
        mRecyclerView.getItemAnimator().setMoveDuration(250);
        mRecyclerView.getItemAnimator().setChangeDuration(250);
        mRecyclerView.getItemAnimator().setRemoveDuration(250);
        mRecyclerView.setAdapter(mAdapter);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_news_summary_title;
        private TextView tv_news_summary_digest;
        private TextView tv_news_summary_ptime;
        private ImageView iv_news_summary_photo;

        public MyViewHolder(View view){
            super(view);
            tv_news_summary_title = (TextView) view.findViewById(R.id.tv_news_summary_title);
            tv_news_summary_digest = (TextView) view.findViewById(R.id.tv_news_summary_digest);
            tv_news_summary_ptime = (TextView) view.findViewById(R.id.tv_news_summary_ptime);
            iv_news_summary_photo = (ImageView) view.findViewById(R.id.iv_news_summary_photo);
        }

        public TextView getTv_news_summary_title() {
            return tv_news_summary_title;
        }

        public TextView getTv_news_summary_digest() {
            return tv_news_summary_digest;
        }

        public TextView getTv_news_summary_ptime() {
            return tv_news_summary_ptime;
        }

        public ImageView getIv_news_summary_photo() {
            return iv_news_summary_photo;
        }
    }
}
