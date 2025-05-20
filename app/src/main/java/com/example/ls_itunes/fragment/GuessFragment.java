package com.example.ls_itunes;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.room.Room;
import com.example.ls_itunes.fragment.SearchFragment;
import com.squareup.picasso.Picasso;
import java.util.Collections;
import java.util.List;

public class GuessFragment extends Fragment {
    private MediaPlayer mediaPlayer;
    private List<FavoriteSong> songs;
    private FavoriteSong currentSong;
    private int currentIndex = 0;
    private int score = 0;
    private int attempts = 0;

    private TextView textScore, textFeedback;
    private ImageView imageArtwork;
    private EditText editGuess;
    private Button buttonPlay, buttonCheck;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guess, container, false);

        // Inicializar vistas
        textScore = view.findViewById(R.id.text_score);
        imageArtwork = view.findViewById(R.id.image_artwork);
        editGuess = view.findViewById(R.id.edit_guess);
        buttonPlay = view.findViewById(R.id.button_play);
        buttonCheck = view.findViewById(R.id.button_check);
        textFeedback = view.findViewById(R.id.text_feedback);

        // Cargar canciones favoritas
        AppDatabase db = Room.databaseBuilder(
                requireContext(),
                AppDatabase.class,
                "favorites-db"
        ).allowMainThreadQueries().build();

        songs = db.favoriteDao().getAll();
        Collections.shuffle(songs);

        // Configurar botones
        buttonPlay.setOnClickListener(v -> playCurrentSong());
        buttonCheck.setOnClickListener(v -> checkAnswer());

        updateUI();

        return view;
    }

    private void playCurrentSong() {
        if (songs.isEmpty()) {
            Toast.makeText(getContext(), "No tens cançons favorites!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            if (mediaPlayer != null) {
                mediaPlayer.release();
            }

            currentSong = songs.get(currentIndex);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(currentSong.getPreviewUrl());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> {
                mp.start();
                Picasso.get().load(currentSong.getArtworkUrl()).into(imageArtwork);
                textFeedback.setText("");
                editGuess.setText("");
                attempts = 0;
            });
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error al reproduir", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkAnswer() {
        if (currentSong == null) {
            Toast.makeText(getContext(), "Prem ▶ primer", Toast.LENGTH_SHORT).show();
            return;
        }

        String userGuess = editGuess.getText().toString().trim();
        if (userGuess.isEmpty()) {
            textFeedback.setText("Escriu el nom de la cançó!");
            return;
        }

        attempts++;

        if (userGuess.equalsIgnoreCase(currentSong.getTrackName())) {
            score++;
            textFeedback.setText("✅ Correcte! És: " + currentSong.getTrackName());

            if (score >= 3) {
                textFeedback.append("\n🎉 Has encertat 3 cançons! Tornant al menú...");
                buttonPlay.setEnabled(false);
                buttonCheck.setEnabled(false);
                if (mediaPlayer != null) mediaPlayer.stop();

                new Handler().postDelayed(() -> {
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new SearchFragment())
                            .commit();
                }, 2000);
            } else {
                // Esperar 1 segon abans de passar a la següent cançó
                new Handler().postDelayed(() -> {
                    currentIndex = (currentIndex + 1) % songs.size();
                    playCurrentSong(); // Reproduce la següent automàticament
                }, 1000);
            }

        } else if (attempts >= 3) {
            textFeedback.setText("❌ Has fallat. Era: " + currentSong.getTrackName());
            currentIndex = (currentIndex + 1) % songs.size();
            playCurrentSong();
        } else {
            textFeedback.setText("❌ Incorrecte. Intents: " + attempts + "/3");
        }

        updateUI();
    }

    private void updateUI() {
        if (!songs.isEmpty()) {
            textScore.setText("Cançons encertades: " + score + "/" + songs.size());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
