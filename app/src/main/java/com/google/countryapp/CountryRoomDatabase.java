package com.google.countryapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CountryRoom.class}, version = 1)
public abstract class CountryRoomDatabase extends RoomDatabase {

    public abstract CountryDao countryDao();

    private static volatile CountryRoomDatabase INSTANCE;

    static CountryRoomDatabase getINSTANCE(Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (CountryRoomDatabase.class)
            {
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CountryRoomDatabase.class, "country_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
