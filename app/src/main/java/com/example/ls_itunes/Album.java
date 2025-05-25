package com.example.ls_itunes;

import com.google.gson.annotations.SerializedName;

public class Album {

    @SerializedName("collectionName")
    private String title;
    @SerializedName("collectionId")
    private int collectionId;

    public int getCollectionId() {
        return collectionId;
    }

    @SerializedName("artistName")
    private String artist;

    @SerializedName("artworkUrl100")
    private String imageUrl;

    public Album() {
    }

    public Album(String title, String artist, String imageUrl) {
        this.title = title;
        this.artist = artist;
        this.imageUrl = imageUrl;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
