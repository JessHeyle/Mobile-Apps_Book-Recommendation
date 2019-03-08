package com.example.jessb.wwid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class Update extends AppCompatActivity {

    SQLiteDatabase mydatabase;
    ListView bookListView;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String finished = "no";
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.update);
        super.onCreate(savedInstanceState);
        Spinner dropdown1 = (Spinner) findViewById(R.id.spinner_finished);
        final Spinner dropdown2 = (Spinner) findViewById(R.id.spinner_pause);
        final TextView pause = (TextView) findViewById(R.id.tv_pause);
        final Button saveButton = (Button) findViewById(R.id.btn_save);
        mydatabase = openOrCreateDatabase("Books",MODE_PRIVATE,null);
        sp = getSharedPreferences("your_prefs", InitialSetUp.MODE_PRIVATE);
        editor = sp.edit();
        //create a list of items
        String[] items = new String[]{"", "Yes", "No"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown1.setAdapter(adapter);
        dropdown2.setAdapter(adapter);

        //listen for when the first dropdown is selected
        dropdown1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0 && position != 1) { //if it is not Yes then display second dropdown
                    dropdown2.setVisibility(View.VISIBLE);
                    pause.setVisibility(View.VISIBLE);
                }
                else if (position != 0) { //otherwise set finished to yes
                    finished = "yes";
                    dropdown2.setVisibility(View.GONE);
                    pause.setVisibility(View.GONE);
                }
                else {
                    dropdown2.setVisibility(View.GONE);
                    pause.setVisibility(View.GONE);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });
        //listen for when dropdown 2 is selected
        dropdown2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0 && position != 2) { //if it is yes then change finished to yes and changed paused in DB to yes
                    finished = "yes";
                    int currBook = sp.getInt("currbookid", 0);
                    mydatabase.execSQL("UPDATE Books SET Paused='Yes' WHERE Book_ID = "+ currBook);

                }
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //listen or save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (finished == "yes") { //if finished is yes then set current book ID to 0
                    editor.putInt("currbookid", 0);
                    editor.commit();
                    Intent mainPage = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainPage); //go to main activity
                } else {
                    Intent mainPage = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(mainPage); //otherwise just go to main activity
                }
            }
        });

    }
}