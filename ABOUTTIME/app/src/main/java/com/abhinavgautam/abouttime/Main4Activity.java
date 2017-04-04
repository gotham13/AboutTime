package com.abhinavgautam.abouttime;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.text.style.TtsSpan;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.abhinavgautam.abouttime.db.TaskContract;

import java.util.Timer;
import java.util.TimerTask;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Main4Activity extends FragmentActivity {
    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    public  FloatingActionButton fab;
    public  FloatingActionButton button1;
    public  FloatingActionButton button2;
    public  FloatingActionButton button3;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    int[] colorIntArray = {R.color.cyan,R.color.green,R.color.orange,R.color.brown,R.color.blue,R.color.grey,R.color.darkorange};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        fab=(FloatingActionButton)findViewById(R.id.fab1);
        button1=(FloatingActionButton)findViewById(R.id.fab10);
        button2=(FloatingActionButton)findViewById(R.id.fab11);
        button3=(FloatingActionButton)findViewById(R.id.fab12);
        button1.hide();
        button2.hide();
        button3.hide();
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                button1.show();
                button2.show();
                button3.show();
                return true;
            }
        });
        viewPager = (ViewPager) findViewById(R.id.pager);
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        int maxy=mdispSize.y;
        final Animation animation = new TranslateAnimation(0,0,maxy/4,0);
        animation.setDuration(750);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(70);
        fab.startAnimation(animation);
        fab.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), colorIntArray[0]));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                if(position%7==0)
                    position=0;
                else if((position-1)%7==0)
                    position=1;
                else if((position-2)%7==0)
                    position=2;
                else if((position-3)%7==0)
                    position=3;
                else if((position-4)%7==0)
                    position=4;
                else if((position-5)%7==0)
                    position=5;
                else if((position-6)%7==0)
                    position=6;
                animateFab(position);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
                            }
        });
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect viewRect = new Rect();
        Rect viewRect1=new Rect();
        Rect viewRect2=new Rect();
        Rect viewRect3=new Rect();
        button1.getGlobalVisibleRect(viewRect);
        fab.getGlobalVisibleRect(viewRect1);
        button2.getGlobalVisibleRect(viewRect2);
        button3.getGlobalVisibleRect(viewRect3);
        if (!(viewRect.contains((int) ev.getRawX(), (int) ev.getRawY())||viewRect1.contains((int) ev.getRawX(), (int) ev.getRawY())||viewRect2.contains((int) ev.getRawX(), (int) ev.getRawY())||viewRect3.contains((int) ev.getRawX(), (int) ev.getRawY()))) {
            {
                button1.hide();
                button2.hide();
                button3.hide();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    int[] iconIntArray = {R.drawable.create};
    protected void animateFab(final int position) {

        fab.clearAnimation();
        fab.setClickable(false);
        // Scale down animation
        ScaleAnimation shrink =  new ScaleAnimation(1f, 0.2f, 1f, 0.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        shrink.setDuration(150);     // animation duration in milliseconds
        shrink.setInterpolator(new DecelerateInterpolator());
        shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                fab.setClickable(false);
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                // Change FAB color and icon
                fab.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), colorIntArray[position]));
                button1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),colorIntArray[position]));
                button2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),colorIntArray[position]));
                button3.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(),colorIntArray[position]));
                fab.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), iconIntArray[0]));
                // Scale up animation
                ScaleAnimation expand =  new ScaleAnimation(0.2f, 1f, 0.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

                expand.setDuration(100);     // animation duration in milliseconds
                expand.setInterpolator(new AccelerateInterpolator());
                fab.startAnimation(expand);
                expand.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                            fab.setClickable(false);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        final Timer t=new Timer();
                        t.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                fab.setClickable(true);
                                t.cancel();
                            }
                        },3000);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        fab.startAnimation(shrink);
    }
    @Override
    public void onBackPressed()
    {
        button1.hide();
        button2.hide();
        button3.hide();
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        int maxy=mdispSize.y;
        final Animation animation = new TranslateAnimation(0,0,0,maxy/4);
        animation.setDuration(750);
        fab.startAnimation(animation);
        viewPager.animate().alphaBy(-1).setDuration(750);
        fab.animate().alphaBy(-1).setDuration(750);
        final Timer t=new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
                t.cancel();
                finish();
            }
        }, 750);
    }
}
