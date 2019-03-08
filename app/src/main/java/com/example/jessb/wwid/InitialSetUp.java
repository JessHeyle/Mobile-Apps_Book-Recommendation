package com.example.jessb.wwid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InitialSetUp extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    int speed_result;
    EditText result;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initial_setup);
        result = (EditText) findViewById(R.id.edit_result); //set result by getting input
        Button saveButton = (Button) findViewById(R.id.button_save);

        sp = getSharedPreferences("your_prefs", InitialSetUp.MODE_PRIVATE); //initialise shared preferences
        editor = sp.edit(); //initialise editor

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //use a try catch to ensure a number is entered
                try {
                    speed_result = Integer.parseInt(result.getText().toString());
                    editor.putInt("speedresult", speed_result);
                    editor.commit();
                    Intent mainPage = new Intent (getApplicationContext(), MainActivity.class);
                    startActivity(mainPage);
                } catch (Exception e) {
                    finish();
                    startActivity(getIntent()); //reload page if not
                    Context context = getApplicationContext();
                    CharSequence text = "You must enter a number";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show(); //show a pop up error
                }
            }
        });
    }
}
