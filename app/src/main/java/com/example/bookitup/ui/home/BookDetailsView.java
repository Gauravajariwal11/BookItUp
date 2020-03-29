package com.example.bookitup.ui.home;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookitup.BookActivity;
import com.example.bookitup.BookDatabaseEdit;
import com.example.bookitup.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class BookDetailsView extends AppCompatActivity {
    private ImageView edImage;
    private TextView edBook;
    private TextView edAuthor;
    private TextView edEdition;
    private TextView edIsbn;
    private TextView edCondition;
    private TextView edPrice;
    private TextView edDate;
    private TextView edDescription;

    private String key;
    private String book;
    private String author;
    private String edition;
    private String isbn;
    private String condition;
    private Float price;
    private String date;
    private String description;

    private Button contactBtn;
    private Button wishlistBtn;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.book_details);
        key = getIntent().getStringExtra("key");
        book = getIntent().getStringExtra("book");
        author = getIntent().getStringExtra("author");
        edition = getIntent().getStringExtra("edition");
        isbn = getIntent().getStringExtra("isbn");
        condition = getIntent().getStringExtra("condition");
        price = getIntent().getFloatExtra("price", (float) 21.2);
        date = getIntent().getStringExtra("date");
        description = getIntent().getStringExtra("description");

        edBook = findViewById(R.id.bookTV);
        edBook.setText(book);
        edAuthor = findViewById(R.id.authorTV);
        edAuthor.setText(author);
        edEdition = findViewById(R.id.editionTV);
        edEdition.setText(edition);
        edIsbn = findViewById(R.id.isbnTV);
        edIsbn.setText(isbn);
        edCondition =  findViewById(R.id.conditionTV);
        edCondition.setText(condition);
        edPrice = findViewById(R.id.priceTV);
        edPrice.setText(price.toString());
        edDate = findViewById(R.id.dateTV);
        edDate.setText(date);
        edDescription = findViewById(R.id.descriptionTV);
        edDescription.setText(description);

        contactBtn = findViewById(R.id.contact);
        wishlistBtn = findViewById(R.id.wishlist);
    }
}