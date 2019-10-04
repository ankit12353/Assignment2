package com.assignment3.model;

import android.os.Parcel;
import android.os.Parcelable;

public class StudentDetails implements Parcelable {
    private String tvDisplayName;
    private int tvDisplayRollNo,tvDisplayClass;

    public StudentDetails(String tvDisplayName, int tvDisplayRollNo, int tvDisplayClass) {
        this.tvDisplayName = tvDisplayName;
        this.tvDisplayClass=tvDisplayClass;
        this.tvDisplayRollNo=tvDisplayRollNo;
    }

    protected StudentDetails(Parcel in) {
        tvDisplayName = in.readString();
        tvDisplayRollNo = in.readInt();
        tvDisplayClass = in.readInt();
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

    public String getTvDisplayName() {
        return tvDisplayName;
    }

    public int getTvDisplayRollNo() {
        return tvDisplayRollNo;
    }

    public int getTvDisplayClass() {
        return tvDisplayClass;
    }

    public void setTvDisplayName(String tvDisplayName) {
        this.tvDisplayName = tvDisplayName;
    }

    public void setTvDisplayRollNo(int tvDisplayRollNo) {
        this.tvDisplayRollNo = tvDisplayRollNo;
    }

    public void setTvDisplayClass(int tvDisplayClass) {
        this.tvDisplayClass = tvDisplayClass;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tvDisplayName);
        parcel.writeInt(tvDisplayRollNo);
        parcel.writeInt(tvDisplayClass);
    }
}
