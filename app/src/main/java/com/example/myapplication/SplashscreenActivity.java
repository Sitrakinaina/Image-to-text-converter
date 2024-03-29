package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SplashscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splashscreen);
        getSupportActionBar().hide();
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent intent = new Intent(SplashscreenActivity.this , MainActivity.class);
               startActivity(intent);
               finish();
           }
       },3000);
    }
}