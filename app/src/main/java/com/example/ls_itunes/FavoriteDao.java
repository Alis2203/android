package com.example.ls_itunes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert
    void insert(FavoriteSong song);

    @Delete
    void delete(FavoriteSong song);

    @Query("SELECT * FROM favorites")
    List<FavoriteSong> getAll();

    @Query("SELECT * FROM favorites WHERE trackName = :trackName AND artistName = :artistName LIMIT 1")
    FavoriteSong getSong(String trackName, String artistName);
}