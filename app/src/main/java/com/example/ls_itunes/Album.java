package com.example.ls_itunes;

public class Album {
    private String collectionName;
    private String artistName;
    private double collectionPrice;
    private String artworkUrl100;

    public Album(String collectionName, String artistName, double collectionPrice, String artworkUrl100) {
        this.collectionName = collectionName;
        this.artistName = artistName;
        this.collectionPrice = collectionPrice;
        this.artworkUrl100 = artworkUrl100;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getArtistName() {
        return artistName;
    }

    public double getCollectionPrice() {
        return collectionPrice;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }
}
