package com.example.ls_itunes;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ls_itunes.adapter.SongAdapter;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllSongsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SongAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_songs);

        recyclerView = findViewById(R.id.recycler_all_songs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String query = getIntent().getStringExtra("search_term");
        if (query != null && !query.isEmpty()) {
            fetchSongs(query);
        } else {
            Toast.makeText(this, "No s'ha proporcionat cap cerca.", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchSongs(String term) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ITunesService service = retrofit.create(ITunesService.class);
        Call<ITunesResponse> call = service.searchSongs(term, "music", "musicTrack", 100);

        call.enqueue(new Callback<ITunesResponse>() {
            @Override
            public void onResponse(Call<ITunesResponse> call, Response<ITunesResponse> response) {
                if (response.isSuccessful()) {
                    List<Song> songs = response.body().getResults();
                    adapter = new SongAdapter(songs, AllSongsActivity.this);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ITunesResponse> call, Throwable t) {
                Toast.makeText(AllSongsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
