package com.example.libraryapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.LineNumberReader;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BarrowBookOperationsActivity extends AppCompatActivity {
    Button btnBarrowBookSave;
    TextView txtName, txtAuthor,txtPublisher,txtType,txtUserNameSurname,txtTelephone,txtEmail,txtbbBookID,txtbbUserID;
    private Spinner spnBooks,spnUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barrowbook_operations);

        //TEXTS
        txtName=findViewById(R.id.nameCustomList);
        txtAuthor=findViewById(R.id.authorCustomList);
        txtPublisher=findViewById(R.id.publisherCustomList);
        txtType=findViewById(R.id.typeCustomList);
        txtUserNameSurname=findViewById(R.id.userNameSurnameCustomList);
        txtTelephone=findViewById(R.id.userTelephoneCustomList);
        txtEmail=findViewById(R.id.userEmailCustomList);
        txtbbBookID=findViewById(R.id.bbBookIDText);
        txtbbUserID=findViewById(R.id.bbUserIDText);

        btnBarrowBookSave=findViewById(R.id.btnBarrowBookSave);
        spnBooks = (Spinner) findViewById(R.id.spnBooks);
        spnUsers = (Spinner) findViewById(R.id.spnUsers);

        BarrowBookDataBase barrowBookDataBase=new BarrowBookDataBase(getApplicationContext());
        Veritabani veritabani=new Veritabani(getApplicationContext());
        UserDataBase userDataBase=new UserDataBase(getApplicationContext());


        Intent i = getIntent();
        String id = i.getStringExtra("barrowbookid");
        if(id!=null)
        {
            BookModel bookModel=new BookModel();
            UserModel userModel=new UserModel();
            BarrowBookModel barrowBookModel=new BarrowBookModel();
            barrowBookModel=barrowBookDataBase.getBarrowBook(Long.parseLong(id));

            bookModel=veritabani.getBook(Long.parseLong(barrowBookModel.getBookID()));
            userModel=userDataBase.getUser(Long.parseLong(barrowBookModel.getUserID()));

            txtName.setText(bookModel.getName());
            txtAuthor.setText(bookModel.getAuthor());
            txtPublisher.setText(bookModel.getPublisher());
            txtType.setText(bookModel.getType());

            txtUserNameSurname.setText(userModel.getName()+" "+userModel.getSurname());
            txtTelephone.setText(userModel.getTelephone());
            txtEmail.setText(String.valueOf(userModel.getEmail()));

            List<String> tekBookList=new ArrayList<>();
            tekBookList.add(barrowBookModel.getBookID());
            if(tekBookList==null)
            {
                Toast.makeText(this,id,Toast.LENGTH_SHORT).show();
            }
            ArrayAdapter<String> adapters = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tekBookList);
            spnBooks.setAdapter(adapters);
            spnBooks.setEnabled(false);

            List<String> tekUserList=new ArrayList<>();
            tekUserList.add(barrowBookModel.getUserID());
            if(tekUserList==null)
            {
                Toast.makeText(this,id,Toast.LENGTH_SHORT).show();
            }
            ArrayAdapter<String> useradapters = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tekUserList);
            spnUsers.setAdapter(useradapters);
            spnUsers.setEnabled(false);
            btnBarrowBookSave.setText("KİTAP GERİ AL");

        }

        else {
            List<String> list=veritabani.getAllBookID();
            if(list!=null)
            {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
                spnBooks.setAdapter(adapter);
            }

            List<String> userList=userDataBase.getAllUserID();
            if(userList!=null) {
                ArrayAdapter<String> useradapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userList);
                spnUsers.setAdapter(useradapter);
            }

        }


        btnBarrowBookSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BarrowBookModel barrowBookModel=new BarrowBookModel();
                if(id!=null){
                    barrowBookModel=barrowBookDataBase.getBarrowBook(Long.parseLong(id));
                    veritabani.statusGuncelle(Long.parseLong(barrowBookModel.getBookID()), "1");
                    barrowBookDataBase.deleteUser(Long.parseLong(id));
                    Toast.makeText(getApplicationContext(), "Kitap kullanıcıdan geri alındı!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), BarrowBooksActivity.class);
                    startActivity(intent);
                    BarrowBookOperationsActivity.this.finishAffinity();
                }
                else {
                    if (barrowBookModel != null) {
                        barrowBookModel.setBookID(String.valueOf(txtbbBookID.getText()));
                        barrowBookModel.setUserID(String.valueOf(txtbbUserID.getText()));
                        barrowBookModel.setStatus("1");
                        veritabani.statusGuncelle(Long.parseLong(barrowBookModel.getBookID()), "0");
                        barrowBookDataBase.addBarrowBook(barrowBookModel);
                        Toast.makeText(getApplicationContext(), "Kitap emanet olarak kullanıcıya verildi!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), BarrowBooksActivity.class);
                        startActivity(intent);
                        BarrowBookOperationsActivity.this.finishAffinity();
                    } else {
                        Toast.makeText(getApplicationContext(), "Kitap ve Kullanıcı Seçmelisiniz!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        spnBooks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                BookModel bookModel=new BookModel();
                bookModel=veritabani.getBook(Long.parseLong(spnBooks.getSelectedItem().toString()));
                txtbbBookID.setText(String.valueOf(bookModel.getID()));
                txtName.setText(bookModel.getName());
                txtAuthor.setText(bookModel.getAuthor());
                txtPublisher.setText(bookModel.getPublisher());
                txtType.setText(bookModel.getType());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spnUsers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                UserModel userModel=new UserModel();
                userModel=userDataBase.getUser(Long.parseLong(spnUsers.getSelectedItem().toString()));
                txtbbUserID.setText(String.valueOf(userModel.getID()));
                txtUserNameSurname.setText(userModel.getName()+" "+userModel.getSurname());
                txtTelephone.setText(userModel.getTelephone());
                txtEmail.setText(userModel.getEmail());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

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
            BarrowBookOperationsActivity.this.finishAffinity();
        }
        else if(i==R.id.tlbLogout){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            BarrowBookOperationsActivity.this.finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }
}
