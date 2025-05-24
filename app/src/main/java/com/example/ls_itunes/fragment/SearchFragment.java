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
import com.example.ls_itunes.AllSongsActivity;
import com.example.ls_itunes.ITunesResponse;
import com.example.ls_itunes.ITunesService;
import com.example.ls_itunes.R;
import com.example.ls_itunes.Song;
import com.example.ls_itunes.adapter.SongAdapter;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private SongAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.songs_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SearchView searchView = view.findViewById(R.id.my_search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchSongs(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        Button btnAllSongs = view.findViewById(R.id.button_all_songs);
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
                if (response.isSuccessful()) {
                    List<Song> songs = response.body().getResults();
                    adapter = new SongAdapter(songs, getContext());
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ITunesResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
