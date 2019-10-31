package com.example.assignment4.servicePkg;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.assignment4.database.DatabaseHelper;
import com.example.assignment4.model.StudentDetails;

import static android.content.ContentValues.TAG;
import static com.example.assignment4.fragment.AddUpdateFragment.STUDENT_OBJ;


public class ServiceAddStudentDetail extends Service {

    StudentDetails studentDetails;
    public static final String ADD_BROADCAST = "broadcast add";
    public static final String ADD_BROADCAST_STUDENT_OBJ = "broadcast add";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        try {
            studentDetails = intent.getParcelableExtra(STUDENT_OBJ);

            assert studentDetails != null;
            if (studentDetails.getType() == 1) {
                boolean result = new DatabaseHelper(getApplicationContext()).saveStudentDetail(studentDetails.getStudentName(), studentDetails.getStudentClass(), studentDetails.getStudentRoll());
                if (result) {
                    Intent intent1 = new Intent(ADD_BROADCAST);
                    intent1.putExtra(ADD_BROADCAST_STUDENT_OBJ, studentDetails);
                    sendBroadcast(intent1);
                }
            } else if (studentDetails.getType() == 2) {
                boolean result = new DatabaseHelper(getApplicationContext()).updateStudentDetail(studentDetails);
                if (result) {
                    Intent intent1 = new Intent(ADD_BROADCAST);
                    intent1.putExtra(ADD_BROADCAST_STUDENT_OBJ, studentDetails);
                    sendBroadcast(intent1);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return START_STICKY;
    }
}