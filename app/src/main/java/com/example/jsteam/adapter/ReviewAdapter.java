package com.example.jsteam.adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jsteam.activity.DetailActivity;
import com.example.jsteam.databinding.ItemGameBinding;
import com.example.jsteam.databinding.ItemReviewBinding;
import com.example.jsteam.helper.CurrentUser;
import com.example.jsteam.model.Game;
import com.example.jsteam.model.Review;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private ItemReviewBinding binding;
    private List<Game> games;
    private List<Review> reviews;

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(games.get(position), reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public ReviewAdapter(List<Review> reviews, List<Game> games) {
        this.reviews = reviews;
        this.games = games;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ItemReviewBinding binding;

        public ViewHolder(ItemReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Game game, Review review){
            binding.getRoot().setOnClickListener(view -> {
                Intent intent = new Intent(binding.getRoot().getContext(), DetailActivity.class);
                intent.putExtra("GameID", game.getId());
                binding.getRoot().getContext().startActivity(intent);
            });
            binding.getRoot().setBackgroundColor(Color.parseColor("#30343d"));
            binding.descLayout.setBackgroundColor(Color.parseColor("#30343d"));
            binding.name.setText(game.getName());
            binding.comment.setText(review.getComment());
            binding.username.setText(CurrentUser.user.getUsername());
            setImage(game);
        }

        public void setImage(Game game){
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
        }
    }
}
