package com.example.coursework1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnViewSchedules, btnAddSchedules;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAddSchedules = findViewById(R.id.btnAddSchedules);
        btnViewSchedules = findViewById(R.id.btnViewSchedules);

        btnAddSchedules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddACourse.class);
                startActivity(intent);
            }
        });

        btnViewSchedules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CourseRecyclerView.class);
                startActivity(intent);
            }
        });
    }
}