package com.example.ls_itunes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {
    private List<FavoriteSong> favorites;

    public FavoriteAdapter(List<FavoriteSong> favorites) {
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_song, parent, false); // Reutiliza el mismo layout que las canciones
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        FavoriteSong song = favorites.get(position);
        holder.textSongName.setText(song.getTrackName());
        holder.textArtistName.setText(song.getArtistName());
        Picasso.get().load(song.getArtworkUrl()).into(holder.imageArtwork);
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView imageArtwork;
        TextView textSongName, textArtistName;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imageArtwork = itemView.findViewById(R.id.image_artwork);
            textSongName = itemView.findViewById(R.id.text_song_name);
            textArtistName = itemView.findViewById(R.id.text_artist_name);
        }
    }
}