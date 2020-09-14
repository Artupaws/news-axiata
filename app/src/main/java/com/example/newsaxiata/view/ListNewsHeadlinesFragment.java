package com.example.newsaxiata.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsaxiata.R;
import com.example.newsaxiata.databinding.FragmentListNewsHeadlinesBinding;
import com.example.newsaxiata.model.Article;
import com.example.newsaxiata.model.News;
import com.example.newsaxiata.viewmmodel.ViewModelNews;

import java.util.List;

public class ListNewsHeadlinesFragment extends Fragment implements View.OnClickListener{

    ViewModelNews viewModelNews;
    FragmentListNewsHeadlinesBinding binding;
    private AdapterNews adapterNews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListNewsHeadlinesBinding.inflate(getLayoutInflater());

        //viewModel init
        viewModelNews = ViewModelProviders.of(this).get(ViewModelNews.class);
        viewModelNews.getNews();

        viewModelNews.listMutableLiveData.observe(getActivity(), new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articleList) {
                adapterNews = new AdapterNews(articleList, getActivity());
                binding.recyclerNew.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.recyclerNew.setAdapter(adapterNews);
                adapterNews.setArticleList(articleList);
                adapterNews.notifyDataSetChanged();
            }
        });

        binding.cvBusiness.setOnClickListener(this);
        binding.cvEntertainment.setOnClickListener(this);
        binding.cvGeneral.setOnClickListener(this);
        binding.cvHealth.setOnClickListener(this);
        binding.cvScience.setOnClickListener(this);
        binding.cvSports.setOnClickListener(this);
        binding.cvTechnology.setOnClickListener(this);
        binding.tvSearch.setOnClickListener(this);

        return binding.getRoot();
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