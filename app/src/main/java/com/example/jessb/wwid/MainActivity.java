package com.example.jessb.wwid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Integer time_to_read = 0;
    EditText time = null;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    int readingSpeed;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button setupButton = (Button) findViewById(R.id.btn_InitialSetUp);
        Button updateButton = (Button) findViewById(R.id.btn_update);
        Button recommendationButton = (Button) findViewById(R.id.btn_find);
        time = (EditText) findViewById(R.id.et_time);
        SQLiteDatabase mydatabase = openOrCreateDatabase("Books",MODE_PRIVATE,null);
        sp = getSharedPreferences("your_prefs", InitialSetUp.MODE_PRIVATE);
        editor = sp.edit();
        readingSpeed = sp.getInt("speedresult", 0);

        //if the reading speed is not entered (is 0)
        if (readingSpeed == 0) {
            Context context = getApplicationContext();
            CharSequence text = "Please set up your reading speed to begin";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show(); //use pop up to request that its entered

            //go to set up page
            Intent setupPage = new Intent(getApplicationContext(), InitialSetUp.class);
            startActivity(setupPage);
        }

        //create tables in database
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Books(Book_ID INT PRIMARY KEY, Book_Name VARCHAR, Author VARCHAR, Synopsis VARCHAR, " +
                "Word_Count Int, Read VARCHAR, Start_Date VARCHAR, End_Date VARCHAR, Paused VARCHAR);");
        mydatabase.execSQL("INSERT OR IGNORE INTO Books(Book_ID, Book_Name, Author, Synopsis, Word_Count, Read, Start_Date, End_Date, Paused) VALUES(1,'Harry Potter and the Order of the Phoenix', 'J.K. Rowling', 'Harry Potter is due to start his fifth year at Hogwarts School of Witchcraft and Wizadry. He is desperate to get back to school and find out why his friends Ron and Hermione have been so secretive all summer. However, what Harry is about to discover in his new year at Hogwarts will turn his whole world upside down', 257045, 'No', Null, Null, 'No')");
        mydatabase.execSQL("INSERT OR IGNORE INTO Books(Book_ID, Book_Name, Author,  Synopsis, Word_Count, Read, Start_Date, End_Date, Paused) VALUES(2,'The Hunger Games', 'Suzanne Collins', 'When 16-year-old Katniss young sister, Prim, is selected as District 12s female representative, Katniss volunteers to take her place. She and her male counterpart Peeta, are pitted against bigger, stronger representatives, some of whom have trained for this their whole lives, she sees it as a death sentence. But Katniss has been close to death before. For her, survival is second nature.', 99750, 'No', Null, Null, 'No')");
        mydatabase.execSQL("INSERT OR IGNORE INTO Books(Book_ID, Book_Name, Author,  Synopsis, Word_Count, Read, Start_Date, End_Date, Paused) VALUES(3,'The Catcher in the Rye', 'J.D. Salinger', 'The hero-narrator of The Catcher in the Rye is an ancient child of sixteen, a native New Yorker named Holden Caulfield. Through circumstances that tend to preclude adult, secondhand description, he leaves his prep school in Pennsylvania and goes underground in New York City for three days.', 70544, 'No', Null, Null, 'No')");
        mydatabase.execSQL("INSERT OR IGNORE INTO Books(Book_ID, Book_Name, Author,  Synopsis, Word_Count, Read, Start_Date, End_Date, Paused) VALUES(4,'The Da Vinci Code', 'Dan Brown', 'An ingenious code hidden in the works of Leonardo da Vinci. A desperate race through the cathedrals and castles of Europe. An astonishing truth concealed for centuries . . . unveiled at last.', 138952, 'No', Null, Null, 'No')");
        mydatabase.execSQL("INSERT OR IGNORE INTO Books(Book_ID, Book_Name, Author,  Synopsis, Word_Count, Read, Start_Date, End_Date, Paused) VALUES(5,'The Hitchhikers Guide to the Galaxy', 'Douglas Adams', 'Seconds before Earth is demolished to make way for a galactic freeway, Arthur Dent is plucked off the planet by his friend Ford Prefect, a researcher for the revised edition of The Hitchhikers Guide to the Galaxy who, for the last fifteen years, has been posing as an out-of-work actor.', 46333, 'No', Null, Null, 'No')");

        //onclick listener for the set up button
        setupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent setupPage = new Intent (getApplicationContext(), InitialSetUp.class);
                startActivity(setupPage);
            }
        });

        //onclick listener for the update button
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent updatePage = new Intent (getApplicationContext(), Update.class);
                startActivity(updatePage);
            }
        });

        //onclick listener for the recommendations button
        recommendationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                try {
                    time_to_read = Integer.parseInt(String.valueOf(time.getText()));
                    //Log.d("timetoread", String.valueOf(time_to_read));
                    //get the current book id from shared preferences
                    int bookid2 = sp.getInt("currbookid", 0);
                    if(bookid2 != 0) { //if book ID is not 0 (already a current book)
                        Context context = getApplicationContext();
                        CharSequence text = "You can only read one book at a time please update progress";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show(); //advise that there is only one book allowed to read at a time
                        Intent updatePage = new Intent(getApplicationContext(), Update.class);
                        startActivity(updatePage); //go to update page
                }   else {
                        //otherwise go to recommendations page
                        Intent recPage = new Intent(getApplicationContext(), BookRecommendation.class);
                        recPage.putExtra("HOURS_PER_DAY", time_to_read);
                        startActivity(recPage);
                    }
                } catch (Exception e) {
                        finish();
                        startActivity(getIntent());
                        Context context = getApplicationContext();
                        CharSequence text = "You must enter a number"; //if not a number reload and ask again
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    }
                }


        });
    }
}
