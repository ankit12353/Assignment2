package com.example.assignment4.model;

import android.os.Parcel;
import android.os.Parcelable;

public class StudentDetails implements Parcelable {
    private String studentName;
    private int studentClass, studentRoll, type;

    public StudentDetails(String studentName, int studentClass, int studentRoll, int type) {
        this.studentName = studentName;
        this.studentClass = studentClass;
        this.studentRoll = studentRoll;
        this.type = type;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getStudentClass() {
        return studentClass;
    }

    public int getStudentRoll() {
        return studentRoll;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    protected StudentDetails(Parcel in) {
        studentName = in.readString();
        studentClass = in.readInt();
        studentRoll = in.readInt();
        type = in.readInt();
    }

    public static final Creator<StudentDetails> CREATOR = new Creator<StudentDetails>() {
        @Override
        public StudentDetails createFromParcel(Parcel in) {
            return new StudentDetails(in);
        }

        @Override
        public StudentDetails[] newArray(int size) {
            return new StudentDetails[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(studentName);
        parcel.writeInt(studentClass);
        parcel.writeInt(studentRoll);
        parcel.writeInt(type);
    }
}
