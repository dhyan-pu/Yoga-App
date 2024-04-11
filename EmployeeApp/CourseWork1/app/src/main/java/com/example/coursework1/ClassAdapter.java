package com.example.coursework1;

import static com.example.coursework1.DataHelper.TABLE_NAME;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.myViewHolder> {
    ArrayList<ClassList> dataHolder;
    ClassDataHelper dataHelper;
    Context context;

    public ClassAdapter(Context context, ArrayList<ClassList> dataHolder, ClassDataHelper dataHelper) {
        this.context = context;
        this.dataHolder = dataHolder;
        this.dataHelper = dataHelper;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_list, parent, false);
        return new ClassAdapter.myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        holder.teacherName.setText(dataHolder.get(position).getTeacherName());
        holder.date.setText(dataHolder.get(position).getDate());
        holder.comments.setText("Additional Comments:\n" + dataHolder.get(position).getComments());
        byte[] imageData = dataHolder.get(position).getImage();
        if (imageData != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            holder.imageView.setImageBitmap(bitmap);
        } else {
            holder.imageView.setImageResource(R.drawable.baseline_person_24);
        }
    }

    @Override
    public int getItemCount() {
        return dataHolder.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        MaterialCardView materialCardView1;
        TextView teacherName, date, comments;
        ShapeableImageView imageView;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            materialCardView1 = itemView.findViewById(R.id.materialCardView1);
            teacherName = itemView.findViewById(R.id.txtName);
            date = itemView.findViewById(R.id.txtDisplayDate);
            comments = itemView.findViewById(R.id.txtAdditionalComments);
            imageView = itemView.findViewById(R.id.imgTeacher);
        }
    }
}
