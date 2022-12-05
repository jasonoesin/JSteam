package com.example.jsteam.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jsteam.R;
import com.example.jsteam.databinding.ActivityHomeBinding;
import com.example.jsteam.helper.GameHelper;
import com.example.jsteam.helper.UserHelper;
import com.example.jsteam.model.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {


    private ActivityHomeBinding binding;
    private GameHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());

//        helper = new GameHelper(this);
//
//        helper.volleyLoadData();

        setContentView(binding.getRoot());
    }


}