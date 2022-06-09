package com.example.libraryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Veritabani extends SQLiteOpenHelper {

    private static final String VERITABANI_ISMI = "BookVeritabani";
    private static final String BOOK_TABLE = "Books";
    private static final int VERSIYON = 10;

    //kolon isimleri
    private static final String ID = "_ID";
    private static final String Name = "Name";
    private static final String Author = "Author";
    private static final String Publisher = "Publisher";
    private static final String Type = "Type";
    private static final String YearOfPublic = "YearOfPublic";
    private static final String NumberOfPage = "NumberOfPage";
    private static final String Image = "Image";
    private static final String Status = "Status";





    public Veritabani(Context context) {
        super(context, VERITABANI_ISMI, null, VERSIYON);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BOOK_TABLE +
                " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Name + " TEXT NOT NULL, " + //ad boş bırakılamaz
                Author + " TEXT, " +
                Publisher + " TEXT, " +
                Type + " TEXT, " +
                YearOfPublic + " TEXT, " +
                NumberOfPage + " TEXT, " +
                Status + " TEXT, " +
                Image + " BLOB);"); //byte arrayi tutar
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + BOOK_TABLE);
        onCreate(db);

    }

    public long kaydet(BookModel model) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Name, model.getName());
        cv.put(Author, model.getAuthor());
        cv.put(Publisher, model.getPublisher());
        cv.put(Type, model.getType());
        cv.put(YearOfPublic, model.getYearOfPublic());
        cv.put(NumberOfPage, model.getNumberOfPage());
        cv.put(Status, model.getStatus());

        if (model.getImage() != null) {
            cv.put(Image, model.getImage());
        }

        long id = db.insert(BOOK_TABLE, null, cv); //tabloya ekleme işlemi
        db.close();

        return id;

    }

    public void guncelle(long id, BookModel model) { //veritabanımızda id ye göre güncelleme işlemi yapan metot
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Name, model.getName());
        cv.put(Author, model.getAuthor());
        cv.put(Publisher, model.getPublisher());
        cv.put(Type, model.getType());
        cv.put(YearOfPublic, model.getYearOfPublic());
        cv.put(NumberOfPage, model.getNumberOfPage());
        cv.put(Status, model.getStatus());

        if (model.getImage() != null) {
            cv.put(Image, model.getImage());
        }

        db.update(BOOK_TABLE, cv, ID + "=" + id, null);

        db.close();
    }

    public void statusGuncelle(long id, String status) { //veritabanımızda id ye göre güncelleme işlemi yapan metot
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Status, status);
        db.update(BOOK_TABLE, cv, ID + "=" + id, null);

        db.close();
    }

    public List<BookModel> getAllBooks() {
        List<BookModel> modelList = new ArrayList<BookModel>(); //Kitap model listemiz
        SQLiteDatabase db=getReadableDatabase(); //Veritabanı sınıfımızdan okuma işlemi yapmamızı sağlar

        String [] sutunlar = new String[]{Name,Author,Publisher,Type,YearOfPublic,NumberOfPage,ID,Image,Status}; //Veritabanı tablomuzdaki kolonlar

        String[] args = { "1" };
        Cursor c = db.query(BOOK_TABLE,sutunlar,"Status=?",args,null,null,null);

        int nameSirano = c.getColumnIndex(Name); //Veritabanı kolonlarımızın index numaraları
        int authorSirano = c.getColumnIndex(Author);
        int publisherSirano = c.getColumnIndex(Publisher);
        int typeSirano = c.getColumnIndex(Type);
        int yearofpublicSirano = c.getColumnIndex(YearOfPublic);
        int numberofpageSirano = c.getColumnIndex(NumberOfPage);
        int statusSirano = c.getColumnIndex(Status);
        int idSirano = c.getColumnIndex(ID);
        int imageSirano = c.getColumnIndex(Image);

        if (c.moveToFirst()){ //İlk kayıda git

            do {
                BookModel model =new BookModel();

                model.setName(c.getString(nameSirano));
                model.setAuthor(c.getString(authorSirano));
                model.setPublisher(c.getString(publisherSirano));
                model.setType(c.getString(typeSirano));
                model.setYearOfPublic(c.getString(yearofpublicSirano));
                model.setNumberOfPage(c.getString(numberofpageSirano));
                model.setStatus(c.getString(statusSirano));
                model.setID(c.getLong(idSirano));

                if (c.getBlob(imageSirano)!=null) //Kitap fotoğrafı varsa kaydet yoksa kaydetmeye gerek yok
                    model.setImage(c.getBlob(imageSirano));

                modelList.add(model);

            }while (c.moveToNext()); //Veritabanımızdaki sıradaki kayıda götürür kayıtlarımızı gezmemizi sağlar.


        }else{ // ilk kayıt yoksa null döndürür.
            modelList=null;
        }
        db.close();

        return modelList;
    }

    public List<String> getAllBookID() {
        List<String> modelList = new ArrayList<String>(); //Kitap model listemiz
        SQLiteDatabase db=getReadableDatabase(); //Veritabanı sınıfımızdan okuma işlemi yapmamızı sağlar

        String [] sutunlar = new String[]{Name,Author,Publisher,Type,YearOfPublic,NumberOfPage,ID,Image,Status}; //Veritabanı tablomuzdaki kolonlar

        String[] args = { "1" };
        Cursor c = db.query(BOOK_TABLE,sutunlar,"Status=?",args,null,null,null);

        int nameSirano = c.getColumnIndex(Name); //Veritabanı kolonlarımızın index numaraları
        int authorSirano = c.getColumnIndex(Author);
        int publisherSirano = c.getColumnIndex(Publisher);
        int typeSirano = c.getColumnIndex(Type);
        int yearofpublicSirano = c.getColumnIndex(YearOfPublic);
        int numberofpageSirano = c.getColumnIndex(NumberOfPage);
        int statusSirano = c.getColumnIndex(Status);
        int idSirano = c.getColumnIndex(ID);
        int imageSirano = c.getColumnIndex(Image);

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

    public BookModel getBook(long id) {
        BookModel model=new BookModel();

        SQLiteDatabase db=getReadableDatabase();

        String [] sutunlar = new String[]{Name,Author,Publisher,Type,YearOfPublic,NumberOfPage,ID,Image,Status}; //Veritabanı tablomuzdaki kolonlar

        Cursor c = db.query(BOOK_TABLE,sutunlar,ID+" = ?  ",new String[]{String.valueOf(id)},null,null,null);

        //c.moveToNext();
        c.moveToFirst(); // Buraya dikkat

        int nameSirano = c.getColumnIndex(Name); //Veritabanı kolonlarımızın index numaraları
        int authorSirano = c.getColumnIndex(Author);
        int publisherSirano = c.getColumnIndex(Publisher);
        int typeSirano = c.getColumnIndex(Type);
        int yearofpublicSirano = c.getColumnIndex(YearOfPublic);
        int numberofpageSirano = c.getColumnIndex(NumberOfPage);
        int statusSirano = c.getColumnIndex(Status);
        int idSirano = c.getColumnIndex(ID);
        int imageSirano = c.getColumnIndex(Image);


        model.setName(c.getString(nameSirano));
        model.setAuthor(c.getString(authorSirano));
        model.setPublisher(c.getString(publisherSirano));
        model.setType(c.getString(typeSirano));
        model.setYearOfPublic(c.getString(yearofpublicSirano));
        model.setNumberOfPage(c.getString(numberofpageSirano));
        model.setStatus(c.getString(statusSirano));
        model.setID(c.getLong(idSirano));

        if (c.getBlob(imageSirano)!=null) //Kitap fotoğrafı varsa kaydet yoksa kaydetmeye gerek yok
            model.setImage(c.getBlob(imageSirano));

        db.close();
        return model;
    }

    public void sil(long id) {

        SQLiteDatabase db=getWritableDatabase();
        db.delete(BOOK_TABLE,ID+" = ? ",new String[]{String.valueOf(id)});
        db.close();
    }


}
