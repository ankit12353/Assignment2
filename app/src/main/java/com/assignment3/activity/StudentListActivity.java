package com.assignment3.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import com.assignment3.R;
import com.assignment3.adapter.StudentListAdapter;
import com.assignment3.model.StudentDetails;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class StudentListActivity extends AppCompatActivity implements StudentListAdapter.ItemClicked {
    TextView tvDisplayNoRecord;
    Button btnAddStudent;
    ImageButton ibSort,ibListGrid;
    RecyclerView.Adapter studentListAdapter;

    RecyclerView recyclerView;
    ArrayList<StudentDetails> studentList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        btnAddStudent=findViewById(R.id.btn_addStudent);
        tvDisplayNoRecord=findViewById(R.id.tv_display_noRecords);
        ibListGrid=findViewById(R.id.ib_list_grid);
        ibSort=findViewById(R.id.ib_sort);
        recyclerView=findViewById(R.id.rv_student_list);

        studentListAdapter=new StudentListAdapter(studentList,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(studentListAdapter);

        //sort list wrt name and roll no
        ibSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu sortPopupMenu=new PopupMenu(StudentListActivity.this,ibSort);
                sortPopupMenu.getMenuInflater().inflate(R.menu.popup_menu,sortPopupMenu.getMenu());
                sortPopupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.ib_sort_name:
                                Collections.sort(studentList, new Comparator<StudentDetails>() {
                                    @Override
                                    public int compare(StudentDetails studentList, StudentDetails t1) {
                                        return studentList.getTvDisplayName().compareToIgnoreCase(t1.getTvDisplayName());

                                    }
                                });
                                studentListAdapter.notifyDataSetChanged();
                                return true;
                            case R.id.ib_sort_roll:
                                Collections.sort(studentList, new Comparator<StudentDetails>() {
                                    @Override
                                    public int compare(StudentDetails r1, StudentDetails r2) {
                                        String rollOne=String.valueOf(r1.getTvDisplayRollNo());
                                        String rollTwo=String.valueOf(r2.getTvDisplayRollNo());
                                        return rollOne.compareToIgnoreCase(rollTwo);
                                    }
                                });
                                studentListAdapter.notifyDataSetChanged();
                                return true;
                            default:
                                    return false;
                        }

                    }
                });
                sortPopupMenu.show();
            }
        });

        //add student button event
        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(StudentListActivity.this,StudentActivity.class),1);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==1){
            if (resultCode== RESULT_OK){
                StudentDetails student=data.getParcelableExtra("Object");
                studentList.add(student);
                if (studentList.size()==0){
                    recyclerView.setVisibility(View.INVISIBLE);
                    tvDisplayNoRecord.setVisibility(View.VISIBLE);
                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    tvDisplayNoRecord.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @Override
    public void onItemClicked(final int position) {
        final Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        Button btnView=dialog.findViewById(R.id.btn_view);
        Button btnUpdate=dialog.findViewById(R.id.btn_update);
        Button btnDelete=dialog.findViewById(R.id.btn_delete);
        dialog.show();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentList.remove(position);
                studentListAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(StudentListActivity.this,StudentActivity.class);
                i.putExtra("Code",3);
                i.putExtra("Object",studentList.get(position));
                startActivity(i);
                dialog.dismiss();
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(StudentListActivity.this,StudentActivity.class);
                i.putExtra("Code",2);
                i.putExtra("Object",studentList.get(position));
                startActivity(i);
                dialog.dismiss();
            }
        });

    }
}
