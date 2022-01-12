package com.google.countryapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.countryapp.databinding.ItemListBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.CountryViewHolder>  {

    Context context;
    List<CountryRoom> countries;
    List<CountryRoom> countriesfull;


    public CountryAdapter(Context context, List<CountryRoom> countries) {
        this.context = context;
        this.countries = countries;
        countriesfull = new ArrayList<>();
        countriesfull.addAll(countries);
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

        CountryRoom country = countries.get(position);

        holder.binding.country.setText(country.getCountryName());
        holder.binding.capital.setText(country.getCapitalName());
        holder.binding.region.setText(country.getCountryRegion());
        holder.binding.subregion.setText(country.getSubRegion());
        holder.binding.population.setText( country.getPopulation().toString());
        Uri flagImageUri = Uri.parse(country.getFlagPng());
        holder.binding.borders.setText(country.getBorders());
        holder.binding.languages.setText(country.getLanguages());

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

