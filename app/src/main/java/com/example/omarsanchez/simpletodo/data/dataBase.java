package com.example.omarsanchez.simpletodo.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by omarsanchez on 3/3/17.
 */

public class DataBase extends SQLiteOpenHelper {

    final static String name = "ToDoDataBase.sqlite";
    final static int version = 3;
    String table = "CREATE TABLE Task (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, date TEXT, note TEXT, priority TEXT, status INTEGER)";

    public DataBase(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Task");
        db.execSQL(table);
    }
}
