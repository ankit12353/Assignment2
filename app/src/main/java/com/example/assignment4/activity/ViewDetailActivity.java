package com.example.assignment4.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.assignment4.R;
import com.example.assignment4.model.StudentDetails;

import static com.example.assignment4.fragment.StudentListFragment.STUDENT_OBJ;

public class ViewDetailActivity extends AppCompatActivity {
    private ImageButton ibSort,ibGrid;
    private TextView tvLabelHome;
    private EditText etViewName,etViewClass,etViewRoll;
    private Button btnViewBack;
    private StudentDetails studentDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        init();

        studentDetails=getIntent().getParcelableExtra(STUDENT_OBJ);

        viewDetails();

        btnViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    private void viewDetails() {
        ibGrid.setVisibility(View.INVISIBLE);
        ibSort.setVisibility(View.INVISIBLE);
        tvLabelHome.setText(getResources().getString(R.string.btn_name_view));

        etViewName.setBackground(getResources().getDrawable(R.drawable.et_view_data));
        etViewClass.setBackground(getResources().getDrawable(R.drawable.et_view_data));
        etViewRoll.setBackground(getResources().getDrawable(R.drawable.et_view_data));

        etViewName.setInputType(InputType.TYPE_NULL);
        etViewClass.setInputType(InputType.TYPE_NULL);
        etViewRoll.setInputType(InputType.TYPE_NULL);
        btnViewBack.setText(R.string.label_back);

        etViewName.setText(studentDetails.getStudentName());
        etViewRoll.setText(String.valueOf(studentDetails.getStudentRoll()));
        etViewClass.setText(String.valueOf(studentDetails.getStudentClass()));
    }

    private void init() {
        ibSort=findViewById(R.id.ib_sort);
        ibGrid=findViewById(R.id.ib_grid);
        tvLabelHome=findViewById(R.id.tv_label);
        etViewName=findViewById(R.id.et_name);
        etViewRoll=findViewById(R.id.et_roll);
        etViewClass=findViewById(R.id.et_class);
        btnViewBack=findViewById(R.id.btn_add_details);
    }
}
