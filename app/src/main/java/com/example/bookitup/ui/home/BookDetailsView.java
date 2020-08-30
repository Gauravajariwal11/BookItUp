package com.example.bookitup.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bookitup.BookActivity;

import com.example.bookitup.R;

import com.example.bookitup.SellerAdapter;
import com.example.bookitup.SellerModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BookDetailsView extends AppCompatActivity {
    private TextView edBook;
    private TextView edAuthor;
    private TextView edEdition;
    private TextView edIsbn;

    private ImageView mImage;

    private String book;
    private String author;
    private String edition;
    private String isbn;
    private String condition;
    private String price;
    private String date;
    private String description;
    private String seller;
    private String email;

    private Button contactBtn;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private FirebaseDatabase database;
    private ArrayList<SellerModel> models = new ArrayList<>();
    private SellerModel m;

    private RequestQueue mQueue;
    BookActivity bookActivity;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = database.getReference("Booklist");
        setContentView(R.layout.book_details);

        //recycler view
        recyclerView = findViewById(R.id.sellerList);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);




        bookActivity = new BookActivity();
        mImage = findViewById(R.id.coverIM);
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
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            condition = data.getValue(BookActivity.class).getXcondition();
                            price = data.getValue(BookActivity.class).getXprice();
                            description = data.getValue(BookActivity.class).getXdescription();
                            date = data.getValue(BookActivity.class).getDate();
                            seller = data.getValue(BookActivity.class).getSellerName();
                            email = data.getValue(BookActivity.class).getEmail();

                            //Set book details
                            m = new SellerModel();
                            m.setCondition(condition);
                            m.setPrice(price);
                            m.setDescription(description);
                            m.setDate(date);
                            m.setBookName(book);

                            //Set seller info
                            m.setEmail(email);
                            m.setName(seller);
                            models.add(m);
                            m = null;

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        // specify an adapter
        mAdapter = new SellerAdapter(this, models);

        recyclerView.setAdapter(mAdapter);

        //getting book cover
        mQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn.substring(6);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray items = response.getJSONArray("items");
                            for (int i = 0; i < items.length(); i++) {
                                JSONObject jsonObject1 = items.getJSONObject(i);
                                JSONObject volumeInfo = jsonObject1.getJSONObject("volumeInfo");
                                if (volumeInfo.has("imageLinks")) {
                                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                                    String imgLink = imageLinks.getString("smallThumbnail").substring(4);
                                    Glide.with(getApplicationContext()).load("https" + imgLink).error(R.drawable.ic_nocover).into(mImage);

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




//retrieve fields
        edBook = findViewById(R.id.bookTV);
        edBook.setText(book);
        edAuthor = findViewById(R.id.authorTV);
        edAuthor.setText(author);
        edEdition = findViewById(R.id.editionTV);
        edEdition.setText(edition);
        edIsbn = findViewById(R.id.isbnTV);
        edIsbn.setText(isbn);



    }

}