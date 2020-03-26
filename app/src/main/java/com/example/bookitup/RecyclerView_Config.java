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
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class RecyclerView_Config {
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
        private TextView mCondition;
        private TextView mPrice;
        private TextView mDate;
        private TextView mDescription;
        private String mUid;

        private Boolean mstate;
        private String key;
        public BookItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.list_view,parent,false));
            mTitle = (TextView) itemView.findViewById(R.id.book_name_text);
            mAuthor = (TextView) itemView.findViewById(R.id.author_name);
            mEdition = (TextView) itemView.findViewById(R.id.edition);
            mISBN = (TextView) itemView.findViewById(R.id.isbn);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View view) {
                    if(mstate)
                    {
                        Toast.makeText(view.getContext(),"Opening this Book, Please wait!",Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(mContext, Edit_Delete.class);
                        intent.putExtra("key",key);
                        intent.putExtra("title",mTitle.getText().toString());
                        intent.putExtra("author",mAuthor.getText().toString());
                        intent.putExtra("edition",mEdition.getText().toString());
                        intent.putExtra("isbn",mISBN.getText().toString());
                        //intent.putExtra("condition",mCondition.getText().toString());
                        //intent.putExtra("isbn",mPrice.getText().toString());
                        //intent.putExtra("isbn",mDate.getText().toString());
                        //intent.putExtra("isbn",mDescription.getText().toString());


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
            //mCondition.setText(book.getXcondition());
            //mPrice.setText(book.getXprice().toString());
            //mDate.setText(book.getXdate());
            //mDescription.setText(book.getXdescription());
            mUid = book.getXuid();
            mstate=state;
            this.key = key;
        }
    }

    class BooksAdapter extends RecyclerView.Adapter<BookItemView>{
        private List<BookActivity> mBookList;
        private List<String> mKeys;
        private Boolean mstate;

        public BooksAdapter(List<BookActivity> mBookList, List<String> mKeys,Boolean state) {
            this.mBookList = mBookList;
            this.mKeys = mKeys;
            this.mstate=state;
        }


        @Override
        public BookItemView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BookItemView(parent);
        }

        @Override
        public void onBindViewHolder( BookItemView holder, int position) {
            holder.bind(mBookList.get(position), mKeys.get(position),mstate);
        }

        @Override
        public int getItemCount() {
            return mBookList.size();
        }
    }
}