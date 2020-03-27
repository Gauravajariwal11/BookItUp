package com.example.bookitup.ui.home;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookitup.BookActivity;
import com.example.bookitup.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseUser;

public class BookDetailsView {

    public class BookDetailsActivity extends AppCompatActivity {

        private ImageView coverIM;
        private TextView bookTV, authorTV, editionTV, isbnTV, sellerTV, conditionTV, priceTV, dateTV, descriptionTV;
        private BookActivity receivedBook;
        private CollapsingToolbarLayout mCollapsingToolbarLayout;

        private void initializeWidgets(){
            coverIM = findViewById(R.id.coverIM);

            bookTV= findViewById(R.id.bookTV);
            authorTV= findViewById(R.id.descriptionTV);
            editionTV= findViewById(R.id.editionTV);
            isbnTV= findViewById(R.id.isbnTV);
            sellerTV= findViewById(R.id.sellerTV);
            conditionTV= findViewById(R.id.conditionTV);
            priceTV = findViewById(R.id.priceTV);
            dateTV = findViewById(R.id.dateTV);
        }

        private void receiveAndShowData(){

                bookTV.setText(receivedBook.getXbook());
                authorTV.setText(receivedBook.getXauthor());
                editionTV.setText(receivedBook.getXedition());
                isbnTV.setText(receivedBook.getXisbn());
                sellerTV.setText(receivedBook.getUid());

              //  mCollapsingToolbarLayout.setTitle(receivedScientist.getName());
            }

        }

//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.book_details);
//
//            initializeWidgets();
//            receiveAndShowData();
//        }
//    }
}
