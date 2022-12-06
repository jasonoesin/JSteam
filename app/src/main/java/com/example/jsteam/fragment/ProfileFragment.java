package com.example.jsteam.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jsteam.R;
import com.example.jsteam.databinding.FragmentProfileBinding;
import com.example.jsteam.databinding.FragmentReviewBinding;
import com.example.jsteam.helper.CurrentUser;

public class ProfileFragment extends Fragment {


    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater);

        binding.email.setText(CurrentUser.user
                .getEmail());

        binding.username.setText(CurrentUser.user
                .getUsername());

        binding.phone.setText(CurrentUser.user
                .getPhoneNumber());

        binding.region.setText(CurrentUser.user
                .getRegion());

        return binding.getRoot();
    }
}