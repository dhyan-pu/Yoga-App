package com.example.coursework1;

import static com.example.coursework1.DataHelper.TABLE_NAME;
import android.annotation.SuppressLint;
import android.app.*;
import android.content.*;
import android.database.sqlite.SQLiteDatabase;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public  class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.myviewholder> {

    ArrayList<CourseList> dataHolder;
    DataHelper dataHelper;
    Context context;
    Calendar calendar;
    int currentHour, currentMinute;
    String amPm;
    TimePickerDialog startTime, endTime;
    public CourseAdapter(Context context, ArrayList<CourseList> dataHolder, DataHelper dataHelper) {
        this.context = context;
        this.dataHolder = dataHolder;
        this.dataHelper = dataHelper;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_list, parent, false);
         return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.courseName.setText(dataHolder.get(position).getTypeOfClass());
        holder.dayOfTheWeek.setText(dataHolder.get(position).getDayOfTheWeek());
        holder.duration.setText(String.format("Duration: %s", dataHolder.get(position).getDuration()) + "min");
        holder.capacity.setText(String.format("Capacity: %s", dataHolder.get(position).getCapacity()));
        holder.startAt.setText(dataHolder.get(position).getStartAt() + "-" + dataHolder.get(position).getEndAt());
        holder.pricePerClass.setText(String.format("Price: £%s", dataHolder.get(position).getPricePerClass()));
        holder.description.setText(String.format("Description:  %s", dataHolder.get(position).getDescription()));


        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.activity_add_acourse);

                EditText edtTypeOfClass = dialog.findViewById(R.id.edtTypeOfClass);
                EditText edtStart = dialog.findViewById(R.id.edtStart);
                EditText edtEnd = dialog.findViewById(R.id.edtEnd);
                TextInputEditText edtPricePerClass = dialog.findViewById(R.id.edtPricePerClass);
                EditText edtDescription = dialog.findViewById(R.id.edtDescription);
                TextInputEditText edtDuration = dialog.findViewById(R.id.edtDuration);
                Spinner spnDayOfTheWeek = dialog.findViewById(R.id.spnDayOfTheWeek);
                Button btnAdd = dialog.findViewById(R.id.btnAdd);
                EditText edtCapacity = dialog.findViewById(R.id.edtCapacity);
                TextView txtHeading = dialog.findViewById(R.id.txtHeading);

                txtHeading.setText(R.string.update_course);
                btnAdd.setText(R.string.update);

                edtTypeOfClass.setText(dataHolder.get(position).getTypeOfClass());
                edtStart.setText(dataHolder.get(position).getStartAt());
                edtEnd.setText(dataHolder.get(position).getEndAt());
                edtPricePerClass.setText(dataHolder.get(position).getPricePerClass());
                edtDescription.setText(dataHolder.get(position).getDescription());
                edtDuration.setText(dataHolder.get(position).getDuration());
                String selectedDay = dataHolder.get(position).getDayOfTheWeek();
                edtCapacity.setText(dataHolder.get(position).getCapacity());
                int spinnerPosition = 0;
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, R.array.dayOfTheWeek, android.R.layout.simple_spinner_item);
                for (int i = 0; i < adapter.getCount(); i++) {
                    if (adapter.getItem(i).toString().equals(selectedDay)) {
                        spinnerPosition = i;
                        break;
                    }
                }
                spnDayOfTheWeek.setSelection(spinnerPosition);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String strTypeOfClass = edtTypeOfClass.getText().toString();
                        String strDuration = Objects.requireNonNull(edtDuration.getText()).toString();
                        String strStartTime = edtStart.getText().toString();
                        String strEndTime = edtEnd.getText().toString();
                        String strDescription = edtDescription.getText().toString();
                        String strPricePerClass = Objects.requireNonNull(edtPricePerClass.getText()).toString();
                        String strCapacity = edtCapacity.getText().toString();
                        String strSpnDayOfTheWeek = spnDayOfTheWeek.getSelectedItem().toString();

                        if(strTypeOfClass.equals("")) {
                            edtTypeOfClass.setError("Required Field");
                            Toast.makeText(context, "Required fields cannot be empty", Toast.LENGTH_LONG).show();
                        }else if (strStartTime.equals("")) {
                            edtStart.setError("Required Field");
                            Toast.makeText(context,"Required fields cannot be empty",Toast.LENGTH_LONG).show();
                        } else if (strPricePerClass.equals("")) {
                            edtPricePerClass.setError("Required Field");
                            Toast.makeText(context,"Required fields cannot be empty",Toast.LENGTH_LONG).show();
                        } else if (strDuration.equals("")) {
                            edtDuration.setError("Required Field");
                            Toast.makeText(context,"Required fields cannot be empty",Toast.LENGTH_LONG).show();
                        } else if (strEndTime.equals("")) {
                            edtEnd.setError("Required Field");
                            Toast.makeText(context,"Required fields cannot be empty",Toast.LENGTH_LONG).show();
                        } else if (strCapacity.equals(" ")) {
                            edtCapacity.setError("Required Field");
                            Toast.makeText(context,"Required fields cannot be empty",Toast.LENGTH_LONG).show();
                        } else if (strSpnDayOfTheWeek.equals("Choose A Day")) {
                            Toast.makeText(context,"Required fields cannot be empty",Toast.LENGTH_LONG).show();
                        }else {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                            alertDialog.setTitle("Details Entered");
                            alertDialog.setMessage("Type of Class: " + strTypeOfClass + "\n" + "Duration: " + strDuration + "min\n" +
                                    "\t\t\tTime of course\n" + "Start At: " + strStartTime + "\t\t" + "End At: " +
                                    strEndTime + "\nDay of the week:" + strSpnDayOfTheWeek + "\n" + "Capacity: " + strCapacity  + "\nPrice per Class: £" + strPricePerClass + "\nDescription: " + strDescription + "\n");
                            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            alertDialog.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dataHolder.set(position, new CourseList(strTypeOfClass ,strStartTime, strEndTime,strSpnDayOfTheWeek,strCapacity, strDuration,strPricePerClass,strDescription));
                                    notifyItemChanged(position);

                                    Toast.makeText(context, "Data Updated successfully", Toast.LENGTH_LONG).show();
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
                        startTime = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
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
                        endTime = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
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
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spnDayOfTheWeek.setAdapter(adapter);
                dialog.show();
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    CourseList itemToDelete = dataHolder.get(adapterPosition);
                    String courseName = itemToDelete.getTypeOfClass();
                    SQLiteDatabase database = dataHelper.getWritableDatabase();
                    int deletedRows = database.delete(TABLE_NAME, "Course_Name=?", new String[]{courseName});
                    database.close();

                    if (deletedRows > 0) {
                        dataHolder.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition);
                        Toast.makeText(v.getContext(), "Course deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        holder.viewOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(v.getContext(), v);
                popup.inflate(R.menu.item_menu);
                popup.show();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menuViewClass) {
                            Intent intent = new Intent(v.getContext(), ClassRecyclerView.class);
                            v.getContext().startActivity(intent);
                            return true;
                        } else if (item.getItemId() == R.id.menuAddClass) {
                            Intent intent = new Intent(v.getContext(), AddAClass.class);
                            v.getContext().startActivity(intent);
                            return true;
                        }
                        return false;
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    class myviewholder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        TextView courseName, dayOfTheWeek, duration, capacity, startAt, pricePerClass, description, viewOptions;
        Button btnEdit, btnDelete;
        public myviewholder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.materialCardView);
            courseName = itemView.findViewById(R.id.txtClsName);
            dayOfTheWeek = itemView.findViewById(R.id.txtDayOfTheWeek);
            duration = itemView.findViewById(R.id.txtDur);
            capacity = itemView.findViewById(R.id.txtCap);
            startAt = itemView.findViewById(R.id.txtStartAt);
            pricePerClass = itemView.findViewById(R.id.txtPricePCls);
            description = itemView.findViewById(R.id.txtDes);
            viewOptions = itemView.findViewById(R.id.txtViewOptions);

            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
