package com.example.bookitup;

public class BookActivity
{

    private String Xbook;
    private String Xauthor;



    private String Xedition;
    private String isbn;
    private String Xdate;

    private Float Xprice;
    private String Xcondition;
    private String Xdescription;


    private String uid;



    private String Ximage;
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
    }
    public String getXedition() {
        return Xedition;
    }

    public void setXedition(String xedition) {
        Xedition = xedition;
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
        if(Xuid==null)
        {
            return " ";
        }
        return Xuid;
    }

    public void setXuid(String xuid) {
        Xuid = xuid;
    }
    public String getXimage() {
        return Ximage;
    }

    public void setXimage(String ximage) {
        Ximage = ximage;
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
