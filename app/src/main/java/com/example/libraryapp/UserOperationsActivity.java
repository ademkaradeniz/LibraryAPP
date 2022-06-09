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

public class UserOperationsActivity extends AppCompatActivity {
    Button btnSave,btnDelete;
    EditText txtName, txtSurname,txtUserName,txtPassword,txtTelephone,txtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_operations);

        //TEXTS
        txtName=findViewById(R.id.txtName);
        txtSurname=findViewById(R.id.txtSurname);
        txtUserName=findViewById(R.id.txtUsername);
        txtPassword=findViewById(R.id.txtPassword);
        txtTelephone=findViewById(R.id.txtTelephone);
        txtEmail=findViewById(R.id.txtEmail);

        //BUTTONS
        btnSave = findViewById(R.id.btnBarrowBookSave);
        btnDelete = findViewById(R.id.btnDelete);

        UserDataBase veritabani=new UserDataBase(getApplicationContext());

        Intent i = getIntent();
        String id = i.getStringExtra("userid");
        if(id!=null)
        {
            //Toast.makeText(this,id,Toast.LENGTH_SHORT).show();
            UserModel model=new UserModel();
            UserDataBase vt=new UserDataBase(getApplicationContext());
            model=vt.getUser(Long.parseLong(id));

            txtName.setText(model.getName());
            txtSurname.setText(model.getSurname());
            txtUserName.setText(model.getUsername());
            txtPassword.setText(model.getPassword());
            txtTelephone.setText(model.getTelephone());
            txtEmail.setText(String.valueOf(model.getEmail()));

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
                    veritabani.deleteUser(Long.parseLong(id));
                    Toast.makeText(getApplicationContext(), "Üye Silindi!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
                    startActivity(intent);
                    UserOperationsActivity.this.finishAffinity();
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=txtName.getText().toString();
                String surname=txtSurname.getText().toString();
                String username=txtUserName.getText().toString();
                String password=txtPassword.getText().toString();
                String telephone=txtTelephone.getText().toString();
                String email=txtEmail.getText().toString();

                UserModel userModel=new UserModel(name,surname,username,email,password,telephone,1);
                if(id!=null){
                    veritabani.updateUser(Long.parseLong(id),userModel);
                    Toast.makeText(getApplicationContext(),"Üye Güncellendi!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
                    startActivity(intent);
                    UserOperationsActivity.this.finishAffinity();
                }
                else{
                    long id=veritabani.addUser(userModel);
                    try {
                        if(id>0)
                        {
                            Toast.makeText(getApplicationContext(),"Üye Başarıyla Eklendi!",Toast.LENGTH_LONG).show();
                            textClear();
                            Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
                            startActivity(intent);
                            UserOperationsActivity.this.finishAffinity();
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"Üye Eklenemedi!",Toast.LENGTH_LONG).show();
                    }
                }



            }

        });


    }
    public void textClear(){
        txtName.setText("");
        txtSurname.setText("");
        txtUserName.setText("");
        txtPassword.setText("");
        txtTelephone.setText("");
        txtEmail.setText("");
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
            UserOperationsActivity.this.finishAffinity();
        }
        else if(i==R.id.tlbLogout){
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            UserOperationsActivity.this.finishAffinity();
        }

        return super.onOptionsItemSelected(item);
    }

}
