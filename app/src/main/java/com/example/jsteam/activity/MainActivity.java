package com.example.jsteam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.jsteam.R;
import com.example.jsteam.databinding.ActivityMainBinding;
import com.example.jsteam.fragment.AboutUsFragment;
import com.example.jsteam.fragment.HomeFragment;
import com.example.jsteam.fragment.LocationFragment;
import com.example.jsteam.fragment.ReviewFragment;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        replaceFragment(new HomeFragment());

        binding.bottomNavbar.setOnItemSelectedListener(event -> {
            switch (event.getItemId()){
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.review:
                    replaceFragment(new ReviewFragment());
                    break;
                case R.id.about_us:
                    replaceFragment(new LocationFragment());
                    break;

            }
            return true;
        });

        setContentView(binding.getRoot());
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}