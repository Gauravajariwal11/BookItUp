package com.example.bookitup.ui.books;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.example.bookitup.UserInformation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MyBookView extends AppCompatActivity {
    private TextView edBook;
    private TextView edAuthor;
    private TextView edEdition;
    private TextView edIsbn;
    private TextView edCondition;
    private TextView edPrice;
    private TextView edDate;
    private TextView edDescription;
    private ImageView mImage;
    private TextView eSeller;

    private String key;
    private String book;
    private String author;
    private String edition;
    private String isbn;
    private String condition;
    private Float price;
    private String date;
    private String description;
    private String seller;

    private Button editBtn;
    private Button deleteBtn;

    private FirebaseDatabase database;


    private RequestQueue mQueue;
    BookActivity bookActivity;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = database.getReference("Booklist");
        setContentView(R.layout.my_book_view);
        bookActivity = new BookActivity();
        mImage = findViewById(R.id.coverIM);
        key = getIntent().getStringExtra("key");
        book = getIntent().getStringExtra("title");
        author = getIntent().getStringExtra("author");
        edition = getIntent().getStringExtra("edition");
        isbn = getIntent().getStringExtra("isbn");
        //Call from database Booklist
        String searchString = isbn.substring(6);
        databaseRef.orderByChild("isbn")
                .equalTo(searchString)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data: dataSnapshot.getChildren()){
                            condition = data.getValue(BookActivity.class).getXcondition();
                            edCondition.setText("Book condition: " +condition);
                            price = data.getValue(BookActivity.class).getXprice();
                            edPrice.setText("Listed for: $" + price.toString());
                            description = data.getValue(BookActivity.class).getXdescription();
                            edDescription.setText("Description: " + description.toString());
                            date = data.getValue(BookActivity.class).getDate();
                            edDate.setText("Date posted: " + date.substring(0,10));
                            seller = data.getValue(BookActivity.class).getXuid();
                            //Retrieve seller info
                            final DatabaseReference databaseUserRef = database.getReference("Users");
                            databaseUserRef.orderByKey().equalTo(seller)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for(DataSnapshot data: dataSnapshot.getChildren()){
                                                String sellerName = "Listed by: " + data.getValue(UserInformation.class).getfname() + " "
                                                        + data.getValue(UserInformation.class).getlname();
                                                eSeller = findViewById(R.id.sellerTV);
                                                eSeller.setText(sellerName);

                                            }
                                        }
                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




        price = getIntent().getFloatExtra("price", (float)0.0);
        date = getIntent().getStringExtra("date");
        description = getIntent().getStringExtra("description");
        //getting book cover
        mQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn.substring(6) ;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray items = response.getJSONArray("items");
                            for (int i = 0; i < items.length(); i++) {
                                JSONObject jsonObject1 = items.getJSONObject(i);
                                JSONObject volumeInfo = jsonObject1.getJSONObject("volumeInfo");
                                if(volumeInfo.has("imageLinks")){
                                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                                    String imgLink = imageLinks.getString("smallThumbnail").substring(4);
                                    Glide.with(getApplicationContext()).load("https"+imgLink).error(R.drawable.ic_nocover).into(mImage);

                                    System.out.println(imgLink);


                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        mQueue.add(jsonObjectRequest);



        edBook = findViewById(R.id.bookTV);
        edBook.setText(book);
        edAuthor = findViewById(R.id.authorTV);
        edAuthor.setText(author);
        edEdition = findViewById(R.id.editionTV);
        edEdition.setText(edition);
        edIsbn = findViewById(R.id.isbnTV);
        edIsbn.setText(isbn);
        edCondition =  findViewById(R.id.conditionTV);
        edPrice = findViewById(R.id.priceTV);
        edPrice.setText(price.toString());
        edDate = findViewById(R.id.dateTV);
        edDate.setText(date);
        edDescription = findViewById(R.id.descriptionTV);
        edDescription.setText(description);

        editBtn = findViewById(R.id.edit);
        deleteBtn = findViewById(R.id.delete);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyBookView.this,Edit_Delete.class);
                startActivity(intent);
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener(){
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
                        Toast.makeText(MyBookView.this,"Book record has been deleted successfully",Toast.LENGTH_LONG).show();
                        finish();return;
                    }
                });
            }
        });
    }
}
