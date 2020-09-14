package com.example.newsaxiata.view;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsaxiata.databinding.FragmentSearchArticleBinding;
import com.example.newsaxiata.model.Article;
import com.example.newsaxiata.utility.EndlessRecyclerViewScrollListener;
import com.example.newsaxiata.viewmmodel.ViewModelNews;

import java.util.List;

public class SearchArticleFragment extends Fragment {

    ViewModelNews viewModelNews;
    FragmentSearchArticleBinding binding;
    private AdapterSearchNews adapterSearchNews;
    private EndlessRecyclerViewScrollListener scrollListener;
    private LinearLayoutManager linearLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchArticleBinding.inflate(getLayoutInflater());

        //viewModel init
        viewModelNews = ViewModelProviders.of(this).get(ViewModelNews.class);
        viewModelNews.getNews();

        viewModelNews.listMutableLiveData.observe(getActivity(), new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articleList) {
                adapterSearchNews = new AdapterSearchNews(articleList, getActivity());
                binding.rvArticles.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.rvArticles.setAdapter(adapterSearchNews);
                adapterSearchNews.setArticleList(articleList);
                adapterSearchNews.notifyDataSetChanged();
            }
        });

        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterSearchNews.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    adapterSearchNews.getFilter().filter(newText);
                } else {
                    viewModelNews.getNews();
                }
                return true;
            }
        });

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                viewModelNews.getNews();
            }
        };

        return binding.getRoot();
    }


}