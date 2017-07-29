package com.example.nomis.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.nomis.newsapp.data.Contract;
import com.example.nomis.newsapp.data.DBUtils;
import com.example.nomis.newsapp.data.DBHelper;
//import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Void>, NewsAdapter.ItemClickListener {
    private ProgressBar progress;
    private RecyclerView rv;
    private NewsAdapter adapter;
    private Cursor cursor;
    private SQLiteDatabase db;
    private final static String TAG = "Main";
    private static final int NEWS_LOADER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //checks for first time setup
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirst = prefs.getBoolean("isFirst", true);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        //using layoutmanager for the recyclerview pieces
        rv = (RecyclerView) findViewById(R.id.recyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));


        if (isFirst) {
            load();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("isFirst", false);
            editor.commit();
        }
        //update the app every minute
        ScheduleUtil.scheduleRefresh(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        db = new DBHelper(MainActivity.this).getReadableDatabase();//database is called
        cursor = DBUtils.getAll(db); //cursor gets the data
        adapter = new NewsAdapter(cursor, this);//adapter gets the data from the cursor
        rv.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
        cursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //handles events when search is clicked
    //check this search
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemNumber = item.getItemId();
        if (itemNumber == R.id.search) {
            load();
        }
        return true;
    }

    //insert error message stuff here


    //create a new loader
    @Override
    public Loader<Void> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<Void>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progress.setVisibility(View.VISIBLE);
            }

            @Override
            public Void loadInBackground() {
                RefreshTask.refreshArticles(MainActivity.this);
                return null;
            }

        };
    }

    //send data to the adapter
    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        progress.setVisibility(View.GONE);
        db = new DBHelper(MainActivity.this).getReadableDatabase();
        cursor = DBUtils.getAll(db);

        adapter = new NewsAdapter(cursor, this);
        rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {
    }
//when user clicks in an item they can open that item in a browser
    @Override
    public void onItemClick(Cursor cursor, int clickedItemIndex) {
        cursor.moveToPosition(clickedItemIndex);
        String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_WEBURL));
        Log.d(TAG, String.format("Url %s", url));

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
//create a load manager
    public void load() {
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();

    }
}

