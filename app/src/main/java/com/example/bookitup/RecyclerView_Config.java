package com.example.bookitup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private BooksAdapter mBooksAdapter;
    public void setConfig(RecyclerView recyclerView, Context context, List<BookActivity> books, List<String> keys){
        mContext = context;
        mBooksAdapter = new BooksAdapter(books,keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mBooksAdapter);
    }

    class BookItemView extends RecyclerView.ViewHolder {
        private TextView mTitle;
        private TextView mAuthor;
        private TextView mISBN;
        private TextView mCategory;

        private String key;
        public BookItemView(ViewGroup parent){
            super(LayoutInflater.from(mContext).inflate(R.layout.list_view,parent,false));
            mTitle = (TextView) itemView.findViewById(R.id.book_name_text);
            mAuthor = (TextView) itemView.findViewById(R.id.author_name);
            mCategory = (TextView) itemView.findViewById(R.id.edition);
            mISBN = (TextView) itemView.findViewById(R.id.isbn);

        }
        public void bind(BookActivity book, String key){
            mTitle.setText(book.getXbook());
            mAuthor.setText(book.getXauthor());
            mCategory.setText(book.getXdescription());
            mISBN.setText(book.getXisbn());
            this.key = key;
        }
    }

    class BooksAdapter extends RecyclerView.Adapter<BookItemView>{
        private List<BookActivity> mBookList;
        private List<String> mKeys;

        public BooksAdapter(List<BookActivity> mBookList, List<String> mKeys) {
            this.mBookList = mBookList;
            this.mKeys = mKeys;
        }


        @Override
        public BookItemView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new BookItemView((parent));
        }

        @Override
        public void onBindViewHolder( BookItemView holder, int position) {
            holder.bind(mBookList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mBookList.size();
        }
    }
}