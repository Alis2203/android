package com.example.ls_itunes;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ls_itunes.adapter.AlbumAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.List;

public class AllAlbumsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AlbumAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_albums);

        recyclerView = findViewById(R.id.recycler_all_albums);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String query = getIntent().getStringExtra("search_term");
        if (query != null && !query.isEmpty()) {
            fetchAlbums(query);
        } else {
            Toast.makeText(this, "No s'ha proporcionat cap cerca.", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchAlbums(String term) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ITunesService service = retrofit.create(ITunesService.class);
        Call<ITunesResponse<Album>> call = service.searchAlbums(term, "music", "album", 100);

        call.enqueue(new Callback<ITunesResponse<Album>>() {
            @Override
            public void onResponse(Call<ITunesResponse<Album>> call, Response<ITunesResponse<Album>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Album> albums = response.body().getResults();
                    adapter = new AlbumAdapter(albums, AllAlbumsActivity.this);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ITunesResponse<Album>> call, Throwable t) {
                Toast.makeText(AllAlbumsActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
