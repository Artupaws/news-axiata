package com.example.newsaxiata.view;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.newsaxiata.R;
import com.example.newsaxiata.api.Api;
import com.example.newsaxiata.api.Client;
import com.example.newsaxiata.databinding.FragmentListNewsHeadlinesBinding;
import com.example.newsaxiata.model.Article;
import com.example.newsaxiata.model.News;
import com.example.newsaxiata.viewmmodel.ViewModelNews;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListNewsHeadlinesFragment extends Fragment implements View.OnClickListener{

    FragmentListNewsHeadlinesBinding binding;
    public static String API_KEY ="";
    RecyclerView.LayoutManager layoutManager;
    private List<Article> articleList = new ArrayList<>();
    private AdapterNews adapterNews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListNewsHeadlinesBinding.inflate(getLayoutInflater());
        API_KEY = getActivity().getResources().getString(R.string.api_key);

        layoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerNew.setLayoutManager(layoutManager);
        binding.recyclerNew.setAdapter(adapterNews);
        binding.cvBusiness.setOnClickListener(this);
        binding.cvEntertainment.setOnClickListener(this);
        binding.cvGeneral.setOnClickListener(this);
        binding.cvHealth.setOnClickListener(this);
        binding.cvScience.setOnClickListener(this);
        binding.cvSports.setOnClickListener(this);
        binding.cvTechnology.setOnClickListener(this);
        binding.tvSearch.setOnClickListener(this);

        loadArticle();
        return binding.getRoot();
    }


    public void loadArticle(){
        Api api = Client.getClient().create(Api.class);
        String country = "us";
        Call<News> call;
        call = api.getNews(country, API_KEY);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticles() != null){
                    if (!articleList.isEmpty()){
                        articleList.clear();
                    }
                    articleList = response.body().getArticles();
                    adapterNews = new AdapterNews(articleList, getActivity());
                    binding.recyclerNew.setAdapter(adapterNews);
                    adapterNews.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cv_business:
                ListNewsHeadlinesFragmentDirections.ActionListNewFragmentToListCategoryFragment action = ListNewsHeadlinesFragmentDirections.actionListNewFragmentToListCategoryFragment();
                action.setCategory("business");
                Navigation.findNavController(binding.cvBusiness).navigate(action);
                break;

            case R.id.cv_entertainment:
                ListNewsHeadlinesFragmentDirections.ActionListNewFragmentToListCategoryFragment action2 = ListNewsHeadlinesFragmentDirections.actionListNewFragmentToListCategoryFragment();
                action2.setCategory("entertainment");
                Navigation.findNavController(binding.cvEntertainment).navigate(action2);
                break;

            case R.id.cv_general:
                ListNewsHeadlinesFragmentDirections.ActionListNewFragmentToListCategoryFragment action3 = ListNewsHeadlinesFragmentDirections.actionListNewFragmentToListCategoryFragment();
                action3.setCategory("general");
                Navigation.findNavController(binding.cvGeneral).navigate(action3);
                break;

            case R.id.cv_health:
                ListNewsHeadlinesFragmentDirections.ActionListNewFragmentToListCategoryFragment action4 = ListNewsHeadlinesFragmentDirections.actionListNewFragmentToListCategoryFragment();
                action4.setCategory("health");
                Navigation.findNavController(binding.cvGeneral).navigate(action4);
                break;

            case R.id.cv_science:
                ListNewsHeadlinesFragmentDirections.ActionListNewFragmentToListCategoryFragment action5 = ListNewsHeadlinesFragmentDirections.actionListNewFragmentToListCategoryFragment();
                action5.setCategory("science");
                Navigation.findNavController(binding.cvGeneral).navigate(action5);
                break;

            case R.id.cv_sports:
                ListNewsHeadlinesFragmentDirections.ActionListNewFragmentToListCategoryFragment action6 = ListNewsHeadlinesFragmentDirections.actionListNewFragmentToListCategoryFragment();
                action6.setCategory("sports");
                Navigation.findNavController(binding.cvGeneral).navigate(action6);
                break;

            case R.id.cv_technology:
                ListNewsHeadlinesFragmentDirections.ActionListNewFragmentToListCategoryFragment action7 = ListNewsHeadlinesFragmentDirections.actionListNewFragmentToListCategoryFragment();
                action7.setCategory("technology");
                Navigation.findNavController(binding.cvGeneral).navigate(action7);
                break;

            case R.id.tv_search:
                Navigation.findNavController(binding.tvSearch).navigate(ListNewsHeadlinesFragmentDirections.actionListNewFragmentToSearchArticleFragment());
                break;
        }
    }
}