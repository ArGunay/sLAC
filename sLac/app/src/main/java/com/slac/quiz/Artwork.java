package com.slac.quiz;

public class Artwork {

    private String author;
    private int beaconID;
    private String name;
    private int yearOfCreation;
    private String placeOfCreation;


    public Artwork() {

    }

    public Artwork(int beaconID, String author, String name, int yearOfCreation, String placeOfCreation)
    {
        this.beaconID = beaconID;
        this.name = name;
        this.author = author;
        this.yearOfCreation = yearOfCreation;
        this.placeOfCreation = placeOfCreation;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public int getYearOfCreation()
    {
        return yearOfCreation;
    }

    public String getPlaceOfCreation()
    {
        return placeOfCreation;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getBeaconID() {
        return beaconID;
    }

    public void setBeaconID(int beaconID) {
        this.beaconID = beaconID;
    }


}