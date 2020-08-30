package com.example.bookitup.ui.books;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bookitup.BookActivity;
import com.example.bookitup.BookDatabaseEdit;
import com.example.bookitup.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Edit_Delete extends AppCompatActivity {
    private EditText edBook;
    private EditText edAuthor;
    private EditText edEdition;
    private EditText edIsbn;
    private EditText edCondition;
    private EditText edPrice;
    private EditText edDate;
    private EditText edDescription;

    private String key;
    private String book;
    private String author;
    private String edition;
    private String isbn;
    private String condition;
    private String price;
    private String date;
    private String description;

    private Button saveBtn;
    private Button cancelBtn;

    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private RequestQueue mQueue;


        //edit each place holder tiles on MyBooks
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.book_edit_delete);
        key = getIntent().getStringExtra("key");
        book = getIntent().getStringExtra("title");
        author = getIntent().getStringExtra("author");
        edition = getIntent().getStringExtra("edition");
        isbn = getIntent().getStringExtra("isbn");
        condition = getIntent().getStringExtra("condition");
        price = getIntent().getStringExtra("price");
        date = getIntent().getStringExtra("date");
        description = getIntent().getStringExtra("description");

        //New filled information to edit the database
        edBook =  findViewById(R.id.bookBED);
        edBook.setText(book);
        edAuthor = findViewById(R.id.authorBED);
        edAuthor.setText(author);
        edEdition = findViewById(R.id.editionBED);
        edEdition.setText(edition);
        edIsbn = findViewById(R.id.isbnBED);
        edIsbn.setText(isbn);
        edCondition = findViewById(R.id.conditionBED);
        edCondition.setText(condition);
        edPrice = findViewById(R.id.priceBED);
        edPrice.setText(price.toString());
        edDate =  findViewById(R.id.dateBED);
        edDate.setText(date);
        edDescription = findViewById(R.id.descriptionBED);
        edDescription.setText(description);

        saveBtn =  findViewById(R.id.save);
        cancelBtn = findViewById(R.id.cancel);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {               //saves data when button clicked
                BookActivity book = new BookActivity();
                book.setXbook(edBook.getText().toString());
                book.setXauthor(edAuthor.getText().toString());
                book.setEdition(edEdition.getText().toString());
                book.setIsbn(edIsbn.getText().toString());
                book.setXcondition(edCondition.getText().toString());
                book.setXprice(edPrice.getText().toString());
                book.setDate(edDate.getText().toString());
                book.setXdescription(edDescription.getText().toString());


                new BookDatabaseEdit().updateBook(key, book, new BookDatabaseEdit.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<BookActivity> books, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(Edit_Delete.this,"Book record has been updated successfully",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Edit_Delete.this, MyBookView.class);
                startActivity(intent);
            }
        });
    }

}
