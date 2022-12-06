package com.example.jsteam.helper;

import static com.example.jsteam.activity.LoginActivity.preference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.jsteam.activity.LoginActivity;
import com.example.jsteam.activity.MainActivity;

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
        String query = "Create table User(id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT UNIQUE, username TEXT UNIQUE,password TEXT, region TEXT, phonenumber TEXT)";
        sqLiteDatabase.execSQL(query);

        query = "Create table Review(id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, game_id INTEGER,comment TEXT)";
        sqLiteDatabase.execSQL(query);

        query = "Create table Game(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, genre TEXT, rating FLOAT, price TEXT, image TEXT, description TEXT)";
        sqLiteDatabase.execSQL(query);

        context.getSharedPreferences(preference, Context.MODE_PRIVATE).edit().clear().apply();

        goToLogin();
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

    public void goToLogin(){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
