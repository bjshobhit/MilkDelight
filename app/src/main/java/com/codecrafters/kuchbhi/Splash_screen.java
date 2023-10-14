package com.codecrafters.kuchbhi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Splash_screen extends AppCompatActivity {
ImageView goto_signin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        goto_signin=findViewById(R.id.goto_singin);
        goto_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Splash_screen.this, Sign_in.class);
                startActivity(intent);
                finish();
            }
        });
    }
}