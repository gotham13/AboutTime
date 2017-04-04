package com.abhinavgautam.abouttime;

import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Parcel;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.abhinavgautam.abouttime.db.TaskContract;
import com.abhinavgautam.abouttime.db.TaskDbHelper;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.parseColor;

public class Main3Activity extends AppCompatActivity {
    private ArrayAdapter mAdapter2;
    private TaskDbHelper mHelper;
    private FloatingActionButton button1;
    private FloatingActionButton button2;
    private FloatingActionButton button;
    private ListView mTaskListView;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    int[] colorIntArray = {R.color.cyan,R.color.green,R.color.orange,R.color.brown,R.color.blue,R.color.grey,R.color.darkorange};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        button1 = (FloatingActionButton)findViewById(R.id.fab2);
        button2 = (FloatingActionButton)findViewById(R.id.fab3);
        button2.hide();
        button1.hide();
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        final int maxX = mdispSize.x;
        button = (FloatingActionButton)findViewById(R.id.fab);
        button.measure(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int width = button.getMeasuredWidth();

        button.animate().alphaBy(1).rotationBy(1440).setDuration(1500).x((maxX-width)/2).setDuration(1500);
        button.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), colorIntArray[1]));

        mHelper = new TaskDbHelper(this);
        if(getSupportActionBar()!=null)
         getSupportActionBar().hide();
        mTaskListView = (ListView) findViewById(R.id.list_todo);
        mTaskListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    button.hide();
                    return false;
                }
                else if(event.getAction()==MotionEvent.ACTION_UP){
                    button.show();
                    return false;
                }
                return false;
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                button1.show();
                button2.show();
                return true;
            }
        });
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_NOTI, TaskContract.TaskEntry.COL_DATE, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);

        cursor.close();
        db.close();
        updateUI();
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Rect viewRect = new Rect();
        Rect viewRect1=new Rect();
        Rect viewRect2=new Rect();
        button1.getGlobalVisibleRect(viewRect);
        button.getGlobalVisibleRect(viewRect1);
        button2.getGlobalVisibleRect(viewRect2);
        if (!(viewRect.contains((int) ev.getRawX(), (int) ev.getRawY())||viewRect1.contains((int) ev.getRawX(), (int) ev.getRawY())||viewRect2.contains((int) ev.getRawX(), (int) ev.getRawY()))) {
            {
                button1.hide();
                button2.hide();
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    @Override
    public void onContentChanged() {
        super.onContentChanged();

        View empty = findViewById(R.id.empty);
        ListView list = (ListView) findViewById(R.id.list_todo);
        list.setEmptyView(empty);
    }

    private class MyAdapter extends ArrayAdapter<String> {

        ArrayList<String> taskList;
        LayoutInflater mInflater;

        public MyAdapter(Context context, int resource, int textViewResourceId,
                         ArrayList<String> taskList1) {
            super(context, resource, textViewResourceId, taskList1);
            taskList = taskList1;
            mInflater = (LayoutInflater) Main3Activity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = mInflater.inflate(R.layout.item_todo, null, false);
            Calendar c = Calendar.getInstance();
            Calendar c1=Calendar.getInstance();
            c.clear();
            c1.clear();
            c.setTimeInMillis(System.currentTimeMillis());
            c1.set(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DATE),0,0);
            c1.add(Calendar.DATE,-1);
            TextView text = (TextView) view.findViewById(R.id.task_title);
            FloatingActionButton noti=(FloatingActionButton) view.findViewById(R.id.noti);
            String s = taskList.get(position);
            int indn=s.lastIndexOf(">");
            int indn1=s.lastIndexOf("<");
            String notis=s.substring(indn1+1,indn);
            int[] iconIntArray = {R.drawable.noti,R.drawable.nonoti};
            int indd=s.lastIndexOf(")");
            int indd1=s.lastIndexOf("(");
            long time=Long.parseLong(s.substring(indd1+1,indd));
            int ind=s.lastIndexOf("/");
            int ind1=s.lastIndexOf("/",ind-1);
            String a=s.substring(ind1+1,ind);
            s=s.substring(0,ind1);
            s=s+'\n';
            int indend = s.lastIndexOf('\n');
            int indstamp=s.lastIndexOf("ˑ",indend-1);
            String s1 = s.substring(indstamp+1,indend);
            if(notis.equals("1"))
                noti.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), iconIntArray[0]));
            else if(notis.equals("0")&&s1.contains(":"))
                noti.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), iconIntArray[1]));
            else
            noti.hide();
            if(c.getTimeInMillis()>time&&time!=0&&s1.contains("m")) {
                view.setBackgroundResource(R.drawable.asd);
                text.setAlpha(0.3F);
            }
            else if(time<=c1.getTimeInMillis()&&time!=0)
            {
                view.setBackgroundResource(R.drawable.asd);
                text.setAlpha(0.3F);
            }
            s=s.substring(0,indstamp);
            SpannableString str1 = new SpannableString(s1);
            str1.setSpan(new RelativeSizeSpan(0.7f), 0,str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (Build.VERSION.SDK_INT >= 23) {
               str1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.green, null)), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else
            {
                str1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.green)), 0, str1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            SpannableString str2 = new SpannableString(s);
            SpannableString a1=new SpannableString("/"+a+"/");
           a1.setSpan(new RelativeSizeSpan(0.0001f), 0,a1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            a1.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, a1.length(), 0);
            text.setText(TextUtils.concat(str2,str1,a1));
            return view;
        }
    }

    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_NOTI,TaskContract.TaskEntry.COL_DATE, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, TaskContract.TaskEntry.COL_DATE+ " ASC");
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            int idx1 = cursor.getColumnIndex(TaskContract.TaskEntry._ID);
            int idx2=cursor.getColumnIndex(TaskContract.TaskEntry.COL_NOTI);
            int idx3=cursor.getColumnIndex(TaskContract.TaskEntry.COL_DATE);
            taskList.add(cursor.getString(idx)+"/"+cursor.getString(idx1)+"/"+"<"+cursor.getInt(idx2)+">"+"("+cursor.getLong(idx3)+")");
        }

        if (mAdapter2 == null) {
            mAdapter2 = new MyAdapter(this, R.layout.item_todo, R.id.task_title, taskList);
            mTaskListView.setAdapter(mAdapter2);
        } else {
            mAdapter2.clear();
            mAdapter2.addAll(taskList);
            mAdapter2.notifyDataSetChanged();
        }
        cursor.close();
        db.close();
    }
    public void deleteTask(View view) {
        FloatingActionButton button = (FloatingActionButton)findViewById(R.id.fab);
         button.animate().rotationBy(-360);
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        int ind=task.lastIndexOf("/");
        int ind1=task.lastIndexOf("/",ind-1);
        String a=task.substring(ind1+1,ind);
        int aint=Integer.parseInt(a);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry._ID + " = ?",
                new String[]{a});
        deleteNotification(aint);
        db.close();
        updateUI();
    }
    public void bulkDelete(View view)
    {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_NOTI,TaskContract.TaskEntry.COL_DATE, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, TaskContract.TaskEntry.COL_DATE+ " ASC");
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_NOTI);
            if(cursor.getInt(idx)==1)
            {
                int idx1=cursor.getColumnIndex(TaskContract.TaskEntry._ID);
                int id=cursor.getInt(idx1);
                deleteNotification(id);
            }
        }
        cursor.close();
        db.delete(TaskContract.TaskEntry.TABLE, null, null);
        db.close();
        updateUI();

    }
    public void deleteNotification(int id)
    {
        Intent myIntent1 = new Intent(this, UtilityReceiver.class);
        myIntent1.setAction("MyBroadcast");
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, id, myIntent1,
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent1);
    }

    public void setNotification(int day,int month,int year,int hour,int minute,String task,int id)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.clear();
        cal.set(year,month,day,hour,minute);
        Intent myIntent1 = new Intent(this, UtilityReceiver.class);
        myIntent1.setAction("MyBroadcast");
        myIntent1.putExtra("ID",id);
        myIntent1.putExtra("task",task);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COL_NOTI,1);
        db.update(TaskContract.TaskEntry.TABLE, values, "_id="+id, null);
        db.close();
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, id, myIntent1,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager1.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent1);
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager1.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent1);
            }
        else
        {
            alarmManager1.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent1);
        }
    }
    public static class UtilityReceiver  extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
           Bundle extras = intent.getExtras();
            Intent notificationIntent = new Intent(context, Main3Activity.class);
            PendingIntent pendingIntent2 = PendingIntent.getActivity(context,0,notificationIntent,
                    0);
            Uri alarmSound = Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.noti);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.checki)
                    .setContentTitle("Deadline over for "+extras.get("task"))
                    .setPriority(1)
                    .setSound(alarmSound)
                    .setVibrate(new long[]{0,500})
                    .setContentIntent(pendingIntent2)
                    .setAutoCancel(true);
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(extras.getInt("ID"), mBuilder.build());
        }
    }
    public void button1Click(final View v)
    {
        Toast.makeText(Main3Activity.this, "I WAS CLICKED",
                Toast.LENGTH_LONG).show();
    }
    public void buttonClick(final View v) {
        button1.hide();
        button2.hide();
        FloatingActionButton button = (FloatingActionButton)findViewById(R.id.fab);
        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.5,20);
        myAnim.setInterpolator(interpolator);
        button.startAnimation(myAnim);
        beginDialog("",false,0);
    }
    public void editTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        int ind=task.lastIndexOf("/");
        int ind1=task.lastIndexOf("/",ind-1);
        int aint=Integer.parseInt(task.substring(ind1+1,ind));
        SQLiteDatabase db=mHelper.getReadableDatabase();
        Cursor cursor1= db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_NOTI, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while (cursor1.moveToNext()) {
            int cidx = cursor1.getColumnIndex(TaskContract.TaskEntry._ID);
            if (cursor1.getInt(cidx) == aint) {
                int cidx1 = cursor1.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
                String s = cursor1.getString(cidx1);
                int indstamp = s.lastIndexOf("ˑ");
                if(s.charAt(indstamp-1)=='\n')
                s = s.substring(0,indstamp-1);
                else
                s=s.substring(0,indstamp);
                beginDialog(s, true, aint);
                break;
            }
        }
            cursor1.close();
            db.close();
    }
    public void beginDialog(String t,final boolean edit,final int id)
    {
        final EditText taskEditText = new EditText(this);
        taskEditText.setText(t);
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.f);
        builder
                .setTitle("NEW TASK")
                .setMessage("What do you want to do next?")
                .setView(taskEditText);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                final String task = String.valueOf(taskEditText.getText()).toUpperCase();
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(taskEditText.getWindowToken(), 0);
                dateDialog(task,edit,id);
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }
    public void dateDialog(final String task,final boolean edit,final int idimpo)
    {
        final DatePicker picker = new DatePicker(this);
        picker.setMinDate(System.currentTimeMillis() - 1000);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP)
            picker.setCalendarViewShown(false);
        final AlertDialog dialog1 = new AlertDialog.Builder(Main3Activity.this,R.style.f)
                .setMessage("SELECT THE DEADLINE")
                .setView(picker)
                .setNegativeButton("no deadline", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase db = mHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task+"ˑ");
                        values.put(TaskContract.TaskEntry.COL_NOTI,0);
                        if(edit)
                        {
                            db.update(TaskContract.TaskEntry.TABLE, values, "_id="+idimpo, null);
                            db.close();
                            deleteNotification(idimpo);

                        }
                        else {
                            db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                    null,
                                    values,
                                    SQLiteDatabase.CONFLICT_REPLACE);
                            db.close();
                            FloatingActionButton button = (FloatingActionButton) findViewById(R.id.fab);
                            button.animate().rotationBy(360);
                        }
                        updateUI();
                    }
                })
                .setPositiveButton("SELECT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final int day = picker.getDayOfMonth();
                        final int month = picker.getMonth() + 1;
                        final int year = picker.getYear();
                        Calendar c = Calendar.getInstance();
                        if(!(month-1==c.get(Calendar.MONTH)&&year==c.get(Calendar.YEAR)&&day<c.get(Calendar.DAY_OF_MONTH)))
                        {
                            DateFormatSymbols dfs = new DateFormatSymbols();
                            String[] months = dfs.getShortMonths();
                            String monthst = months[month - 1];
                            monthst = monthst.toLowerCase();
                            String sub;
                            if (day % 10 == 1&&day%100!=11)
                                sub = "st";
                            else if (day % 10 == 2&&day%100!=12)
                                sub = "nd";
                            else if (day % 10 == 3&&day%100!=13)
                                sub = "rd";
                            else
                                sub = "th";
                            final String s = "" + day + sub + " " + monthst + " " + year+" ";
                            timeDialog(year,month,day,task,s,edit,idimpo);
                        }else
                        {
                            Toast.makeText(Main3Activity.this, "SELECTED DATE ALREADY PASSED",
                                    Toast.LENGTH_LONG).show();
                        }
                    }}).create();
        dialog1.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i==KeyEvent.KEYCODE_BACK)
                {
                    dialog1.dismiss();
                    beginDialog(task,edit,idimpo);
                }
                return true;
            }
        });
        dialog1.show();
    }
    public void timeDialog(final int year,final int month,final int day,final String task,final String s,final boolean edit,final int idimpo)
    {
        final TimePicker picker1 = new TimePicker(this);
        final AlertDialog dialog2 = new AlertDialog.Builder(Main3Activity.this,R.style.f)
                .setMessage("SELECT TIME")
                .setView(picker1)
                .setNegativeButton("NO TIME BOUND", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase db = mHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(System.currentTimeMillis());
                        cal.clear();
                        cal.set(year,month-1,day,0,0);
                        long t=cal.getTimeInMillis();
                        values.put(TaskContract.TaskEntry.COL_DATE, t);
                        values.put(TaskContract.TaskEntry.COL_NOTI,0);
                        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task + "\n" +"ˑ"+s);
                        if(edit)
                        {
                            db.update(TaskContract.TaskEntry.TABLE, values, "_id="+idimpo, null);
                            db.close();
                            deleteNotification(idimpo);
                        }
                        else {
                            db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                    null,
                                    values,
                                    SQLiteDatabase.CONFLICT_REPLACE);
                            db.close();
                            FloatingActionButton button = (FloatingActionButton) findViewById(R.id.fab);
                            button.animate().rotationBy(360);
                        }
                        updateUI();

                    }
                })
                .setPositiveButton("select", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int hour;
                        int minute;
                        Calendar c = Calendar.getInstance();
                        if (Build.VERSION.SDK_INT >=23)
                        {
                            hour = picker1.getHour();
                            minute = picker1.getMinute();
                        }
                        else
                        {
                            hour = picker1.getCurrentHour();
                            minute = picker1.getCurrentMinute();
                        }
                        final int hour1=hour;
                        final int minute1=minute;
                        if(!((year==c.get(Calendar.YEAR)&& c.get(Calendar.MONTH)==month-1&&day==c.get(Calendar.DAY_OF_MONTH)&&hour==c.get(Calendar.HOUR_OF_DAY)&&minute<=c.get(Calendar.MINUTE))||(year==c.get(Calendar.YEAR)&&month-1==c.get(Calendar.MONTH)&&day==c.get(Calendar.DAY_OF_MONTH)&&hour<c.get(Calendar.HOUR_OF_DAY))))
                        {
                            String am_pm = (hour < 12) ? "am" : "pm";
                            if (hour >12)
                                hour = hour - 12;
                            if(hour==0)
                                hour=12;
                            String s2;
                            if (minute == 0) {
                                s2 = "" + hour + ":" + minute + "0" + am_pm;
                            } else if (minute > 0 && minute <= 9)
                                s2 = "" + hour + ":" + "0" + minute + am_pm;
                            else
                                s2 = "" + hour + ":" + minute + am_pm;
                            final String s3 = s2;
                            final AlertDialog dialog3=new AlertDialog.Builder(Main3Activity.this,R.style.f)
                                    .setMessage("DO YOU WANT TO SET A REMINDER ABOUT THIS TASK?")
                                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            SQLiteDatabase db = mHelper.getWritableDatabase();
                                            ContentValues values = new ContentValues();
                                            Calendar cal = Calendar.getInstance();
                                            cal.setTimeInMillis(System.currentTimeMillis());
                                            cal.clear();
                                            cal.set(year,month-1,day,hour1,minute1);
                                            long t=cal.getTimeInMillis();
                                            values.put(TaskContract.TaskEntry.COL_DATE, t);
                                            values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task + "\n" +"ˑ"+s + "  " + s3);
                                            if(edit)
                                            {
                                                db.update(TaskContract.TaskEntry.TABLE, values, "_id="+idimpo, null);
                                                db.close();
                                                deleteNotification(idimpo);
                                                setNotification(day, month - 1, year, hour1, minute1, task, idimpo);
                                            }
                                            else {
                                                final int id = (int) db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                                        null,
                                                        values,
                                                        SQLiteDatabase.CONFLICT_REPLACE);
                                                db.close();
                                                setNotification(day, month - 1, year, hour1, minute1, task, id);
                                                FloatingActionButton button = (FloatingActionButton)findViewById(R.id.fab);
                                                button.animate().rotationBy(360);
                                            }
                                            Toast.makeText(Main3Activity.this, "NOTIFICATION SET",
                                                    Toast.LENGTH_LONG).show();
                                            updateUI();
                                        }
                                    })
                                    .setNegativeButton("NO",new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            SQLiteDatabase db = mHelper.getWritableDatabase();
                                            ContentValues values = new ContentValues();
                                            Calendar cal = Calendar.getInstance();
                                            cal.setTimeInMillis(System.currentTimeMillis());
                                            cal.clear();
                                            cal.set(year,month-1,day,hour1,minute1);
                                            long t=cal.getTimeInMillis();
                                            values.put(TaskContract.TaskEntry.COL_DATE, t);
                                            values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task + "\n" +"ˑ"+s + "  " + s3);
                                            values.put(TaskContract.TaskEntry.COL_NOTI,0);
                                            if(edit)
                                            {
                                                db.update(TaskContract.TaskEntry.TABLE, values, "_id="+idimpo, null);
                                                db.close();
                                                deleteNotification(idimpo);
                                            }
                                            else {
                                               db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                                        null,
                                                        values,
                                                        SQLiteDatabase.CONFLICT_REPLACE);
                                                FloatingActionButton button = (FloatingActionButton)findViewById(R.id.fab);
                                                button.animate().rotationBy(360);
                                            }
                                            db.close();
                                            updateUI();
                                        }
                                    })
                                    .create();
                            dialog3.setOnKeyListener(new DialogInterface.OnKeyListener() {
                                @Override
                                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                                    if (i == KeyEvent.KEYCODE_BACK) {
                                        dialog3.dismiss();
                                        timeDialog(year,month,day,task,s,edit,idimpo);
                                    }
                                        return true;
                                    }
                                });
                            dialog3.show();
                        }else{
                            Toast.makeText(Main3Activity.this, "SELECTED TIME ALREADY PASSED",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }).create();
        dialog2.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i==KeyEvent.KEYCODE_BACK)
                {   dialog2.dismiss();
                    dateDialog(task,edit,idimpo);
                }
                return true;
            }
        });
        dialog2.show();
    }

    @Override
    public void onBackPressed() {
        button1.hide();
        button2.hide();
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        int maxX = mdispSize.x;
        FloatingActionButton button = (FloatingActionButton)findViewById(R.id.fab);
        Rect myViewRect = new Rect();
        button.getGlobalVisibleRect(myViewRect);
        float x = myViewRect.left;
        mTaskListView.animate().alphaBy(-1).setDuration(1500);
        TextView tv1=(TextView)findViewById(R.id.textView6);
        tv1.animate().alphaBy(-1).setDuration(1500);
        TextView tv2=(TextView)findViewById(R.id.empty);
        tv2.animate().alphaBy(-1).setDuration(1500);
        button.measure(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        int width = button.getMeasuredWidth();
        button.animate().alphaBy(-1).rotationBy(-1080).setDuration(1500).xBy(-(maxX-width)/2).setDuration(1500);
        final Timer t=new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                t.cancel();
                finish();
            }
        }, 1500);

    }
}
