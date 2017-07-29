package com.example.nomis.newsapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.example.nomis.newsapp.data.Contract;

/**
 * Created by Nomis on 6/29/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ArticleHolder>{
    private Cursor cursor;
    private ItemClickListener listener;
    private Context context;

    private NewsAdapter(){}
    public NewsAdapter(Cursor cursor, ItemClickListener listener){
        this.cursor = cursor;
        this.listener = listener;
    }

    // allows user to click on articles
    public interface ItemClickListener {
        void onItemClick(Cursor cursor, int clickedItemIndex);
    }


//creates viewsholders for the data
    @Override
    public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType){
        this.context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately= false;

        View view = inflater.inflate(R.layout.article, parent, shouldAttachToParentImmediately);
        ArticleHolder holder = new ArticleHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ArticleHolder holder, int position){

        holder.bind(position);
    }

    @Override
    public int getItemCount(){

        return cursor.getCount();
    }

//get the data and set it to the viewholders
    class ArticleHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView description;
        TextView publishedAt;
        ImageView image;
        //constructor for your your views
        ArticleHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.title_tview);
            description = (TextView) view.findViewById(R.id.description_tview);
            publishedAt = (TextView) view.findViewById(R.id.publishedAT_tview);
            image =(ImageView) view.findViewById(R.id.image_tview);
            view.setOnClickListener(this);
        }
        //set data into your views
        public void bind(int position){
            cursor.moveToPosition(position);
            title.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_TITLE)));
            description.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_DESCRIPTION)));
            publishedAt.setText(cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_PUBLISHEDAT)));
            String url = cursor.getString(cursor.getColumnIndex(Contract.TABLE_ARTICLES.COLUMN_NAME_IMG));

            if(url != null){
                Picasso.with(context).load(url).into(image);
            }
        }
        //get the position/location of items that were click
        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(cursor, pos);
    }
    }

}
