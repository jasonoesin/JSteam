package com.example.jsteam.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jsteam.R;
import com.example.jsteam.adapter.GameAdapter;
import com.example.jsteam.databinding.FragmentHomeBinding;
import com.example.jsteam.databinding.FragmentReviewBinding;
import com.example.jsteam.helper.GameHelper;
import com.example.jsteam.model.Game;

import java.util.List;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    private GameHelper helper;
    private List<Game> games;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        helper = new GameHelper(getContext());

        loadData();

        return binding.getRoot();
    }

    public void loadData(){
        helper.open();
        games = helper.fetchGames();

        GameAdapter adapter = new GameAdapter(games);
        binding.rvGames.setAdapter(adapter);
        binding.rvGames.setLayoutManager(new LinearLayoutManager(getContext()));

        helper.close();
    }
}