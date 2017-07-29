package com.example.nomis.newsapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nomis.newsapp.model.NewsItem;

import java.util.ArrayList;
import static com.example.nomis.newsapp.data.Contract.TABLE_ARTICLES.*;
/**
 * Created by Nomis on 7/28/2017.
 */

public class DBUtils {
    //grabs data from the database for the cursor
    public static Cursor getAll(SQLiteDatabase db) {
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                COLUMN_NAME_PUBLISHEDAT+ " DESC"
        );
        return cursor;
    }
    // inserting data into the database
    public static void bulkInsert(SQLiteDatabase db, ArrayList<NewsItem> articles) {

        db.beginTransaction();
        try {
            for (NewsItem a : articles) {
                ContentValues cv = new ContentValues();
                cv.put(COLUMN_NAME_AUTHOR, a.getAuthor());
                cv.put(COLUMN_NAME_TITLE, a.getTitle());
                cv.put(COLUMN_NAME_DESCRIPTION, a.getDescription());
                cv.put(COLUMN_NAME_WEBURL, a.getUrl());
                cv.put(COLUMN_NAME_IMG, a.getImage());
                cv.put(COLUMN_NAME_PUBLISHEDAT, a.getPublishedAt());
                db.insert(TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
    //clears the database
    public static void deleteAll(SQLiteDatabase db) {

        db.delete(TABLE_NAME, null, null);
    }

}
