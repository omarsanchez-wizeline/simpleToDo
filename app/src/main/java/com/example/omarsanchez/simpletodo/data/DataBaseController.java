package com.example.omarsanchez.simpletodo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.omarsanchez.simpletodo.model.Task;
import com.example.omarsanchez.simpletodo.util.Priority;

import java.util.ArrayList;

/**
 * Created by omarsanchez on 3/3/17.
 */

public class DataBaseController {
    public static DataBaseController dataBaseController;
    private DataBase dataBase;

    public DataBaseController(Context context) {
        dataBase = new DataBase(context);
    }

    public static DataBaseController getInstance(Context context) {
        if (dataBaseController == null) {
            dataBaseController = new DataBaseController(context);
        }
        return dataBaseController;
    }

    public long insertTask(Task task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", task.getTaskName());
        contentValues.put("date", task.getDate());
        contentValues.put("note", task.getNote());
        contentValues.put("priority", task.getPriority().name());
        contentValues.put("status", task.isDone() ? 1 : 0);
        SQLiteDatabase db = dataBase.getWritableDatabase();
        long id = db.insert("Task", null, contentValues);
        db.close();
        return id;

    }

    public void deleteTask(Task task) {
        SQLiteDatabase db = dataBase.getWritableDatabase();
        int test = db.delete("Task", "id = ?", new String[]{String.valueOf(task.getId())});
        db.close();
    }

    public void updateTask(Task task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", task.getTaskName());
        contentValues.put("date", task.getDate());
        contentValues.put("note", task.getNote());
        contentValues.put("priority", task.getPriority().name());
        contentValues.put("status", task.isDone() ? 1 : 0);
        SQLiteDatabase db = dataBase.getWritableDatabase();
        int test = db.update("Task", contentValues, "id = ?", new String[]{String.valueOf(task.getId())});
        db.close();
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        SQLiteDatabase db = dataBase.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * from Task", null);
        if (c.moveToFirst()) {
            do {
                tasks.add(new Task(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), Priority.valueOf(c.getString(4)), c.getInt(5) == 1));
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return tasks;
    }
}
