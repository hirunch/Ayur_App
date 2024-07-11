package com.s22010120.ayur;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    //define database name
    public static final String DATABASE_NAME = "User.db";
    //define table name
    public static final String TABLE_NAME = "user_table";

    //define column names
    public static final String COL_1 = "ID";
    public static final String COL_2 = "FIRSTNAME";
    public static final String COL_3 = "LASTNAME";
    public static final String COL_4 = "EMAIL";
    public static final String COL_5 = "PASSWORD";
    public static final String COL_6 = "ISADMIN";
    private Context context;

    // Constructor for DatabaseHelper class, Initializes the database with the specified name and version
    public DatabaseHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
        this.context = context;
    }

    //Creates the table specified by TABLE_NAME with columns ID, FIRSTNAME, LASTNAME, EMAIL, PASSWORD, and ISADMIN.
    @Override
    public void onCreate(SQLiteDatabase db) {
        //create sqlite query
        db.execSQL("create table "+ TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT , " +
                "FIRSTNAME TEXT, LASTNAME TEXT, EMAIL TEXT, PASSWORD TEXT, ISADMIN BOOLEAN)");

    }

    //Drops the existing table specified by TABLE_NAME if it exists and calls onCreate() to recreate it
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }

    //insert data base user data
    public boolean dataInsert(String firstName, String lastName, String email, String password, boolean isAdmin){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, firstName);
        contentValues.put(COL_3, lastName);
        contentValues.put(COL_4, email);
        contentValues.put(COL_5, password);
        contentValues.put(COL_6, isAdmin);

        long result = db.insert(TABLE_NAME,null, contentValues);
         if(result == -1){
             return false;
         }else{
             return true;
         }

    }

    public boolean checkAdmin(String isChecked){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor  cursor = db.rawQuery("select * from "+ TABLE_NAME +" where ischecked = ? "
                , new String[]{isChecked} );

        if (cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }

    //check login user to email and password
    public boolean checkLogin(String email, String password){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_NAME +" where email = ? " +
                "and password = ?" , new String[]{email,password} );
        if(cursor.getCount()> 0){
            return true;
        }else {
            return false;
        }
    }


    //user reset password validate in id
    public boolean resetPassword(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_4, email );
        contentValues.put(COL_5, password);

        long result = db.update(TABLE_NAME, contentValues, "EMAIL = ?", new String[] {email});
        if(result > 0){
            return true;

        }else{
            return false;
        }
    }

    //view user data
    public Cursor getUserData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ TABLE_NAME ,null);
        return cursor;
    }


    //delete user data
    public Integer DeleteAccount(String email, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "email = ? and password = ?", new String[] {email, password});

    }

    //update user data
    public boolean UpdateUserData(String firstName, String lastName, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, firstName);
        contentValues.put(COL_3, lastName);
        contentValues.put(COL_4, email);

        db.update(TABLE_NAME, contentValues, "EMAIL = ?", new String[] {email});

        return true;

    }
}
