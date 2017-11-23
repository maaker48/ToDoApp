package com.example.stephan.todoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Stephan on 22-11-2017.
 */

public class TodoDatabase extends SQLiteOpenHelper {
    private static TodoDatabase instance;
    // Databse Info
    private static final String DATABASE_NAME = "ToDoDatabase";
    private static final Integer DATABASE_VERSION = 1;

    private static final String TABLE_TODOS = "todos";

    private static final String KEY_TODO_ID = "_id";
    private static final String KEY_TODO_TITLE = "title";
    private static final String KEY_TODO_COMP = "completed";

    private TodoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableTodo = "CREATE TABLE " + TABLE_TODOS +
                "(" +
                KEY_TODO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_TODO_TITLE + " TEXT ," +
                KEY_TODO_COMP + " INTEGER DEFAULT 0" +
                ")";
        sqLiteDatabase.execSQL(createTableTodo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        if (i != i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TODOS);
            onCreate(sqLiteDatabase);
        }
    }
    public static TodoDatabase getInstance(Context context){
        if (instance != null) {

            return instance;
        }
        else {
            return instance = new TodoDatabase(context, DATABASE_NAME, null, DATABASE_VERSION );
        }
    }
    public Cursor selectAll(){
        String selectall = "SELECT * FROM todos";
        SQLiteDatabase db = instance.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectall, null);
        return cursor;
    }
    public void insert(String item){
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TODO_TITLE, item);
        SQLiteDatabase db = instance.getWritableDatabase();
        Log.d("adding","addData: Adding " + item + " to " + KEY_TODO_TITLE);
        db.insert(TABLE_TODOS, null, contentValues);
    }
    public void update(int id, int completed){
        SQLiteDatabase db = instance.getWritableDatabase();
        String updateCompleted = " UPDATE " + TABLE_TODOS +
                " SET completed = "+ completed + " WHERE _id = "+ id+";";
        db.execSQL(updateCompleted);
        Log.d("yolo", updateCompleted);
    }
    public void delete(int id){
        SQLiteDatabase db = instance.getWritableDatabase();
        String deleteToDo = "DELETE FROM " + TABLE_TODOS + " Where _id = " + id + ";";
        db.execSQL(deleteToDo);
    }

}
