package com.example.jsteam.activity;

import static com.example.jsteam.activity.LoginActivity.preference;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.example.jsteam.R;
import com.example.jsteam.databinding.ActivityDetailBinding;
import com.example.jsteam.helper.CurrentUser;
import com.example.jsteam.helper.GameHelper;
import com.example.jsteam.helper.ReviewHelper;
import com.example.jsteam.model.Game;
import com.example.jsteam.model.Review;

import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DetailActivity extends AppCompatActivity {

    private ActivityDetailBinding binding;
    private GameHelper helper;
    private ReviewHelper reviewHelper;
    private Game game;
    private Review review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());

        helper = new GameHelper(this);
        reviewHelper = new ReviewHelper(this);
        reviewHelper.open();

        Integer gameId = getIntent().getIntExtra("GameID", 0);
        game = helper.fetchGame(gameId);
        review = reviewHelper.fetchReview(gameId, CurrentUser.user.getId());

        binding.deleteButton.setEnabled(false);
        binding.deleteButton.setVisibility(View.INVISIBLE);

        reviewButtonDefault();

        applyData(game);
        setContentView(binding.getRoot());
    }

    public void reviewButtonDefault(){
        binding.reviewButton.setText("Review");
        binding.reviewButton.setOnClickListener(event -> {
            String comment = binding.reviewText.getText().toString();
            if(comment.equals("")){
                Toast toast = Toast.makeText(getApplicationContext(), "Review comment can't be empty", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }else{
                reviewHelper.postReview(game, comment);
                refresh();
            }
        });
    }

    public void refresh(){
        Intent mIntent = getIntent();
        finish();
        startActivity(mIntent);
    }

    public void applyData(Game game){
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

        if(review != null){
            binding.reviewText.setText(review.getComment());
            binding.reviewButton.setText("Update Comment");

            binding.deleteButton.setEnabled(true);
            binding.deleteButton.setVisibility(View.VISIBLE);

            binding.deleteButton.setOnClickListener(event -> {
                // Dialog Delete
                DialogInterface.OnClickListener dialogClickListener;

                dialogClickListener = (dialog, which) -> {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            reviewHelper.deleteReview(game);
                            refresh();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            dialog.dismiss();

                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to delete the reviewed comment ?")
                        .setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener)
                        .show();
            });

            binding.reviewButton.setOnClickListener(event -> {
                String comment = binding.reviewText.getText().toString();
                if(comment.equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Updated comment can't be empty", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    // Dialog Update
                    DialogInterface.OnClickListener dialogClickListener;

                    dialogClickListener = (dialog, which) -> {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                reviewHelper.updateReview(game, comment);
                                refresh();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();

                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Update the reviewed comment ?")
                            .setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener)
                            .show();
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        reviewHelper.close();
        helper.close();
        super.onDestroy();
    }
}