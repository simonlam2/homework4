package com.example.nomis.newsapp;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.nomis.newsapp.model.NewsItem;
import com.example.nomis.newsapp.data.DBHelper;
import com.example.nomis.newsapp.data.DBUtils;
import com.example.nomis.newsapp.utilities.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
/**
 * Created by Nomis on 7/28/2017.
 */

public class RefreshTask {

    public static final String ACTION_REFRESH = "refresh";


    public static void refreshArticles(Context context) {
        ArrayList<NewsItem> result = null;
        URL url = NetworkUtils.makeUrl();

        SQLiteDatabase db = new DBHelper(context).getWritableDatabase();
        //deletes data in table and puts in new data
        try {
            DBUtils.deleteAll(db);
            String json = NetworkUtils.getResponseFromHttpUrl(url);
            result = NetworkUtils.parseJson(json);
            DBUtils.bulkInsert(db, result);

        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.close();
    }
}