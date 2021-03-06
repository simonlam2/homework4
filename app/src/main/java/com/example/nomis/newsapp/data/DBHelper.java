package com.example.nomis.newsapp.data;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/**
 * Created by Nomis on 7/28/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "articles.db";
    private static final String TAG = "dbhelper";

    public DBHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //creating the database to match api
    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryString = "CREATE TABLE " + Contract.TABLE_ARTICLES.TABLE_NAME + " ("+
                Contract.TABLE_ARTICLES._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_AUTHOR + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION + " TEXT, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_WEBURL + " TEXT, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_IMG + " TEXT, " +
                Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHEDAT + " DATE" +
                "); ";

        Log.d(TAG, "Create table SQL: " + queryString);
        db.execSQL(queryString);
    }
    //drop old tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + Contract.TABLE_ARTICLES.TABLE_NAME + " if exists;");
    }
}
