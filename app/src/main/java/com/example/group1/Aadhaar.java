package com.example.group1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;

public class Aadhaar extends AppCompatActivity {
    EditText ed_aadhaaar;
    Button btn_aadhaar;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    TextView textView3;
    private long backPressedTime;
    Toast backToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aadhaar);

        ed_aadhaaar=findViewById(R.id.ed_aadhaar);
        btn_aadhaar=findViewById(R.id.btn_aadhaar);
        textView3=findViewById(R.id.textView3);
        firebaseFirestore=FirebaseFirestore.getInstance();
        documentReference=firebaseFirestore.collection("Verified Users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()){
                    String a= value.getString("aadhaarcard");
                    textView3.setText(a);
                }
            }
        });

        btn_aadhaar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aadhaarno=ed_aadhaaar.getText().toString();
                if (aadhaarno.equals(textView3.getText().toString())){
                    HashMap<String, Object> hashMap=new HashMap<>();
                    hashMap.put("usedaadhaar",aadhaarno);

                    FirebaseDatabase.getInstance().getReference("Used Aadhaar")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(hashMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        startActivity(new Intent(getApplicationContext(),AadhaarVerified.class));
                                        finish();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(Aadhaar.this, "Your Aadhaar number don't match with the" +
                            "registered aadhaar number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (backPressedTime + 1 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Can't go back", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }
}