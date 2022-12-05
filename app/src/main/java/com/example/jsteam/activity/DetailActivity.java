package com.example.jsteam.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.jsteam.R;
import com.example.jsteam.databinding.ActivityDetailBinding;
import com.example.jsteam.helper.GameHelper;
import com.example.jsteam.model.Game;

import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private GameHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());

        helper = new GameHelper(this);

        Integer gameId = getIntent().getIntExtra("GameID", 0);

        if(gameId != 0){
            Game game = helper.fetchGame(gameId);
            binding.name.setText(game.getName());
            binding.rating.setText(game.getRating().toString());
            binding.description.setText(game.getDescription());
            binding.price.setText(game.getPrice());
            binding.genre.setText(game.getGenre());

            Executor executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() -> {
                try {
                    InputStream in = new java.net.URL(game.getImage()).openStream();
                    Bitmap bmp = BitmapFactory.decodeStream(in);

                    handler.post(() ->{
                        binding.image.setImageBitmap(bmp);
                    });

                }catch (Exception e){
                    e.printStackTrace();
                }
            });
            helper.close();
        }


        setContentView(binding.getRoot());
    }
}