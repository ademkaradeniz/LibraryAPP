package com.example.libraryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BarrowBookDataBase extends SQLiteOpenHelper {

    private static final String VERITABANI_ISMI = "BarrowBooksDatabase";
    private static final String BARROWBOOK_TABLE = "BarrowBooks";
    private static final int VERSIYON = 10;


    //kolon isimleri
    private static final String BB_ID = "_ID";
    private static final String BB_USERID = "UserID";
    private static final String BB_BOOKID = "BookID";
    private static final String BB_STATUS = "Status";



    public BarrowBookDataBase(Context context) {
        super(context, VERITABANI_ISMI, null, VERSIYON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + BARROWBOOK_TABLE +
                " (" + BB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BB_USERID + " TEXT NOT NULL, " +
                BB_BOOKID + " TEXT NOT NULL, " +
                BB_STATUS + " TEXT);");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + BARROWBOOK_TABLE);
        onCreate(db);

    }

    //USER İşlemleri

    public long addBarrowBook(BarrowBookModel model) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BB_BOOKID, model.getBookID());
        cv.put(BB_USERID, model.getUserID());
        cv.put(BB_STATUS, model.getStatus());

        long id = db.insert(BARROWBOOK_TABLE, null, cv); //tabloya ekleme işlemi
        db.close();

        return id;

    }

    public void updateBarrowBook(long id, BarrowBookModel model) { //veritabanımızda id ye göre güncelleme işlemi yapan metot
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(BB_BOOKID, model.getBookID());
        cv.put(BB_USERID, model.getUserID());
        cv.put(BB_STATUS, model.getStatus());

        db.update(BARROWBOOK_TABLE, cv, BB_ID + "=" + id, null);
        db.close();
    }

    public List<BarrowBookModel> getAllBarrowBooks() {
        List<BarrowBookModel> modelList = new ArrayList<BarrowBookModel>(); //User model listemiz
        SQLiteDatabase db=getReadableDatabase(); //Veritabanı sınıfımızdan okuma işlemi yapmamızı sağlar

        String [] sutunlar = new String[]{BB_ID,BB_USERID,BB_BOOKID,BB_STATUS}; //Veritabanı tablomuzdaki kolonlar

        Cursor c = db.query(BARROWBOOK_TABLE,sutunlar,null,null,null,null,null);

        int idSirano = c.getColumnIndex(BB_ID);
        int userIdSirano = c.getColumnIndex(BB_USERID);
        int bookIdSirano = c.getColumnIndex(BB_BOOKID);
        int statusSirano = c.getColumnIndex(BB_STATUS);

        if (c.moveToFirst()){ //İlk kayıda git

            do {
                BarrowBookModel model =new BarrowBookModel();

                model.setUserID(c.getString(userIdSirano));
                model.setBookID(c.getString(bookIdSirano));
                model.setStatus(c.getString(statusSirano));
                model.setID(c.getLong(idSirano));

                modelList.add(model);

            }while (c.moveToNext()); //Veritabanımızdaki sıradaki kayıda götürür kayıtlarımızı gezmemizi sağlar.


        }else{ // ilk kayıt yoksa null döndürür.
            modelList=null;
        }
        db.close();

        return modelList;
    }

    public BarrowBookModel getBarrowBook(long id) {
        BarrowBookModel model=new BarrowBookModel();

        SQLiteDatabase db=getReadableDatabase();

        String [] sutunlar = new String[]{BB_ID,BB_USERID,BB_BOOKID,BB_STATUS}; //Veritabanı tablomuzdaki kolonlar

        Cursor c = db.query(BARROWBOOK_TABLE,sutunlar,BB_ID+" = ? ",new String[]{String.valueOf(id)},null,null,null);

        //c.moveToNext();
        c.moveToFirst(); // Buraya dikkat

        int idSirano = c.getColumnIndex(BB_ID);
        int userIdSirano = c.getColumnIndex(BB_USERID);
        int bookIdSirano = c.getColumnIndex(BB_BOOKID);
        int statusSirano = c.getColumnIndex(BB_STATUS);


        model.setUserID(c.getString(userIdSirano));
        model.setBookID(c.getString(bookIdSirano));
        model.setStatus(c.getString(statusSirano));
        model.setID(c.getLong(idSirano));

        db.close();
        return model;
    }

    public void deleteUser(long id) {

        SQLiteDatabase db=getWritableDatabase();
        db.delete(BARROWBOOK_TABLE,BB_ID+" = ? ",new String[]{String.valueOf(id)});
        db.close();
    }

}
