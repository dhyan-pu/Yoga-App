package com.example.coursework1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class AddAClass extends AppCompatActivity {
    TextView date;
    EditText teacherName, addComments;
    ShapeableImageView imageView;
    FloatingActionButton pic;
    Calendar calendar;
    Button addClass;
    ClassDataHelper db;
    DatePickerDialog.OnDateSetListener mDateSetListener;
    ActivityResultLauncher<Intent> resultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aclass);

        teacherName = findViewById(R.id.edtTName);
        date = findViewById(R.id.txtDatePicker);
        addComments = findViewById(R.id.edtAddComments);
        pic = findViewById(R.id.floatingActionButton);
        addClass = findViewById(R.id.btnClass);
        imageView = findViewById(R.id.imgTeacher);

        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTeacherName = teacherName.getText().toString();
                String strDate = date.getText().toString();
                String strComments = addComments.getText().toString();

                Drawable drawable = imageView.getDrawable();
                Bitmap imageBitmap = null;
                if (drawable instanceof BitmapDrawable) {
                    imageBitmap = ((BitmapDrawable) drawable).getBitmap();
                } else if (drawable instanceof VectorDrawable) {
                    imageBitmap = convertVectorDrawableToBitmap((VectorDrawable) drawable);
                }

                db = new ClassDataHelper(AddAClass.this);

                if(strTeacherName.equals("")) {
                    teacherName.setError("Required Field");
                    Toast.makeText(AddAClass.this, "Required fields cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (strDate.equals("")) {
                    date.setError("Required field");
                    Toast.makeText(AddAClass.this, "Required fields cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] imageBytes = stream.toByteArray();

                    db = new ClassDataHelper(AddAClass.this);
                    boolean insertData;
                    insertData = db.insert(strTeacherName, strDate, strComments, imageBytes);
                    if (insertData) {
                        teacherName.setText(" ");
                        date.setText(" ");
                        addComments.setText(" ");
                        Toast.makeText(AddAClass.this, "Class Added successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AddAClass.this, "Failed", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(AddAClass.this, android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            try {
                                Intent data = result.getData();
                                if (data != null) {
                                    Uri imageUri = data.getData();
                                    Log.d("Image URI", "URI: " + imageUri.toString());
                                    imageView.setImageURI(imageUri);
                                }
                            } catch (Exception e) {
                                Toast.makeText(AddAClass.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String dateChosen = month + "/" + dayOfMonth + "/" + year;
                date.setText(dateChosen);
            }
        };

        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pickImage();
            }
        });
    }
    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        if (intent.resolveActivity(getPackageManager()) != null) {
            resultLauncher.launch(intent);
        } else {
            Toast.makeText(this, "No app available to handle image picking", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap convertVectorDrawableToBitmap(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }
}