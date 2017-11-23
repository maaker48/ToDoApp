package com.example.stephan.todoapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public Button bt_add;
    public EditText et_input;
//    public TodoDatabase db;
    private TodoDatabase db;
    private TodoAdapter adapter;
    public ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = TodoDatabase.getInstance(getApplicationContext());
        Context context = getApplicationContext();
        Cursor cursor = db.selectAll();

        // Find ListView to populate
        lvItems = findViewById(R.id.results_listview);

        // Setup cursor adapter using cursor from last step
        adapter = new TodoAdapter(context, cursor);

        // Attach cursor adapter to the ListView
        lvItems.setAdapter(adapter);

        lvItems.setOnItemClickListener(lvListener);
        lvItems.setOnItemLongClickListener(lvLongListener);
        et_input  = findViewById(R.id.input);


        bt_add = findViewById(R.id.bt_add);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = et_input.getText().toString();
                if (et_input.length() != 0) {
                    db.insert(newEntry);
                    et_input.setText("");
                    updateData();
                } else {
                    Log.d("geeninput", "geeninput");
                }

            }
        });
    }

    private final AdapterView.OnItemClickListener lvListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Cursor selectedItem = (Cursor)adapterView.getItemAtPosition(position);
            int id = selectedItem.getInt(0);
            int oldValue = selectedItem.getInt(2);
            String oldString = selectedItem.getString(1);
            if (oldValue == 0) {
                oldValue = 1;
                db.update(id, oldValue);

                updateData();
            }
            else{
                oldValue = 0;
                db.update(id, oldValue);
                updateData();
            }
            Log.d("Click", oldString);
            Log.d("Click", Integer.toString(oldValue));
        }
    };
    private final AdapterView.OnItemLongClickListener lvLongListener  = new AdapterView.OnItemLongClickListener () {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
            Cursor longItemClick = (Cursor)adapterView.getItemAtPosition(position);
            int id = longItemClick.getInt(0);

            db.delete(id);
            updateData();
            return true;
        }
    };


    private void updateData(){
        Cursor cursor = db.selectAll();
        adapter.swapCursor(cursor);
        ListView lvItems = findViewById(R.id.results_listview);
        lvItems.setAdapter(adapter);
        et_input  = findViewById(R.id.input);

    }
}
