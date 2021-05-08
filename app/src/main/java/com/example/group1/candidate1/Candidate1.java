package com.example.group1.candidate1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.group1.R;
import com.example.group1.VoteSuccess;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

public class Candidate1 extends AppCompatActivity {
    Button java;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate1);

        firebaseFirestore=FirebaseFirestore.getInstance();

        java=findViewById(R.id.java);
        java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.runTransaction(new Transaction.Function<Object>() {
                    @Nullable
                    @Override
                    public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                        DocumentReference documentReference = firebaseFirestore.collection("Candidate")
                                .document("Java");
                        DocumentSnapshot documentSnapshot = transaction.get(documentReference);
                        long newVote = documentSnapshot.getLong("Vote") + 1;
                        transaction.update(documentReference, "Vote", newVote);
                        return newVote;
                    }
                });

                startActivity(new Intent(getApplicationContext(), VoteSuccess.class));
                finish();
            }
        });
    }
}