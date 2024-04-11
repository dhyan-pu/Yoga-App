package com.example.coursework1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import java.util.ArrayList;

public class ClassRecyclerView extends AppCompatActivity {

    RecyclerView classRecyclerView;
    ArrayList<ClassList> classList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_recycler_view);

        ClassDataHelper dataHelper = new ClassDataHelper(ClassRecyclerView.this);

        classRecyclerView = findViewById(R.id.classRecyclerView);
        classRecyclerView.setLayoutManager(new LinearLayoutManager(ClassRecyclerView.this));
        classList = new ArrayList<>();
        Cursor cursor = dataHelper.fetch();

        while(cursor.moveToNext()){
            ClassList obj = new ClassList(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getBlob(3));
            classList.add(obj);
        }

        ClassAdapter adapter = new ClassAdapter(ClassRecyclerView.this, classList, dataHelper);
        classRecyclerView.setAdapter(adapter);
        
    }
}