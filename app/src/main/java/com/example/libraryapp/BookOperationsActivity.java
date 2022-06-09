package com.example.libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class BookOperationsActivity extends AppCompatActivity {
    Button btnSave,btnDelete;
    EditText txtName, txtAuthor,txtPublisher,txtType,txtYearOfPublic,txtNumberOfPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_operations);

        //TEXTS
        txtName=findViewById(R.id.txtName);
        txtAuthor=findViewById(R.id.txtAuthor);
        txtPublisher=findViewById(R.id.txtPublisher);
        txtType=findViewById(R.id.txtType);
        txtYearOfPublic=findViewById(R.id.txtYearOfPublic);
        txtNumberOfPage=findViewById(R.id.txtNumberOfPage);
        //BUTTONS
        btnSave = findViewById(R.id.btnBarrowBookSave);
        btnDelete = findViewById(R.id.btnDelete);

        Veritabani veritabani=new Veritabani(getApplicationContext());

        Intent i = getIntent();
        String id = i.getStringExtra("bookid");
        if(id!=null)
        {
            BookModel model=new BookModel();
            Veritabani vt=new Veritabani(getApplicationContext());
            model=vt.getBook(Long.parseLong(id));
            txtName.setText(model.getName());
            txtAuthor.setText(model.getAuthor());
            txtPublisher.setText(model.getPublisher());
            txtType.setText(model.getType());
            txtYearOfPublic.setText(model.getYearOfPublic().toString());
            txtNumberOfPage.setText(String.valueOf(model.getNumberOfPage()));
            btnSave.setText("GUNCELLE");
            btnDelete.setVisibility(View.VISIBLE);
        }
        else {
            textClear();
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id!=null) {

                    veritabani.sil(Long.parseLong(id));
                    Toast.makeText(getApplicationContext(), "Kitap Silindi!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), BooksActivity.class);
                    startActivity(intent);
                    BookOperationsActivity.this.finishAffinity();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=txtName.getText().toString();
                String author=txtAuthor.getText().toString();
                String publisher=txtPublisher.getText().toString();
                String type=txtType.getText().toString();
                String yearOfPublic=txtYearOfPublic.getText().toString();
                String numberOfPage=txtNumberOfPage.getText().toString();

                BookModel bookModel=new BookModel(name,author,publisher,type,yearOfPublic,numberOfPage,null,"1");
                if(id!=null){
                    veritabani.guncelle(Long.parseLong(id),bookModel);
                    Toast.makeText(getApplicationContext(),"Kitap Güncellendi!",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), BooksActivity.class);
                    startActivity(intent);
                    BookOperationsActivity.this.finishAffinity();
                }
                else{
                    long id=veritabani.kaydet(bookModel);
                    try {
                        if(id>0)
                        {
                            Toast.makeText(getApplicationContext(),"Kitap Başarıyla Eklendi!",Toast.LENGTH_SHORT).show();
                            textClear();
                            Intent intent = new Intent(getApplicationContext(), BooksActivity.class);
                            startActivity(intent);
                            BookOperationsActivity.this.finishAffinity();
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Kitap Eklenemedi!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void textClear(){
        txtName.setText("");
        txtAuthor.setText("");
        txtPublisher.setText("");
        txtType.setText("");
        txtYearOfPublic.setText("");
        txtNumberOfPage.setText("");
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
            BookOperationsActivity.this.finishAffinity();
        }
        else if(i==R.id.tlbLogout){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            BookOperationsActivity.this.finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }

}
