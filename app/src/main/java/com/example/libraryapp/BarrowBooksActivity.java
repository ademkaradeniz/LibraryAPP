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

public class BarrowBooksActivity extends AppCompatActivity implements BarrowBookAdapter.OnBarrowBookModelClickListener{
    FloatingActionButton btnAddBarrowBook;
    RecyclerView barrowBookList;
    BarrowBookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barrowbooks);
        BarrowBookDataBase barrowBookDataBase = new BarrowBookDataBase(getApplicationContext());
        barrowBookList=findViewById(R.id.barrowBookList);

        mAdapter = new BarrowBookAdapter(this, this, this);
        barrowBookList.setAdapter(mAdapter);
        barrowBookList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setBarrowBookModels(barrowBookDataBase.getAllBarrowBooks(),-1);

        btnAddBarrowBook = findViewById(R.id.btnAddBarrowBook);
        btnAddBarrowBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BarrowBooksActivity.this, BarrowBookOperationsActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onBarrowBookModelClick(long barrowBookModelId) {
        Intent intent = new Intent(getApplicationContext(), BarrowBookOperationsActivity.class);
        intent.putExtra("barrowbookid", String.valueOf(barrowBookModelId));
        startActivity(intent);
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
            BarrowBooksActivity.this.finishAffinity();
        }
        else if(i==R.id.tlbLogout){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            BarrowBooksActivity.this.finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }
}
