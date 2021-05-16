package com.example.group1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class PhoneVerification extends AppCompatActivity {
    Button btn_phone;
    EditText number;
    ProgressBar progressBar;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);

        btn_phone=findViewById(R.id.btn_phone);
        number=findViewById(R.id.number);
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        btn_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String phoneNumber = number.getText().toString();
                if (phoneNumber.isEmpty())
                    number.setError("This Filed Can't be empty");
                else {
                    //verify phone number
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91"+phoneNumber,
                            60, TimeUnit.SECONDS,
                            PhoneVerification.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                                    signInUser(phoneAuthCredential);
                                }

                                @Override
                                public void onVerificationFailed(FirebaseException e) {
                                    Toast.makeText(PhoneVerification.this, "Error", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(final String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(verificationId, forceResendingToken);

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent otpIntent = new Intent(getApplicationContext(), EnterOtp.class);
                                            otpIntent.putExtra("auth" , verificationId);
                                            startActivity(otpIntent);
                                        }
                                    }, 2000);
                                }
                            });
                }
            }
        });

    }

    private void signInUser(PhoneAuthCredential credential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                           startActivity(new Intent(getApplicationContext(),Registration.class));
                           finish();
                        }else {
                            Toast.makeText(PhoneVerification.this, "Error in verification", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}