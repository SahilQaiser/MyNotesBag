package com.sq.mynotesbag;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView list;
    private static SQLiteDatabase database;
    private Cursor cursor;
    public static final String FIRSTLAUNCH = "FIRSTLAUNCH";
    SharedPreferences pref;
    CursorAdapter cursorAdapter;
    boolean firstLaunch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getSharedPreferences(preferences_textsize.PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        boolean First = pref.getBoolean(FIRSTLAUNCH,true);
        editor.putBoolean(FIRSTLAUNCH,false);
        if(First){
            editor.apply();
        }


        DBOpenHelper helper = new DBOpenHelper(this);
        try{
            database = helper.getWritableDatabase();
        } catch (SQLiteException e){
            e.printStackTrace();
            Toast.makeText(this,"Database Not Available", Toast.LENGTH_LONG).show();
        }

//Retrieve Notes
        String[] from = {DBOpenHelper.NOTE_TITLE, DBOpenHelper.NOTE_CREATED};
        int[] to = {R.id.text1, R.id.text2};

        cursor = database.query("notes", DBOpenHelper.ALL_COLUMNS, null, null, null, null, DBOpenHelper.NOTE_CREATED + " DESC");
        cursorAdapter = new SimpleCursorAdapter(this, R.layout.listlayout, cursor, from, to, 0);

//Declaring Views
        list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(cursorAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this,NoteViewer.class);
                i.putExtra(NoteViewer.NOTE_ID,id);
                startActivity(i);
            }
        });
    }

    public void reloadList() {
        cursor = database.query("notes", DBOpenHelper.ALL_COLUMNS, null, null, null, null, DBOpenHelper.NOTE_CREATED + " DESC");
        cursorAdapter.changeCursor(cursor);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Clear All Notes
    private void deleteAllNotes() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        database.delete("notes", null, null);
                        reloadList();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Do U Want to Delete All Notes?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_create_sample:
                insertNewNote();
                break;
            case R.id.action_delete_all:
                deleteAllNotes();
                break;
            case R.id.action_preferences:
                Intent i = new Intent(MainActivity.this,Settings.class);
                startActivity(i);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    //Inserting Notes
    private void insertNewNote() {
        Intent i = new Intent(MainActivity.this,insertNote.class);
        i.putExtra(insertNote.INTENT_SOURCE,"Main");
        startActivity(i);
        reloadList();
    }

    public static void insertaNote(String noteTitle, String noteText) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.NOTE_TEXT, noteText);
        values.put(DBOpenHelper.NOTE_TITLE, noteTitle);
        database.insert("notes", null, values);
    }
    public static void insertaNote(String noteTitle, String noteText, Long id) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.NOTE_TEXT, noteText);
        values.put(DBOpenHelper.NOTE_TITLE, noteTitle);
        values.put(DBOpenHelper.NOTE_CREATED, "Aug 16");
        database.update("notes",values,"_id = ?",new String[]{Long.toString(id)});
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        database.close();
        cursor.close();
    }
}
