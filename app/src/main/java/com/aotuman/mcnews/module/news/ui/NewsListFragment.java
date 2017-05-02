package com.aotuman.mcnews.module.news.ui;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.aotuman.mcnews.module.news.ui.adapter.NewsAdapter;
import com.aotuman.mcnews.R;
import com.aotuman.mcnews.annotation.ActivityFragmentInject;
import com.aotuman.mcnews.base.BaseFragment;
import com.aotuman.mcnews.base.BaseSpacesItemDecoration;
import com.aotuman.mcnews.bean.NeteastNewsSummary;
import com.aotuman.mcnews.bean.SinaPhotoDetail;
import com.aotuman.mcnews.common.DataLoadType;
import com.aotuman.mcnews.module.news.presenter.INewsListPresenter;
import com.aotuman.mcnews.module.news.presenter.INewsListPresenterImpl;
import com.aotuman.mcnews.module.news.view.INewsListView;
import com.aotuman.mcnews.utils.ClickUtils;
import com.aotuman.mcnews.utils.MeasureUtil;
import com.socks.library.KLog;

import java.util.ArrayList;
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
    private NewsAdapter mAdapter;
    private List<NeteastNewsSummary> datas;
    // 一般作为ViewPager承载的Fragment都会把位置索引传过来，这里放到基类，
    // 方便下面的initRefreshLayoutOrRecyclerViewEvent()方法处理订阅事件
    protected int mPosition;
    private ProgressBar mLoadingView;
    private SinaPhotoDetail mSinaPhotoDetail;

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
        mLoadingView = (ProgressBar) fragmentRootView.findViewById(R.id.progressbar);
        mPresenter = new INewsListPresenterImpl(this, mNewsId, mNewsType);
    }

    @Override
    public void updateNewsList(List<NeteastNewsSummary> data, @NonNull String errorMsg, @DataLoadType.DataLoadTypeChecker int type) {
        if (mAdapter == null) {
            initNewsList(data);
        }
    }

    private void initNewsList(final List<NeteastNewsSummary> datas) {
        this.datas = datas;
        // mAdapter为空肯定为第一次进入状态
        mAdapter = new NewsAdapter(datas);
        mAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (ClickUtils.isFastDoubleClick()) {
                    return;
                }

                // imgextra不为空的话，无新闻内容，直接打开图片浏览
                KLog.e(datas.get(position).title + ";" + datas.get(position).postid);

                view = view.findViewById(R.id.iv_news_summary_photo);

                if (datas.get(position).postid == null) {
                    toast("此新闻浏览不了哎╮(╯Д╰)╭");
                    return;
                }

                // 跳转到新闻详情
                if (!TextUtils.isEmpty(datas.get(position).digest)) {
                    Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                    intent.putExtra("postid", datas.get(position).postid);
                    intent.putExtra("imgsrc", datas.get(position).imgsrc);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                                view.findViewById(R.id.iv_news_summary_photo), "photos");
                        getActivity().startActivity(intent, options.toBundle());
                    } else {
                        //让新的Activity从一个小的范围扩大到全屏
                        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth()/* / 2*/, view.getHeight()/* / 2*/, 0, 0);
                        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
                    }
                } else {
                    // 以下将数据封装成新浪需要的格式，用于点击跳转到图片浏览
                    mSinaPhotoDetail = new SinaPhotoDetail();
                    mSinaPhotoDetail.data = new SinaPhotoDetail.SinaPhotoDetailDataEntity();
                    mSinaPhotoDetail.data.title = datas.get(position).title;
                    mSinaPhotoDetail.data.content = "";
                    mSinaPhotoDetail.data.pics = new ArrayList<>();
                    // 天啊，什么格式都有 --__--
                    if (datas.get(position).ads != null) {
                        for (NeteastNewsSummary.AdsEntity entiity : datas.get(position).ads) {
                            SinaPhotoDetail.SinaPhotoDetailPicsEntity sinaPicsEntity = new SinaPhotoDetail.SinaPhotoDetailPicsEntity();
                            sinaPicsEntity.pic = entiity.imgsrc;
                            sinaPicsEntity.alt = entiity.title;
                            sinaPicsEntity.kpic = entiity.imgsrc;
                            mSinaPhotoDetail.data.pics.add(sinaPicsEntity);
                        }
                    } else if (datas.get(position).imgextra != null) {
                        for (NeteastNewsSummary.ImgextraEntity entiity : datas.get(position).imgextra) {
                            SinaPhotoDetail.SinaPhotoDetailPicsEntity sinaPicsEntity = new SinaPhotoDetail.SinaPhotoDetailPicsEntity();
                            sinaPicsEntity.pic = entiity.imgsrc;
                            sinaPicsEntity.kpic = entiity.imgsrc;
                            mSinaPhotoDetail.data.pics.add(sinaPicsEntity);
                        }
                    }

//                    Intent intent = new Intent(getActivity(), PhotoDetailActivity.class);
//                    intent.putExtra("neteast", mSinaPhotoDetail);
//                    ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
//                    ActivityCompat.startActivity(getActivity(), intent, options.toBundle());

                }
            }
        });

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

    @Override
    public void showProgress() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingView.setVisibility(View.GONE);
    }

}
