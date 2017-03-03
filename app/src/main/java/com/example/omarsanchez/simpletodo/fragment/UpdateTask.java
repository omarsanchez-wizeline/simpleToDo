package com.example.omarsanchez.simpletodo.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.omarsanchez.simpletodo.R;
import com.example.omarsanchez.simpletodo.adapter.ToDoAdapter;
import com.example.omarsanchez.simpletodo.interfaces.Updated;
import com.example.omarsanchez.simpletodo.model.Task;
import com.example.omarsanchez.simpletodo.util.Priority;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by omarsanchez on 3/3/17.
 */

public class UpdateTask extends AlertDialog.Builder {
    TextView name, date, note;
    Spinner priority, status;
    DatePickerDialog datePickerDialog;
    Task task;

    public UpdateTask(@NonNull Context context) {
        super(context);
    }

    public static UpdateTask newInstance(Context context) {
        return new UpdateTask(context);
    }


    public AlertDialog createDialog(final Activity activity, final Task task) {
        this.task = task;
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_add, null);
        name = (TextView) view.findViewById(R.id.add_name);
        date = (TextView) view.findViewById(R.id.add_date);
        note = (TextView) view.findViewById(R.id.add_note);
        priority = (Spinner) view.findViewById(R.id.add_priority);
        status = (Spinner) view.findViewById(R.id.add_status);
        priority.setAdapter(new ArrayAdapter<Priority>(getContext(), android.R.layout.simple_spinner_item, Priority.values()));
        status.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, new String[]{getContext().getString(R.string.todo), getContext().getString(R.string.done)}));
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getContext(), R.style.AppTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                Calendar tempCalendar = Calendar.getInstance();
                tempCalendar.set(year, month, dayOfMonth);
                date.setText(dateFormatter.format(tempCalendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        name.setText(task.getTaskName());
        date.setText(task.getDate());
        note.setText(task.getNote());
        priority.setSelection(task.getPriority().ordinal());
        status.setSelection(task.isDone() ? 1 : 0);

        this.setView(view);
        this.setPositiveButton(getContext().getString(R.string.save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                task.setTaskName(name.getText().toString());
                task.setDate(date.getText().toString());
                task.setNote(note.getText().toString());
                task.setPriority(Priority.values()[priority.getSelectedItemPosition()]);
                task.setStatus(!(status.getSelectedItemPosition() == 0));
                ((Updated) activity).onUpdated(task);
            }
        });
        this.setNegativeButton(getContext().getString(R.string.cancel), null);
        return this.create();
    }
}
