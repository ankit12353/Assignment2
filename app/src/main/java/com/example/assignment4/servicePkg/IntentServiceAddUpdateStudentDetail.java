package com.example.assignment4.servicePkg;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.assignment4.database.DatabaseHelper;
import com.example.assignment4.model.StudentDetails;

import static android.content.ContentValues.TAG;
import static com.example.assignment4.fragment.AddUpdateFragment.STUDENT_OBJ_INTENT_SERVICE;

public class IntentServiceAddUpdateStudentDetail extends IntentService {

    StudentDetails studentDetails;
    public static final String INTENT_SERVICE_BROADCAST = "INTENT_SERVICE_BROADCAST";
    public static final String STUDENT_OBJ_INTENT_SERVICE_BROADCAST = "STUDENT_OBJ_INTENT_SERVICE_BROADCAST";

    public IntentServiceAddUpdateStudentDetail() {
        super("Intent Service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        assert intent != null;
        studentDetails = intent.getParcelableExtra(STUDENT_OBJ_INTENT_SERVICE);

        assert studentDetails != null;
        if (studentDetails.getType() == 1) {
            boolean isResult = new DatabaseHelper(getApplicationContext()).saveStudentDetail(studentDetails.getStudentName(), studentDetails.getStudentClass(), studentDetails.getStudentRoll());
            if (isResult) {
                intent.setAction(INTENT_SERVICE_BROADCAST);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent.putExtra(STUDENT_OBJ_INTENT_SERVICE_BROADCAST, studentDetails));
            }

        } else if (studentDetails.getType() == 2) {
            boolean isResult = new DatabaseHelper(getApplicationContext()).updateStudentDetail(studentDetails);

            if (isResult) {

                intent.setAction(INTENT_SERVICE_BROADCAST);
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent.putExtra(STUDENT_OBJ_INTENT_SERVICE_BROADCAST, studentDetails));
            }
        }

    }

}