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

public class UsersActivity extends AppCompatActivity implements UserAdapter.OnUserModelClickListener{
    FloatingActionButton btnAddUser;
    RecyclerView userList;
    UserAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        UserDataBase vt = new UserDataBase(getApplicationContext());
        userList=findViewById(R.id.userList);

        mAdapter = new UserAdapter(this, this, this);
        userList.setAdapter(mAdapter);
        userList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.setUserModels(vt.getAllUsers(),-1);


        btnAddUser = findViewById(R.id.btnAddUser);
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UsersActivity.this, UserOperationsActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    public void onUserModelClick(long userModelId) {
        Intent intent = new Intent(getApplicationContext(), UserOperationsActivity.class);
        intent.putExtra("userid", String.valueOf(userModelId));
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
            UsersActivity.this.finishAffinity();
        }
        else if(i==R.id.tlbLogout){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            UsersActivity.this.finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }
}
