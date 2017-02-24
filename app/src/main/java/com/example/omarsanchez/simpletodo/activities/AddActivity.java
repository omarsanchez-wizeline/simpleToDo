package com.example.omarsanchez.simpletodo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.omarsanchez.simpletodo.R;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
