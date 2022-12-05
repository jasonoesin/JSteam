package com.example.jsteam.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jsteam.model.Game;
import com.example.jsteam.model.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class GameHelper {
    private static final String database_table = "Game";
    private final Context context;
    private DatabaseHelper database_helper;
    public SQLiteDatabase database;

    public GameHelper(Context context){
        this.context = context;
    }

    public void open() throws SQLException{
        database_helper = new DatabaseHelper(context);
        database = database_helper.getWritableDatabase();
    }

    public void close() throws SQLException{
        database_helper.close();
    }

    public ArrayList<Game> fetchGames(){
        ArrayList<Game> games = new ArrayList<>();

        String query = "select * from Game";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        if(cursor.getCount()>0){
            do {

                Integer tempId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String tempName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String tempGenre = cursor.getString(cursor.getColumnIndexOrThrow("genre"));
                Float tempRating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"));
                String tempPrice = cursor.getString(cursor.getColumnIndexOrThrow("price"));
                String tempImage = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                String tempDescription = cursor.getString(cursor.getColumnIndexOrThrow("description"));

                games.add(
                        new Game(tempId, tempName,tempGenre,tempRating,tempPrice,tempImage,tempDescription)
                );
                cursor.moveToNext();
            }while(!cursor.isAfterLast());
        }
        cursor.close();
        return games;
    }

    public Game fetchGame(Integer gameId){
        this.open();

        String query = "select * from Game where id = "+ gameId+"";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        if(cursor.getCount()>0){
            do {

                Integer tempId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String tempName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String tempGenre = cursor.getString(cursor.getColumnIndexOrThrow("genre"));
                Float tempRating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"));
                String tempPrice = cursor.getString(cursor.getColumnIndexOrThrow("price"));
                String tempImage = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                String tempDescription = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                cursor.close();

                return new Game(tempId, tempName,tempGenre,tempRating,tempPrice,tempImage,tempDescription);
            }while(!cursor.isAfterLast());
        }

        cursor.close();
        return null;
    }
}
