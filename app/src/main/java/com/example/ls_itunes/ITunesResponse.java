package com.example.ls_itunes;



import java.util.List;

public class ITunesResponse {
    private List<Song> results;
    private List<Album> albums;

    public List<Song> getResults() {
        return results;
    }

    public void setResults(List<Song> results) {
        this.results = results;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
