package com.example.bookitup;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookitup.ui.books.Edit_Delete;
import com.example.bookitup.ui.home.BookDetailsView;

import java.util.List;

public class RecyclerViewConfig {
    private Context mContext;
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
        private String mUid;

        private Boolean myState;
        private String key;
        public BookItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.list_view,parent,false));
            mTitle = itemView.findViewById(R.id.book_name_text);
            mAuthor = itemView.findViewById(R.id.author_name);
            mEdition = itemView.findViewById(R.id.edition);
            mISBN = itemView.findViewById(R.id.isbn);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if(myState)
                    {
                        //Toast.makeText(view.getContext(),"Opening this Book, Please wait!",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(mContext, Edit_Delete.class);
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
            mTitle.setText(book.getXbook());
            mAuthor.setText(book.getXauthor());
            mEdition.setText(book.getXdescription());
            mISBN.setText(book.getXisbn());
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