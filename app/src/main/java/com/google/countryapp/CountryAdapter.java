package com.google.countryapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.countryapp.databinding.ItemListBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder> {

    Context context;
    List<Country> countries;


    public CountryAdapter(Context context, List<Country> countries) {
        this.context = context;
        this.countries = countries;
    }

    @NonNull
    @Override
    public CountryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_list, parent, false);
        return new CountryViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CountryViewHolder holder, int position) {

        Country country = countries.get(position);

        holder.binding.country.setText(country.getName());
        holder.binding.capital.setText(country.getCapital());
        holder.binding.region.setText(country.getRegion());
        holder.binding.subregion.setText(country.getSubRegion());
        holder.binding.population.setText(country.getPopulation().toString());
        Uri flagImageUri = Uri.parse(country.getFlagImage());
        holder.binding.borders.setText(country.getBorders());
        holder.binding.languages.setText(country.getLanguage());

        Glide.with(context)
                .load(flagImageUri)
                .into(holder.binding.flagImageView);


        int colorCode = getRandomColor();
        holder.binding.parentLayout.setBackgroundColor(holder.itemView.getResources().getColor(colorCode));

    }

    private int getRandomColor() {
        List<Integer> colors = new ArrayList<>();
        colors.add(R.color.color1);
        colors.add(R.color.color2);
        colors.add(R.color.color3);
        colors.add(R.color.color4);
        colors.add(R.color.color5);
        colors.add(R.color.color6);
        colors.add(R.color.color7);
        colors.add(R.color.color8);
        colors.add(R.color.color9);
        colors.add(R.color.color10);
        colors.add(R.color.color11);
        colors.add(R.color.color12);

        Random random = new Random();
        int number = random.nextInt(colors.size());
        return colors.get(number);
    }

    @Override
    public int getItemCount() {
        return countries.size();
    }


    public static class CountryViewHolder extends RecyclerView.ViewHolder
    {

        ItemListBinding binding;
        public CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemListBinding.bind(itemView);
        }
    }
}
//public class CountryAdapter extends ArrayAdapter<Country> {
//
//    public CountryAdapter(Activity context, List<Country> countries)
//    {
//        super(context,0,countries);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//
//        View view = convertView;
//
//        if(view == null)
//        {
//            view = LayoutInflater.from(getContext()).inflate(
//                    R.layout.item_list, parent, false
//            );
//        }
//
//        Country country = getItem(position);
//
//        TextView countryName = view.findViewById(R.id.country);
//        TextView countryCapital = view.findViewById(R.id.capital);
//        TextView countryRegion = view.findViewById(R.id.region);
//        TextView countrySubregion = view.findViewById(R.id.subregion);
//
//        countryName.setText(country.getName());
//        countryCapital.setText(country.getCapital());
//        countryRegion.setText(country.getRegion());
//        countrySubregion.setText(country.getSubRegion());
//
//        return view;
//    }
//
//}
