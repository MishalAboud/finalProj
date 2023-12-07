package com.example.finalproj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "articlesDatabase";
    private static final int DATABASE_VERSION = 2; // Incremented version

    private static final String TABLE_ARTICLES = "articles";
    private static final String KEY_ARTICLE_ID = "id";
    private static final String KEY_ARTICLE_TITLE = "title";
    private static final String KEY_ARTICLE_URL = "url";
    private static final String KEY_ARTICLE_HDURL = "hdUrl";
    private static final String KEY_ARTICLE_EXPLANATION = "explanation";
    private static final String KEY_ARTICLE_DATE = "date";
    private static final String KEY_ARTICLE_SECTION_NAME = "sectionName";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ARTICLES_TABLE = "CREATE TABLE " + TABLE_ARTICLES + "("
                + KEY_ARTICLE_ID + " INTEGER PRIMARY KEY,"
                + KEY_ARTICLE_TITLE + " TEXT,"
                + KEY_ARTICLE_URL + " TEXT,"
                + KEY_ARTICLE_HDURL + " TEXT,"
                + KEY_ARTICLE_EXPLANATION + " TEXT,"
                + KEY_ARTICLE_DATE + " TEXT,"
                + KEY_ARTICLE_SECTION_NAME + " TEXT" + ")";
        db.execSQL(CREATE_ARTICLES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Implement upgrade logic if needed, like adding new columns
        }
    }

    public void addArticle(Article article) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ARTICLE_TITLE, article.getTitle());
        values.put(KEY_ARTICLE_URL, article.getUrl());
        values.put(KEY_ARTICLE_HDURL, article.getHdUrl());
        values.put(KEY_ARTICLE_EXPLANATION, article.getExplanation());
        values.put(KEY_ARTICLE_DATE, article.getDate());
        values.put(KEY_ARTICLE_SECTION_NAME, article.getSectionName());

        db.insert(TABLE_ARTICLES, null, values);
        db.close();
    }

    public ArrayList<Article> getAllArticles() {
        ArrayList<Article> articles = new ArrayList<>();
        String ARTICLES_SELECT_QUERY = "SELECT * FROM " + TABLE_ARTICLES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(ARTICLES_SELECT_QUERY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    int titleIndex = cursor.getColumnIndex(KEY_ARTICLE_TITLE);
                    int urlIndex = cursor.getColumnIndex(KEY_ARTICLE_URL);
                    int hdUrlIndex = cursor.getColumnIndex(KEY_ARTICLE_HDURL);
                    int explanationIndex = cursor.getColumnIndex(KEY_ARTICLE_EXPLANATION);
                    int dateIndex = cursor.getColumnIndex(KEY_ARTICLE_DATE);
                    int sectionNameIndex = cursor.getColumnIndex(KEY_ARTICLE_SECTION_NAME);

                    if (titleIndex != -1 && urlIndex != -1 && hdUrlIndex != -1 && explanationIndex != -1 && dateIndex != -1 && sectionNameIndex != -1) {
                        Article newArticle = new Article(
                                cursor.getString(titleIndex),
                                cursor.getString(urlIndex),
                                cursor.getString(hdUrlIndex),
                                cursor.getString(explanationIndex),
                                cursor.getString(dateIndex),
                                cursor.getString(sectionNameIndex)
                        );
                        articles.add(newArticle);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return articles;
    }

    public void deleteArticle(Article article) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ARTICLES, KEY_ARTICLE_TITLE + " = ?", new String[]{String.valueOf(article.getTitle())});
        db.close();
    }
}
