package com.example.jessb.wwid;

public class Book {

    //variables
    private int Book_ID;
    private String Book_Name;
    private String Author;
    private String Synopsis;
    private int Word_Count;
    private String Read;
    private String Start_Date;
    private String End_Date;
    private String Paused;
    private int readingSpeed;
    private int hoursPerDay;

    //set up book object
    public Book(int Book_ID, String Book_Name, String Author, int readingSpeed, int hoursPerDay, int Word_Count, String Start_Date) {
        this.Book_ID = Book_ID;
        this.Book_Name = Book_Name;
        this.Author = Author;
        this.Word_Count = Word_Count;
        this.hoursPerDay = hoursPerDay;
        this.readingSpeed = readingSpeed;
    }

    public int getBook_ID() {
        return Book_ID;
    }

    public String getBook_Name() {
        return Book_Name;
    }

    public String getAuthor() {
        return Author;
    }

    //not currently used
    public String getSynopsis() {
        return Synopsis;
    }

    public int getWord_Count() {
        return Word_Count;
    }

    public void setWord_Count(int word_Count) {
        Word_Count = word_Count;
    }

    public String getRead() {
        return Read;
    }

    public void setRead(String read) {
        Read = read;
    }

    //not currently used - for future would track reading speed
    public String getStart_Date() {
        return Start_Date;
    }

    public void setStart_Date(String start_Date) {
        Start_Date = start_Date;
    }

    //not currently used - for future would track reading speed
    public String getEnd_Date() {
        return End_Date;
    }

    public void setEnd_Date(String end_Date) {
        End_Date = end_Date;
    }

    public String getPaused() {
        return Paused;
    }

    public void setPaused(String paused) {
        Paused = paused;
    }

    //used to calculate how many days it will take to read the book
    public String daysToRead ()
    {
        int speedPerDay = readingSpeed * 60 * hoursPerDay; //words per minute reading speed multiplied by 60 (hour) and multiplied by number of hours per day
        return "Days to Read: " + (Word_Count / speedPerDay); //word count divided by above calculation
    }
}
