package com.example.coursework1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class AddACourse extends AppCompatActivity {

    EditText edtStart, edtEnd, edtTypeOfClass, edtDescription, edtCapacity;
    TextInputLayout priceContainer, durationContainer;
    TextInputEditText edtDuration, edtPricePerClass;
    TimePickerDialog startTime, endTime;
    Spinner spnDayOfTheWeek;
    Button btnAdd;
    Calendar calendar;
    int currentHour, currentMinute;
    String amPm;
    AlertDialog.Builder alertDialog;
    DataHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_acourse);

        edtTypeOfClass = findViewById(R.id.edtTypeOfClass);
        edtStart = findViewById(R.id.edtStart);
        edtEnd = findViewById(R.id.edtEnd);
        edtPricePerClass = findViewById(R.id.edtPricePerClass);
        edtDescription = findViewById(R.id.edtDescription);
        edtDuration = findViewById(R.id.edtDuration);
        spnDayOfTheWeek = findViewById(R.id.spnDayOfTheWeek);
        btnAdd = findViewById(R.id.btnAdd);
        priceContainer = findViewById(R.id.priceContainer);
        durationContainer = findViewById(R.id.durationContainer);
        edtCapacity = findViewById(R.id.edtCapacity);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTypeOfClass = edtTypeOfClass.getText().toString();
                String strDuration = edtDuration.getText().toString();
                String strStartTime = edtStart.getText().toString();
                String strEndTime = edtEnd.getText().toString();
                String strDescription = edtDescription.getText().toString();
                String strPricePerClass = edtPricePerClass.getText().toString();
                String strCapacity = edtCapacity.getText().toString();
                String strSpnDayOfTheWeek = spnDayOfTheWeek.getSelectedItem().toString();
                db = new DataHelper(AddACourse.this);

                if(strTypeOfClass.equals("")) {
                    edtTypeOfClass.setError("Required Field");
                    Toast.makeText(AddACourse.this, "Required fields cannot be empty", Toast.LENGTH_LONG).show();
                }else if (strStartTime.equals("")) {
                    edtStart.setError("Required Field");
                    Toast.makeText(AddACourse.this,"Required fields cannot be empty",Toast.LENGTH_LONG).show();
                } else if (strPricePerClass.equals("")) {
                    edtPricePerClass.setError("Required Field");
                    Toast.makeText(AddACourse.this,"Required fields cannot be empty",Toast.LENGTH_LONG).show();
                } else if (strDuration.equals("")) {
                    edtDuration.setError("Required Field");
                    Toast.makeText(AddACourse.this,"Required fields cannot be empty",Toast.LENGTH_LONG).show();
                } else if (strEndTime.equals("")) {
                    edtEnd.setError("Required Field");
                    Toast.makeText(AddACourse.this,"Required fields cannot be empty",Toast.LENGTH_LONG).show();
                } else if (strCapacity.equals(" ")) {
                    edtCapacity.setError("Required Field");
                    Toast.makeText(AddACourse.this,"Required fields cannot be empty",Toast.LENGTH_LONG).show();
                } else if (strSpnDayOfTheWeek.equals("Choose A Day")) {
                    Toast.makeText(AddACourse.this,"Required fields cannot be empty",Toast.LENGTH_LONG).show();
                }else {
                    alertDialog = new AlertDialog.Builder(AddACourse.this);
                    alertDialog.setTitle("Details Entered");
                    alertDialog.setMessage("Type of Class: " + strTypeOfClass + "\n" + "Duration: " + strDuration + "min\n" +
                            "\t\t\tTime of course\n" + "Start At: " + strStartTime + "\t\t" + "End At: " +
                            strEndTime + "\nDay of the week: " + strSpnDayOfTheWeek + "\n" + "Capacity: " + strCapacity  + "\nPrice per Class: £" + strPricePerClass + "\nDescription: " + strDescription + "\n");
                    alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertDialog.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            boolean insertData;
                            insertData = db.insert(strTypeOfClass, strStartTime, strEndTime, strSpnDayOfTheWeek, strCapacity, strDuration, strPricePerClass,strDescription);
                            if(insertData){
                                edtTypeOfClass.setText(" ");
                                edtCapacity.setText(" ");
                                edtDescription.setText(" ");
                                edtEnd.setText(" ");
                                edtStart.setText(" ");
                                edtDuration.setText(" ");
                                edtPricePerClass.setText(" ");

                                Toast.makeText(AddACourse.this, "Course added successfully", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(AddACourse.this, "Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    alertDialog.show();
                }
            }
        });

        edtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);
                startTime = new TimePickerDialog(AddACourse.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int    minute) {
                        if(hourOfDay >= 12){
                            amPm = "PM";
                        }else{
                            amPm = "AM";
                        }
                        edtStart.setText(String.format("%02d:%02d",hourOfDay,minute) + amPm);
                    }
                },currentHour,currentMinute,false);
                startTime.show();
            }
        });

        edtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);
                endTime = new TimePickerDialog(AddACourse.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        edtEnd.setText(String.format("%02d:%02d", hourOfDay, minute) + amPm);
                    }
                },currentHour,currentMinute,false);
                endTime.show();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.dayOfTheWeek, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnDayOfTheWeek.setAdapter(adapter);

    }
}