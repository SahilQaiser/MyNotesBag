package com.sq.mynotesbag;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;
    //Constants for table Rows and Columns
    public static final String TABLE_NOTES = "notes";
    public static final String NOTE_ID = "_id";
    public static final String NOTE_TITLE = "noteTitle";
    public static final String NOTE_TEXT = "noteText";
    public static final String NOTE_CREATED = "noteCreated";

    public static final String[] ALL_COLUMNS = {
            NOTE_ID,NOTE_TITLE, NOTE_TEXT,NOTE_CREATED
    };
    //SQL to create table
    private static final String TABLE_CREATE =
            "CREATE TABLE "+TABLE_NOTES+" ("+
                    NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    NOTE_TITLE + " TEXT, "+
                    NOTE_TEXT + " TEXT, "+
                    NOTE_CREATED + " TEXT default CURRENT_TIMESTAMP"+
                    ")";
    private static final String TABLE_INSERT =
            "INSERT INTO "+TABLE_NOTES+" ("+NOTE_TITLE+","+NOTE_TEXT+")"+" VALUES ("+
                    " \"QuickFox ;)\", "+
                    " \"The Quick Brown Fox Jumps Over The Lazy Dog\" "+
                    ")";

    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_INSERT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NOTES);
        onCreate(db);
    }
}
