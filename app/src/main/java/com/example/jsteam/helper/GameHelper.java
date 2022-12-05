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

//    public void volleyLoadData(){
//        this.open();
//
//        RequestQueue queue = Volley.newRequestQueue(context);
//
//        String url = "https://mocki.io/v1/6b7306e9-5c3b-4341-8efa-601bbb9b3a94";
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null,
//                        response ->{
//                            ArrayList<Game> games = new ArrayList<>();
//
//                            try {
//                                JSONArray jsonArray = response.getJSONArray("games");
//
//                                for (int i = 0 ; i < jsonArray.length() ; i++){
//                                    String name, genre, price, image, desc;
//                                    Double rating;
//
//                                    name = jsonArray.getJSONObject(i).getString("name");
//                                    genre = jsonArray.getJSONObject(i).getString("genre");
//                                    price = jsonArray.getJSONObject(i).getString("price");
//                                    image = jsonArray.getJSONObject(i).getString("image");
//                                    desc = jsonArray.getJSONObject(i).getString("description");
//                                    rating = jsonArray.getJSONObject(i).getDouble("rating");
//
//                                    games.add(
//                                            new Game(name,genre,rating.floatValue(),price,image,desc)
//                                    );
//                                }
//                                insertGames(games);
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        , error -> {
//                });
//
//        queue.add(jsonObjectRequest);
//    }
//
//    public void insertGames(ArrayList<Game> games){
//        database.execSQL("drop table if exists Game");
//        String query = "Create table Game(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, genre TEXT, rating FLOAT, price TEXT, image TEXT, description TEXT)";
//        database.execSQL(query);
//
//        for(Game g: games) {
//            String description = g.getDescription().replace("'","''");
//
//            query = "insert into Game values(null, '"+g.getName()+"' ,'"+g.getGenre()+"',"+g.getRating()+",'"+g.getPrice()+"','"+g.getImage()+"','"+description+"' )";
//
//            Log.d("DEBUG", query);
//
//
//            database.execSQL(query);
//        }
//
//        this.close();
//    }

    public ArrayList<Game> fetchGames(){
        ArrayList<Game> games = new ArrayList<Game>();

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
            }while(!cursor.isAfterLast());
        }
        cursor.close();
        return games;
    }
}
