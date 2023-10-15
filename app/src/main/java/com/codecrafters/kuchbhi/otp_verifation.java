package com.codecrafters.kuchbhi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class otp_verifation extends AppCompatActivity {
private Button goto_dashboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verifation);
        goto_dashboard=findViewById(R.id.goto_dashboard);
        goto_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(otp_verifation.this,MainActivity.class));
                finish();
            }
        });
    }
}