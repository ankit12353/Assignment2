package com.example.assignment4.servicePkg;

import android.content.Context;
import android.os.AsyncTask;

import com.example.assignment4.database.DatabaseHelper;
import com.example.assignment4.model.StudentDetails;


public class AsyncTaskAddStudentDetail extends AsyncTask<StudentDetails, Void, Boolean> {

    private AsyncTaskCallback listner;
    private StudentDetails studentDetails;
    private static final String TAG = "AsyncTaskAddStudentDeta";
    private Context mContext;


    public AsyncTaskAddStudentDetail(final Context context, final AsyncTaskCallback listner) {
        this.mContext = context;
        this.listner = listner;
    }

    @Override
    protected Boolean doInBackground(StudentDetails... params) {
        studentDetails = params[0];
        if (studentDetails.getType()==1) {
            return new DatabaseHelper(mContext).saveStudentDetail(studentDetails.getStudentName(), studentDetails.getStudentClass(), studentDetails.getStudentRoll());
        } else if (studentDetails.getType()==2) {
            return new DatabaseHelper(mContext).updateStudentDetail(studentDetails);
        }else if (studentDetails.getType()==3) {
            return new DatabaseHelper(mContext).deleteStudentDetail(studentDetails.getStudentRoll());
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {
        super.onPostExecute(isSuccess);
            listner.asyncClickListner(isSuccess, studentDetails);
    }

    public interface AsyncTaskCallback {
        void asyncClickListner(final boolean isSuccess, final StudentDetails getStudentDetails);
    }

}
