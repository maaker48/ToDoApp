package com.example.stephan.todoapp;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Created by Stephan on 22-11-2017.
 */

public class TodoAdapter extends ResourceCursorAdapter {
    public TodoAdapter(Context context, Cursor cursor){
        super(context , R.layout.row_todo, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        int color = cursor.getInt(2);
        if (color == 1){
            tvTitle.setBackgroundColor(Color.GREEN);
        }
        else{
            tvTitle.setBackgroundColor(Color.WHITE);
        }
        tvTitle.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
    }


}
