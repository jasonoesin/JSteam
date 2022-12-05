package com.example.jsteam.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.jsteam.model.User;

public class UserHelper {
    private static final String database_table = "User";
    private final Context context;
    private DatabaseHelper database_helper;
    private SQLiteDatabase database;

    public UserHelper(Context context){
        this.context = context;
    }

    public void open() throws SQLException{
        database_helper = new DatabaseHelper(context);
        database = database_helper.getWritableDatabase();
    }

    public void close() throws SQLException{
        database_helper.close();
    }

    public User login(String username, String password){
        String query = "select * from user";

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();

        if(cursor.getCount()>0){
            do {

                Integer tempId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String tempEmail = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String tempUsername = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String tempRegion = cursor.getString(cursor.getColumnIndexOrThrow("region"));
                String tempPassword = cursor.getString(cursor.getColumnIndexOrThrow("password"));

                if(username.equals(tempUsername) && password.equals(tempPassword)){
                    cursor.close();
                    return new User(tempId, tempEmail, tempUsername, tempPassword, tempRegion);
                }


            }while(!cursor.isAfterLast());
        }
        cursor.close();
        return null;
    }

    public User register(String email, String username, String region, String password){
        String query = "insert into User values(null, '"+email+"' ,'"+username+"','"+password+"','"+region+"' )";

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
        inserted.close();
        return new User(_idLastInsertedRow, email, username, password, region);
    }

}
