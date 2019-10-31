package com.example.assignment4.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.util.Log;

import com.example.assignment4.model.StudentDetails;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "student.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "student_table";
    private static final String KEY_STUDENT_NAME = "NAME";
    private static final String KEY_STUDENT_CLASS = "CLASS";
    private static final String KEY_STUDENT_ROLL = "ROLL";
    private SQLiteDatabase sqLiteDatabase;
    private static final String TAG = "DatabaseHelper";
    private Context mContext;


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "DatabaseHelper: database created");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //creating table
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + "(" + KEY_STUDENT_NAME + " text," + KEY_STUDENT_CLASS + " integer," + KEY_STUDENT_ROLL + " integer PRIMARY KEY);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //insert student details to database
    public boolean saveStudentDetail(String studentName, int studentClass, int studentRoll) {

        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_STUDENT_NAME, studentName);
        contentValues.put(KEY_STUDENT_CLASS, studentClass);
        contentValues.put(KEY_STUDENT_ROLL, studentRoll);

        long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    //update student detail
    public boolean updateStudentDetail(StudentDetails studentDetails) {
        sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(KEY_STUDENT_NAME,studentDetails.getStudentName());
        contentValues.put(KEY_STUDENT_CLASS,studentDetails.getStudentClass());
//        contentValues.put(KEY_STUDENT_ROLL,studentDetails.getStudentRoll());

        long result= sqLiteDatabase.update(TABLE_NAME,contentValues,KEY_STUDENT_ROLL+"="+studentDetails.getStudentRoll(),null);

        return result != -1;
    }

    //get all student details
    public Cursor getAllStudentDetails(){
        sqLiteDatabase=this.getWritableDatabase();
        String [] columns={KEY_STUDENT_NAME,KEY_STUDENT_CLASS,KEY_STUDENT_ROLL};
        return sqLiteDatabase.query(TABLE_NAME,columns,null,null,null,null,null);

    }

    //delete data from database
    public boolean deleteStudentDetail(int clickedRollNo){
        sqLiteDatabase=this.getWritableDatabase();
        long result=sqLiteDatabase.delete(TABLE_NAME,KEY_STUDENT_ROLL+"="+clickedRollNo,null);
        return  result!=-1;
    }

}
