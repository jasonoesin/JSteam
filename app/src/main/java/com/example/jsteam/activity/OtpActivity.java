package com.example.jsteam.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.jsteam.databinding.ActivityOtpBinding;
import com.example.jsteam.helper.CurrentUser;
import com.example.jsteam.model.User;

public class OtpActivity extends AppCompatActivity {

    ActivityOtpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());

        String otp = getIntent().getStringExtra("otp");



        Log.d("PRINT", otp);
        Log.d("PRINT", CurrentUser.user.getUsername());

        binding.submitBtn.setOnClickListener(event -> {

            if(otp.equals(binding.otpCode.getText().toString())){
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                saveSession(CurrentUser.user);

                startActivity(intent);
            }else{
                Toast toast = Toast.makeText(getApplicationContext(), "OTP Code doesn't match", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        setContentView(binding.getRoot());
    }

    public void saveSession(User user){
        SharedPreferences sharedpreferences = getSharedPreferences(preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(Id, user.getId().toString());
        editor.putString(Name, user.getUsername());
        editor.putString(Region, user.getRegion());
        editor.putString(Email, user.getEmail());
        editor.putString(Phone, user.getPhoneNumber());
        editor.apply();
    }

    public static final String preference = "Preference" ;
    public static final String Name = "nameKey";
    public static final String Id = "idKey";
    public static final String Email = "emailKey";
    public static final String Region = "regionKey";
    public static final String Phone = "phoneKey";
}