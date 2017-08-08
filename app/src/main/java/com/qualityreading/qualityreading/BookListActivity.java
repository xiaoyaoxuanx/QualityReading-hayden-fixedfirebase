package com.qualityreading.qualityreading;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Vector;

public class BookListActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private DatabaseReference databaseBookShelf;
    private FirebaseDatabase database;

    private ListView listviewBookShelf;

    private List<Book> bookList = new Vector<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

    }

    private void renderShelf(){
        TableLayout tbl = (TableLayout) findViewById(R.id.shelfTable);

        int num = 0;
        while (num < bookList.size()) {
            HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this);
            horizontalScrollView.setLayoutParams(new ActionBar.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));

            TableRow tblRow = new TableRow(this);
            tblRow.setLayoutParams(new ActionBar.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
            tblRow.setBackgroundResource(R.drawable.shelf_background);

            for(int i = 0; i < 4; i++) {
                ImageView imageView = new ImageView(this);
                bookList.get(i).displayThumbnail(imageView);

                TextView textView = new TextView(this);
                textView.setText(bookList.get(i).getTitle());

                textView.setLayoutParams(new ActionBar.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                ));

                tblRow.addView(imageView, i);
            }

            horizontalScrollView.addView(tblRow);
            tbl.addView(horizontalScrollView, num / 4);
            num += 4;
        }
    }


    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null) {
                    Log.d("BookListView","User is not signed in");

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                }
            }
        });

        databaseBookShelf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookList.clear();
                for(DataSnapshot bookSnapShot : dataSnapshot.getChildren()) {
                    Book book = bookSnapShot.getValue(Book.class);
                    bookList.add(book);
                }

                renderShelf();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("BookListView", databaseError.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
