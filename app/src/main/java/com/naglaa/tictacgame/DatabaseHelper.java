package com.naglaa.tictacgame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String databaseName = "Signup.dp";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "Signup.dp", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("create Table allusers(email TEXT primary key,password TEXT  )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDatabase, int i, int i1) {
        MyDatabase.execSQL("drop Table if exists allusers");

    }

    public boolean insertData(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("allusers", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("select * from allusers where email = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /*public boolean checkEmailPassword(String email,String password){
        SQLiteDatabase  MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("select * from allusers where email = ?",new String[]{email,password});
        if(cursor.getCount() > 0){return  true;}
        else {return  false;}
    }*/

  /*  public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM allusers WHERE email = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[] { email, password });
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
    }*/

    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM allusers WHERE email = ? AND password = ?";
            cursor = db.rawQuery(query, new String[]{email, password});
            return cursor.getCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }
}




