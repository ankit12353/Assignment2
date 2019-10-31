package com.example.assignment4.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.assignment4.servicePkg.AsyncTaskAddStudentDetail;
import com.example.assignment4.R;
import com.example.assignment4.activity.MainActivity;
import com.example.assignment4.model.StudentDetails;
import com.example.assignment4.servicePkg.IntentServiceAddUpdateStudentDetail;
import com.example.assignment4.servicePkg.ServiceAddStudentDetail;

import java.util.Objects;

import static com.example.assignment4.servicePkg.IntentServiceAddUpdateStudentDetail.INTENT_SERVICE_BROADCAST;
import static com.example.assignment4.servicePkg.IntentServiceAddUpdateStudentDetail.STUDENT_OBJ_INTENT_SERVICE_BROADCAST;
import static com.example.assignment4.servicePkg.ServiceAddStudentDetail.ADD_BROADCAST;
import static com.example.assignment4.servicePkg.ServiceAddStudentDetail.ADD_BROADCAST_STUDENT_OBJ;

public class AddUpdateFragment extends Fragment implements AsyncTaskAddStudentDetail.AsyncTaskCallback {
    private EditText etGetName, etGetRollNo, etGetClass;
    private Button btnAdd;
    private Context mContext;
    private StudentDetails studentDetails;
    private boolean mIsAddStudentClicked = true, mIsBroadRequired = false;
    private int clickedPosition;
    public static final String STUDENT_OBJ = "STUDENT_OBJ";
    public static final String STUDENT_OBJ_INTENT_SERVICE = "STUDENT_OBJ_INTENT_SERVICE";
    private int task;
    private int isService;

    public AddUpdateFragment() {

    }

    public AddUpdateFragment(final boolean isBroadRequired) {
        this.mIsBroadRequired = isBroadRequired;
    }

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
                addStudentDetails();
            }

        });

        return view;

    }

    //add details through async task , intentservices and service
    public void addStudentDetails() {
        if (isValidate()) {
            //using previous pop up for add button
            final Dialog dialog = new Dialog(mContext);
            dialog.setContentView(R.layout.custom_dialog);

            Button btnAsyncTask = dialog.findViewById(R.id.btn_update);
            Button btnService = dialog.findViewById(R.id.btn_view);
            Button btnIntentService = dialog.findViewById(R.id.btn_delete);

            //renaming the button names as per use
            btnAsyncTask.setText(R.string.btn_async);
            btnService.setText(R.string.btn_service);
            btnIntentService.setText(R.string.btn_intent_service);

            btnAsyncTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isService = 1;
                    if (mIsAddStudentClicked) {
                        addStudentDetailServices(1);
                        dialog.dismiss();
                    } else {
                        updateStudentDetailServices(1);
                        dialog.dismiss();
                        etGetRollNo.setInputType(InputType.TYPE_CLASS_NUMBER);
                        btnAdd.setText(getResources().getString(R.string.label_add));
                        etGetRollNo.setBackground(getResources().getDrawable(R.drawable.common_border));
                    }
                }
            });
            btnIntentService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mIsAddStudentClicked) {
                        addStudentDetailServices(3);
                        dialog.dismiss();
                    } else {
                        updateStudentDetailServices(3);
                        dialog.dismiss();
                    }

                }
            });
            btnService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mIsAddStudentClicked) {
                        addStudentDetailServices(2);
                        dialog.dismiss();
                    } else {
                        updateStudentDetailServices(2);
                        dialog.dismiss();
                    }
                }

            });
            dialog.show();
        }
    }


    //ADD data through async task ,1 for async task ,2 for services,3 for intent service
    private void addStudentDetailServices(int task) {
        this.task = task;
        if (isValidate()) {
            String studentName = etGetName.getText().toString().trim();
            String studentClass = etGetClass.getText().toString().trim();
            String studentRoll = etGetRollNo.getText().toString().trim();

            StudentDetails studentDetails = new StudentDetails(studentName, Integer.parseInt(studentClass), Integer.parseInt(studentRoll), 1);
            if (task == 1) {

                new AsyncTaskAddStudentDetail(mContext, this).execute(studentDetails);

            } else if (task == 2) {

                Intent serviceIntent = new Intent(mContext, ServiceAddStudentDetail.class);
                serviceIntent.putExtra(STUDENT_OBJ, studentDetails);
                mContext.startService(serviceIntent);

            } else if (task == 3) {

                Intent intentServiceIntent = new Intent(mContext, IntentServiceAddUpdateStudentDetail.class);
                intentServiceIntent.putExtra(STUDENT_OBJ_INTENT_SERVICE, studentDetails);
                mContext.startService(intentServiceIntent);

            } else {

                Toast.makeText(mContext, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //UPDATE data through async task, 1 for async task ,2 for services,3 for intent service
    private void updateStudentDetailServices(int task) {
        if (isValidate()) {
            String studentUpdateName = etGetName.getText().toString().trim();
            String studentUpdateClass = etGetClass.getText().toString().trim();
            int studentUpdateRoll = studentDetails.getStudentRoll();

            StudentDetails studentDetailsUpdate = new StudentDetails(studentUpdateName, Integer.parseInt(studentUpdateClass), studentUpdateRoll, 2);
            if (task == 1) {

                new AsyncTaskAddStudentDetail(mContext, this).execute(studentDetailsUpdate);

            } else if (task == 2) {

                Intent serviceIntent = new Intent(mContext, ServiceAddStudentDetail.class);
                serviceIntent.putExtra(STUDENT_OBJ, studentDetailsUpdate);
                mContext.startService(serviceIntent);

            } else if (task == 3) {

                Intent intentServiceIntent = new Intent(mContext, IntentServiceAddUpdateStudentDetail.class);
                intentServiceIntent.putExtra(STUDENT_OBJ_INTENT_SERVICE, studentDetailsUpdate);
                mContext.startService(intentServiceIntent);

            } else {

                Toast.makeText(mContext, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //getting data to update
    public void updateStudentDetail(final int clickedPosiition, final StudentDetails studentObj, Boolean updateClicked) {

        clickedPosition = clickedPosiition;
        studentDetails = studentObj;
        mIsAddStudentClicked = false;

        etGetRollNo.setInputType(InputType.TYPE_NULL);
        etGetRollNo.setBackground(getResources().getDrawable(R.drawable.et_view_data));
        btnAdd.setText(getResources().getString(R.string.label_update));

        etGetName.setText(studentObj.getStudentName());
        etGetRollNo.setText(String.valueOf(studentObj.getStudentRoll()));
        etGetClass.setText(String.valueOf(studentObj.getStudentClass()));
    }

    //async task callback listner
    @Override
    public void asyncClickListner(final boolean isSuccess, final StudentDetails studentDetails) {
        if (isSuccess) {
            ((MainActivity) mContext).addUpdateData(clickedPosition, studentDetails);
            ((MainActivity) mContext).switchViewPager();
            clearEt();
        }
    }

    //broadcast receiver
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            StudentDetails studentDetails = intent.getParcelableExtra(ADD_BROADCAST_STUDENT_OBJ);

            ((MainActivity) mContext).addUpdateData(clickedPosition, studentDetails);
            ((MainActivity) mContext).switchViewPager();
            clearEt();
            etGetRollNo.setInputType(InputType.TYPE_CLASS_NUMBER);
            btnAdd.setText(getResources().getString(R.string.label_add));
            etGetRollNo.setBackground(getResources().getDrawable(R.drawable.common_border));
        }
    };

    //register receiver
    private void intentServiceReceiver(){
        IntentServiceBroadcastReceiver intentServiceBroadcastReceiver=new IntentServiceBroadcastReceiver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(INTENT_SERVICE_BROADCAST);

        LocalBroadcastManager.getInstance(mContext).registerReceiver(intentServiceBroadcastReceiver,intentFilter);

    }

    //broadcast receiver for intent service
    public class IntentServiceBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            StudentDetails studentDetails = intent.getParcelableExtra(STUDENT_OBJ_INTENT_SERVICE_BROADCAST);
            ((MainActivity) mContext).addUpdateData(clickedPosition, studentDetails);

            ((MainActivity) mContext).switchViewPager();
            clearEt();
            etGetRollNo.setInputType(InputType.TYPE_CLASS_NUMBER);
            btnAdd.setText(getResources().getString(R.string.label_add));
            etGetRollNo.setBackground(getResources().getDrawable(R.drawable.common_border));

        }
    }

    //register broadcast receiver intent service
    @Override
    public void onStart() {
        super.onStart();
        if (mIsBroadRequired) {
            intentServiceReceiver();
        }
    }

    //unregister broadcast receiver intent service
    @Override
    public void onStop() {
        super.onStop();
        if (mIsBroadRequired) {

        }
    }

    //registering broadcast receiver service
    @Override
    public void onResume() {
        super.onResume();
        if (mIsBroadRequired) {
            ((MainActivity) mContext).registerReceiver(receiver, new IntentFilter(ADD_BROADCAST));

        }
    }

    //unregister broadcast receiver service
    @Override
    public void onPause() {
        super.onPause();
        if (mIsBroadRequired) {
            ((MainActivity) mContext).unregisterReceiver(receiver);
        }
    }

    //clear all et's
    private void clearEt() {
        etGetName.setText("");

        etGetRollNo.setText("");
        etGetClass.setText("");
    }

    //checking add student click
    public void clickedAddStudentCheck(boolean mIsAddClicked) {
        mIsAddStudentClicked = mIsAddClicked;

    }

    //validation
    private boolean isValidate() {
        String etAddName = etGetName.getText().toString().trim();
        String etAddClass = etGetClass.getText().toString().trim();
        String etAddRollNo = etGetRollNo.getText().toString().trim();
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
//else if (Integer.parseInt(etAddRollNo)==studentDetails.getStudentRoll()){
//        etGetRollNo.setError(getResources().getString(R.string.label_empty_field));
//        return false;
//
//        }