package com.example.jsteam.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.jsteam.databinding.ActivityRegisterBinding;
import com.example.jsteam.helper.UserHelper;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private UserHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        helper = new UserHelper(this);

        helper.open();


        // To Login Page
        binding.toLogin.setOnClickListener(event -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        // Register
        binding.register.setOnClickListener(event -> {
            String username, region, email, password, confirm, phone;

            username = String.valueOf(binding.username.getText());
            region = String.valueOf(binding.region.getText());
            email = String.valueOf(binding.email.getText());
            password = String.valueOf(binding.password.getText());
            confirm = String.valueOf(binding.confirmPassword.getText());
            phone = String.valueOf(binding.phone.getText());


            if(username.equals("") || region.equals("") || email.equals("") || password.equals("") || confirm.equals("") || phone.equals("")){
                Toast toast = Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }else if(!password.equals(confirm)){
                Toast toast = Toast.makeText(getApplicationContext(), "Password doesn't match", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }else if(!validateUsername(username)){
                return;
            }else if(!validatePassword(password)){
                return;
            }else if(!validateEmail(email)){
                Toast toast = Toast.makeText(getApplicationContext(), "Email must ends with .com !", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            helper.register(email, username, region, password, phone);

            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        setContentView(binding.getRoot());
    }

    public boolean validateUsername(String username){

        if(!helper.checkUnique(username)){
            Toast toast = Toast.makeText(getApplicationContext(), "Username is not unique !", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        for (int i = 0; i < username.length(); i++)
        {
            char c = username.charAt(i);
            if (!(c >= 'A' && c <= 'Z') &&
                    !(c >= 'a' && c <= 'z') &&
                    !(c >= '0' && c <= '9')) {
                Toast toast = Toast.makeText(getApplicationContext(), "Username must be alphanumeric !", Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }
        }
        return true;
    }

    public boolean validatePassword(String password){
        if(password.length() < 5){
            Toast toast = Toast.makeText(getApplicationContext(), "Password length must be greater than 5", Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }

        for (int i = 0; i < password.length(); i++)
        {
            char c = password.charAt(i);
            if (!(c >= 'A' && c <= 'Z') &&
                    !(c >= 'a' && c <= 'z') &&
                    !(c >= '0' && c <= '9')) {
                Toast toast = Toast.makeText(getApplicationContext(), "Password must be alphanumeric !", Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }
        }
        return true;
    }

    public boolean validateEmail(String email) {
        return email.endsWith(".com");
    }

    @Override
    public void onDestroy() {
        helper.close();
        super.onDestroy();
    }
}