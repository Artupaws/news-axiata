package com.example.newsaxiata.view;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.newsaxiata.R;
import com.example.newsaxiata.api.Api;
import com.example.newsaxiata.api.Client;
import com.example.newsaxiata.databinding.FragmentSearchArticleBinding;
import com.example.newsaxiata.model.Article;
import com.example.newsaxiata.model.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchArticleFragment extends Fragment {

    FragmentSearchArticleBinding binding;
    public static String API_KEY = "";
    private List<Article> articleList = new ArrayList<>();
    private AdapterSearchNews adapterSearchNews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchArticleBinding.inflate(getLayoutInflater());
        API_KEY = getActivity().getResources().getString(R.string.api_key);

        initViews();
        loadArticle();

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
                    loadArticle();
                }
                return true;
            }
        });

        return binding.getRoot();
    }

    public void loadArticle() {
        Api api = Client.getClient().create(Api.class);
        String country = "us";
        Call<News> call;
        call = api.getNews(country, API_KEY);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, final Response<News> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    articleList = response.body().getArticles();
                    adapterSearchNews = new AdapterSearchNews(articleList, getActivity());
                    binding.rvArticles.setAdapter(adapterSearchNews);
                    adapterSearchNews.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }

    private void initViews() {
        binding.rvArticles.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.rvArticles.setLayoutManager(layoutManager);
    }

}