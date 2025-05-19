package com.example.ls_itunes;  // Paquete corregido

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteSong.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavoriteDao favoriteDao();
}