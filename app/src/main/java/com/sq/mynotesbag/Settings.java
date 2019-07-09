package com.sq.mynotesbag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {

    ListView settings_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        List<String> list = new ArrayList<String>();
        list.add("Change Text Size");
        list.add("Change Text Color");
        list.add("Change Font");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,R.layout.text_settings_list,list);

        settings_list = (ListView)findViewById(R.id.settings_list);
        settings_list.setAdapter(dataAdapter);
        //ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_2,listitems);

        settings_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Position : "+position);
                switch(position){
                    case 0:
                        Intent i = new Intent(Settings.this,preferences_textsize.class);
                        startActivity(i);
                        break;
                    case 1:
                        Intent c = new Intent(Settings.this,preferences_textColor.class);
                        startActivity(c);
                        break;
                    case 2:
                        Intent f = new Intent(Settings.this,preferences_fontType.class);
                        startActivity(f);
                        break;
                }
            }
        });
    }

}
