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
    private String Xprice;
    private String Xcondition;
    private String Xdescription;
    private String Xuid;
    private String sellerName;
    private String email;
    public BookActivity() {
    }

    public BookActivity(String xbook, String isbn, String xauthor, String xprice, String xcondition, String xdescription, String sellerName, String email) {
        Xbook = xbook;
        this.isbn = isbn;
        Xauthor = xauthor;
        Xprice = xprice;
        Xcondition = xcondition;
        Xdescription = xdescription;
        date = ServerValue.TIMESTAMP.toString();
        this.sellerName = sellerName;
        this.email = email;
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

    public String getXprice() {
        return Xprice;
    }

    public void setXprice(String xprice) {
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

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
