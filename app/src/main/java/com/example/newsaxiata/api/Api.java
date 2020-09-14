package com.example.newsaxiata.api;

import com.example.newsaxiata.model.News;
import com.example.newsaxiata.model.Source;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("top-headlines")
    Call<News> getNews(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );

    @GET("sources")
    Call<News> getSources(
            @Query("country") String country,
            @Query("category") String category,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<News> getArticles(
            @Query("everything") String search,
            @Query("apiKey") String apiKey
    );
}
