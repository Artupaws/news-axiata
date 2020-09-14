package com.example.newsaxiata.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
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
import com.example.newsaxiata.viewmmodel.ViewModelCategory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCategoryFragment extends Fragment {

    ViewModelCategory viewModelCategory;
    FragmentListCategoryBinding binding;
    private AdapterCategory adapterCategory;
    String category = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListCategoryBinding.inflate(getLayoutInflater());

        if (getArguments() !=null){
            ListCategoryFragmentArgs args = ListCategoryFragmentArgs.fromBundle(getArguments());
            category = args.getCategory();
            Log.i("isinya", category);
        } else {
            category = "general";
        }

        //viewModel init
        viewModelCategory = ViewModelProviders.of(this).get(ViewModelCategory.class);
        viewModelCategory.getSource(category);

        viewModelCategory.listMutableLiveData.observe(getActivity(), new Observer<List<Source>>() {
            @Override
            public void onChanged(List<Source> sources) {
                adapterCategory = new AdapterCategory(sources, getActivity());
                binding.rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
                binding.rvCategory.setAdapter(adapterCategory);
                adapterCategory.setSourceList(sources);
                adapterCategory.notifyDataSetChanged();
            }
        });

        binding.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapterCategory.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    adapterCategory.getFilter().filter(newText);
                } else {
                    viewModelCategory.getSource(category);
                }
                return true;
            }
        });

        return binding.getRoot();
    }

}