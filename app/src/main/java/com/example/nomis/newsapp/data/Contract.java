package com.example.nomis.newsapp.data;
import android.provider.BaseColumns;
/**
 * Created by Nomis on 7/28/2017.
 */

public class Contract {
    public static class TABLE_ARTICLES implements BaseColumns {
        public static final String TABLE_NAME="articles";
        public static final String COLUMN_NAME_AUTHOR="author";
        public static final String COLUMN_NAME_TITLE="title";
        public static final String COLUMN_NAME_DESCRIPTION="description";
        public static final String COLUMN_NAME_WEBURL="web_url";
        public static final String COLUMN_NAME_IMG="image";
        public static final String COLUMN_NAME_PUBLISHEDAT="published";
    }
}
