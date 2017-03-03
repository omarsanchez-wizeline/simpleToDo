package com.example.omarsanchez.simpletodo.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.omarsanchez.simpletodo.R;
import com.example.omarsanchez.simpletodo.model.Task;
import com.example.omarsanchez.simpletodo.util.Priority;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    TextView name, date, note;
    Spinner priority, status;
    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = (TextView) findViewById(R.id.add_name);
        date = (TextView) findViewById(R.id.add_date);
        note = (TextView) findViewById(R.id.add_note);
        priority = (Spinner) findViewById(R.id.add_priority);
        status = (Spinner) findViewById(R.id.add_status);
        priority.setAdapter(new ArrayAdapter<Priority>(this, android.R.layout.simple_spinner_item, Priority.values()));
        status.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{getString(R.string.todo), getString(R.string.done)}));
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, R.style.AppTheme, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                saveObject();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveObject() {
        if (validate()) {
            Task task = new Task(name.getText().toString(), date.getText().toString(), note.getText().toString(), getPriority(), getStatus());
            Intent intent = new Intent();
            intent.putExtra(getString(R.string.add_task), task);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private boolean validate() {
        if (name.getText().toString().isEmpty()) {
            name.setError(getString(R.string.requiered));
            return false;
        }
        return true;
    }

    private boolean getStatus() {
        int position = status.getSelectedItemPosition();
        return !(position == 0);
    }

    public Priority getPriority() {
        return Priority.values()[priority.getSelectedItemPosition()];

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.set(year, month, dayOfMonth);
        date.setText(dateFormatter.format(tempCalendar.getTime()));
    }
}
