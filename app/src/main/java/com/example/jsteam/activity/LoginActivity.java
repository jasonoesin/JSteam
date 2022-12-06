package com.example.jsteam.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PackageManagerCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import com.example.jsteam.databinding.ActivityLoginBinding;
import com.example.jsteam.helper.CurrentUser;
import com.example.jsteam.helper.GameHelper;
import com.example.jsteam.helper.UserHelper;
import com.example.jsteam.model.User;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private UserHelper helper;
    private GameHelper gameHelper;




    SharedPreferences sharedpreferences;
    SmsManager smsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        helper = new UserHelper(this);
        gameHelper = new GameHelper(this);

        // Load Session
        loadSession();

        helper.open();
        gameHelper.volleyLoadData();


        // SMS
        smsManager = SmsManager.getDefault();
        int sendSmsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if(sendSmsPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }

        // -----


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



            User user = helper.login(username, password);

            if(user == null){
                Toast toast = Toast.makeText(getApplicationContext(), "Username and password doesn't match", Toast.LENGTH_SHORT);
                toast.show();
                return;
            }

            // Generate OTP
            String otp = generateOtp();

            sendSms(user, otp);

            Intent intent = new Intent(this, OtpActivity.class);
            intent.putExtra("otp", otp);

            CurrentUser.user = user;

            startActivity(intent);

        });
        setContentView(binding.getRoot());
    }

    public void sendSms(User user, String otp){

        smsManager.sendTextMessage(
                user.getPhoneNumber(),
                null,
                otp,
                null,
                null
        );
    }

    public String generateOtp(){
        String numbers = "0123456789";
        Random random = new Random();
        char[] otp = new char[4];

        for (int i = 0; i < 4; i++)
        {
            otp[i] =
                    numbers.charAt(random.nextInt(numbers.length()));
        }
        return new String(otp);
    }

    public void loadSession(){
        sharedpreferences = getSharedPreferences(preference, Context.MODE_PRIVATE);

        String id, name, region, email, phone;

        id = sharedpreferences.getString(Id, "");
        name = sharedpreferences.getString(Name, "");
        region = sharedpreferences.getString(Region, "");
        email = sharedpreferences.getString(Email, "");
        phone = sharedpreferences.getString(Phone, "");

        Log.d("PRINT", id + name + region + email);

        if(!(id.equals("") || name.equals("") || region.equals("") || email.equals("") || phone.equals(""))){
            CurrentUser.user = new User(Integer.parseInt(id),email,name,region, phone);
            Log.d("PRINT", CurrentUser.user.getUsername());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public static final String preference = "Preference" ;
    public static final String Name = "nameKey";
    public static final String Id = "idKey";
    public static final String Email = "emailKey";
    public static final String Region = "regionKey";
    public static final String Phone = "phoneKey";


    @Override
    public void onDestroy() {
        helper.close();
        gameHelper.close();
        super.onDestroy();
    }
}