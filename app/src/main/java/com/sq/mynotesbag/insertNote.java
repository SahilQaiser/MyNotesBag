package com.sq.mynotesbag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class insertNote extends AppCompatActivity {
    Button edit;
    public static String INTENT_SOURCE = "INTENT_SOURCE";
    EditText eTitle,etBody;
    public String TITLE, NOTE_BODY;
    SharedPreferences pref;
    //SQLiteDatabase db;
    Cursor cursor;
    LinearLayout containerEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_note);

        containerEditor = (LinearLayout)findViewById(R.id.containerEditor);
        eTitle = (EditText)findViewById(R.id.etTitle);
        etBody = (EditText)findViewById(R.id.etBody);
        edit = (Button)findViewById(R.id.bSaveNote);
        containerEditor.animate().alpha(1f).setDuration(1500);
//TextSizes
        textSizes();
//Fonts
        changeFonts();

//Colors
        changeColors();

//Main Operations
        Intent i = getIntent();
        String intSource = i.getStringExtra(INTENT_SOURCE);
        //Toast.makeText(insertNote.this,intSource,Toast.LENGTH_SHORT).show();
        if(intSource.equals("NoteViewer")){

            final long id = i.getLongExtra(DBOpenHelper.NOTE_ID,0);
            cursor = NoteViewer.readIDData(id);
            //DBOpenHelper helper = new DBOpenHelper(this);
            //db = helper.getWritableDatabase();
            //cursor = db.query("notes",DBOpenHelper.ALL_COLUMNS,"_id=?",new String[]{Long.toString(id)},null,null,null);
            if(cursor.moveToFirst()){

                eTitle.setText(cursor.getString(1));
                etBody.setText(cursor.getString(2));
            }
            edit.setText("Save Changes");
            Toast.makeText(this,"Time : "+ System.currentTimeMillis(), Toast.LENGTH_SHORT).show();
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TITLE = eTitle.getText().toString();
                    if(TITLE.equals("")){
                        TITLE = "Untitled";
                    }
                    NOTE_BODY = etBody.getText().toString();
                    MainActivity.insertaNote(TITLE,NOTE_BODY,id);
                    Toast.makeText(insertNote.this,"Note Created...", Toast.LENGTH_SHORT).show();
                }
            });

        } else if(intSource.equals("Main")){
            Toast.makeText(insertNote.this,"Creating A New Note", Toast.LENGTH_SHORT).show();
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TITLE = eTitle.getText().toString();
                    if(TITLE.equals("")){
                        TITLE = "Untitled";
                    }
                    NOTE_BODY = etBody.getText().toString();
                    MainActivity.insertaNote(TITLE,NOTE_BODY);
                    Toast.makeText(insertNote.this,"Note Created...", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void changeColors() {
        int color = pref.getInt(preferences_textColor.TEXTCOLOR, Color.BLACK);
        etBody.setTextColor(preferences_textColor.getColorId(color));
        eTitle.setTextColor(preferences_textColor.getColorId(color));

        color = pref.getInt(preferences_textColor.BACKCOLOR, Color.WHITE);

        containerEditor.setBackgroundColor(preferences_textColor.getColorId(color));
    }

    private void changeFonts() {
        int selectedFont = pref.getInt(preferences_fontType.FONT,4);
        Typeface font = Typeface.createFromAsset(getAssets(),"Quicksand.ttf");
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
        etBody.setTypeface(font);
        eTitle.setTypeface(font);
    }

    private void textSizes() {
        pref = getSharedPreferences(preferences_textsize.PREF,MODE_PRIVATE);
        int textSize_Editor = pref.getInt(preferences_textsize.FONT_SIZE_EDITOR,24);
        etBody.setTextSize(textSize_Editor);
        eTitle.setTextSize(textSize_Editor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_viewer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings_textsize:
                Intent i = new Intent(insertNote.this,preferences_textsize.class);
                startActivity(i);
                break;
            case R.id.action_settings_textcolor:
                Intent c = new Intent(insertNote.this,preferences_textColor.class);
                startActivity(c);
                break;
            case R.id.action_settings_fontchange:
                Intent f = new Intent(insertNote.this,preferences_fontType.class);
                startActivity(f);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        changeColors();
        changeFonts();
        textSizes();
    }
}
