package com.codecrafters.kuchbhi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Sign_in extends AppCompatActivity {
    private Button get_otp;
    private ImageView transition_image;
    private TextView t1,t2;
    private CountryCodePicker ccp;
    private TextInputEditText number;
    private FirebaseAuth mAuth;
    String phonenumber,codeSent;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallbacks;

    ProgressBar progressbarofmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        get_otp=findViewById(R.id.get_otp);
        transition_image=findViewById(R.id.imageView3);
        t1=findViewById(R.id.textview1);
        t2=findViewById(R.id.textview2);
        ccp = findViewById(R.id.countryCodePicker);
        number=findViewById(R.id.phonenumberforlogin);
        progressbarofmain=findViewById(R.id.progressbarofmain);


        ccp.registerCarrierNumberEditText(number);
        mAuth=FirebaseAuth.getInstance();

        Pair[] pairs= new Pair[4];
        pairs[0]=new Pair(transition_image,"image");
        pairs[1]=new Pair(t1,"textview1");
        pairs[2]=new Pair(t2,"textview2");
        pairs[3]=new Pair(get_otp,"button");

        ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(Sign_in.this,pairs);

        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numberstr;
                numberstr = number.getText().toString();
                if (numberstr.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter Your Number", Toast.LENGTH_SHORT).show();
                }

                else if (numberstr.replace(" ","").length()!=10){
                    Toast.makeText(getApplicationContext(), "Please Enter Correct Number", Toast.LENGTH_SHORT).show();

                }
                else {
                    progressbarofmain.setVisibility(View.VISIBLE);
                    phonenumber=ccp.getFullNumberWithPlus().replace(" ", "");
                    PhoneAuthOptions options= PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(phonenumber)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(Sign_in.this)
                            .setCallbacks(mcallbacks)
                            .build();

                    PhoneAuthProvider.verifyPhoneNumber(options);

                }



            }
        });
        mcallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                // Automatically Fetch OTP CODE HERE
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                progressbarofmain.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "OTP Sent Successfully.", Toast.LENGTH_SHORT).show();
                codeSent=s;
                Intent intent=new Intent(Sign_in.this,otp_verifation.class);
                intent.putExtra("otp", codeSent);
                intent.putExtra("number",phonenumber);
                startActivity(intent,options.toBundle());
            }
        };
    }


}