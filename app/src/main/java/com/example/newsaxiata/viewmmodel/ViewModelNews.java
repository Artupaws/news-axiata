package com.example.newsaxiata.viewmmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsaxiata.api.Client;
import com.example.newsaxiata.model.Article;
import com.example.newsaxiata.model.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModelNews extends ViewModel {
    public MutableLiveData<List<Article>> listMutableLiveData = new MutableLiveData<>();

    public void getNews(){
        Client.getINSTANCE().getNews().enqueue(new Callback <News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticles() !=null){
                    Log.i("isinya", response.body().getArticles().toString());
                    listMutableLiveData.setValue(response.body().getArticles());
                } else {
                    Log.i("error", "error");
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }


}
