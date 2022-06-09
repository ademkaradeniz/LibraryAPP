package com.example.libraryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserDataBase extends SQLiteOpenHelper {

    private static final String VERITABANI_ISMI = "UserVeritabani";
    private static final String USER_TABLE = "Users";
    private static final int VERSIYON = 11;



    //User kolon isimleri
    private static final String UID = "_ID";
    private static final String UName = "Name";
    private static final String USurname = "Surname";
    private static final String UUserName = "Username";
    private static final String UPassword = "Password";
    private static final String UEmail = "Email";
    private static final String UTelephone = "Telephone";
    private static final String UStatus = "Status";




    public UserDataBase(Context context) {
        super(context, VERITABANI_ISMI, null, VERSIYON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + USER_TABLE +
                " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UName + " TEXT NOT NULL, " + //ad boş bırakılamaz
                USurname + " TEXT NOT NULL, " +
                UUserName + " TEXT NOT NULL, " +
                UPassword + " TEXT NOT NULL, " +
                UEmail + " TEXT NOT NULL, " +
                UTelephone + " TEXT, " +
                UStatus + " TEXT);");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        onCreate(db);

    }

    //USER İşlemleri

    public long addUser(UserModel model) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(UName, model.getName());
        cv.put(USurname, model.getSurname());
        cv.put(UUserName, model.getUsername());
        cv.put(UPassword, model.getPassword());
        cv.put(UEmail, model.getEmail());
        cv.put(UTelephone, model.getTelephone());
        cv.put(UStatus, model.getStatus());

        long id = db.insert(USER_TABLE, null, cv); //tabloya ekleme işlemi
        db.close();

        return id;

    }

    public void updateUser(long id, UserModel model) { //veritabanımızda id ye göre güncelleme işlemi yapan metot
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(UName, model.getName());
        cv.put(USurname, model.getSurname());
        cv.put(UUserName, model.getUsername());
        cv.put(UPassword, model.getPassword());
        cv.put(UEmail, model.getEmail());
        cv.put(UTelephone, model.getTelephone());
        cv.put(UStatus, model.getStatus());

        db.update(USER_TABLE, cv, UID + "=" + id, null);
        db.close();
    }

    public List<UserModel> getAllUsers() {
        List<UserModel> modelList = new ArrayList<UserModel>(); //User model listemiz
        SQLiteDatabase db=getReadableDatabase(); //Veritabanı sınıfımızdan okuma işlemi yapmamızı sağlar

        String [] sutunlar = new String[]{UID,UName,USurname,UUserName,UEmail,UPassword,UTelephone,UStatus}; //Veritabanı tablomuzdaki kolonlar

        Cursor c = db.query(USER_TABLE,sutunlar,null,null,null,null,null);

        int idSirano = c.getColumnIndex(UID);
        int nameSirano = c.getColumnIndex(UName);
        int surnameSirano = c.getColumnIndex(USurname);
        int usernameSirano = c.getColumnIndex(UUserName);
        int passwordSirano = c.getColumnIndex(UPassword);
        int emailSirano = c.getColumnIndex(UEmail);
        int telephoneSirano = c.getColumnIndex(UTelephone);
        int statusSirano = c.getColumnIndex(UStatus);

        if (c.moveToFirst()){ //İlk kayıda git

            do {
                UserModel model =new UserModel();

                model.setName(c.getString(nameSirano));
                model.setSurname(c.getString(surnameSirano));
                model.setUsername(c.getString(usernameSirano));
                model.setPassword(c.getString(passwordSirano));
                model.setEmail(c.getString(emailSirano));
                model.setTelephone(c.getString(telephoneSirano));
                model.setStatus(statusSirano);
                model.setID(c.getLong(idSirano));

                modelList.add(model);

            }while (c.moveToNext()); //Veritabanımızdaki sıradaki kayıda götürür kayıtlarımızı gezmemizi sağlar.


        }else{ // ilk kayıt yoksa null döndürür.
            modelList=null;
        }
        db.close();

        return modelList;
    }

    public List<String> getAllUserID() {
        List<String> modelList = new ArrayList<String>(); //Kitap model listemiz
        SQLiteDatabase db=getReadableDatabase(); //Veritabanı sınıfımızdan okuma işlemi yapmamızı sağlar

        String [] sutunlar = new String[]{UID,UName,USurname,UUserName,UEmail,UPassword,UTelephone,UStatus}; //Veritabanı tablomuzdaki kolonlar

        Cursor c = db.query(USER_TABLE,sutunlar,null,null,null,null,null);

        int idSirano = c.getColumnIndex(UID);
        int nameSirano = c.getColumnIndex(UName);
        int surnameSirano = c.getColumnIndex(USurname);
        int usernameSirano = c.getColumnIndex(UUserName);
        int passwordSirano = c.getColumnIndex(UPassword);
        int emailSirano = c.getColumnIndex(UEmail);
        int telephoneSirano = c.getColumnIndex(UTelephone);
        int statusSirano = c.getColumnIndex(UStatus);

        if (c.moveToFirst()){ //İlk kayıda git

            do {

                modelList.add(String.valueOf(c.getLong(idSirano)));

            }while (c.moveToNext()); //Veritabanımızdaki sıradaki kayıda götürür kayıtlarımızı gezmemizi sağlar.


        }else{ // ilk kayıt yoksa null döndürür.
            modelList=null;
        }
        db.close();

        return modelList;
    }

    public UserModel getUser(long id) {
        UserModel model=new UserModel();

        SQLiteDatabase db=getReadableDatabase();

        String [] sutunlar = new String[]{UID,UName,USurname,UUserName,UPassword,UEmail,UTelephone,UStatus}; //Veritabanı tablomuzdaki kolonlar

        Cursor c = db.query(USER_TABLE,sutunlar,UID+" = ? ",new String[]{String.valueOf(id)},null,null,null);

        //c.moveToNext();
        c.moveToFirst(); // Buraya dikkat

        int idSirano = c.getColumnIndex(UID);
        int nameSirano = c.getColumnIndex(UName);
        int surnameSirano = c.getColumnIndex(USurname);
        int usernameSirano = c.getColumnIndex(UUserName);
        int passwordSirano = c.getColumnIndex(UPassword);
        int emailSirano = c.getColumnIndex(UEmail);
        int telephoneSirano = c.getColumnIndex(UTelephone);
        int statusSirano = c.getColumnIndex(UStatus);


        model.setName(c.getString(nameSirano));
        model.setSurname(c.getString(surnameSirano));
        model.setUsername(c.getString(usernameSirano));
        model.setPassword(c.getString(passwordSirano));
        model.setEmail(c.getString(emailSirano));
        model.setTelephone(c.getString(telephoneSirano));
        model.setStatus(statusSirano);
        model.setID(c.getLong(idSirano));

        db.close();
        return model;
    }

    public void deleteUser(long id) {

        SQLiteDatabase db=getWritableDatabase();
        db.delete(USER_TABLE,UID+" = ? ",new String[]{String.valueOf(id)});
        db.close();
    }

}
