package com.example.group1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class EnterOtp extends AppCompatActivity {
     Button mVerifyCodeBtn;
     EditText otpEdit;
     String OTP;
     FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        mVerifyCodeBtn = findViewById(R.id.mVerifyCodeBtn);
        otpEdit = findViewById(R.id.otpEdit);

        firebaseAuth = FirebaseAuth.getInstance();

        OTP = getIntent().getStringExtra("auth");

        mVerifyCodeBtn.setOnClickListener(v -> {
            String verification_code = otpEdit.getText().toString();
            if(!verification_code.isEmpty()){
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OTP , verification_code);
                signIn(credential);
            }else{
                Toast.makeText(EnterOtp.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void signIn(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    sendToMain();
                }else{
                    Toast.makeText(EnterOtp.this, "Verification Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser !=null){
            sendToMain();
        }
    }

    private void sendToMain(){
        startActivity(new Intent(EnterOtp.this , Registration.class));
        finish();
    }
}