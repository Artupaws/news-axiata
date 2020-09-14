package com.example.newsaxiata.viewmmodel;

import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.newsaxiata.api.Api;
import com.example.newsaxiata.api.Client;
import com.example.newsaxiata.model.Article;

import java.util.List;

import io.reactivex.disposables.Disposable;

//import static com.example.newsaxiata.view.ListNewFragment.API_KEY;

public class ViewModelNews extends ViewModel {

    private MutableLiveData<List<Article>> mArticle;

    public void init(){
    }

    public LiveData<List<Article>> getArticle(){
        return mArticle;
    }

//    //    val users = MutableLiveData<List<UserInformation>>()
//    val dogs = mutableLiveData.getValue(articleList)''
//    val dogsLoadError = MutableLiveData<Boolean>()
//    val loading = MutableLiveData<Boolean>()
//
//    fun refresh(){
//        fetchFromRemote()
//    }
//
//    private void fetchFromRemote(){
//        disposable.add(
//                dogsService.getDogs()
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(object: DisposableSingleObserver<List<DogInformation>>(){
//            override fun onSuccess(dogList: List<DogInformation>) {
//                dogs.value = dogList
//                dogsLoadError.value = false
//                loading.value = false
//            }
//
//            override fun onError(e: Throwable) {
//                dogsLoadError.value = true
//                loading.value = false
//                e.printStackTrace()
//            }
//
//        })
//        )
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        disposable.clear()
//    }
}
