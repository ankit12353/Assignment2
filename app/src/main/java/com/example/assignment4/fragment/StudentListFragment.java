package com.example.assignment4.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment4.servicePkg.AsyncTaskAddStudentDetail;
import com.example.assignment4.database.DatabaseHelper;
import com.example.assignment4.R;
import com.example.assignment4.activity.MainActivity;
import com.example.assignment4.activity.ViewDetailActivity;
import com.example.assignment4.adapter.RecyclerViewAdapter;
import com.example.assignment4.model.StudentDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StudentListFragment extends Fragment implements RecyclerViewAdapter.ItemClicked,AsyncTaskAddStudentDetail.AsyncTaskCallback {
    private Button btnAddStudent;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter rvAdapter;
    private Context mContext;
    private TextView tvNoRecord;
    public static final String STUDENT_OBJT="Object";
    private Boolean updateClicked=true;
    private ArrayList<StudentDetails> mStudentDetails = new ArrayList<>();
    private Boolean mIsClickedAddStudent=true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);


        tvNoRecord = view.findViewById(R.id.tv_display_no_record);
        btnAddStudent = view.findViewById(R.id.btn_add_student);
        recyclerView = view.findViewById(R.id.rv_student_list);

        Cursor cursor=new DatabaseHelper(mContext).getAllStudentDetails();
        while (cursor.moveToNext()){
            String name=cursor.getString(0);
            int studentClass=cursor.getInt(1);
            int roll=cursor.getInt(2);
            StudentDetails st=new StudentDetails(name,studentClass,roll,0);
            mStudentDetails.add(st);
        }

        rvAdapter = new RecyclerViewAdapter(mStudentDetails, this);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(rvAdapter);
        labelVisibility();

        //add student onclick listener
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).switchViewPager();
                ((MainActivity)getActivity()).clickedAddStudent(mIsClickedAddStudent);
            }
        });

        return view;
    }

    //getting object from activity
    public void addUpdateData(final int position,final StudentDetails studentDetails) {
        if(studentDetails.getType()==1)
        {
            mStudentDetails.add(studentDetails);
        }else if(studentDetails.getType()==2){
            mStudentDetails.set(position,studentDetails);
        }
        labelVisibility();

    }

    private void labelVisibility() {
        if (mStudentDetails.size() == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            tvNoRecord.setVisibility(View.VISIBLE);
            rvAdapter.notifyDataSetChanged();
        } else {
            tvNoRecord.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            rvAdapter.notifyDataSetChanged();
        }
        rvAdapter.notifyDataSetChanged();
    }

    //list to grid and to list RV
    public void toggleView(final boolean isListShowing) {
        if (isListShowing) {
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            recyclerView.setLayoutManager(linearLayoutManager);

        } else {
            final GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
    }

    //sorting data as name
    public void sortDetailsName(final boolean mIsSortData) {
        if (mIsSortData) {
            Collections.sort(mStudentDetails, new Comparator<StudentDetails>() {
                @Override
                public int compare(StudentDetails studentDetails1, StudentDetails studentDetails2) {
                    String name1 = studentDetails1.getStudentName();
                    String name2 = studentDetails2.getStudentName();
                    return name1.compareToIgnoreCase(name2);
                }
            });
        }
        rvAdapter.notifyDataSetChanged();
    }

    //sorting data as roll no
    public void sortDetailsRoll(final boolean mIsSortData) {
        if (mIsSortData) {
            Collections.sort(mStudentDetails, new Comparator<StudentDetails>() {
                @Override
                public int compare(StudentDetails studentDetails3, StudentDetails studentDetails4) {
                    String roll1 = String.valueOf(studentDetails3.getStudentRoll());
                    String roll2 = String.valueOf(studentDetails4.getStudentRoll());
                    return roll1.compareToIgnoreCase(roll2);
                }
            });
        }
        rvAdapter.notifyDataSetChanged();
    }

    //recyclerview click dialog box
    @Override
    public void onItemClicked(final int position) {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.custom_dialog);

        Button btnView = dialog.findViewById(R.id.btn_view);
        Button btnUpdate = dialog.findViewById(R.id.btn_update);
        Button btnDelete = dialog.findViewById(R.id.btn_delete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StudentDetails studentDetails=mStudentDetails.get(position);
                studentDetails.setType(3);

                new AsyncTaskAddStudentDetail(mContext, StudentListFragment.this).execute(studentDetails);
                dialog.dismiss();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)mContext).dialogClick(position,mStudentDetails.get(position),updateClicked);
                //rvAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, ViewDetailActivity.class);
                intent.putExtra(STUDENT_OBJT,mStudentDetails.get(position));
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void asyncClickListner(boolean isSuccess, StudentDetails studentDetails) {
        if(isSuccess){
            mStudentDetails.remove(studentDetails);
            rvAdapter.notifyDataSetChanged();
        }else{

        }
    }
}
