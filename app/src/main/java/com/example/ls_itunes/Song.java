package com.example.ls_itunes;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public  class Song implements Parcelable {
    @SerializedName("trackName")
    private String trackName;

    @SerializedName("artistName")
    private String artistName;

    @SerializedName("collectionName")
    private String collectionName;

    @SerializedName("collectionType")
    private String collectionType;

    @SerializedName("artworkUrl100")
    private String artworkUrl100;

    @SerializedName("previewUrl")
    private String previewUrl;

    @SerializedName("primaryGenreName")
    private String primaryGenreName;

    @SerializedName("releaseDate")
    private String releaseDate;

    @SerializedName("trackPrice")
    private double trackPrice;

    // Constructor vacío (requerido por GSON)
    public Song() {}

    // Setters
    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
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

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setTrackPrice(double trackPrice) {
        this.trackPrice = trackPrice;
    }

    // Getters
    public String getTrackName() {
        return trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getCollectionType() {
        return collectionType;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getTrackPrice() {
        return trackPrice;
    }

    // Parcelable
    protected Song(Parcel in) {
        trackName = in.readString();
        artistName = in.readString();
        collectionName = in.readString();
        collectionType = in.readString();
        artworkUrl100 = in.readString();
        previewUrl = in.readString();
        primaryGenreName = in.readString();
        releaseDate = in.readString();
        trackPrice = in.readDouble();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(trackName);
        parcel.writeString(artistName);
        parcel.writeString(collectionName);
        parcel.writeString(collectionType);
        parcel.writeString(artworkUrl100);
        parcel.writeString(previewUrl);
        parcel.writeString(primaryGenreName);
        parcel.writeString(releaseDate);
        parcel.writeDouble(trackPrice);
    }

}

