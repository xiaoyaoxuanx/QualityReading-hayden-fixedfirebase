package com.qualityreading.qualityreading;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class BookListViewAdapter extends BaseAdapter {
    Context context;
    List<Book> bookList;

    BookListViewAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Book book = this.bookList.get(position);
        View row;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.book_list_view_layout, null);

        TextView listTitle = (TextView) row.findViewById(R.id.listTitle);
        listTitle.setText(book.getTitle().length() > 38 ? book.getTitle().substring(0, 35) + "..." : book.getTitle());

        TextView listAuthor = (TextView) row.findViewById(R.id.listAuthor);
        listAuthor.setText(book.getAuthor());

        TextView listPublisher = (TextView) row.findViewById(R.id.listPublisher);
        listPublisher.setText(book.getPublisher());

        TextView listPubDate = (TextView) row.findViewById(R.id.listPubDate);
        listPubDate.setText(book.getPublishDate());

        book.displayThumbnail((ImageView) row.findViewById(R.id.thumbnail));

        Log.i("This book", book.toString());

        return row;
    }
}
