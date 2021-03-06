package com.example.newsaxiata.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsaxiata.R;
import com.example.newsaxiata.model.Article;
import com.example.newsaxiata.model.Source;

import java.util.ArrayList;
import java.util.List;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.ViewHolder> implements Filterable {

    private List<Source> sourceList;
    private List<Source> filterList;
    private FragmentActivity activity;

    public AdapterCategory(List<Source> sourceList, FragmentActivity activity) {
        this.sourceList = sourceList;
        this.filterList = sourceList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdapterCategory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_category_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdapterCategory.ViewHolder holder, int position) {
        Source sources = sourceList.get(position);

        holder.tv_desc.setText(sources.getDescription());
        holder.tv_name.setText(sources.getName());
        holder.tv_url.setText(sources.getUrl());
        holder.layoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListCategoryFragmentDirections.ActionListCategoryFragmentToWebViewFragment action = ListCategoryFragmentDirections.actionListCategoryFragmentToWebViewFragment();
                action.setWebUrl(holder.tv_url.getText().toString());
                Navigation.findNavController(holder.layoutParent).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }

    public void setSourceList(List<Source> sourceList){
        this.sourceList = sourceList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filterList = sourceList;
                } else {
                    ArrayList<Source> result = new ArrayList<>();
                    for (Source row : sourceList) {
                        if (row.getName().toLowerCase().contains(charString)) {
                            result.add(row);
                        }
                    }
                    filterList.clear();
                    filterList.addAll(result);
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = sourceList.size();
                filterResults.values = sourceList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                sourceList = (ArrayList<Source>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_desc, tv_name, tv_url;
        private ConstraintLayout layoutParent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_desc = itemView.findViewById(R.id.tv_desc);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_url = itemView.findViewById(R.id.tv_url);
            layoutParent = itemView.findViewById(R.id.layoutParent);
        }
    }
}
