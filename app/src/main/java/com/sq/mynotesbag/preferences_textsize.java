package com.sq.mynotesbag;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class preferences_textsize extends AppCompatActivity {

    NumberPicker np_Viewer, np_Editor;
    public SharedPreferences pref;
    TextView tvfontSize_Viewer, tvfontSize_Editor;
    Button bPrefSave;
    public static final String PREF = "PREFERENCES";
    public static int fontSize;
    public static final String FONT_SIZE = "PREFERENCES_FONT_SIZE_VIEWER";
    public static final String FONT_SIZE_EDITOR = "PREFERENCES_FONT_SIZE_EDITOR";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences_textsize);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        bPrefSave = (Button)findViewById(R.id.bPrefSave);

        np_Viewer = (NumberPicker)findViewById(R.id.numberPicker_Viewer);
        np_Viewer.setMaxValue(120);
        np_Viewer.setMinValue(5);
        //np_Viewer.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        //np_Editor.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np_Editor = (NumberPicker)findViewById(R.id.numberPicker_Editor);
        np_Editor.setMaxValue(120);
        np_Editor.setMinValue(5);
        tvfontSize_Viewer   = (TextView)findViewById(R.id.tvfontSize_Viewer);
        tvfontSize_Editor   = (TextView)findViewById(R.id.tvfontSize_Editor);

        pref = getSharedPreferences(PREF,MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        int textSize = pref.getInt(FONT_SIZE,24);
        int textSize_Editor = pref.getInt(FONT_SIZE_EDITOR,24);
        //TEXT COLOR
        int color = pref.getInt(preferences_textColor.TEXTCOLOR, Color.BLACK);
        tvfontSize_Editor.setTextColor(preferences_textColor.getColorId(color));
        tvfontSize_Viewer.setTextColor(preferences_textColor.getColorId(color));
        if(pref.getBoolean(MainActivity.FIRSTLAUNCH,true)){
            tvfontSize_Editor.setTextColor(Color.BLACK);
            tvfontSize_Viewer.setTextColor(Color.BLACK);
        }
        //BACK COLOR
        color = pref.getInt(preferences_textColor.BACKCOLOR, Color.WHITE);
        LinearLayout textSize_Editor_ll = (LinearLayout)findViewById(R.id.textSize_Editor);
        LinearLayout textSize_Viewer_ll = (LinearLayout)findViewById(R.id.textSize_Viewer);
        LinearLayout ll_save_button = (LinearLayout)findViewById(R.id.LLsaveButton);
        textSize_Editor_ll.setBackgroundColor(preferences_textColor.getColorId(color));
        textSize_Viewer_ll.setBackgroundColor(preferences_textColor.getColorId(color));
        ll_save_button.setBackgroundColor(preferences_textColor.getColorId(color));
        /*if(pref.getBoolean(MainActivity.FIRSTLAUNCH,true)){
            textSize_Editor_ll.setBackgroundColor(Color.WHITE);
            textSize_Viewer_ll.setBackgroundColor(Color.WHITE);
            ll_save_button.setBackgroundColor(Color.WHITE);
        }*/

        tvfontSize_Viewer.setTextSize(textSize);
        tvfontSize_Editor.setTextSize(textSize_Editor);
        np_Viewer.setValue(textSize);
        np_Editor.setValue(textSize_Editor);

        np_Viewer.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tvfontSize_Viewer.setTextSize(newVal);
            }
        });
        np_Editor.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                tvfontSize_Editor.setTextSize(newVal);
            }
        });

        bPrefSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int textSize_Viewer = np_Viewer.getValue();
                int textSize_Editor = np_Editor.getValue();
                editor.putInt(FONT_SIZE,textSize_Viewer);
                editor.putInt(FONT_SIZE_EDITOR,textSize_Editor);

                Toast.makeText(preferences_textsize.this,"New Text Sizes Saved...", Toast.LENGTH_SHORT).show();
                editor.apply();
            }
        });



    }
}
