package com.example.growup.ui.course.components;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.growup.R;

public class SearchBarView extends LinearLayout {
    private EditText searchInput;
    private LinearLayout layoutFilter;
    private ImageButton filterButton;

    public SearchBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.course_fragment_list, this);
        searchInput= findViewById(R.id.inputResearch);
//        searchButton = findViewById(R.id.search_button);
        filterButton = findViewById(R.id.filter_button);
        layoutFilter = findViewById(R.id.layoutFilter);
    }

//    public void setOnSearchListener(View.OnClickListener listener) {
//        searchButton.setOnClickListener(listener);
//    }

    public void setOnFilterClickListener() {
        filterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutFilter.setVisibility(View.VISIBLE);
            }
        });
    }


//    public String getQuery() {
//        return searchEditText.getText().toString();
//    }
}

