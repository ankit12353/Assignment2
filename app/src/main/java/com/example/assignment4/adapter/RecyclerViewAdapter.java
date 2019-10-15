package com.example.assignment4.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment4.R;
import com.example.assignment4.model.StudentDetails;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private ArrayList<StudentDetails> studentDetails;
    private ItemClicked clickActivity;

    public RecyclerViewAdapter(ArrayList<StudentDetails> studentDetails, ItemClicked clickActivity) {
        this.clickActivity = clickActivity;
        this.studentDetails=studentDetails;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View studentDetails=layoutInflater.inflate(R.layout.student_detail,parent,false);
        RecyclerViewAdapter.ViewHolder viewHolder=new RecyclerViewAdapter.ViewHolder(studentDetails);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.itemView.setTag(studentDetails.get(position));
        holder.tvDisplayName.setText(studentDetails.get(position).getStudentName());
        holder.tvDisplayClass.setText(String.valueOf(studentDetails.get(position).getStudentClass()));
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickActivity.onItemClicked(holder.getAdapterPosition());
            }
        });
    }



    @Override
    public int getItemCount() {
        return studentDetails.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDisplayName,tvDisplayClass;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDisplayName=itemView.findViewById(R.id.tv_display_name);
            tvDisplayClass=itemView.findViewById(R.id.tv_display_class);
            linearLayout=itemView.findViewById(R.id.ll_display_details);

        }
    }
    public interface ItemClicked{
        void onItemClicked(int position);
    }
}
