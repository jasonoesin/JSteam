package com.example.jsteam.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.jsteam.databinding.ActivityLoginBinding;
import com.example.jsteam.helper.GameHelper;
import com.example.jsteam.helper.UserHelper;
import com.example.jsteam.model.User;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private UserHelper helper;
    private GameHelper gameHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        helper = new UserHelper(this);
        gameHelper = new GameHelper(this);


        gameHelper.volleyLoadData();



        binding.toRegister.setOnClickListener(event -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        binding.login.setOnClickListener(event -> {
            String username,password;

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
                helper.close();
                return;
            }

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

            helper.close();

        });



        setContentView(binding.getRoot());
    }

    @Override
    public void onDestroy() {
        helper.close();
        gameHelper.close();
        super.onDestroy();
    }
}