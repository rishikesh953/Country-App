package com.google.countryapp;

import static android.icu.text.MessagePattern.ArgType.SELECT;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CountryDao {

    @Insert
    void insertMultipleCountry(List<CountryRoom> countryRoomList);


    @Query("SELECT * FROM CountryRoom")
    List<CountryRoom> selectAll();

    @Delete
    void deleteAll(List<CountryRoom> countryRoomList);

    @Query("DELETE FROM CountryRoom")
    void deleteRoom();
}
