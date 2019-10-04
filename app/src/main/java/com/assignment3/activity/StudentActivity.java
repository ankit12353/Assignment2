package com.assignment3.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.assignment3.R;
import com.assignment3.model.StudentDetails;
import com.assignment3.util.CommonUtil;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    EditText etName, etRollNo, etClass;
    TextView tvLabel;
    ImageButton ibSort,ibListGrid;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        etName = findViewById(R.id.et_name);
        etClass = findViewById(R.id.et_class);
        etRollNo = findViewById(R.id.et_rollNo);
        btnSubmit = findViewById(R.id.btn_submit);
        tvLabel=findViewById(R.id.tv_label);
        ibListGrid=findViewById(R.id.ib_list_grid);
        ibSort=findViewById(R.id.ib_sort);

        tvLabel.setText(R.string.label_add_student);
        ibSort.setVisibility(View.INVISIBLE);
        ibListGrid.setVisibility(View.INVISIBLE);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Integer.parseInt(etClass.getText().toString().trim());
                    try {
                        Integer.parseInt(etRollNo.getText().toString().trim());
                        submitData();
                    }
                    catch (Exception e){
                        CommonUtil.showSnackBar(StudentActivity.this,getResources().getString(R.string.label_invalid_roll));
                    }
                }catch (Exception e){
                    CommonUtil.showSnackBar(StudentActivity.this,getResources().getString(R.string.label_invalid_class));
                }
            }
        });

        int code=getIntent().getIntExtra("Code",0);
        final StudentDetails studentList=getIntent().getParcelableExtra("Object");
        if (code==2){

            etName.setText(getResources().getString(R.string.label_name)+studentList.getTvDisplayName());
            etRollNo.setText(getResources().getString(R.string.label_roll)+String.valueOf(studentList.getTvDisplayRollNo()));
            etClass.setText(getResources().getString(R.string.label_class)+String.valueOf(studentList.getTvDisplayClass()));

            etName.setBackground(getResources().getDrawable(R.drawable.et_view_data));
            etRollNo.setBackground(getResources().getDrawable(R.drawable.et_view_data));
            etClass.setBackground(getResources().getDrawable(R.drawable.et_view_data));

            etName.setInputType(InputType.TYPE_NULL);
            etClass.setInputType(InputType.TYPE_NULL);
            etRollNo.setInputType(InputType.TYPE_NULL);

            tvLabel.setText(R.string.label_view);
            ibSort.setVisibility(View.INVISIBLE);
            ibListGrid.setVisibility(View.INVISIBLE);

            btnSubmit.setText(R.string.label_back);
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
        if (code==3){
            tvLabel.setText(R.string.label_update);
            ibSort.setVisibility(View.INVISIBLE);
            ibListGrid.setVisibility(View.INVISIBLE);
            btnSubmit.setText(R.string.label_update);

            etRollNo.setInputType(InputType.TYPE_NULL);
            etRollNo.setBackground(getResources().getDrawable(R.drawable.et_view_data));

            final String etUpdateName=etName.getText().toString().trim();
//            final String etUpdateClass=etClass.getText().toString().trim();
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    studentList.setTvDisplayName(etUpdateName);
//                    studentList.setTvDisplayClass(Integer.parseInt(etUpdateClass));
                    finish();
                }
            });

        }

    }

    public void submitData() {
        String etAddName = etName.getText().toString().trim();
        String etAddClass = etClass.getText().toString().trim();
        String etAddRollNo = etRollNo.getText().toString().trim();



        if (isValidate()) {
            StudentDetails studentList = new StudentDetails(etAddName, Integer.parseInt(etAddRollNo), Integer.parseInt(etAddClass));

            Intent intent = new Intent(StudentActivity.this, StudentListActivity.class);
            intent.putExtra("Object", studentList);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private boolean isValidate() {
        String etAddName = etName.getText().toString().trim();
        String etAddClass = etClass.getText().toString().trim();
        String etAddRollNo = etRollNo.getText().toString().trim();
        String namePattern = ("^[a-zA-Z\\s]*$");
        if (etAddName.isEmpty()) {
            CommonUtil.showSnackBar(StudentActivity.this, getResources().getString(R.string.label_enter_name));
            return false;
        } else if (etAddName.equals(namePattern)){
            CommonUtil.showSnackBar(StudentActivity.this,getResources().getString(R.string.label_invalid_name));
            return false;
        }
        else if (etAddClass.isEmpty()) {
            CommonUtil.showSnackBar(StudentActivity.this, getResources().getString(R.string.enter_class));
            return false;
        }
        else if (etAddRollNo.isEmpty()) {
            CommonUtil.showSnackBar(StudentActivity.this, getResources().getString(R.string.enter_roll));
            return false;
        }else if (Integer.parseInt(etAddClass)>12){
            CommonUtil.showSnackBar(StudentActivity.this, getResources().getString(R.string.label_wrong_class));
            return false;
        }else if (Integer.parseInt(etAddClass)<0){
            CommonUtil.showSnackBar(StudentActivity.this, getResources().getString(R.string.label_wrong_class));
            return false;
        }else if(Integer.parseInt(etAddRollNo)<0){
            CommonUtil.showSnackBar(StudentActivity.this,getResources().getString(R.string.label_invalid_roll));
            return false;
        }
        else {
            return true;
        }

    }

}

