package com.example.coursework1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
import android.os.Bundle;
import java.util.ArrayList;

public class CourseRecyclerView extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<CourseList> dataHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_recycler_view);


        DataHelper dataHelper = new DataHelper(CourseRecyclerView.this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(CourseRecyclerView.this));

        dataHolder = new ArrayList<>();
        Cursor cursor = dataHelper.fetch();

        while(cursor.moveToNext()){
            CourseList obj = new CourseList(cursor.getString(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
            dataHolder.add(obj);
        }

        CourseAdapter adapter = new CourseAdapter(CourseRecyclerView.this, dataHolder, dataHelper);
        recyclerView.setAdapter(adapter);
    }
}