package com.example.group1.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.group1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    TextView txtfname,txtmname,txtlname,txtvoteid,txtphoneno,txtstate,txtdistrict,txtaadhaar,txtaddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtfname=findViewById(R.id.txtfname);
        txtmname=findViewById(R.id.txtmname);
        txtlname=findViewById(R.id.txtlname);
        txtvoteid=findViewById(R.id.txtvoteid);
        txtstate=findViewById(R.id.txtstate);
        txtdistrict=findViewById(R.id.txtdistrict);
        txtaadhaar=findViewById(R.id.txtaadhaar);
        txtaddress=findViewById(R.id.txtaddress);
        firebaseFirestore=FirebaseFirestore.getInstance();

        documentReference=firebaseFirestore.collection("Verified Users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()){
                    String fname= value.getString("firstname");
                    txtfname.setText(fname);
                    String mname= value.getString("middlename");
                    txtmname.setText(mname);
                    String lname= value.getString("lastname");
                    txtlname.setText(lname);
                    String state=value.getString("state");
                    txtstate.setText(state);
                    String district=value.getString("district");
                    txtdistrict.setText(district);
                    String aadhaar=value.getString("aadhaarcard");
                    txtaadhaar.setText(aadhaar);
                    String address=value.getString("address");
                    txtaddress.setText(address);
                }
            }
        });

        documentReference=firebaseFirestore.collection("Voter Code")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()){
                   String voteid=value.getString("uniquecode");
                   txtvoteid.setText(voteid);
                }
            }
        });

        documentReference=firebaseFirestore.collection("Verified Phone")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid());
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.exists()){
                    String phone=value.getString("verifiedphone");
                    txtphoneno.setText(phone);
                }
            }
        });

    }
}