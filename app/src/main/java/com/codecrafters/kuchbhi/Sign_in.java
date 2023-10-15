package com.codecrafters.kuchbhi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Sign_in extends AppCompatActivity {
private Button get_otp;
private ImageView transition_image;
private TextView t1,t2;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        get_otp=findViewById(R.id.get_otp);
        transition_image=findViewById(R.id.imageView3);
        t1=findViewById(R.id.textview1);
        t2=findViewById(R.id.textview2);

        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Sign_in.this, otp_verifation.class);
                Pair[] pairs= new Pair[4];
                pairs[0]=new Pair(transition_image,"image");
                pairs[1]=new Pair(t1,"textview1");
                pairs[2]=new Pair(t2,"textview2");
                pairs[3]=new Pair(get_otp,"button");

                ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(Sign_in.this,pairs);
                startActivity(intent,options.toBundle());




            }
        });
    }
}