package com.example.bookitup;

import com.google.firebase.database.ServerValue;

import java.util.Date;


public class BookActivity
{

    private String Xbook;
    private String Xauthor;



    private String edition;
    private String isbn;
    private String date;
    private Float Xprice;
    private String Xcondition;
    private String Xdescription;
    private String Xuid;
    public BookActivity() {
    }

    public BookActivity(String xbook, String isbn, String xauthor, Float xprice, String xcondition, String xdescription) {
        Xbook = xbook;
        this.isbn = isbn;
        Xauthor = xauthor;
        Xprice = xprice;
        Xcondition = xcondition;
        Xdescription = xdescription;
        date = ServerValue.TIMESTAMP.toString();
    }
    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }
    public String getXbook() {
        return Xbook;
    }

    public void setXbook(String xbook) {
        Xbook = xbook;
    }

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }




    public void setDate(String date){
        this.date = date;
    }

    public String getDate() {
        return date;
    }


    public String getXauthor() {
        return Xauthor;
    }

    public void setXauthor(String xauthor) {
        Xauthor = xauthor;
    }

    public Float getXprice() {
        return Xprice;
    }

    public void setXprice(Float xprice) {
        Xprice = xprice;
    }

    public String getXcondition() {
        return Xcondition;
    }

    public void setXcondition(String xcondition) {
        Xcondition = xcondition;
    }

    public String getXdescription() {
        return Xdescription;
    }

    public void setXdescription(String xdescription) {
        Xdescription = xdescription;
    }

    public String getXuid() {
        return Xuid;
    }

    public void setXuid(String xuid) {
        Xuid = xuid;
    }

}
