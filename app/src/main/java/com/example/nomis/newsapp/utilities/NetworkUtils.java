package com.example.nomis.newsapp.utilities;

/**
 * Created by Nomis on 6/21/2017.
 */

import android.net.Uri;
import android.nfc.Tag;
import android.util.Log;

import com.example.nomis.newsapp.model.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
public class NetworkUtils {
    //https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=
    //9afb25d9435f4779934d24d24cb2cc33
    public static final String BASE_URL = "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest";
    public static final String PARAM_QUERY = "source";
    public static final String PARAM_SORT = "sortBy";
    public static final String sortBy0 = "latest";
    public static final String PARAM_API_KEY ="apiKey";
    public static final String TAG = "NetworkUtils";


    public static URL makeUrl(){
        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_API_KEY,"9afb25d9435f4779934d24d24cb2cc33")
                .build();


        URL url = null;
        try{
            String urlString = uri.toString();
            Log.d(TAG, "Url: " + urlString);
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
//parse the data and put into a object
    public static ArrayList<NewsItem> parseJson(String json) throws JSONException{
        ArrayList<NewsItem> parsedData = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        JSONArray articles = main.getJSONArray("articles");

        for(int i = 0; i < articles.length(); i++){
            JSONObject article = articles.getJSONObject(i);
            String author = article.getString("author");
            String title = article.getString("title");
            String description = article.getString("description");
            String url = article.getString("url");
            String urlToImage = article.getString("urlToImage");
            String publishedAt = article.getString("publishedAt");
            //NewsItem news = new NewsItem(author, title, description, url,urlToImage, publishedAt);
            parsedData.add(new NewsItem(author, title, description, url,urlToImage, publishedAt));
        }
        return parsedData;
    }
}

