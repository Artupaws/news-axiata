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

import java.util.ArrayList;
import java.util.List;

public class AdapterSearchNews extends RecyclerView.Adapter<AdapterSearchNews.ViewHolder> implements Filterable {

    private List<Article> articleList;
    private List<Article> filterList;
    private Context context;

    public AdapterSearchNews(List<Article> articleList, Context context) {
        this.articleList = articleList;
        this.filterList = articleList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        Article articles = articleList.get(position);

        viewHolder.tv_title.setText(articles.getTitle());
        viewHolder.tv_desc.setText(articles.getDescription());
        viewHolder.tv_date.setText(articles.getPublishedAt());
        Glide.with(context).load(articles.getUrlToImage())
                .into(viewHolder.image_news);
        viewHolder.tv_url.setText(articles.getUrl());
        viewHolder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchArticleFragmentDirections.ActionSearchArticleFragmentToWebViewFragment action = SearchArticleFragmentDirections.actionSearchArticleFragmentToWebViewFragment();
                action.setWebUrl(viewHolder.tv_url.getText().toString());
                Navigation.findNavController(viewHolder.parent_layout).navigate(action);
            }
        });

    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterList = articleList;
                } else {
                    ArrayList<Article> result = new ArrayList<>();
                    for (Article row : articleList) {
                        if (row.getTitle().toLowerCase().contains(charString)) {
                            result.add(row);
                        }
                    }
                    filterList.clear();
                    filterList.addAll(result);
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = articleList.size();
                filterResults.values = articleList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filterList = (ArrayList<Article>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_date, tv_title, tv_desc, tv_url;
        private ImageView image_news;
        private ConstraintLayout parent_layout;
        private ProgressBar progressBar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            image_news = itemView.findViewById(R.id.image_news);
            tv_url = itemView.findViewById(R.id.tv_url);
            parent_layout = itemView.findViewById(R.id.parent_layout);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
