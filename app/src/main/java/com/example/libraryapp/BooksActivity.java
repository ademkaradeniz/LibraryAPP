package com.example.libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BooksActivity extends AppCompatActivity implements CustomAdapter.OnBookModelClickListener{
    FloatingActionButton btnAddBook;
    RecyclerView bookList;
    CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        Veritabani vt=new Veritabani(getApplicationContext());
        bookList=findViewById(R.id.userList);

        mAdapter = new CustomAdapter(this, this, this);
        bookList.setAdapter(mAdapter);
        bookList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setBookModels(vt.getAllBooks(),-1);



        /*final TextView nameText=(TextView)findViewById(R.id.nameCustomList);
        final TextView authorText=(TextView)findViewById(R.id.authorCustomList);
        final TextView publisherText=(TextView)findViewById(R.id.publisherCustomList);
        Veritabani vt=new Veritabani(getApplicationContext());

        List<BookModel> bookModelList=new ArrayList<BookModel>();
        bookModelList=vt.getAllBooks();

        StringBuilder sb=new StringBuilder();
        for (BookModel _bookModel:bookModelList) {
            nameText.setText(_bookModel.getName());
            authorText.setText(_bookModel.getAuthor());
            publisherText.setText(_bookModel.getPublisher());
        }*/

        btnAddBook = findViewById(R.id.btnAddBook);
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BooksActivity.this, BookOperationsActivity.class);

                //TODO 5.1 startActivity metoduna yazdığımız intent'i veriyoruz Bu şekilde diğer activity'ye geçeceğiz.
                startActivity(intent);

            }
        });

    }

    @Override
    public void onBookModelClick(long bookModelId) {
        Intent intent = new Intent(getApplicationContext(), BookOperationsActivity.class);
        intent.putExtra("bookid", String.valueOf(bookModelId));
        startActivity(intent);
        //Toast.makeText(this,String.valueOf(bookModelId),Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.exit_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i =item.getItemId();

        if(i==R.id.tlbMain){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            BooksActivity.this.finishAffinity();
        }
        else if(i==R.id.tlbLogout){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            BooksActivity.this.finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }
}
