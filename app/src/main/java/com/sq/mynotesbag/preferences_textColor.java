package com.sq.mynotesbag;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class preferences_textColor extends AppCompatActivity {

    SharedPreferences pref;
    public static final String TEXTCOLOR = "TEXTCOLOR";
    public static final String BACKCOLOR = "BACKCOLOR";

    RadioGroup rgText, rgBack;
    Button bSaveColor;
    TextView tvTextColor, tvBackColor,tvSample;
    LinearLayout color_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences_text_color);

        bSaveColor = (Button)findViewById(R.id.bSaveColor);
        rgText = (RadioGroup)findViewById(R.id.rgText);
        rgBack = (RadioGroup)findViewById(R.id.rgBack);
        tvTextColor = (TextView)findViewById(R.id.tvTextColor);
        tvBackColor = (TextView)findViewById(R.id.tvBackColor);
        tvSample = (TextView)findViewById(R.id.textColorCheck);
        color_check = (LinearLayout)findViewById(R.id.color_check);

//SharedPreferences
        pref = getSharedPreferences(preferences_textsize.PREF,MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();
        int color = pref.getInt(TEXTCOLOR,R.id.rbtBlack);
        rgText.check(color);
        tvSample.setTextColor(getColorId(color));
        color = pref.getInt(BACKCOLOR,R.id.rbbWhite);
        rgBack.check(color);
        color_check.setBackgroundColor(getColorId(color));


//OnRadioButton ChangeListener
        rgText.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tvSample.setTextColor(getColorId(checkedId));
            }
        });
        rgBack.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                color_check.setBackgroundColor(getColorId(checkedId));

            }
        });

//SAVE BUTTON
        bSaveColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int textColor = rgText.getCheckedRadioButtonId();
                int backColor = rgBack.getCheckedRadioButtonId();

                Toast.makeText(preferences_textColor.this,"Text and Background Color Settings Saved..", Toast.LENGTH_SHORT).show();

                editor.putInt(TEXTCOLOR,textColor);
                editor.putInt(BACKCOLOR,backColor);
                editor.apply();
            }
        });
    }
    public static int getColorId(int id){
        switch(id){
            case R.id.rbbBlue:
                return Color.BLUE;

            case R.id.rbbGray:
                return Color.GRAY;

            case R.id.rbbIndigo:
                return (Color.parseColor("#3F51B5"));

            case R.id.rbbOlive:
                return (Color.parseColor("#d3ffce"));

            case R.id.rbbPurple:
                return (Color.parseColor("#8a2be2"));

            case R.id.rbbWhite:
                return (Color.WHITE);

            case R.id.rbtBlack:
                return (Color.BLACK);

            case R.id.rbtBlue:
                return (Color.BLUE);

            case R.id.rbtGray:
                return (Color.GRAY);

            case R.id.rbtGreen:
                return (Color.GREEN);

            case R.id.rbtPurple:
                return (Color.parseColor("#8a2be2"));

            case R.id.rbtSteel:
                return (Color.parseColor("#C5CAE9"));
            case Color.BLACK:
                return Color.BLACK;
            case Color.WHITE:
                return Color.WHITE;

        }
        return Color.BLACK;
    }
}
