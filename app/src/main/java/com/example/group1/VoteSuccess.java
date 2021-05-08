package com.example.group1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Random;

public class VoteSuccess extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_success);

        generateCode();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),Dashboard.class));
                finish();
            }
        },2000);
    }

    private void generateCode() {
        firebaseFirestore=FirebaseFirestore.getInstance();
        Random random=new Random();

        int n=1000000+random.nextInt(9999999);
        String code=String.valueOf(n);

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("uniquecode",code);

        firebaseFirestore.collection("Voter Code")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .set(hashMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(VoteSuccess.this, "Code Generated", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(VoteSuccess.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}