package com.google.countryapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CountryRoom")
public class CountryRoom {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "countryName")
    private String countryName;


    @ColumnInfo(name = "capitalName")
    private String capitalName;


    @ColumnInfo(name = "flagPng")
    private String flagPng;


    @ColumnInfo(name = "countryRegion")
    private String countryRegion;


    @ColumnInfo(name = "subRegion")
    private String subRegion;


    @ColumnInfo(name = "population")
    private long population;


    @ColumnInfo(name = "borders")
    private String borders;


    @ColumnInfo(name = "languages")
    private String languages;

    public CountryRoom(String countryName, String capitalName, String flagPng, String countryRegion, String subRegion, long population, String borders, String languages) {
        this.countryName = countryName;
        this.capitalName = capitalName;
        this.flagPng = flagPng;
        this.countryRegion = countryRegion;
        this.subRegion = subRegion;
        this.population = population;
        this.borders = borders;
        this.languages = languages;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCapitalName() {
        return capitalName;
    }

    public void setCapitalName(String capitalName) {
        this.capitalName = capitalName;
    }

    public String getFlagPng() {
        return flagPng;
    }

    public void setFlagPng(String flagPng) {
        this.flagPng = flagPng;
    }

    public String getCountryRegion() {
        return countryRegion;
    }

    public void setCountryRegion(String countryRegion) {
        this.countryRegion = countryRegion;
    }

    public String getSubRegion() {
        return subRegion;
    }

    public void setSubRegion(String subRegion) {
        this.subRegion = subRegion;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public String getBorders() {
        return borders;
    }

    public void setBorders(String borders) {
        this.borders = borders;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    @Override
    public String toString() {
        return "CountryRoom{" +
                "uid=" + uid +
                ", countryName='" + countryName + '\'' +
                ", capitalName='" + capitalName + '\'' +
                ", flagPng='" + flagPng + '\'' +
                ", countryRegion='" + countryRegion + '\'' +
                ", subRegion='" + subRegion + '\'' +
                ", population=" + population +
                ", borders='" + borders + '\'' +
                ", languages='" + languages + '\'' +
                '}';
    }
}
