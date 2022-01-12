package com.google.countryapp;


import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    private final static String LOG_TAG = "QueryUtils";

    private QueryUtils() {


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<CountryRoom> fetchCountryData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("QueryUtils", "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object

        // Return the {@link Event}
        return extractCountryInfo(jsonResponse);
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("QueryUtils", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("QueryUtils", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;

    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();

    }

    private static URL createUrl(String requestUrl) {

        URL url = null;
        try {
            url = new URL(requestUrl);
        } catch (MalformedURLException e) {
            Log.e("QueryUtils", "Error with creating URL ", e);
        }
        return url;

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<CountryRoom> extractCountryInfo(String jsonResponse) {

        if (TextUtils.isEmpty(jsonResponse)) {
            Log.v(LOG_TAG,"JsonResponse Null");
            return null;

        }

        List<CountryRoom> countries = new ArrayList<>();

        try {

            JSONArray root = new JSONArray(jsonResponse);

            for(int i = 0; i < root.length(); i++)
            {

                JSONObject currentCountry = root.getJSONObject(i);
                String countryName = currentCountry.getString("name");

                String countryCapital = "- - -";

                if(currentCountry.has("capital"))
                {
                    countryCapital =  currentCountry.getString("capital");
                }

                String countryRegion = currentCountry.getString("region");
                String countrySubregion = currentCountry.getString("subregion");
                Long population = currentCountry.getLong("population");
                JSONObject flag = currentCountry.getJSONObject("flags");
                String flag_image = flag.getString("png");

                String borders = "";

                if(currentCountry.has("borders"))
                {
                    JSONArray border = currentCountry.getJSONArray("borders");

                    for(int j = 0; j < border.length(); j++)
                    {
                        if(j == border.length() -1)
                        {
                            borders += border.get(j) +".";
                        }

                        else
                        {
                            borders += border.get(j) + ", ";
                        }

                    }
                }
                else
                {
                    borders = "- - -";
                }

                    String languages = "";

                    JSONArray language = currentCountry.getJSONArray("languages");

                    for(int k =0; k < language.length(); k++)
                    {
                        JSONObject obj = language.getJSONObject(k);
                        String name = obj.getString("name");
                        if(k == language.length() -1)
                        {
                            languages += name + ".";
                        }

                        else
                        {
                            languages  += name + ", ";
                        }
                    }


                CountryRoom country = new CountryRoom(countryName, countryCapital, flag_image,
                        countryRegion, countrySubregion, population, borders, languages);

                countries.add(country);
            }


        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }


        return countries;
    }
}
