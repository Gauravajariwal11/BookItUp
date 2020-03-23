package com.example.bookitup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class Books {
    private String bookName, authorName, edition, isbn, cover;

    public Books(){}

    public String getBookName() {
        return bookName;
    }
    public void setBookName(String bookName) { this.bookName = bookName; }

    public String getAuthorName(){return authorName;}
    public void setAuthorName(String authorName) { this.authorName = authorName;}

    public String getEdition(){return edition;}
    public void setEdition(String edition){this.edition=edition;}

    public String getIsbn(){return isbn;}
    public void setIsbn(String isbn){this.isbn=isbn;}

    public String getCover(){return cover;}
    public void setCover(String cover){this.cover = cover;}

//    public Books(String bookName, String authorName, String edition, long isbn){
//        this.bookName = bookName;
//        this.authorName = authorName;
//        this.edition = edition;
//        this.isbn = isbn;
//    }
}
