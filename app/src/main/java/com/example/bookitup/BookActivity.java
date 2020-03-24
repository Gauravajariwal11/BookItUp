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
    private String Xuid;
    public BookActivity() {
    }

    public BookActivity(String xbook, String xisbn, String xauthor, float xprice, String xcondition, String xdescription) {
        Xbook = xbook;
        Xisbn = xisbn;
        Xauthor = xauthor;
        Xprice = xprice;
        Xcondition = xcondition;
        Xdescription = xdescription;
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

    public String getXuid() {
        if(Xuid==null)
        {
            return " ";
        }
        return Xuid;
    }

    public void setXuid(String xuid) {
        Xuid = xuid;
    }
}
