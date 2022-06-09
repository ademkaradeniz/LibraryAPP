package com.example.libraryapp;

public class BookModel {

    private long ID;
    private String Name;
    private String Author;
    private String Publisher;
    private String Type;
    private String YearOfPublic;
    private String NumberOfPage;
    private byte [] Image;
    private String Status;


    public BookModel() {
    }

    public BookModel(String Name, String Author, String Publisher, String Type,String YearOfPublic, String NumberOfPage, byte [] Image, String Status) {
        this.Name = Name;
        this.Author = Author;
        this.Publisher = Publisher;
        this.Type = Type;
        this.YearOfPublic = YearOfPublic;
        this.NumberOfPage = NumberOfPage;
        this.Image = Image;
        this.Status = Status;
    }



    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getYearOfPublic() {
        return YearOfPublic;
    }

    public void setYearOfPublic(String yearOfPublic) {
        YearOfPublic = yearOfPublic;
    }

    public String getNumberOfPage() {
        return NumberOfPage;
    }

    public void setNumberOfPage(String numberOfPage) {
        NumberOfPage = numberOfPage;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
