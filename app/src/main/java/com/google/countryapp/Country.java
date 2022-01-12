package com.google.countryapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

//@Entity(tableName = "CountryTable")
public class Country {


//    @PrimaryKey(autoGenerate = true)
    private int uid;

//    @ColumnInfo(name = "name")
    private String name;
//    @ColumnInfo(name = "capital")
    private String capital;
//    @ColumnInfo(name = "region")
    private String region;
//    @ColumnInfo(name = "subregion")
    private String subRegion;
//    @ColumnInfo(name = "flagImage")
    private String flagImage;
//    @ColumnInfo(name = "borders")
    private String borders;
//    @ColumnInfo(name = "language")
    private String language;

    public String getLanguage() {
        return language;
    }



    public String getBorders() {
        return borders;
    }

    public Long getPopulation() {
        return population;
    }

    public String getFlagImage() {
        return flagImage;
    }


    Long population;

    public Country(String name, String capital, String region, String subRegion, Long population,
     String flagImage, String borders, String language) {
        this.name = name;
        this.capital = capital;
        this.region = region;
        this.subRegion = subRegion;
        this.population = population;
        this.flagImage = flagImage;
        this.borders = borders;
        this.language = language;
    }

    public String getName() {
        return name;
    }



    public String getCapital() {
        return capital;
    }



    public String getRegion() {
        return region;
    }



    public String getSubRegion() {
        return subRegion;
    }


}
