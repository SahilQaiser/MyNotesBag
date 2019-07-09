package com.sq.mynotesbag;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class NoteViewer extends AppCompatActivity {

    public static final String NOTE_ID = "NOTE_ID";
    static SQLiteDatabase db;
    Cursor cursor;
    Typeface font;
    TextView note;
    TextView editNote;
    LinearLayout activity_note_viewer,backViewer,backTitle;
    Button bEdit;
    static DBOpenHelper helper;
    ContentValues values;
    SharedPreferences pref;
    long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_viewer);

        pref = getSharedPreferences(preferences_textsize.PREF,MODE_PRIVATE);
        helper = new DBOpenHelper(this);
        activity_note_viewer = (LinearLayout)findViewById(R.id.activity_note_viewer);
        activity_note_viewer.animate().alpha(1f).setDuration(1500);

        note = (TextView)findViewById(R.id.viewNote);
        editNote = (TextView) findViewById(R.id.editNote);
        bEdit = (Button)findViewById(R.id.bsave);
        values = new ContentValues();
        textSize();
//TextColor from SharedPref
        changeColor();
//Font Change
        fontChange();


       try{
           db = helper.getWritableDatabase();
           Intent intent = getIntent();
           id = intent.getLongExtra(NOTE_ID,0);
           Toast.makeText(this,"NoteId : "+id, Toast.LENGTH_SHORT).show();
           //Retrieve Selected Data from Table
           readCursor();
           bEdit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent i = new Intent(NoteViewer.this,insertNote.class);
                   i.putExtra(DBOpenHelper.NOTE_ID,id);
                   i.putExtra(insertNote.INTENT_SOURCE,"NoteViewer");
                   Toast.makeText(NoteViewer.this,"Editing Note...", Toast.LENGTH_SHORT).show();
                   startActivity(i);

               }
           });
       } catch(Exception e){
           e.printStackTrace();
           Toast.makeText(this,"Database Not Available", Toast.LENGTH_LONG).show();
       }

    }

    private void textSize() {
        int textSize = pref.getInt(preferences_textsize.FONT_SIZE,24);
        editNote.setTextSize(textSize);
    }

    private void changeColor() {
        int color = pref.getInt(preferences_textColor.TEXTCOLOR, Color.BLACK);
        editNote.setTextColor(preferences_textColor.getColorId(color));
        note.setTextColor(preferences_textColor.getColorId(color));
//Back Color from Shared Pref
        color = pref.getInt(preferences_textColor.BACKCOLOR, Color.WHITE);
        backViewer = (LinearLayout)findViewById(R.id.backViewer);
        backTitle = (LinearLayout)findViewById(R.id.backTitle);
        backTitle.setBackgroundColor(preferences_textColor.getColorId(color));
        backViewer.setBackgroundColor(preferences_textColor.getColorId(color));
    }

    private void fontChange() {
        int selectedFont = pref.getInt(preferences_fontType.FONT,4);
        switch(selectedFont){
            case 1:
                font = Typeface.createFromAsset(getAssets(),"Amatic.ttf");
                break;
            case 2:
                font = Typeface.createFromAsset(getAssets(),"black_jack.ttf");
                break;
            case 3:
                font = Typeface.createFromAsset(getAssets(),"DancingScript.ttf");
                break;
            case 4:
                font = Typeface.createFromAsset(getAssets(),"Quicksand.ttf");
                break;
            case 5:
                font = Typeface.createFromAsset(getAssets(),"school.ttf");
                break;
            case 6:
                font = Typeface.createFromAsset(getAssets(),"sensation.ttf");
                break;
            case 7:
                font = Typeface.createFromAsset(getAssets(),"SweetlyBroken.ttf");
                break;
        }
        editNote.setTypeface(font);
        note.setTypeface(font);
    }

    private void readCursor() {
        try{
            cursor = db.query("notes", DBOpenHelper.ALL_COLUMNS,"_id = ?",new String[]{Long.toString(id)},null,null,null);
            if(cursor.moveToFirst()){
                note.setText(cursor.getString(1));
                editNote.setText(cursor.getString(2));
            }
        }catch(CursorIndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    public static Cursor readIDData(long id)
    {
        db = helper.getWritableDatabase();
        Cursor cursor1;
        cursor1 = db.query("notes",DBOpenHelper.ALL_COLUMNS,"_id = ?",new String[]{Long.toString(id)},null,null,null);
        return cursor1;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_delete:
                deleteNote();
                break;
            case R.id.action_settings_textsize:
                Intent i = new Intent(NoteViewer.this,preferences_textsize.class);
                startActivity(i);
                break;
            case R.id.action_settings_textcolor:
                Intent c = new Intent(NoteViewer.this,preferences_textColor.class);
                startActivity(c);
                break;
            case R.id.action_settings_fontchange:
                Intent f = new Intent(NoteViewer.this,preferences_fontType.class);
                startActivity(f);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteNote() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        db.delete("notes","_id = ?",new String[] {Long.toString(id)});
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(NoteViewer.this);
        builder.setMessage("Do U Want to Delete This Note?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        readCursor();
        fontChange();
        changeColor();
        textSize();

    }

}

