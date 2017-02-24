package com.example.omarsanchez.simpletodo.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.omarsanchez.simpletodo.R;
import com.example.omarsanchez.simpletodo.model.Task;

public class DetailActivity extends AppCompatActivity {
    TextView name, date, note, priority, status;
    Task task;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (savedInstanceState != null) {
            task = (Task) savedInstanceState.getSerializable(getString(R.string.detail));
            position = savedInstanceState.getInt(getString(R.string.position));
        } else {
            Bundle bundle = getIntent().getExtras();
            task = (Task) bundle.getSerializable(getString(R.string.detail));
            position = bundle.getInt(getString(R.string.position));
        }
        name = (TextView) findViewById(R.id.name);
        date = (TextView) findViewById(R.id.date);
        note = (TextView) findViewById(R.id.note);
        priority = (TextView) findViewById(R.id.priority);
        status = (TextView) findViewById(R.id.status);

        name.setText(task.getTaskName());
        date.setText(task.getDate());
        note.setText(task.getNote());
        priority.setText(task.getPriority().name());
        status.setText(task.getStatus());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                confirmDelete();
                break;
            case R.id.cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(getString(R.string.detail), task);
        outState.putInt(getString(R.string.position), position);
    }

    public void confirmDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.warning))
                .setMessage(getString(R.string.shure))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.putExtra(getString(R.string.position), position);
                        setResult(RESULT_OK, intent);
                        DetailActivity.this.finish();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create()
                .show();
    }
}
