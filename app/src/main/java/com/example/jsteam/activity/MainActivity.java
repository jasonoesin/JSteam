package com.example.jsteam.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.jsteam.R;
import com.example.jsteam.databinding.ActivityMainBinding;
import com.example.jsteam.databinding.ActivityRegisterBinding;
import com.example.jsteam.helper.UserHelper;
import com.example.jsteam.model.User;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private UserHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        helper = new UserHelper(this);


        binding.toRegister.setOnClickListener(event -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        binding.login.setOnClickListener(event -> {
            String username, region, email, password, confirm;

            username = String.valueOf(binding.username.getText());
            password = String.valueOf(binding.password.getText());


            if(username.equals("")  || password.equals("")){
                Toast toast = Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            helper.open();

            User user = helper.login(username, password);

            if(user == null){
                Toast toast = Toast.makeText(getApplicationContext(), "Username and password doesn't match", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);

            helper.close();

        });



        setContentView(binding.getRoot());
    }
}