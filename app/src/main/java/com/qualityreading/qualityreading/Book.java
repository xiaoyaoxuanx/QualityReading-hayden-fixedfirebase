package com.qualityreading.qualityreading;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

public class Book {
    private class DownloadThumbnailTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView view;

        public DownloadThumbnailTask(ImageView view) {
            this.view = view;
        }

        @Override
        protected Bitmap doInBackground(String...params){
            Bitmap thumbnail = null;
            try {
                InputStream inputStream = new URL(params[0]).openStream();
                thumbnail = BitmapFactory.decodeStream(inputStream);
            }
            catch (Exception e) {
                Log.d("DOWNLOAD_THUMBNAIL", e.getMessage());
            }
            return thumbnail;
        }

        @Override
        protected void onPostExecute(Bitmap thumbnail) {
            view.setImageBitmap(thumbnail);
        }
    }

    private String id;
    private String title;
    private String author;
    private String publisher;
    private String publishDate;
    private String thumbnail;
    public Book(){};

    public Book (String id, String title, String author, String publisher, String publishDate, String thumbnail) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishDate = publishDate;
        this.thumbnail = thumbnail;
    }

    public String getID() { return id; }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public String getPublisher(){
        return publisher;
    }

    public String getPublishDate(){
        return publishDate;
    }

    public String getThumbnail(){
        return thumbnail;
    }

    public void displayThumbnail(ImageView view){
        if(!thumbnail.isEmpty()){
            new DownloadThumbnailTask(view).execute(thumbnail);
        }
    }

    @Override
    public String toString() {
        String ret = "book {\n";
        ret += ("  id: " + id + ",\n");
        ret += ("  title: " + title + ",\n");
        ret += ("  author: " + author + ",\n");
        ret += ("  publisher: " + publisher + ",\n");
        ret += ("  publishDate: " + publishDate + ",\n");
        ret += ("  thumbnail: " + thumbnail + ",\n");
        return ret + "}\n";
    }
}

