package com.codecrafters.kuchbhi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.MemoryCacheSettings;
import com.google.firebase.firestore.PersistentCacheSettings;

public class Splash_screen extends AppCompatActivity {

    private ConstraintLayout splash_layout;
    private TextView textView1,textView2;
    private ImageView imageView1,imageView2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        splash_layout = findViewById(R.id.splash_layout);
        textView1 = findViewById(R.id.textView3);
        imageView1 = findViewById(R.id.imageView3);
        textView2 = findViewById(R.id.textView4);
        imageView2 = findViewById(R.id.imageView4);
        Animation slideUpAnimation = AnimationUtils.loadAnimation(this, R.anim.splash_animation);
        splash_layout.startAnimation(slideUpAnimation);
        textView1.startAnimation(slideUpAnimation);
        textView2.startAnimation(slideUpAnimation);
        imageView1.startAnimation(slideUpAnimation);
        imageView2.startAnimation(slideUpAnimation);

        Pair[] pairs= new Pair[3];
        pairs[0]=new Pair(imageView1,"image");
        pairs[1]=new Pair(textView1,"textview1");
        pairs[2]=new Pair(textView2,"textview2");

        ActivityOptions options= ActivityOptions.makeSceneTransitionAnimation(Splash_screen.this,pairs);

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
                    FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()){

                                Intent intent= new Intent(Splash_screen.this,DashBoard.class);
                                startActivity(intent,options.toBundle());
                                finish();
                            }
                            else {
                                Intent intent= new Intent(Splash_screen.this,Sign_in.class);
                                startActivity(intent,options.toBundle());
                                finish();
                            }
                        }
                    });
                }
                else {
                    Intent intent= new Intent(Splash_screen.this,Sign_in.class);
                    startActivity(intent,options.toBundle());
                    finish();
                }


            }
        });

    }
}