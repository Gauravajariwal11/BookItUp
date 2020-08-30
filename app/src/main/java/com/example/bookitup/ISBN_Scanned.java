//package com.example.bookitup;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.android.volley.toolbox.Volley;
//import com.bumptech.glide.Glide;
//import com.example.bookitup.BookActivity;
//import com.example.bookitup.BookDatabaseEdit;
//import com.example.bookitup.R;
//import com.example.bookitup.UserInformation;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.List;
//
//public class ISBN_Scanned extends AppCompatActivity {
//
//    private TextView edBook;
//    private TextView edAuthor;
//    private TextView edEdition;
//    private TextView edIsbn;
//    private TextView edCondition;
//    private TextView edPrice;
//    private TextView edDate;
//    private TextView edDescription;
//    private ImageView mImage;
//    private TextView eSeller;
//
//    private String key;
//    private String book;
//    private String author;
//    private String edition;
//    private String isbn;
//    private String condition;
//    private String price;
//    private String date;
//    private String description;
//    private String seller;
//
//    private Button editBtn;
//    private Button deleteBtn;
//
//    private FirebaseDatabase database;
//
//
//    private RequestQueue mQueue;
//    BookActivity bookActivity;
//
//
//    @SuppressLint("WrongViewCast")
//    @Override
//    protected void onCreate(Bundle saveInstanceState) {
//        super.onCreate(saveInstanceState);
//        database = FirebaseDatabase.getInstance();
//        final DatabaseReference databaseRef = database.getReference("Booklist");
//        setContentView(R.layout.my_book_view);
//        bookActivity = new BookActivity();
//        mImage = findViewById(R.id.coverIM);
//        key = getIntent().getStringExtra("key");
//        book = getIntent().getStringExtra("title");
//        author = getIntent().getStringExtra("author");
//        edition = getIntent().getStringExtra("edition");
//        isbn = getIntent().getStringExtra("isbn");
//        //Call from database Booklist
//        String searchString = isbn.substring(6);
//        databaseRef.orderByChild("isbn");
//                });
//
//
//        price = getIntent().getStringExtra("price");
//        date = getIntent().getStringExtra("date");
//        description = getIntent().getStringExtra("description");
//
//
//        //getting book cover
//        mQueue = Volley.newRequestQueue(getApplicationContext());
//        String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn.substring(6);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONArray items = response.getJSONArray("items");
//                            for (int i = 0; i < items.length(); i++) {
//                                JSONObject jsonObject1 = items.getJSONObject(i);
//                                JSONObject volumeInfo = jsonObject1.getJSONObject("volumeInfo");
////                                JSONObject author = jsonObject1.getJSONObject("authors");
//                                if (volumeInfo.has("imageLinks") && volumeInfo.has("authors")) {
//                                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
//                                    JSONObject authors = volumeInfo.getJSONObject("authors");
//                                    String imgLink = imageLinks.getString("smallThumbnail").substring(4);
//                                    String author = authors.getString("authors");
//                                    Glide.with(getApplicationContext()).load("https" + imgLink).error(R.drawable.ic_nocover).into(mImage);
//
//                                    System.out.println(imgLink);
//                                    System.out.println(author);
//
//
//                                }
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO: Handle error
//
//                    }
//                });
//        mQueue.add(jsonObjectRequest);
//
//        Intent intent = new Intent(ISBN_Scanned.this, AddBookActivity.class);
//        startActivity(intent);
//
//    }
//}