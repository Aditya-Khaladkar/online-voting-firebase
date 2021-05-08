package com.example.group1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.group1.helpcenter.HelpCenter;
import com.example.group1.livecounting.LiveCounting;
import com.example.group1.profile.Profile;
import com.example.group1.votecategory.VoteCategory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Dashboard extends AppCompatActivity {
    DocumentReference documentReference;
    TextView textView;
    FirebaseFirestore firebaseFirestore;
    LinearLayout layout_vote,layout_profile,layout_live,layout_help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        textView=findViewById(R.id.textView);
//        layout_vote=findViewById(R.id.layout_vote);
//        layout_profile=findViewById(R.id.layout_profile);
//        layout_live=findViewById(R.id.layout_live);
//        layout_help=findViewById(R.id.layout_help);

//        layout_help.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Query query=FirebaseDatabase.getInstance().getReference().child("Used Aadhaar")
//                        .orderByChild("usedaadhaar");
//                query.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.getChildrenCount() > 0) {
//                            startActivity(new Intent(getApplicationContext(),LiveCounting.class));
//                        }
//                        else {
//                            Toast.makeText(Dashboard.this, "You haven't voted yet ! phele vote kar fir counting dekhna", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        });
//
//        layout_live.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), LiveCounting.class));
//            }
//        });
//
//        layout_vote.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(Dashboard.this, "Please Wait While Redirecting", Toast.LENGTH_SHORT).show();
//
//                Query query=FirebaseDatabase.getInstance().getReference("Used Aadhaar")
//                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                        .orderByChild("usedaadhaar");
//                query.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if (snapshot.getChildrenCount()>0){
//                            Toast.makeText(Dashboard.this, "Bhosadika Ek Baar Vote Keya Na Kitne Bar Karega", Toast.LENGTH_SHORT).show();
//                        }
//                        else{
//                            startActivity(new Intent(getApplicationContext(),Aadhaar.class));
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        });
//
//        layout_profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), Profile.class));
//            }
//        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        firebaseFirestore=FirebaseFirestore.getInstance();
//
//        documentReference=firebaseFirestore.collection("Verified Users")
//                .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
//        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (value.exists()){
//                    String name=value.getString("firstname");
//                    textView.setText(name);
//                }
//            }
//        });
//    }
}