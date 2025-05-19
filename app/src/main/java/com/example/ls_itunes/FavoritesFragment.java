package com.example.ls_itunes;  // Paquete corregido

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import java.util.List;

public class FavoritesFragment extends Fragment {
    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        recyclerView = view.findViewById(R.id.favorites_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Conexión a la base de datos
        AppDatabase db = Room.databaseBuilder(
                requireContext(),
                AppDatabase.class,
                "favorites-db"
        ).allowMainThreadQueries().build();  // ⚠️ Usa corrutinas en producción

        List<FavoriteSong> favorites = db.favoriteDao().getAll();
        adapter = new FavoriteAdapter(favorites);
        recyclerView.setAdapter(adapter);

        return view;
    }
}