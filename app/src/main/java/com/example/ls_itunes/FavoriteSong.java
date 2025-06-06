package com.example.ls_itunes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class FavoriteSong {
    @PrimaryKey(autoGenerate = true)
    public int id;

    private String trackName;
    private String artistName;
    private String artworkUrl;
    private String previewUrl;


    public FavoriteSong(String trackName, String artistName, String artworkUrl, String previewUrl) {
        this.trackName = trackName;
        this.artistName = artistName;
        this.artworkUrl = artworkUrl;
        this.previewUrl = previewUrl;
    }


    public String getTrackName() { return trackName; }
    public String getArtistName() { return artistName; }
    public String getArtworkUrl() { return artworkUrl; }
    public String getPreviewUrl() { return previewUrl; }
}