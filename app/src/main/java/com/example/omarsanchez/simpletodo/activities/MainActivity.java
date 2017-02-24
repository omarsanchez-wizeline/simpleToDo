package com.example.omarsanchez.simpletodo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.omarsanchez.simpletodo.R;
import com.example.omarsanchez.simpletodo.adapter.ToDoAdapter;
import com.example.omarsanchez.simpletodo.model.Task;
import com.example.omarsanchez.simpletodo.util.Priority;

public class MainActivity extends AppCompatActivity {
    private static final int ADD_REQUEST = 1;
    public static final int DETAIL_REQUEST = 2;
    private RecyclerView recyclerView;
    private EditText inputText;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        inputText = (EditText) findViewById(R.id.inputText);
        addButton = (Button) findViewById(R.id.addButton);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(ToDoAdapter.getInstance());

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!inputText.getText().toString().isEmpty()) {
                    Task task = new Task();
                    task.setTaskName(inputText.getText().toString());
                    task.setPriority(Priority.High);
                    ToDoAdapter.getInstance().addTask(task);
                    inputText.setText("");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                startActivityForResult(new Intent(this, AddActivity.class), ADD_REQUEST);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ADD_REQUEST:
                if (resultCode == RESULT_OK && data != null) {
                    Log.d("request", "success");
                    Task task = (Task) data.getSerializableExtra(getString(R.string.add_task));
                    ToDoAdapter.getInstance().addTask(task);
                }
                break;
            case DETAIL_REQUEST:
                if (resultCode == RESULT_OK) {
                    Log.d("detail", "success");
                    if (data != null) {
                        ToDoAdapter.getInstance().removeTask(data.getIntExtra(getString(R.string.position), -1));
                    }
                }
                break;
        }
    }
}
