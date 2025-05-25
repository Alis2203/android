package com.example.ls_itunes;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.bumptech.glide.Glide;

public class SongDetailActivity extends AppCompatActivity {
    private static final String TAG = "SongDetailActivity";

    private MediaPlayer mediaPlayer;
    private AppDatabase db;
    private Song currentSong;


    private ImageView imageViewArtwork;
    private TextView textViewTrackName;
    private TextView textViewArtistName;
    private TextView textViewCollectionName;
    private TextView textViewReleaseDate;
    private TextView textViewTrackPrice;
    private TextView textViewGenre;

    private Button buttonPlayPreview;
    private Button buttonAddFavorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);

        Log.d(TAG, "onCreate: Iniciando SongDetailActivity");

        // Inicializar vistas
        imageViewArtwork = findViewById(R.id.image_artwork);
        textViewTrackName = findViewById(R.id.text_song_name);
        textViewArtistName = findViewById(R.id.text_artist_name);
        textViewCollectionName = findViewById(R.id.text_album_name);
        textViewReleaseDate = findViewById(R.id.text_release_date);
        textViewTrackPrice = findViewById(R.id.text_price);
        textViewGenre = findViewById(R.id.text_genre);

        buttonPlayPreview = findViewById(R.id.button_play);
        buttonAddFavorite = findViewById(R.id.button_add_favorite);


        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "favorites-db")
                .allowMainThreadQueries()
                .build();
        Log.d(TAG, "onCreate: Base de datos inicializada.");

        if (getIntent().hasExtra("song")) {
            Log.d(TAG, "onCreate: Intent tiene el extra 'song'.");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                currentSong = getIntent().getParcelableExtra("song", Song.class);
            } else {

                Song songFromExtra = getIntent().getParcelableExtra("song");
                currentSong = songFromExtra;

            }
        } else {
            Log.w(TAG, "onCreate: Intent NO tiene el extra 'song'.");
        }

        if (currentSong == null) {
            Log.e(TAG, "onCreate: El objeto Song es null. No se puede continuar.");
            Toast.makeText(this, "Error: No se pudo cargar la información de la canción.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Log.d(TAG, "onCreate: Canción recibida: " + currentSong.getTrackName());

        populateUI();

        buttonAddFavorite.setOnClickListener(v -> {
            Log.d(TAG, "onClick: Botón 'Añadir a Favorito' presionado.");
            addSongToFavorites();
        });

        buttonPlayPreview.setOnClickListener(v -> {
            Log.d(TAG, "onClick: Botón 'Reproducir Preview' presionado.");
            playSongPreview();
        });
    }

    private void populateUI() {
        if (currentSong == null) {
            Log.w(TAG, "populateUI: currentSong es null, no se puede popular la UI.");
            return;
        }

        Log.d(TAG, "populateUI: Populando UI con datos de la canción: " + currentSong.getTrackName());

        // Usar text setters seguros (verificando nulls de los datos de la canción)
        textViewTrackName.setText(currentSong.getTrackName() != null ? currentSong.getTrackName() : "N/A");
        textViewArtistName.setText(currentSong.getArtistName() != null ? currentSong.getArtistName() : "N/A");
        textViewCollectionName.setText(currentSong.getCollectionName() != null ? currentSong.getCollectionName() : "N/A");
        textViewReleaseDate.setText(currentSong.getReleaseDate() != null ? currentSong.getReleaseDate() : "N/A");
        textViewTrackPrice.setText(String.valueOf(currentSong.getTrackPrice())); // Convertir double a String
        textViewGenre.setText(currentSong.getPrimaryGenreName() != null ? currentSong.getPrimaryGenreName() : "N/A");


        if (currentSong.getArtworkUrl100() != null && !currentSong.getArtworkUrl100().isEmpty()) {
            Glide.with(this)
                    .load(currentSong.getArtworkUrl100())
                    .placeholder(R.drawable.ic_music)
                    .error(R.drawable.ic_music)
                    .into(imageViewArtwork);
        } else {
            imageViewArtwork.setImageResource(R.drawable.ic_music);
        }

        buttonPlayPreview.setEnabled(currentSong.getPreviewUrl() != null && !currentSong.getPreviewUrl().isEmpty());
    }

    private void addSongToFavorites() {
        if (currentSong == null) {
            Log.w(TAG, "addSongToFavorites: currentSong es null.");
            Toast.makeText(this, "Error: No hay información de la canción para añadir.", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "addSongToFavorites: Intentando añadir: " + currentSong.getTrackName());

        FavoriteSong favorite = new FavoriteSong(
                currentSong.getTrackName(),
                currentSong.getArtistName(),
                currentSong.getArtworkUrl100(),
                currentSong.getPreviewUrl()
        );

        if (favorite.getTrackName() == null || favorite.getArtistName() == null) {
            Log.w(TAG, "addSongToFavorites: Nombre de pista o artista es null.");
            Toast.makeText(this, "Error: Información de la canción incompleta.", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            Log.d(TAG, "addSongToFavorites (Hilo Secundario): Verificando si existe.");
            FavoriteSong existingFavorite = db.favoriteDao().getSong(favorite.getTrackName(), favorite.getArtistName());
            runOnUiThread(() -> {
                if (existingFavorite == null) {
                    Log.d(TAG, "addSongToFavorites (Hilo Principal): No existe. Insertando...");
                    new Thread(() -> {
                        db.favoriteDao().insert(favorite);
                        Log.d(TAG, "addSongToFavorites (Hilo Secundario): Insertado.");
                        runOnUiThread(() -> Toast.makeText(SongDetailActivity.this, "✅ Añadido a favoritos: " + favorite.getTrackName(), Toast.LENGTH_SHORT).show());
                    }).start();
                } else {
                    Log.d(TAG, "addSongToFavorites (Hilo Principal): Ya existe.");
                    Toast.makeText(SongDetailActivity.this, "⚠️ Ya está en favoritos: " + favorite.getTrackName(), Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    private void playSongPreview() {
        if (currentSong == null || currentSong.getPreviewUrl() == null || currentSong.getPreviewUrl().isEmpty()) {
            Log.w(TAG, "playSongPreview: No hay URL de preview.");
            Toast.makeText(this, "No hay preview disponible.", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.d(TAG, "playSongPreview: Preparando preview: " + currentSong.getPreviewUrl());

        try {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(currentSong.getPreviewUrl());
            mediaPlayer.setOnPreparedListener(mp -> {
                Log.d(TAG, "playSongPreview: MediaPlayer preparado. Reproduciendo.");
                mp.start();
                Toast.makeText(SongDetailActivity.this, "Reproduciendo preview...", Toast.LENGTH_SHORT).show();
            });
            mediaPlayer.setOnCompletionListener(mp -> {
                Log.d(TAG, "playSongPreview: Reproducción completada.");
                Toast.makeText(SongDetailActivity.this, "Preview finalizado.", Toast.LENGTH_SHORT).show();
            });
            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                Log.e(TAG, "playSongPreview: Error en MediaPlayer - what: " + what + ", extra: " + extra);
                Toast.makeText(SongDetailActivity.this, "Error al reproducir preview.", Toast.LENGTH_SHORT).show();
                return true;
            });
            mediaPlayer.prepareAsync();
            Log.d(TAG, "playSongPreview: MediaPlayer.prepareAsync() llamado.");

        } catch (Exception e) {
            Log.e(TAG, "playSongPreview: Excepción al configurar MediaPlayer.", e);
            Toast.makeText(this, "Error al iniciar preview: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            Log.d(TAG, "onPause: Pausando MediaPlayer.");
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            Log.d(TAG, "onStop: Liberando MediaPlayer.");
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: Actividad destruida.");
        if (mediaPlayer != null) {
            Log.d(TAG, "onDestroy: Liberando MediaPlayer (final).");
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}