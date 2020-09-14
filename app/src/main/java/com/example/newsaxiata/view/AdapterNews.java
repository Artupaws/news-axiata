package com.example.newsaxiata.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsaxiata.R;
import com.example.newsaxiata.model.Article;
import com.example.newsaxiata.model.News;

import java.util.List;

public class AdapterNews extends RecyclerView.Adapter<AdapterNews.ViewHolder>  {

    private List<Article> articleList;
    private Context context;

    public AdapterNews(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterNews.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterNews.ViewHolder holder, int position) {
        holder.tv_title.setText(articleList.get(position).getTitle());
        holder.tv_desc.setText(articleList.get(position).getDescription());
        holder.tv_date.setText(articleList.get(position).getPublishedAt());
        Glide.with(context).load(articleList.get(position).getUrlToImage())
                .into(holder.image_news);
        holder.tv_url.setText(articleList.get(position).getUrl());
        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListNewsHeadlinesFragmentDirections.ActionListNewFragmentToWebViewFragment action = ListNewsHeadlinesFragmentDirections.actionListNewFragmentToWebViewFragment();
                action.setWebUrl(holder.tv_url.getText().toString());
                Navigation.findNavController(holder.parent_layout).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public void setArticleList(List<Article> articleList){
        this.articleList = articleList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date, tv_title, tv_desc, tv_url;
        private ImageView image_news;
        private ConstraintLayout parent_layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            image_news = itemView.findViewById(R.id.image_news);
            tv_url = itemView.findViewById(R.id.tv_url);
            parent_layout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
