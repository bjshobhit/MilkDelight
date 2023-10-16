package com.codecrafters.kuchbhi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class otp_verifation extends AppCompatActivity {
    private Button goto_dashboard;
    FirebaseAuth mAuth;
    String number,otpid;
    PinView enterotp;
    TextView numberontext;
    ProgressBar progressbaroffillotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verifation);
        goto_dashboard=findViewById(R.id.goto_dashboard);
        numberontext=findViewById(R.id.phonenumberonotp);
        progressbaroffillotp=findViewById(R.id.progressbaroffillotp);
        enterotp=findViewById(R.id.enter_otp);
        mAuth=FirebaseAuth.getInstance();



        numberontext.setText(getIntent().getStringExtra("number"));

        otpid=getIntent().getStringExtra("otp");
        goto_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(enterotp.getText().toString().length()!=6) {
                    Toast.makeText(otp_verifation.this, "invalid OTP", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressbaroffillotp.setVisibility(View.VISIBLE);
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid, enterotp.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {
                            FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()){
                                        progressbaroffillotp.setVisibility(View.INVISIBLE);
                                        Intent intent = new Intent(otp_verifation.this, DashBoard.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else{
                                        progressbaroffillotp.setVisibility(View.INVISIBLE);
                                        Intent intent = new Intent(otp_verifation.this, MainActivity.class);
                                        intent.putExtra("uid", mAuth.getUid());
                                        intent.putExtra("phone", mAuth.getCurrentUser().getPhoneNumber());
                                        startActivity(intent);
                                        finish();
                                    }

                                }
                            });



                        } else {
                            Toast.makeText(otp_verifation.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}