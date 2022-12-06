package com.example.jsteam.fragment;

import static com.example.jsteam.activity.LoginActivity.preference;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jsteam.R;
import com.example.jsteam.activity.LoginActivity;
import com.example.jsteam.activity.MainActivity;
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

        binding.logout.setOnClickListener(event -> {
            DialogInterface.OnClickListener dialogClickListener;

            dialogClickListener = (dialog, which) -> {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        getContext().getSharedPreferences(preference, Context.MODE_PRIVATE).edit().clear().apply();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        CurrentUser.user = null;
                        startActivity(intent);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();

                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Log out from this account ?")
                    .setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener)
                    .show();
        });


        return binding.getRoot();
    }
}