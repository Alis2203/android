package com.example.ls_itunes.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ls_itunes.AppDatabase;
import com.example.ls_itunes.FavoriteSong;
import com.example.ls_itunes.R;
import com.example.ls_itunes.Song;
import com.example.ls_itunes.SongDetailActivity;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavViewHolder> {
    private final List<FavoriteSong> favorites;
    private final Context context;

    public FavoriteAdapter(List<FavoriteSong> favorites, Context context) {
        this.favorites = favorites;
        this.context = context;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        FavoriteSong song = favorites.get(position);

        holder.title.setText(song.getTrackName());
        holder.artist.setText(song.getArtistName());

        Glide.with(context)
                .load(song.getArtworkUrl())
                .placeholder(R.drawable.ic_music)
                .error(R.drawable.ic_music)
                .into(holder.image);

        // Click para ver detalle
        holder.itemView.setOnClickListener(v -> {
            Song fullSong = new Song();
            fullSong.setTrackName(song.getTrackName());
            fullSong.setArtistName(song.getArtistName());
            fullSong.setArtworkUrl100(song.getArtworkUrl());
            fullSong.setPreviewUrl(song.getPreviewUrl());

            Intent intent = new Intent(context, SongDetailActivity.class);
            intent.putExtra("song", fullSong);
            context.startActivity(intent);
        });

        // Eliminar favorito
        holder.deleteButton.setOnClickListener(v -> {
            AppDatabase db = AppDatabase.getInstance(context);
            new Thread(() -> {
                db.favoriteDao().delete(song);
                ((android.app.Activity) context).runOnUiThread(() -> {
                    favorites.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, favorites.size());
                });
            }).start();
        });
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public static class FavViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, artist;
        ImageButton deleteButton;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.favorite_image);
            title = itemView.findViewById(R.id.favorite_song_name);
            artist = itemView.findViewById(R.id.favorite_artist);
            deleteButton = itemView.findViewById(R.id.button_delete);
        }
    }
}