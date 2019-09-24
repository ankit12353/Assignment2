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
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.assignment2.util.CommanUtil;
import com.example.assignment2.model.SignUp;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView tvLoginRegister;
    EditText etEmail,etPassword;
    Button btnLogin;
    public static ArrayList<SignUp> signUpData=new ArrayList<SignUp>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etEmail=findViewById(R.id.et_login_email);
        etPassword=findViewById(R.id.et_login_password);
        btnLogin=findViewById(R.id.btn_login);
        tvLoginRegister=findViewById(R.id.tv_login_register);

        //storing the text to be used as spannable in text
        String text=getResources().getString(R.string.tv_lable_don_t_have_an_account_register);

        //Intitialize a new span
        SpannableString ss=new SpannableString(text);
        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intentRegister= new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intentRegister);
                finish();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        //storing specific color in diffrent variables
        ForegroundColorSpan fcsBlue=new ForegroundColorSpan(getResources().getColor((android.R.color.holo_blue_dark)));

        //setting desired color to string
        ss.setSpan(fcsBlue,23,31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan,23,31,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvLoginRegister.setText(ss);
        tvLoginRegister.setMovementMethod(LinkMovementMethod.getInstance());


        //login button action , redirects to otp page
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginValidate()){
                    Intent login=new Intent(MainActivity.this,OtpActivity.class);
                    CommanUtil.showSnackBar(MainActivity.this,"Logged In Successfully");
                    startActivity(login);
                    finish();
                }
            }

            private boolean loginValidate() {
                String email=etEmail.getText().toString().trim();
                String password=etPassword.getText().toString().trim();
                if (email.isEmpty()){
                    CommanUtil.showSnackBar(MainActivity.this,"Please enter email");
                    return false;
                }
                else if (password.isEmpty()){
                    CommanUtil.showSnackBar(MainActivity.this,"Enter password");
                    return false;
                }
                else {
                    for (int i=0;i<signUpData.size();i++){
                        String getEmail=signUpData.get(i).getEmail();
                        String getPassword=signUpData.get(i).getUserPassword();
                        if (!email.equals(getEmail)){
                            CommanUtil.showSnackBar(MainActivity.this,"Enter valid email");
                            return false;
                        }
                        if (!password.equals(getPassword)){
                            CommanUtil.showSnackBar(MainActivity.this,"Enter valid password");
                            return false;
                        }
                        if (signUpData.isEmpty()){
                            CommanUtil.showSnackBar(MainActivity.this,"Please enter valid data");
                            return false;
                        }
                    }
                    return true;
                }
            }
        });

    }




}