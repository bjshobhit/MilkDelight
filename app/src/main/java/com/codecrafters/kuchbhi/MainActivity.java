package com.codecrafters.kuchbhi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton finish;
    private TextInputEditText name,email,dob,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        finish=findViewById(R.id.finish_registration);
        name=findViewById(R.id.userfullname);
        email=findViewById(R.id.useremail);
        dob=findViewById(R.id.userdob);
        address=findViewById(R.id.useraddress);


        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty())
                    Toast.makeText(MainActivity.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                else if(email.getText().toString().isEmpty())
                    Toast.makeText(MainActivity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                else if (dob.getText().toString().isEmpty())
                    Toast.makeText(MainActivity.this, "Please Enter Date of Birth", Toast.LENGTH_SHORT).show();
                else if (address.getText().toString().isEmpty())
                    Toast.makeText(MainActivity.this, "Please Enter Address", Toast.LENGTH_SHORT).show();
                else {
                    String namestr=name.getText().toString(), dobstr=dob.getText().toString(),
                            emailstr=email.getText().toString(), addressstr=address.getText().toString();
                    Map<String,Object> data = new HashMap<>();
                    data.put("fullname",namestr);
                    data.put("email",emailstr);
                    data.put("dob",dobstr);
                    data.put("address",addressstr);
                    CollectionReference reference = FirebaseFirestore.getInstance().collection("Users");
                    reference.document(FirebaseAuth.getInstance().getUid()).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(MainActivity.this,DashBoard.class);
                            startActivity(intent);
                            finish();
                        }
                    });


                }
            }
        });
    }
}