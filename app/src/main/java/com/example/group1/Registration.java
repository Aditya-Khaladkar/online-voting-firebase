package com.example.group1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {
    private static final Pattern ADDRESS = Pattern.compile("^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-z])" +         //at least 1 lower case letter
            "(?=.*[A-Z])" +         //at least 1 upper case letter
            //"(?=.*[a-zA-Z])" +      //any letter
            //"(?=.*[@#$%^&+=])" +    //at least 1 special character
            //"(?=\\S+$)" +           //no white spaces
            ".{4,}" +               //at least 4 characters
            "$");
    FirebaseFirestore firebaseFirestore;
    EditText fname,mname,lname,regage,regaadhaar,regaddress,regstate,regdistrict;
    Button btn_verify;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fname=findViewById(R.id.fname);
        mname=findViewById(R.id.mname);
        lname=findViewById(R.id.lname);
        regage=findViewById(R.id.regage);
        regaadhaar=findViewById(R.id.regaadhaar);
        regaddress=findViewById(R.id.regaddress);
        regstate=findViewById(R.id.regstate);
        regdistrict=findViewById(R.id.regdistrict);
        btn_verify=findViewById(R.id.btn_verify);
        progressBar=findViewById(R.id.progressbar);

        firebaseFirestore=FirebaseFirestore.getInstance();

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = fname.getText().toString();
                String middlename = mname.getText().toString();
                String lastname = lname.getText().toString();
                String age = regage.getText().toString();
                String aadhaar = regaadhaar.getText().toString();
                String address = regaddress.getText().toString();
                String state = regstate.getText().toString();
                String district = regdistrict.getText().toString();

                boolean result = Verhoeff.validateVerhoeff(aadhaar);
                String msg = String.valueOf(result);

                if (TextUtils.isEmpty(firstname)) {
                    fname.setError("This Field Can't be empty");
                }
                else if (TextUtils.isEmpty(middlename)) {
                    mname.setError("This Field Can't be empty");
                }
                else if (TextUtils.isEmpty(lastname)) {
                    lname.setError("This Field Can't be empty");
                }
                else if (TextUtils.isEmpty(age)) {
                    regage.setError("This Field Can't be empty");
                }
                else if (Integer.parseInt(age) <= 18) {
                    regage.setError("Age is less not eligible for voting");
                }
                else if (TextUtils.isEmpty(aadhaar)) {
                    regaadhaar.setError("This Field Can't be empty");
                }
                else if (msg == "false") {
                    regaadhaar.setError("Enter Valid Aadhaar Number");
                }
                else if (!ADDRESS.matcher(address).matches()){
                    regaddress.setError("Chand pe raita hai kya address dal");
                }
                else if (state.matches("Maharashtra") && district.matches("Nagpur") ||
                        district.matches("Pune") || district.matches("Nashik") || district.matches("Amravati") ||
                        district.matches("Konkan") || district.matches("Aurangabad"))
                {
                    Toast.makeText(Registration.this, "Verified", Toast.LENGTH_SHORT).show();

                    Query query = FirebaseDatabase.getInstance().getReference().child("Verified Aadhaar")
                            .orderByChild("aadhaar").equalTo(aadhaar);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getChildrenCount() > 0) {
                                Toast.makeText(Registration.this, "Enter Aadhaar Number Already Exists", Toast.LENGTH_SHORT).show();
                            } else {


                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("firstname", firstname);
                                hashMap.put("middlename", middlename);
                                hashMap.put("lastname", lastname);
                                hashMap.put("aadhaarcard", aadhaar);
                                hashMap.put("address", address);
                                hashMap.put("state", state);
                                hashMap.put("district", district);

                                progressBar.setVisibility(View.VISIBLE);

                                firebaseFirestore.collection("Verified Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .set(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Registration.this, "Error", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            HashMap<String, Object> hashMap1 = new HashMap<>();
                            hashMap1.put("aadhaar", aadhaar);
                            FirebaseDatabase.getInstance().getReference("Verified Aadhaar")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(hashMap1)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(Registration.this, "Verified", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                else{
                    Toast.makeText(Registration.this, "Enter Valid State and District", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}