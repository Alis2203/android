package com.example.ls_itunes;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable { // <-- Implement Parcelable here
    // These are example fields. Adjust them based on the actual data
    // you get from the iTunes API for a song.
    private String trackName;
    private String artistName;
    private String collectionName; // Album name
    private String artworkUrl100;  // URL for album artwork (100x100)
    private String previewUrl;     // URL for a song preview
    private String primaryGenreName;
    private double trackPrice;
    private String releaseDate;

    // It's good practice to have a constructor, especially if you use libraries like Gson
    // For Gson, a no-argument constructor is often needed, or it can use setters.
    public Song() {
    }

    // You can also have a constructor to initialize all fields
    public Song(String trackName, String artistName, String collectionName, String artworkUrl100, String previewUrl, String primaryGenreName, double trackPrice, String releaseDate) {
        this.trackName = trackName;
        this.artistName = artistName;
        this.collectionName = collectionName;
        this.artworkUrl100 = artworkUrl100;
        this.previewUrl = previewUrl;
        this.primaryGenreName = primaryGenreName;
        this.trackPrice = trackPrice;
        this.releaseDate = releaseDate;
    }

    // --- GETTERS ---
    public String getTrackName() {
        return trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public double getTrackPrice() {
        return trackPrice;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    // --- SETTERS (optional, but often needed for libraries like Gson) ---
    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public void setPrimaryGenreName(String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
    }

    public void setTrackPrice(double trackPrice) {
        this.trackPrice = trackPrice;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    // --- Parcelable Implementation ---
    protected Song(Parcel in) {
        trackName = in.readString();
        artistName = in.readString();
        collectionName = in.readString();
        artworkUrl100 = in.readString();
        previewUrl = in.readString();
        primaryGenreName = in.readString();
        trackPrice = in.readDouble();
        releaseDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(trackName);
        dest.writeString(artistName);
        dest.writeString(collectionName);
        dest.writeString(artworkUrl100);
        dest.writeString(previewUrl);
        dest.writeString(primaryGenreName);
        dest.writeDouble(trackPrice);
        dest.writeString(releaseDate);
    }

    @Override
    public int describeContents() {
        return 0; // Usually 0 unless you have FileDescriptors
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}