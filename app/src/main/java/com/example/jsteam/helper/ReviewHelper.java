package com.example.jsteam.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.jsteam.model.Game;
import com.example.jsteam.model.Review;
import com.example.jsteam.model.User;

import java.util.ArrayList;

public class ReviewHelper {
    private static final String database_table = "Review";
    private final Context context;
    private DatabaseHelper database_helper;
    public SQLiteDatabase database;

    public ReviewHelper(Context context){
        this.context = context;
    }

    public void open() throws SQLException{
        database_helper = new DatabaseHelper(context);
        database = database_helper.getWritableDatabase();
    }

    public void close() throws SQLException{
        database_helper.close();
    }

    public ArrayList<Review> fetchReviews(Integer user_id){
        ArrayList<Review> reviews = new ArrayList<>();

        String query = "select * from Review where user_id = "+user_id+"";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        if(cursor.getCount()>0){
            do {
                Integer tempId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                Integer tempUserId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                Integer tempGameId = cursor.getInt(cursor.getColumnIndexOrThrow("game_id"));
                String tempComment = cursor.getString(cursor.getColumnIndexOrThrow("comment"));

                reviews.add(
                    new Review(tempId,tempUserId, tempGameId, tempComment)
                );
                cursor.moveToNext();
            }while(!cursor.isAfterLast());
        }
        cursor.close();
        return reviews;
    }

    public Review fetchReview(Integer gameId, Integer user_id){
        this.open();

        String query = "select * from Review where game_id = "+gameId+" and user_id = "+user_id+"";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        if(cursor.getCount()>0){
            do {

                Integer tempId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                Integer tempUserId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                Integer tempGameId = cursor.getInt(cursor.getColumnIndexOrThrow("game_id"));
                String tempComment = cursor.getString(cursor.getColumnIndexOrThrow("comment"));
                cursor.close();
                return new Review(tempId,tempUserId, tempGameId, tempComment);
            }while(!cursor.isAfterLast());
        }

        cursor.close();
        return null;
    }

    public void postReview(Game game, String comment){
        String query = "insert into Review values(null, '"+
                CurrentUser.user.getId()
                +"' ,'"+game.getId()+"','"+comment+"')";

        Cursor inserted = database.rawQuery(query, null);

        int _idLastInsertedRow = 0;
        if (inserted != null) {
            try {
                if (inserted.moveToFirst()) {
                    _idLastInsertedRow = inserted.getInt(0);
                }
            } finally {
                inserted.close();
            }
        }
    }

    public void updateReview(Game game, String comment){
        String query = "update Review set comment = '"+comment+"' where game_id = '"+game.getId()+"' and user_id = '"+CurrentUser.user
                .getId()+"'";

        database.execSQL(query);
    }


    public void deleteReview(Game game){
        String query = "delete from Review where game_id = '"+game.getId()+"' and user_id = '"+CurrentUser.user
                .getId()+"'";

        database.execSQL(query);
    }
}
