package com.example.jsteam.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jsteam.model.Game;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "JSteam";
    private static final int DATABASE_VERSION = 1;
    private static Context context;

    DatabaseHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
        DatabaseHelper.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "Create table User(id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT UNIQUE, username TEXT UNIQUE,password TEXT, region TEXT)";
        sqLiteDatabase.execSQL(query);

        query = "Create table Review(id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, game_id INTEGER,comment TEXT)";
        sqLiteDatabase.execSQL(query);

        query = "Create table Game(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, genre TEXT, rating FLOAT, price TEXT, image TEXT, description TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists User");
        sqLiteDatabase.execSQL("drop table if exists Review");
        sqLiteDatabase.execSQL("drop table if exists Game");
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
