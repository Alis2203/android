package com.example.ls_itunes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;
import android.content.Intent;
public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private List<Song> songs;
    private Context context;

    public SongAdapter(List<Song> songs, Context context) {
        this.songs = songs;
        this.context = context;
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.textSongName.setText(song.getTrackName());
        holder.textArtistName.setText(song.getArtistName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, SongDetailActivity.class);
            intent.putExtra("song", songs.get(position));
            context.startActivity(intent);
        });

        // Cargar imagen con Picasso
        Picasso.get()
                .load(song.getArtworkUrl100())
                .placeholder(R.drawable.ic_music) // Imagen por defecto
                .into(holder.imageArtwork);
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        ImageView imageArtwork;
        TextView textSongName, textArtistName;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            imageArtwork = itemView.findViewById(R.id.image_artwork);
            textSongName = itemView.findViewById(R.id.text_song_name);
            textArtistName = itemView.findViewById(R.id.text_artist_name);
        }
    }
}