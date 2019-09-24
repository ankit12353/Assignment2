package com.example.assignment2;

import android.content.Intent;
import android.os.Bundle;

import com.example.assignment2.util.CommanUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class OtpActivity extends AppCompatActivity {
    ImageButton ibBack;
    EditText et1,et2,et3,et4;
    Button btnResend,btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
    ibBack=findViewById(R.id.ibBack);
    et1=findViewById(R.id.et_otpOne);
    et2=findViewById(R.id.et_otpTwo);
    et3=findViewById(R.id.et_otpThree);
    et4=findViewById(R.id.et_otpFour);
    btnResend=findViewById(R.id.btn_resend);
    btnSubmit=findViewById(R.id.btn_submit);

    btnSubmit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(et1.getText().toString().isEmpty() || et2.getText().toString().isEmpty() || et3.getText().toString().isEmpty() || et4.getText().toString().isEmpty()){
                CommanUtil.showSnackBar(OtpActivity.this,"Enter");

            }
            else {
                CommanUtil.showSnackBar(OtpActivity.this, "Succesfully Registered");
                Intent submit = new Intent(OtpActivity.this, HomeActivity.class);
                startActivity(submit);

            }
        }
    });
    btnResend.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            et1.getText().clear();
            et2.getText().clear();
            et3.getText().clear();
            et4.getText().clear();
            et1.requestFocus();
            CommanUtil.showSnackBar(OtpActivity.this,"OTP has been sent successfully");
        }
    });
    ibBack.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent(OtpActivity.this,MainActivity.class);
            startActivity(intent);
        }
    });

    et1.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            et2.requestFocus();

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(editable.length()==1){
                et2.requestFocus();
            }
            else if(editable.length()==0){
                et1.clearFocus();
            }
        }
    });
    et2.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            et3.requestFocus();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(editable.length()==1){
                et3.requestFocus();
            }
            else if(editable.length()==0){
                et1.clearFocus();
            }
        }
    });
    et3.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            et4.requestFocus();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(editable.length()==1){
                et4.requestFocus();
            }
            else if(editable.length()==0){
                et2.clearFocus();
            }
        }
    });
    et4.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(editable.length()==1){
                et4.requestFocus();
            }
            else if(editable.length()==0){
                et1.requestFocus();
            }
        }
    });


    }

}