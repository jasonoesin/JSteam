package com.example.jsteam.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jsteam.R;
import com.example.jsteam.databinding.ActivityRegisterBinding;
import com.example.jsteam.helper.UserHelper;
import com.example.jsteam.model.User;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private UserHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        helper = new UserHelper(this);

        binding.toLogin.setOnClickListener(event -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        binding.register.setOnClickListener(event -> {
            String username, region, email, password, confirm;

            username = String.valueOf(binding.username.getText());
            region = String.valueOf(binding.region.getText());
            email = String.valueOf(binding.email.getText());
            password = String.valueOf(binding.password.getText());
            confirm = String.valueOf(binding.confirmPassword.getText());

            Log.d("DEBUG", username);

            if(username.equals("") || region.equals("") || email.equals("") || password.equals("") || confirm.equals("")){
                Toast toast = Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }else if(!password.equals(confirm)){
                Toast toast = Toast.makeText(getApplicationContext(), "Password doesn't match", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            helper.open();

            helper.register(email, username, region, password);

            helper.close();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        setContentView(binding.getRoot());
    }
}