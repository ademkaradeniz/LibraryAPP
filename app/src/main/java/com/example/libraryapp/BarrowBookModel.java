package com.example.libraryapp;

public class BarrowBookModel {
    private long ID;
    private String UserID;
    private String BookID;
    private String Status;

    public BarrowBookModel() {
    }

    public BarrowBookModel(String UserID, String BookID, String Status) {
        this.UserID = UserID;
        this.BookID = BookID;
        this.Status = Status;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getBookID() {
        return BookID;
    }

    public void setBookID(String bookID) {
        BookID = bookID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }


}
