package com.example.bookitup.ui.books;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookitup.BookActivity;
import com.example.bookitup.BookDatabaseEdit;
import com.example.bookitup.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Edit_Delete extends AppCompatActivity {
    private ImageView edImage;
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

    private Button edEdit_info;
    private Button edDelete;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.book_edit_delete);
        key = getIntent().getStringExtra("key");
        book = getIntent().getStringExtra("book");
        author = getIntent().getStringExtra("author");
        edition = getIntent().getStringExtra("edition");
        isbn = getIntent().getStringExtra("isbn");
        condition = getIntent().getStringExtra("condition");
        price = getIntent().getStringExtra("price");
        date = getIntent().getStringExtra("date");
        description = getIntent().getStringExtra("description");

        edBook =  (EditText) findViewById(R.id.bookBED);
        edBook.setText(book);
        edAuthor = (EditText) findViewById(R.id.authorBED);
        edAuthor.setText(author);
        edEdition = (EditText) findViewById(R.id.editionBED);
        edEdition.setText(edition);
        edIsbn = (EditText) findViewById(R.id.isbnBED);
        edIsbn.setText(isbn);
        edCondition = (EditText) findViewById(R.id.conditionBED);
        edCondition.setText(condition);
        edPrice = (EditText) findViewById(R.id.priceBED);
        edPrice.setText(price.toString());
        edDate = (EditText) findViewById(R.id.dateBED);
        edDate.setText(date);
        edDescription = (EditText) findViewById(R.id.descriptionBED);
        edDescription.setText(description);

        edEdit_info = (Button) findViewById(R.id.edit_infoBED);
        edDelete = (Button) findViewById(R.id.deleteBED);

        edEdit_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                BookActivity book = new BookActivity();
                book.setXbook(edBook.getText().toString());
                book.setXauthor(edAuthor.getText().toString());
                book.setXedition(edEdition.getText().toString());
                book.setXisbn(edIsbn.getText().toString());
                book.setXcondition(edCondition.getText().toString());
                book.setXprice(Float.parseFloat(edPrice.getText().toString()));
                book.setXdate(edDate.getText().toString());
                book.setXdescription(edDescription.getText().toString());
                book.setXuid(getIntent().getStringExtra("uid"));
                //Log.i("uid",getIntent().getStringExtra("uid"));

                new BookDatabaseEdit().updateBook(key, book, new BookDatabaseEdit.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<BookActivity> books, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {
                        Toast.makeText(Edit_Delete.this,"Book record has been Updated Successfully",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
                finish();
            }
        });
        edDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                new BookDatabaseEdit().deleteBook(key, new BookDatabaseEdit.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<BookActivity> books, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {
                        Toast.makeText(Edit_Delete.this,"Book record has been Deleted Successfully",Toast.LENGTH_LONG).show();
                        finish();return;
                    }
                });
            }
        });
    }

}
