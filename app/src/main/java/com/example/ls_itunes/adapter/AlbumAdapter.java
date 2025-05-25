package com.example.ls_itunes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ls_itunes.Album;
import com.example.ls_itunes.R;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {
    private List<Album> albumList;
    private Context context;

    public AlbumAdapter(List<Album> albums, Context ctx) {
        this.albumList = albums;
        this.context = ctx;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_album, parent, false);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.albumTitle.setText(album.getCollectionName());
        holder.artistName.setText(album.getArtistName());
        Glide.with(context).load(album.getArtworkUrl100()).into(holder.albumCover);
    }

    @Override
    public int getItemCount() {
        return albumList != null ? albumList.size() : 0;
    }

    public static class AlbumViewHolder extends RecyclerView.ViewHolder {
        ImageView albumCover;
        TextView albumTitle;
        TextView artistName;

        public AlbumViewHolder(View itemView) {
            super(itemView);
            albumCover = itemView.findViewById(R.id.album_image);
            albumTitle = itemView.findViewById(R.id.album_title);
            artistName = itemView.findViewById(R.id.album_artist);
        }
    }
}