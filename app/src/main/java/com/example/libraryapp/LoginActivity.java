package com.example.libraryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Button btnGiris;
    TextView txtLoginUserName,txtLoginPassword;
    String username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnGiris = findViewById(R.id.btnGiris);
        txtLoginUserName = findViewById(R.id.txtLoginUserName);
        txtLoginPassword = findViewById(R.id.txtLoginPassword);

        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username=txtLoginUserName.getText().toString();
                password=txtLoginPassword.getText().toString();
                if(username.equals("Adem") && password.equals("123"))
                {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else if(username.equals("Gazi") && password.equals("123"))
                {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Kullanıcı adı veya parola yanlış!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.login_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i =item.getItemId();

        if(i==R.id.tlbLogout){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            LoginActivity.this.finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }
}