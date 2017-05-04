package com.aotuman.mcnews.module.news.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aotuman.mcnews.R;
import com.aotuman.mcnews.bean.NeteastNewsSummary;
import com.aotuman.mcnews.utils.GlideUtils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by aotuman on 2017/5/2.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> implements View.OnClickListener{
    private List<NeteastNewsSummary> datas;

    public NewsAdapter(List<NeteastNewsSummary> datas) {
        this.datas = datas;
    }

    private OnItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news_summary, viewGroup, false);
        MyViewHolder holder = new MyViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        NeteastNewsSummary item = datas.get(position);
        GlideUtils.loadDefault(item.imgsrc, viewHolder.getIv_news_summary_photo(), null, null, DiskCacheStrategy.RESULT);
        viewHolder.getTv_news_summary_digest().setText(item.digest);
        viewHolder.getTv_news_summary_ptime().setText(item.ptime);
        viewHolder.getTv_news_summary_title().setText(item.title);
        //将position保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(position);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas !=null ?datas.size():0;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class MyViewHolder extends RecyclerView.ViewHolder {
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