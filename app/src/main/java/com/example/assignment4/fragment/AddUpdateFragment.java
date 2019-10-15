package com.example.assignment4.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.assignment4.R;
import com.example.assignment4.activity.MainActivity;
import com.example.assignment4.model.StudentDetails;

public class AddUpdateFragment extends Fragment {
    private EditText etGetName, etGetRollNo, etGetClass;
    private Button btnAdd;
    private Context mContext;
    private StudentDetails studentDetails;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_add_update_student, container, false);


        etGetName = view.findViewById(R.id.et_name);
        etGetClass = view.findViewById(R.id.et_class);
        etGetRollNo = view.findViewById(R.id.et_roll);
        btnAdd = view.findViewById(R.id.btn_add_details);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Integer.parseInt(etGetClass.getText().toString().trim());
                    try {
                        Integer.parseInt(etGetRollNo.getText().toString().trim());
                        addDetails();


                    } catch (Exception e) {
                        etGetRollNo.setError(getResources().getString(R.string.error_roll));
                    }
                } catch (Exception e) {
                    etGetClass.setError(getResources().getString(R.string.error_valid_class));
                }
//                addDetails();
            }

        });

        return view;

    }


    //add data
    private void addDetails() {
        String etAddName = etGetName.getText().toString().trim();
        String etAddClass = etGetClass.getText().toString().trim();
        String etAddRollNo = etGetRollNo.getText().toString().trim();
        if (isValidate()) {
            ((MainActivity) mContext).switchViewPager();
            studentDetails = new StudentDetails(etAddName, Integer.parseInt(etAddClass), Integer.parseInt(etAddRollNo));
            ((MainActivity) mContext).addData(studentDetails);
            clearEt();
        }
    }

    //clear all et's
    private void clearEt() {
        etGetName.setText("");
        etGetRollNo.setText("");
        etGetClass.setText("");
    }

    //validation
    private boolean isValidate() {
        String etAddName = etGetName.getText().toString().trim();
        String etAddClass = etGetClass.getText().toString().trim();
        String etAddRollNo = etGetClass.getText().toString().trim();
        String namePattern = ("^[a-zA-Z\\s]*$");


        if (etAddName.isEmpty()) {
            etGetName.setError(getResources().getString(R.string.label_empty_field));
            return false;
        } else if (!etAddName.matches(namePattern)) {
            etGetName.setError(getResources().getString(R.string.error_valid_name));
            return false;
        } else if (etAddClass.isEmpty()) {
            etGetClass.setError(getResources().getString(R.string.label_empty_field));
            return false;
        } else if (etAddRollNo.isEmpty()) {
            etGetRollNo.setError(getResources().getString(R.string.label_empty_field));
            return false;
        } else if (Integer.parseInt(etAddClass) > 12) {
            etGetClass.setError(getResources().getString(R.string.error_wrong_class));
            return false;
        } else if (Integer.parseInt(etAddClass) < 0) {
            etGetClass.setError(getResources().getString(R.string.error_wrong_class));
            return false;
        } else if (Integer.parseInt(etAddRollNo) < 0) {
            etGetRollNo.setError(getResources().getString(R.string.error_roll));
            return false;
        } else {
            return true;
        }

    }
}
