package com.example.jsteam.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jsteam.adapter.GameAdapter;
import com.example.jsteam.adapter.ReviewAdapter;
import com.example.jsteam.databinding.FragmentReviewBinding;
import com.example.jsteam.helper.CurrentUser;
import com.example.jsteam.helper.GameHelper;
import com.example.jsteam.helper.ReviewHelper;
import com.example.jsteam.model.Game;
import com.example.jsteam.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewFragment extends Fragment {


    FragmentReviewBinding binding;
    ReviewHelper helper;
    GameHelper gameHelper;
    private List<Review> reviews;
    private List<Game> games;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReviewBinding.inflate(inflater);
        helper = new ReviewHelper(getContext());
        gameHelper = new GameHelper(getContext());
        helper.open();
        gameHelper.open();

        loadData();

        return binding.getRoot();
    }

    public void loadData(){
        games = new ArrayList<>();
        reviews = helper.fetchReviews(CurrentUser.user.getId());

        for (Review r: reviews) {
            games.add(gameHelper.fetchGame(r.getGame_id()));
        }

        if(reviews.size() != 0){
            binding.noReview.setVisibility(View.INVISIBLE);
        }

        Log.d("PRINT", games.size()+" - " + reviews.size());

        ReviewAdapter adapter = new ReviewAdapter(reviews, games);
        binding.rvReviews.setAdapter(adapter);
        binding.rvReviews.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onDestroy() {
        gameHelper.close();
        helper.close();
        super.onDestroy();
    }
}