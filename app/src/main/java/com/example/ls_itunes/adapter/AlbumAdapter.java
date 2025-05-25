package com.example.ls_itunes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.ls_itunes.Album;
import com.example.ls_itunes.R;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {
    private final List<Album> albumList;
    private final Context context;

    public AlbumAdapter(List<Album> albumList, Context context) {
        this.albumList = albumList;
        this.context = context;
    }

    @NonNull
    @Override
    public AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_album, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.title.setText(album.getTitle());
        holder.artist.setText(album.getArtist());

        Glide.with(context)
                .load(album.getImageUrl())
                .placeholder(R.drawable.ic_music)
                .into(holder.image);
        
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    static class AlbumViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title, artist;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.album_image);
            title = itemView.findViewById(R.id.album_title);
            artist = itemView.findViewById(R.id.album_artist);
        }
    }
}