package com.example.newsaxiata.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.newsaxiata.R;
import com.example.newsaxiata.api.Api;
import com.example.newsaxiata.api.Client;
import com.example.newsaxiata.databinding.FragmentListCategoryBinding;
import com.example.newsaxiata.model.News;
import com.example.newsaxiata.model.Source;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCategoryFragment extends Fragment {

    FragmentListCategoryBinding binding;
    public static String API_KEY ="";
    private String category ="";
    RecyclerView.LayoutManager layoutManager;
    private List<Source> sourceList = new ArrayList<>();
    private AdapterCategory adapterCategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListCategoryBinding.inflate(getLayoutInflater());

        API_KEY = getActivity().getResources().getString(R.string.api_key);
        if (getArguments() !=null){
            ListCategoryFragmentArgs args = ListCategoryFragmentArgs.fromBundle(getArguments());
            category = args.getCategory();
            Log.i("isinya", category);
        }
        loadCategory();

        return binding.getRoot();
    }

    public void loadCategory(){
        Api api = Client.getClient().create(Api.class);
        Call<News> call;
        String country = "us";
        call = api.getSources(country,category,API_KEY);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getSources() != null){
                    if (!sourceList.isEmpty()){
                        sourceList.clear();
                    }
                    layoutManager = new LinearLayoutManager(getActivity());
                    binding.rvCategory.setLayoutManager(layoutManager);
                    sourceList = response.body().getSources();
                    adapterCategory = new AdapterCategory(getActivity(), sourceList);
                    binding.rvCategory.setAdapter(adapterCategory);
                    adapterCategory.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "No Result!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }

}