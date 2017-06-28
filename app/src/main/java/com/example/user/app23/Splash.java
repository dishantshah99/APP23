package com.example.user.app23;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.user.app23.Constant.Constant;

public class Splash extends AppCompatActivity {
    static final int time = 3000;
    Context context = this;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sp = getSharedPreferences(Constant.PER, MODE_PRIVATE);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(context, Reside.class));
            }
        }, time);
    }
}


