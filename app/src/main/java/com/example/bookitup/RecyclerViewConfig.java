package com.example.bookitup;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request;
import com.android.volley.Response;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.bookitup.ui.books.Edit_Delete;
import com.example.bookitup.ui.books.MyBookView;
import com.example.bookitup.ui.home.BookDetailsView;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class RecyclerViewConfig {
    private Context mContext;
    private RequestQueue mQueue;

    private BooksAdapter mBooksAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<BookActivity> books, List<String> keys,Boolean state){
        mContext = context;

        mBooksAdapter = new BooksAdapter(books,keys,state);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mBooksAdapter);
    }

    class BookItemView extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mAuthor;
        private TextView mISBN;
        private TextView mEdition;
        private ImageView mImage;
        private String mUid;

        private Boolean myState;
        private String key;
        public BookItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.list_view,parent,false));
            mTitle = itemView.findViewById(R.id.book_name_text);
            mAuthor = itemView.findViewById(R.id.author_name);
            mEdition = itemView.findViewById(R.id.edition);
            mISBN = itemView.findViewById(R.id.isbn);
            mImage = itemView.findViewById(R.id.cover_image);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if(myState)
                    {
                        //Toast.makeText(view.getContext(),"Opening this Book, Please wait!",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(mContext, MyBookView.class);
                        intent.putExtra("key",key);
                        intent.putExtra("title",mTitle.getText().toString());
                        intent.putExtra("author",mAuthor.getText().toString());
                        intent.putExtra("edition",mEdition.getText().toString());
                        intent.putExtra("isbn",mISBN.getText().toString());
                        mContext.startActivity(intent);
                    }
                    else {
                        //Toast.makeText(view.getContext(),"Opening this Book, Please wait!",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(mContext, BookDetailsView.class);
                        intent.putExtra("key",key);
                        intent.putExtra("title",mTitle.getText().toString());
                        intent.putExtra("author",mAuthor.getText().toString());
                        intent.putExtra("edition",mEdition.getText().toString());
                        intent.putExtra("isbn",mISBN.getText().toString());
                        mContext.startActivity(intent);
                    }

                }
            });
        }

        public void bind(BookActivity book, String key, Boolean state){
            //getting book cover
            mQueue = Volley.newRequestQueue(mContext);
            String url = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + book.getIsbn();
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
                                        Glide.with(mContext).load("https"+imgLink).error(R.drawable.ic_nocover).into(mImage);

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
            mTitle.setText(book.getXbook());
            mAuthor.setText("by "+book.getXauthor());
            mEdition.setText(book.getEdition() +" edition");
            mISBN.setText("ISBN: "+book.getIsbn());

            mUid = book.getXuid();
            myState =state;
            this.key = key;
        }
    }

    class BooksAdapter extends RecyclerView.Adapter<BookItemView>{
        private List<BookActivity> mBookList;
        private List<String> mKeys;
        private Boolean mState;

        public BooksAdapter(List<BookActivity> mBookList, List<String> mKeys,Boolean state) {
            this.mBookList = mBookList;
            this.mKeys = mKeys;
            this.mState =state;
        }


        @Override
        public BookItemView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BookItemView(parent);
        }

        @Override
        public void onBindViewHolder( BookItemView holder, int position) {
            holder.bind(mBookList.get(position), mKeys.get(position), mState);
        }

        @Override
        public int getItemCount() {
            return mBookList.size();
        }
    }
}