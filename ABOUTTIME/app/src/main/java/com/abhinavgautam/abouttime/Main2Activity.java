package com.abhinavgautam.abouttime;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Main2Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getSupportActionBar()!=null)
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main2);
        GridLayout l1=(GridLayout)findViewById(R.id.l1);
        l1.animate().alphaBy(1).setDuration(500);
    }
    public void buttonClickFunction(View v)
    {
        Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
        startActivity(intent);
        finish();
    }
    public void buttonClickFunction1(View v)
    {

        Intent intent = new Intent(getApplicationContext(), Main4Activity.class);
        startActivity(intent);
        finish();

    }
    public void buttonClickFunction2(View v)
    {

        Intent intent = new Intent(getApplicationContext(), Main5Activity.class);
        startActivity(intent);
        finish();

    }
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();

    }
}
