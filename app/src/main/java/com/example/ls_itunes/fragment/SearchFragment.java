package com.example.ls_itunes.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ls_itunes.AllAlbumsActivity;
import com.example.ls_itunes.AllSongsActivity;
import com.example.ls_itunes.Album;
import com.example.ls_itunes.ITunesResponse;
import com.example.ls_itunes.ITunesService;
import com.example.ls_itunes.R;
import com.example.ls_itunes.Song;
import com.example.ls_itunes.adapter.AlbumAdapter;
import com.example.ls_itunes.adapter.SongAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {
    private RecyclerView songsRecyclerView, albumsRecyclerView;
    private SongAdapter songAdapter;
    private AlbumAdapter albumAdapter;
    private SearchView searchView;
    private Button btnAllSongs, btnAllAlbums;
    private List<Song> songsList = new ArrayList<>();
    private List<Album> albumList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchView = view.findViewById(R.id.my_search_view);
        songsRecyclerView = view.findViewById(R.id.songs_recycler_view);
        albumsRecyclerView = view.findViewById(R.id.albums_recycler_view);
        btnAllSongs = view.findViewById(R.id.button_all_songs);
        btnAllAlbums = view.findViewById(R.id.button_all_albums);

        songsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        albumsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        songAdapter = new SongAdapter(songsList, getContext());
        albumAdapter = new AlbumAdapter(albumList, getContext());

        songsRecyclerView.setAdapter(songAdapter);
        albumsRecyclerView.setAdapter(albumAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchSongs(query);
                searchAlbums(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        btnAllSongs.setOnClickListener(v -> {
            String query = searchView.getQuery().toString().trim();
            if (!query.isEmpty()) {
                Intent intent = new Intent(requireContext(), AllSongsActivity.class);
                intent.putExtra("search_term", query);
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Introdueix un terme de cerca.", Toast.LENGTH_SHORT).show();
            }
        });

        btnAllAlbums.setOnClickListener(v -> {
            String query = searchView.getQuery().toString().trim();
            if (!query.isEmpty()) {
                Intent intent = new Intent(requireContext(), AllAlbumsActivity.class);
                intent.putExtra("search_term", query);
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), "Introdueix un terme de cerca.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void searchSongs(String term) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ITunesService service = retrofit.create(ITunesService.class);
        Call<ITunesResponse> call = service.searchSongs(term, "music", "musicTrack", 50);

        call.enqueue(new Callback<ITunesResponse>() {
            @Override
            public void onResponse(Call<ITunesResponse> call, Response<ITunesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    songsList.clear();
                    songsList.addAll(response.body().getResults());
                    songAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ITunesResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchAlbums(String term) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ITunesService service = retrofit.create(ITunesService.class);
        Call<ITunesResponse> call = service.searchAlbums(term, "music", "album", 20);

        call.enqueue(new Callback<ITunesResponse>() {
            @Override
            public void onResponse(Call<ITunesResponse> call, Response<ITunesResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    albumList.clear();
                    albumList.addAll(response.body().getAlbums());
                    albumAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ITunesResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}