package com.example.group1.livecounting;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.group1.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class LiveCounting extends AppCompatActivity {
    TextView cv1,cv2,cv3,cv4;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_counting);

        cv1=findViewById(R.id.cv1);
        cv2=findViewById(R.id.cv2);
        cv3=findViewById(R.id.cv3);
        cv4=findViewById(R.id.cv4);

        firebaseFirestore=FirebaseFirestore.getInstance();
        documentReference=firebaseFirestore.collection("Candidate")
                .document("Aditya Khaladkar");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()){
                    Object count=value.get("Vote");
                    cv1.setText(""+count);
                }
            }
        });

        documentReference=firebaseFirestore.collection("Candidate")
                .document("Piyush Hatewar");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()){
                    Object count=value.get("Vote");
                    cv2.setText(""+count);
                }
            }
        });

        documentReference=firebaseFirestore.collection("Candidate")
                .document("Prathamesh Telmasre");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()){
                    Object count=value.get("Vote");
                    cv3.setText(""+count);
                }
            }
        });

        documentReference=firebaseFirestore.collection("Candidate")
                .document("Akash Chachere");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()){
                    Object count=value.get("Vote");
                    cv4.setText(""+count);
                }
            }
        });
    }
}