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
    private static final int DATABASE_VERSION = 5;
    private static Context context;

    DatabaseHelper(Context context){
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
        DatabaseHelper.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "Create table User(id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT UNIQUE, username TEXT UNIQUE,password TEXT, region TEXT)";
        sqLiteDatabase.execSQL(query);

        query = "Create table Review(id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, games_id INTEGER,comment TEXT)";
        sqLiteDatabase.execSQL(query);

        query = "Create table Game(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, genre TEXT, rating FLOAT, price TEXT, image TEXT, description TEXT)";
        sqLiteDatabase.execSQL(query);

        volleyLoadData(sqLiteDatabase);
    }

    public void volleyLoadData(SQLiteDatabase sqLiteDatabase){

        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "https://mocki.io/v1/6b7306e9-5c3b-4341-8efa-601bbb9b3a94";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,
                        response ->{
                            ArrayList<Game> games = new ArrayList<>();

                            try {
                                JSONArray jsonArray = response.getJSONArray("games");

                                for (int i = 0 ; i < jsonArray.length() ; i++){
                                    String name, genre, price, image, desc;
                                    Double rating;

                                    name = jsonArray.getJSONObject(i).getString("name");
                                    genre = jsonArray.getJSONObject(i).getString("genre");
                                    price = jsonArray.getJSONObject(i).getString("price");
                                    image = jsonArray.getJSONObject(i).getString("image");
                                    desc = jsonArray.getJSONObject(i).getString("description");
                                    rating = jsonArray.getJSONObject(i).getDouble("rating");

                                    games.add(
                                            new Game(name,genre,rating.floatValue(),price,image,desc)
                                    );
                                }
                                insertGames(games, sqLiteDatabase);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        , error -> {
                });

        queue.add(jsonObjectRequest);
    }

    public void insertGames(ArrayList<Game> games, SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("drop table if exists Game");
        String query = "Create table Game(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, genre TEXT, rating FLOAT, price TEXT, image TEXT, description TEXT)";
        sqLiteDatabase.execSQL(query);

        for(Game g: games) {
            String description = g.getDescription().replace("'","''");

            query = "insert into Game values(null, '"+g.getName()+"' ,'"+g.getGenre()+"',"+g.getRating()+",'"+g.getPrice()+"','"+g.getImage()+"','"+description+"' )";

            Log.d("DEBUG", query);

            sqLiteDatabase.execSQL(query);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists User");
        sqLiteDatabase.execSQL("drop table if exists Review");
        sqLiteDatabase.execSQL("drop table if exists Game");
        onCreate(sqLiteDatabase);
    }
}
