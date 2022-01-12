package com.google.countryapp;


import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface CountryDao {

    @Insert
    void insertCountry(CountryRoom countryRoom);

}
