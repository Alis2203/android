package com.example.ls_itunes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ITunesService {
    @GET("search")
    Call<ITunesResponse> searchSongs(
            @Query("term") String term,
            @Query("media") String media,
            @Query("entity") String entity,
            @Query("limit") int limit
    );
}