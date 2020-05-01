package com.example.bookitup.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookitup.BookActivity;
import com.example.bookitup.BookDatabaseEdit;
import com.example.bookitup.R;
import com.example.bookitup.RecyclerViewConfig;
import com.example.bookitup.activity_scan;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView mResultList;
    //Ann
    private EditText mSearchField;
    private ImageButton mSearchBtn;
    private Spinner mSearchOptions;


    private DatabaseReference mUserDatabase;

    private Context homeContext;

    private String option;

    private Button btn_scan;

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_home);
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        homeContext = getContext();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Booklist");


        mSearchField = view.findViewById(R.id.search_field);
        mSearchBtn = view.findViewById(R.id.search_btn);

        mSearchOptions = view.findViewById(R.id.search_options);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(homeContext,
                R.array.search_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSearchOptions.setAdapter(adapter);
        mSearchOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                option = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mResultList = view.findViewById(R.id.result_list);
        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(homeContext));

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchOption = "";
                switch (option){
                    case "Book":
                        searchOption = "xbook";
                        break;
                    case "Author":
                        searchOption = "xauthor";
                        break;
                    case "ISBN":
                        searchOption = "isbn";
                        break;
                }

                String searchText = mSearchField.getText().toString();

                new BookDatabaseEdit().readBooksFiltered(searchText, searchOption, new BookDatabaseEdit.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<BookActivity> books, List<String> keys) {
                        new RecyclerViewConfig().setConfig(mResultList,getContext(),books,keys,false);
                    }

                    @Override
                    public void DataIsInserted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                });
            }
        });


        //piling all books on the main page
        mResultList = view.findViewById(R.id.result_list);
        new BookDatabaseEdit().readBooks(new BookDatabaseEdit.DataStatus() {
            @Override
            public void DataIsLoaded(List<BookActivity> books, List<String> keys) {
                new RecyclerViewConfig().setConfig(mResultList,getContext(),books,keys,false);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

        btn_scan = (Button) view.findViewById(R.id.scan_btn);

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeFragment.this.getActivity(), activity_scan.class);
                startActivity(intent);
            }
        });

        return view;
    }

}