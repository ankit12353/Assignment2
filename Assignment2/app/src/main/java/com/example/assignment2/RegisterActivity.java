package com.example.assignment2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.assignment2.util.CommanUtil;
import com.example.assignment2.model.SignUp;

import static com.example.assignment2.MainActivity.signUpData;

public class RegisterActivity extends AppCompatActivity {
    Button btnContinue;
    ImageButton ibBack;
    EditText etName,etGender,etDob,etUserType,etEmail,etPassword,etOccupation;
    TextView tvChangeAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName=findViewById(R.id.et_name);
        etGender=findViewById(R.id.et_gender);
        etDob=findViewById(R.id.et_dob);
        etUserType=findViewById(R.id.et_userType);
        etEmail=findViewById(R.id.et_email);
        etPassword=findViewById(R.id.et_password);
        etOccupation=findViewById(R.id.et_occupation);
        ibBack=findViewById(R.id.ib_back);
        btnContinue=findViewById(R.id.btn_continue);
        tvChangeAccount=findViewById(R.id.tv_changeAccount);

        //storing value of text from xml file to be clicked
        String text=getResources().getString(R.string.label_sign_up_with_diffrent_account);
        //making the text to be clicked to redirect to login page
        SpannableString ss=new SpannableString(text);
        ClickableSpan cs=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intentSignUp=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intentSignUp);
                finish();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(cs,0,20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvChangeAccount.setMovementMethod(LinkMovementMethod.getInstance());


        //on click on back icon
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(back);
            }
        });


        //Continue button operation
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registerValidate()){
                    //no error or if validated then sent to next page
                    signUpData.add(new SignUp(etEmail.getText().toString().trim(), etPassword.getText().toString().trim()));
                    Intent signUp = new Intent(RegisterActivity.this, OtpActivity.class);
                    startActivity(signUp);
                    finish();
                }

            }

            private boolean registerValidate() {
                String name=etName.getText().toString().trim();
                String gender=etGender.getText().toString().trim().toLowerCase();
                String dob=etDob.getText().toString().trim();
                String userType=etUserType.getText().toString().trim();
                String email=etEmail.getText().toString().trim();
                String password=etPassword.getText().toString().trim();
                String occupation=etOccupation.getText().toString().trim();

                //name validation
                String namePattern =("^[a-zA-Z\\s]*$");

                //email validation
                String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

                //password validation
                String passwordPattern ="^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$";
                if(email.isEmpty()){
                    CommanUtil.showSnackBar(RegisterActivity.this,"Please enter email");
                    return false;
                }
                else if (!email.matches(emailPattern)){
                    CommanUtil.showSnackBar(RegisterActivity.this,"Enter valid email");
                    return false;
                }
                else if(password.isEmpty()){
                    CommanUtil.showSnackBar(RegisterActivity.this,"Please enter password");
                    return false;
                }
                else if(!password.matches(passwordPattern)){
                    CommanUtil.showSnackBar(RegisterActivity.this,"Enter valid password");
                    return false;
                }
                else if(name.isEmpty()){
                    CommanUtil.showSnackBar(RegisterActivity.this,"Please enter name");
                    return false;
                }
                else if(!name.matches(namePattern)){
                    CommanUtil.showSnackBar(RegisterActivity.this,"Invalid name");
                    return false;
                }
                else if(gender.isEmpty()){
                    CommanUtil.showSnackBar(RegisterActivity.this,"Please enter gender");
                    return false;
                }
                else if(!gender.equals("male") && !gender.equals("female")){
                    CommanUtil.showSnackBar(RegisterActivity.this,"Please enter valid gender");
                    return false;
                }
                else if(dob.isEmpty()){
                    CommanUtil.showSnackBar(RegisterActivity.this,"Please enter date of birth");
                    return false;
                }
                else if(userType.isEmpty()){
                    CommanUtil.showSnackBar(RegisterActivity.this,"Please enter user type");
                    return false;
                }
                else if(occupation.isEmpty()){
                    CommanUtil.showSnackBar(RegisterActivity.this,"Please enter occupation");
                    return false;
                }
                else {
                    return true;
                }
            }
        });

    }

}
