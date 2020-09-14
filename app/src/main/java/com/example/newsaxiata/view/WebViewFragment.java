package com.example.newsaxiata.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.newsaxiata.R;
import com.example.newsaxiata.databinding.FragmentWebViewBinding;

public class WebViewFragment extends Fragment {

    FragmentWebViewBinding binding;
    private String web_url ="";

    public WebViewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWebViewBinding.inflate(getLayoutInflater());
        if (getArguments() != null) {
            WebViewFragmentArgs args = WebViewFragmentArgs.fromBundle(getArguments());
            web_url = args.getWebUrl();
            Log.i("isinya", web_url);
        }

        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setUseWideViewPort(true);
        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                binding.progressCircular.setVisibility(View.VISIBLE);
                view.loadUrl(url);

                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                binding.progressCircular.setVisibility(View.GONE);
            }
        });
        binding.webView.loadUrl(web_url);

        return binding.getRoot();
    }
}