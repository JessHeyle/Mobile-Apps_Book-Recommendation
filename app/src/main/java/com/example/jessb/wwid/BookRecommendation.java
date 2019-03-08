package com.example.jessb.wwid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;


public class BookRecommendation extends AppCompatActivity {
    EditText choice;
    SQLiteDatabase mydatabase;
    ListView bookListView;
    TextView tv;
    Cursor c;
    int read_book;
    Integer Book_ID;
    String Book_Name;
    String Author;
    Integer Word_Count;
    String Start_Date;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_recommendation); //get the xml layout
        mydatabase = openOrCreateDatabase("Books", MODE_PRIVATE, null); //open the database
        Button readButton = (Button) findViewById(R.id.btn_read);
        Bundle bundle = getIntent().getExtras();
        Integer hoursPerDay = bundle.getInt("HOURS_PER_DAY"); // getting hours per day from main activity
        sp = getSharedPreferences("your_prefs", InitialSetUp.MODE_PRIVATE); //initialise shared preferences
        editor = sp.edit(); //initialise editor for shared preferences
        int readingSpeed = sp.getInt("speedresult", 0); //get the reading speed from shared preferences
        List<Book> recommendationList = new ArrayList<>(); //set the list of books
        tv = (TextView) findViewById(R.id.tv);

        //use the cursor to filter through the database and create the book objects
        c = mydatabase.rawQuery("SELECT Book_ID,Book_Name, Author, Synopsis, Word_Count, Start_Date FROM Books", null);
        if (c.moveToFirst()) {
            do {
                // Passing values
                Book_ID = c.getInt(0);
                Book_Name = c.getString(1);
                Author = c.getString(2);
                //String Synopsis = c.getString(3); //not currently used
                Word_Count = c.getInt(4);
                Start_Date = c.getString(5); //not currently used but putting in for future use

                //add each database row to book
                recommendationList.add(new Book(Book_ID, Book_Name, Author, readingSpeed, hoursPerDay, Word_Count, Start_Date));
            } while (c.moveToNext());
        }

//        Create custom adapter for books
        final BookAdapter bookAdapter = new BookAdapter(this, recommendationList);
        bookListView = (ListView) findViewById(R.id.lv_recommendations);
        bookListView.setAdapter(bookAdapter);

        //listen for click on list item
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = bookAdapter.getItem(position).getBook_Name(); //set the book name
                read_book = bookAdapter.getItem(position).getBook_ID(); //set the book id for updating in shared preferences
                tv.setText("Your selection: " + text); //display the book name in the text view

            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Not using any of the below currently but would use for updating start date in future
                //Date today = new Date();
                //SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                //String dateToStr = format.format(today);
                //Log.d("DATEID", dateToStr);
                //speedresult
                //String strSQL = "UPDATE Books SET Start_Date = \"" + dateToStr + "\" WHERE Book_ID = "+ read_book;
                //mydatabase.execSQL(strSQL);
                //String startnew = bookAdapter.getItem(read_book - 1).getStart_Date();
                //Log.d("NewDate", startnew);

                editor.putInt("currbookid", read_book); //change the current book id in shared preferences
                editor.commit(); //commit changes to editor
                Intent mainPage = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(mainPage); //go back to main activity
            }
        });

        c.close(); //close the cursor
        mydatabase.close(); //close the database
    }
}