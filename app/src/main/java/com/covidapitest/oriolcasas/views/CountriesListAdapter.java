package com.covidapitest.oriolcasas.views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.covidapitest.oriolcasas.R;
import com.covidapitest.oriolcasas.model.CasesModel;

/**
 * The adapter used to populate the recycler view with the countries list.
 */
public class CountriesListAdapter extends RecyclerView.Adapter<CountriesListAdapter.CountriesListViewHolder> {

    private CasesModel casesModel;
    private CountryClickListener listener;

    public CountriesListAdapter(CasesModel casesModel, CountryClickListener listener) {
        this.casesModel = casesModel;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CountriesListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.countries_list_item, parent, false);
        return new CountriesListViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesListAdapter.CountriesListViewHolder holder, int position) {
        Object[] keys = casesModel.getCountriesData().keySet().toArray(); // The keys are the country names.
        holder.getCountryName().setText(keys[position].toString());
        if (listener != null) {
            holder.itemView.setOnClickListener(v -> listener.onCountryClickedListener(keys[position].toString())); // The fragment controls what to do with the item click
        }
    }

    @Override
    public int getItemCount() {
        return casesModel.getCountriesData().size();
    }

    /**
     * The view used to show every item in the adapter.
     */
    public static class CountriesListViewHolder extends RecyclerView.ViewHolder {
        private final TextView countryName;

        public CountriesListViewHolder(@NonNull View itemView) {
            super(itemView);

            countryName = itemView.findViewById(R.id.country_name);
        }

        public TextView getCountryName() {
            return countryName;
        }
    }
}
