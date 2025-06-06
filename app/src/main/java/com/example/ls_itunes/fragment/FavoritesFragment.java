package com.example.ls_itunes.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.ls_itunes.AppDatabase;
import com.example.ls_itunes.adapter.FavoriteAdapter;
import com.example.ls_itunes.FavoriteSong;
import com.example.ls_itunes.R;

import java.util.List;

public class FavoritesFragment extends Fragment {
    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        recyclerView = view.findViewById(R.id.favorites_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        AppDatabase db = Room.databaseBuilder(
                requireContext(),
                AppDatabase.class,
                "favorites-db"
        ).allowMainThreadQueries().build();

        List<FavoriteSong> favorites = db.favoriteDao().getAll();
        adapter = new FavoriteAdapter(favorites, requireContext());
        recyclerView.setAdapter(adapter);

        return view;
    }
}