package com.example.ls_itunes;  // Paquete corregido

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class SongDetailActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);

        // Inicializa la base de datos
        db = Room.databaseBuilder(this, AppDatabase.class, "favorites-db")
                .allowMainThreadQueries().build();

        Song song = (Song) getIntent().getSerializableExtra("song");
        Button buttonAddFavorite = findViewById(R.id.button_add_favorite);

        buttonAddFavorite.setOnClickListener(v -> {
            FavoriteSong favorite = new FavoriteSong(
                    song.getTrackName(),
                    song.getArtistName(),
                    song.getArtworkUrl100(),
                    song.getPreviewUrl()
            );

            if (db.favoriteDao().getSong(favorite.getTrackName(), favorite.getArtistName()) == null) {
                db.favoriteDao().insert(favorite);
                Toast.makeText(this, "✅ Añadido a favoritos", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "⚠️ Ya está en favoritos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) mediaPlayer.release();
    }
}