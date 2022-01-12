package com.google.countryapp;

import android.annotation.SuppressLint;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.countryapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Country>> {

    ActivityMainBinding binding;

    CountryAdapter countryAdapter;
    List<Country> countries;

    private final String COUNTRY_REQUEST_URL = "https://restcountries.com/v2/all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        countries = new ArrayList<>();

        countryAdapter = new CountryAdapter(this, countries);
        binding.mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.mainRecyclerView.setAdapter(countryAdapter);


        ConnectivityManager manager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if(networkInfo != null)
        {
            LoaderManager loaderManager = getLoaderManager();

            Log.v("MainActivity","Loader Inisialization");

            loaderManager.initLoader(1, null, this);
        }
        else
        {
            binding.progressBar.setVisibility(View.GONE);
            TextView text = findViewById(R.id.text);
            text.setText("No Internet");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
       MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }





        @Override
    public Loader<List<Country>> onCreateLoader(int id, Bundle args) {
        return new Countryloader(this, COUNTRY_REQUEST_URL);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onLoadFinished(Loader<List<Country>> loader, List<Country> data) {

     countries.clear();
        countries.addAll(data);
        binding.progressBar.setVisibility(View.GONE);
     countryAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onLoaderReset(Loader<List<Country>> loader) {

     countries.clear();

    }


    private static class Countryloader extends AsyncTaskLoader<List<Country>> {

        String mUrl;

        public Countryloader(Context context, String url) {
            super(context);
            mUrl = url;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Nullable
        @Override
        public List<Country> loadInBackground() {

            List<Country> countries;

            countries = QueryUtils.fetchCountryData(mUrl);


            return countries;
        }
    }

}