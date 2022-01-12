package com.google.countryapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.countryapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<CountryRoom>> {

    ActivityMainBinding binding;

    CountryAdapter countryAdapter;
    List<CountryRoom> countries;
    private final static int ID_COUNTRY_DATA_LOADER = 101;

    private final static String COUNTRY_REQUEST_URL = "https://restcountries.com/v2/all";

    @SuppressLint("SetTextI18n")
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
            deleteAllFromRoom();
            LoaderManager loaderManager = getLoaderManager();
            Log.v("MainActivity","Loader Initialisation");

            loaderManager.initLoader(ID_COUNTRY_DATA_LOADER, null, this);
        }
        else
        {
            binding.progressBar.setVisibility(View.GONE);
            bringDataFromRoom();
        }

        binding.fbDelete.setOnClickListener(v -> {

            AlertDialog.Builder alertDialogBuilder =
                    new AlertDialog.Builder(MainActivity.this, R.style.AppTheme_Dialog);
            alertDialogBuilder.setTitle(R.string.delete_confirmation);
            alertDialogBuilder.setMessage(R.string.warning);
            alertDialogBuilder.setPositiveButton(R.string.yes, (dialog, which) -> {
                deleteAllFromRoom();
                Toast.makeText(MainActivity.this, "All data is deleted.", Toast.LENGTH_SHORT).show();
            });
            alertDialogBuilder.setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel());
            alertDialogBuilder.create().show();

        });


    }

    @SuppressLint("NotifyDataSetChanged")
    private void deleteAllFromRoom() {

        new  Thread(() -> {

            countries.clear();
            CountryRoomDatabase.getINSTANCE(getApplicationContext())
                    .countryDao()
                    .deleteRoom();
        }).start();

        countryAdapter.notifyDataSetChanged();

    }

    @SuppressLint("NotifyDataSetChanged")
    private void bringDataFromRoom() {

         new Thread(() -> {
             countries.clear();
             countries.addAll(CountryRoomDatabase.getINSTANCE(getApplicationContext())
                     .countryDao()
                     .selectAll());
             countryAdapter.notifyDataSetChanged();
         }).start();

    }


        @Override
    public Loader<List<CountryRoom>> onCreateLoader(int id, Bundle args) {
        return new Countryloader(this, COUNTRY_REQUEST_URL);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onLoadFinished(Loader<List<CountryRoom>> loader, List<CountryRoom> data) {

        binding.progressBar.setVisibility(View.GONE);
        countries.addAll(data);
        countryAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onLoaderReset(Loader<List<CountryRoom>> loader) {

     countries.clear();

    }

    public void restart(View view) {



        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if(networkInfo != null)
        {
            binding.progressBar.setVisibility(View.VISIBLE);
            deleteAllFromRoom();
            getLoaderManager().restartLoader(ID_COUNTRY_DATA_LOADER, null, this);
        }
        else
        {
            Toast.makeText(this, "No Internet Connection.", Toast.LENGTH_SHORT).show();
        }


    }


    private static class Countryloader extends AsyncTaskLoader<List<CountryRoom>> {

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
        public List<CountryRoom> loadInBackground() {

            List<CountryRoom> countries;

            countries = QueryUtils.fetchCountryData(mUrl);

            CountryRoomDatabase.getINSTANCE(getContext())
                    .countryDao()
                    .insertMultipleCountry(countries);
            Log.v("MainActivity", "Country Data Entered into Database");

            return countries;
        }
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(MainActivity.this, R.style.AppTheme_Dialog);
        builder.setTitle("Do you want to exit ?")
                .setPositiveButton(R.string.yes, (dialog, which) -> finishAffinity())
                .setNegativeButton(R.string.no, (dialog, which) -> dialog.cancel());
        builder.create().show();
    }

}