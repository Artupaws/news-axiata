package com.example.newsaxiata.viewmmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsaxiata.api.Client;
import com.example.newsaxiata.model.News;
import com.example.newsaxiata.model.Source;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModelCategory extends ViewModel {
    public MutableLiveData<List<Source>> listMutableLiveData = new MutableLiveData<>();

    public void getSource(String category){
        Client.getINSTANCE().getCategory(category).enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getSources() != null){
                    Log.i("isinya", response.body().getSources().toString());
                    listMutableLiveData.setValue(response.body().getSources());
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }

}
