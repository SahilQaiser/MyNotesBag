package com.sq.mynotesbag;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class preferences_fontType extends AppCompatActivity {

    RadioGroup rgFont;
    SharedPreferences pref;
    public static final String FONT = "FONT";
    RadioButton rb1,rb2,rb3,rb4,rb5,rb6,rb7;
    Typeface font;
    Button bSaveFont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences_font_type);

        pref = getSharedPreferences(preferences_textsize.PREF,MODE_PRIVATE);
        final SharedPreferences.Editor editor = pref.edit();

        rgFont = (RadioGroup)findViewById(R.id.rgFontType);
        rb1 = (RadioButton)findViewById(R.id.rb1);
        rb2 = (RadioButton)findViewById(R.id.rb2);
        rb3 = (RadioButton)findViewById(R.id.rb3);
        rb4 = (RadioButton)findViewById(R.id.rb4);
        rb5 = (RadioButton)findViewById(R.id.rb5);
        rb6 = (RadioButton)findViewById(R.id.rb6);
        rb7 = (RadioButton)findViewById(R.id.rb7);
        bSaveFont = (Button)findViewById(R.id.bSaveFont);


        int selectedFont = pref.getInt(FONT,4);
        Toast.makeText(this,"Font Type : "+selectedFont, Toast.LENGTH_LONG).show();
        switch (selectedFont){
            case 1:
                rgFont.check(R.id.rb1);
                break;
            case 2:
                rgFont.check(R.id.rb2);
                break;
            case 3:
                rgFont.check(R.id.rb3);
                break;
            case 4:
                rgFont.check(R.id.rb4);
                break;
            case 5:
                rgFont.check(R.id.rb5);
                break;
            case 6:
                rgFont.check(R.id.rb6);
                break;
            case 7:
                rgFont.check(R.id.rb7);
                break;
            default:
                rgFont.check(R.id.rb4);
        }
        font = Typeface.createFromAsset(getAssets(),"Quicksand.ttf");
        rb4.setTypeface(font);
        font = Typeface.createFromAsset(getAssets(),"Amatic.ttf");
        rb1.setTypeface(font);
        font = Typeface.createFromAsset(getAssets(),"school.ttf");
        rb5.setTypeface(font);
        font = Typeface.createFromAsset(getAssets(),"black_jack.ttf");
        rb2.setTypeface(font);
        font = Typeface.createFromAsset(getAssets(),"DancingScript.ttf");
        rb3.setTypeface(font);
        font = Typeface.createFromAsset(getAssets(),"sensation.ttf");
        rb6.setTypeface(font);
        font = Typeface.createFromAsset(getAssets(),"SweetlyBroken.ttf");
        rb7.setTypeface(font);

        bSaveFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectFont = getFontFromId(rgFont.getCheckedRadioButtonId());
                editor.putInt(FONT,selectFont);
                Toast.makeText(preferences_fontType.this,"Settings Saved..", Toast.LENGTH_SHORT).show();
                editor.apply();
            }
        });

    }
    public static int getFontFromId(int id){
        switch (id){
            case R.id.rb1:
                return 1;
            case R.id.rb2:
                return 2;
            case R.id.rb3:
                return 3;
            case R.id.rb4:
                return 4;
            case R.id.rb5:
                return 5;
            case R.id.rb6:
                return 6;
            case R.id.rb7:
                return 7;
        }
        return 4;
    }
}
