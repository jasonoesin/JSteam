package com.example.jsteam.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jsteam.R;
import com.example.jsteam.databinding.FragmentAboutUsBinding;
import com.example.jsteam.databinding.FragmentReviewBinding;

public class ReviewFragment extends Fragment {


    FragmentReviewBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentReviewBinding.inflate(inflater);

        return binding.getRoot();
    }
}