package com.assignment3.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.assignment3.R;
import com.assignment3.model.StudentDetails;
import java.util.ArrayList;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {
    private ArrayList<StudentDetails> studentList;
    private ItemClicked clickActivity;

    public StudentListAdapter(ArrayList<StudentDetails> studentList, ItemClicked itemClicked) {
        this.studentList = studentList;
        this.clickActivity=itemClicked;
    }

    @Override
    public StudentListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View studentList=layoutInflater.inflate(R.layout.student_details,parent,false);
        StudentListAdapter.ViewHolder viewHolder=new StudentListAdapter.ViewHolder(studentList);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StudentListAdapter.ViewHolder holder, final int position) {
        holder.itemView.setTag(studentList.get(position));
        holder.tvDisplayName.setText("Name:"+String.valueOf(studentList.get(position).getTvDisplayName()));
        holder.tvDisplayClass.setText("Class:"+String.valueOf(studentList.get(position).getTvDisplayClass()));

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDisplayClass,tvDisplayName,tvDisplayRollNo;
        private LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDisplayClass=itemView.findViewById(R.id.tv__display_class);
            tvDisplayName=itemView.findViewById(R.id.tv__display_name);
            tvDisplayRollNo=itemView.findViewById(R.id.tv_display_roll);
            llMain=itemView.findViewById(R.id.ll_main);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickActivity.onItemClicked(studentList.indexOf(view.getTag()));
                }
            });
        }
    }
    public interface ItemClicked{
        void onItemClicked(int position);
    }
}

