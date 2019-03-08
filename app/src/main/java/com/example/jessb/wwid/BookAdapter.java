package com.example.jessb.wwid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {


    //create adapter for book object
    public BookAdapter(Context context, List<Book> objects) {
        super(context, 0, objects);
    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {

        //get the listview
        View listItemView = convertView;
        if (listItemView==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.book_object, parent, false);
        }

        Book currentBookObject = getItem(position);

        //set the text views
        TextView tvBookID = (TextView) listItemView.findViewById(R.id.tv_book_id);
        TextView tvBookName = (TextView) listItemView.findViewById(R.id.tv_book_name);
        TextView tvBookAuthor = (TextView) listItemView.findViewById(R.id.tv_book_author);
        TextView tvDaysToRead = (TextView) listItemView.findViewById(R.id.tv_daysToRead);

        //change the text on text views
        tvBookID.setText(String.valueOf(currentBookObject.getBook_ID()));
        tvBookName.setText(String.valueOf(currentBookObject.getBook_Name()));
        tvBookAuthor.setText(String.valueOf(currentBookObject.getAuthor()));
        tvDaysToRead.setText(String.valueOf(currentBookObject.daysToRead()));
        return listItemView; //return the list
    }
}
