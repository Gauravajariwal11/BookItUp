package com.example.bookitup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookActivity
{

    private String Xbook;
    private String Xisbn;
    private String Xdate;
    private String Xauthor;
    private float Xprice;
    private String Xcondition;
    private String Xdescription;
    private  String edition;
    private String image;

    public BookActivity() {
    }

    public String getXbook() {
        return Xbook;
    }

    public void setXbook(String xbook) {
        Xbook = xbook;
    }



    public String getXisbn() {

        return Xisbn;
    }

    public void setXisbn(String xisbn) {
        Xisbn = xisbn;
    }



    public String getedition() {

        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }


    public String getImage() {

        return image;
    }
    public void setImage(String image){
        this.image = image;
    }



    public String getXdate() {
        return Xdate;
    }

    public void setXdate(String xdate) {
        Xdate = xdate;
    }

    public String getXauthor() {
        return Xauthor;
    }

    public void setXauthor(String xauthor) {
        Xauthor = xauthor;
    }

    public float getXprice() {
        return Xprice;
    }

    public void setXprice(float xprice) {
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
}
