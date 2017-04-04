    package com.abhinavgautam.abouttime;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.abhinavgautam.abouttime.db.TaskContract;
import com.abhinavgautam.abouttime.db.TaskDbHelper;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

    public class Main5Activity extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        mHelper = new TaskDbHelper(this);
        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();
        mTaskListView = (ListView) findViewById(R.id.listView3);
        button=(FloatingActionButton)findViewById(R.id.fab4);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLEN,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_NOTI, TaskContract.TaskEntry.COL_INITIME, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);

        cursor.close();
        db.close();
        updateUI();

    }
    private class MyAdapter extends ArrayAdapter<String> {

        ArrayList<String> taskList;
        LayoutInflater mInflater;

        public MyAdapter(Context context, int resource, int textViewResourceId,
                         ArrayList<String> taskList1) {
            super(context, resource, textViewResourceId, taskList1);
            taskList = taskList1;
            mInflater = (LayoutInflater) Main5Activity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = mInflater.inflate(R.layout.item_todo, null, false);
            TextView text = (TextView) view.findViewById(R.id.task_title);
            FloatingActionButton noti=(FloatingActionButton)view.findViewById(R.id.noti);
            noti.hide();
            String s = taskList.get(position);
            s=s+"\n";
            int indx = s.lastIndexOf("/");
            int indx1 = s.lastIndexOf("/", indx - 1);
            String a = s.substring(indx1 + 1, indx);
            int ind1=s.lastIndexOf("ˑ");
            String s1=s.substring(0,ind1);
            String s2=s.substring(ind1+1,indx1);
            SpannableString s11 = new SpannableString(s1);
            SpannableString s21 = new SpannableString("\n"+s2);
            s21.setSpan(new RelativeSizeSpan(0.7f), 0, s21.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (Build.VERSION.SDK_INT >= 23) {
                s21.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.green, null)), 0, s21.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else
            {
                s21.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.green)), 0, s21.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            SpannableString a1=new SpannableString("/"+a+"/");
            a1.setSpan(new RelativeSizeSpan(0.7f), 0,a1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            a1.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, a1.length(), 0);
            text.setText(TextUtils.concat(s11,s21,a1));
            return view;
        }
    }
    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLEN,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_NOTI, TaskContract.TaskEntry.COL_INITIME, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, TaskContract.TaskEntry.COL_INITIME+ " ASC");
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            int idx1 = cursor.getColumnIndex(TaskContract.TaskEntry._ID);
            taskList.add(cursor.getString(idx)+"/"+cursor.getString(idx1)+"/");
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
        public void setNotification2(int houri,int minutei,int hourf,int minutef,String task,int time,int id,boolean vib,boolean sound)
        {
            Calendar cal = Calendar.getInstance();
            Calendar dt=Calendar.getInstance();
            Calendar cal1=Calendar.getInstance();
            cal1.clear();
            cal1.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE),hourf,minutef);
            dt.clear();
            dt.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE),houri,minutei);
            dt.add(Calendar.MILLISECOND,time);
            Intent myIntent1 = new Intent(getApplicationContext(), UtilityReceiver2.class);
            myIntent1.setAction("MyBroadcast");
            myIntent1.putExtra("ID",id);
            myIntent1.putExtra("task",task);
            myIntent1.putExtra("vib",vib);
            myIntent1.putExtra("time",time);
            myIntent1.putExtra("sound",sound);
            myIntent1.putExtra("timems",dt.getTimeInMillis());
            myIntent1.putExtra("endms",cal1.getTimeInMillis());
            SQLiteDatabase db = mHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TaskContract.TaskEntry.COL_NOTI,1);
            db.update(TaskContract.TaskEntry.TABLEN, values, "_id="+id, null);
            db.close();
            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplicationContext(), id, myIntent1,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager1.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, dt.getTimeInMillis(), pendingIntent1);
            }
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager1.setExact(AlarmManager.RTC_WAKEUP, dt.getTimeInMillis(), pendingIntent1);
            }
            else
            {
                alarmManager1.set(AlarmManager.RTC_WAKEUP, dt.getTimeInMillis(), pendingIntent1);
            }
        }

        public static class UtilityReceiver2  extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();
                Intent notificationIntent = new Intent(context, Main5Activity.class);
                PendingIntent pendingIntent2 = PendingIntent.getActivity(context,0,notificationIntent,
                        0);
                Uri alarmSound = Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.noti);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.noti)
                        .setContentTitle("Reminder")
                        .setPriority(1)
                        .setContentText("Reminder for "+extras.getString("task"))
                        .setContentIntent(pendingIntent2)
                        .setAutoCancel(true);
                if(extras.getBoolean("vib"))
                    mBuilder.setVibrate(new long[]{0,500});
                if(extras.getBoolean("sound"))
                    mBuilder.setSound(alarmSound);
                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(extras.getInt("ID"), mBuilder.build());
                Calendar dt=Calendar.getInstance();
                dt.clear();
                dt.setTimeInMillis(extras.getLong("timems"));
                dt.add(Calendar.MILLISECOND,extras.getInt("time"));
                if(dt.getTimeInMillis()<=extras.getLong("endms")) {
                    Intent myIntent1 = new Intent(context, UtilityReceiver2.class);
                    myIntent1.setAction("MyBroadcast");
                    myIntent1.putExtra("ID", extras.getInt("ID"));
                    myIntent1.putExtra("task", extras.getString("task"));
                    myIntent1.putExtra("vib", extras.getBoolean("vib"));
                    myIntent1.putExtra("time", extras.getInt("time"));
                    myIntent1.putExtra("sound", extras.getBoolean("sound"));
                    myIntent1.putExtra("endms", extras.getLong("endms"));
                    myIntent1.putExtra("timems", dt.getTimeInMillis());
                    PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, extras.getInt("ID"), myIntent1,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    AlarmManager alarmManager1 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager1.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, dt.getTimeInMillis(), pendingIntent1);
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        alarmManager1.setExact(AlarmManager.RTC_WAKEUP, dt.getTimeInMillis(), pendingIntent1);
                    } else {
                        alarmManager1.set(AlarmManager.RTC_WAKEUP, dt.getTimeInMillis(), pendingIntent1);
                    }
                }

            }
        }

        public void setNotification1(int day,int month,int year,int hour,int minute,String task,int id,boolean vib,boolean sound)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.clear();
            cal.set(year,month,day,hour,minute);
            Intent myIntent1 = new Intent(this, UtilityReceiver1.class);
            myIntent1.setAction("MyBroadcast1");
            myIntent1.putExtra("ID",id);
            myIntent1.putExtra("task",task);
            myIntent1.putExtra("vib",vib);
            myIntent1.putExtra("sound",sound);
            SQLiteDatabase db = mHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TaskContract.TaskEntry.COL_NOTI,1);
            db.update(TaskContract.TaskEntry.TABLEN, values, "_id="+id, null);
            db.close();
            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this,id, myIntent1,
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
        public static class UtilityReceiver1  extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();
                Intent notificationIntent = new Intent(context, Main5Activity.class);
                PendingIntent pendingIntent2 = PendingIntent.getActivity(context,0,notificationIntent,
                        0);
                Uri alarmSound = Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.noti);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.noti)
                        .setContentTitle("Reminder for "+extras.get("task"))
                        .setPriority(1)
                        .setContentIntent(pendingIntent2)
                        .setAutoCancel(true);
                if(extras.getBoolean("sound"))
                mBuilder.setSound(alarmSound);
                if(extras.getBoolean("vib"))
                        mBuilder.setVibrate(new long[]{0,500});

                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(extras.getInt("ID"), mBuilder.build());
            }
        }
        public void setNotificationWeek(int hour,int minute,String task,int id,boolean vib,boolean sound,String action)
        {
            Calendar cal = Calendar.getInstance();
            Calendar dt=Calendar.getInstance();
            dt.clear();
            dt.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE),hour,minute);
            if(action.equals("mon"))
            while(dt.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY)
            {
                dt.add(Calendar.DATE,1);
            }
            if(action.equals("tue"))
                while(dt.get(Calendar.DAY_OF_WEEK)!=Calendar.TUESDAY)
                {
                    dt.add(Calendar.DATE,1);
                }
            if(action.equals("wed"))
                while(dt.get(Calendar.DAY_OF_WEEK)!=Calendar.WEDNESDAY)
                {
                    dt.add(Calendar.DATE,1);
                }
            if(action.equals("thurs"))
                while(dt.get(Calendar.DAY_OF_WEEK)!=Calendar.THURSDAY)
                {
                    dt.add(Calendar.DATE,1);
                }
            if(action.equals("fri"))
                while(dt.get(Calendar.DAY_OF_WEEK)!=Calendar.FRIDAY)
                {
                    dt.add(Calendar.DATE,1);
                }
            if(action.equals("sat"))
                while(dt.get(Calendar.DAY_OF_WEEK)!=Calendar.SATURDAY)
                {
                    dt.add(Calendar.DATE,1);
                }
            if(action.equals("sun"))
                while(dt.get(Calendar.DAY_OF_WEEK)!=Calendar.SUNDAY)
                {
                    dt.add(Calendar.DATE,1);
                }
            Intent myIntent1 = new Intent(Main5Activity.this, UtilityReceiverWeek.class);
            myIntent1.setAction(action);
            myIntent1.putExtra("ID",id);
            myIntent1.putExtra("task",task);
            myIntent1.putExtra("date",dt.get(Calendar.DATE));
            myIntent1.putExtra("year",dt.get(Calendar.YEAR));
            myIntent1.putExtra("month",dt.get(Calendar.MONTH));
            myIntent1.putExtra("hour",hour);
            myIntent1.putExtra("minute",minute);
            myIntent1.putExtra("vib",vib);
            myIntent1.putExtra("sound",sound);
            myIntent1.putExtra("action",action);
            SQLiteDatabase db = mHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(TaskContract.TaskEntry.COL_NOTI,1);
            db.update(TaskContract.TaskEntry.TABLEN, values, "_id="+id, null);
            db.close();
            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(Main5Activity.this, id, myIntent1,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager1 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager1.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, dt.getTimeInMillis(), pendingIntent1);
            }
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager1.setExact(AlarmManager.RTC_WAKEUP, dt.getTimeInMillis(), pendingIntent1);
            }
            else
            {
                alarmManager1.set(AlarmManager.RTC_WAKEUP, dt.getTimeInMillis(), pendingIntent1);
            }

        }
        public static class UtilityReceiverWeek  extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle extras = intent.getExtras();
                Intent notificationIntent = new Intent(context, Main5Activity.class);
                PendingIntent pendingIntent2 = PendingIntent.getActivity(context,0,notificationIntent,
                        0);
                Uri alarmSound = Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.noti);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.noti)
                        .setContentTitle("Reminder")
                        .setPriority(1)
                        .setContentText("Reminder for "+extras.getString("task"))
                        .setContentIntent(pendingIntent2)
                        .setAutoCancel(true);
                if(extras.getBoolean("vib"))
                    mBuilder.setVibrate(new long[]{0,500});
                if(extras.getBoolean("sound"))
                    mBuilder.setSound(alarmSound);
                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(extras.getInt("ID"), mBuilder.build());
                Calendar dt=Calendar.getInstance();
                dt.clear();
                dt.set(extras.getInt("year"),extras.getInt("month"),extras.getInt("date"),extras.getInt("hour"),extras.getInt("minute"));
                dt.add(Calendar.DATE,7);
                Intent myIntent1 = new Intent(context, UtilityReceiverWeek.class);
                myIntent1.setAction(extras.getString("action"));
                myIntent1.putExtra("ID",extras.getInt("ID"));
                myIntent1.putExtra("task",extras.getString("task"));
                myIntent1.putExtra("date",dt.get(Calendar.DATE));
                myIntent1.putExtra("year",dt.get(Calendar.YEAR));
                myIntent1.putExtra("month",dt.get(Calendar.MONTH));
                myIntent1.putExtra("hour",extras.getInt("hour"));
                myIntent1.putExtra("minute",extras.getInt("minute"));
                myIntent1.putExtra("vib",extras.getBoolean("vib"));
                myIntent1.putExtra("sound",extras.getBoolean("sound"));
                PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context,extras.getInt("ID"), myIntent1,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager1 = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager1.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, dt.getTimeInMillis(), pendingIntent1);
                }
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager1.setExact(AlarmManager.RTC_WAKEUP, dt.getTimeInMillis(), pendingIntent1);
                }
                else
                {
                    alarmManager1.set(AlarmManager.RTC_WAKEUP, dt.getTimeInMillis(), pendingIntent1);
                }

            }
        }
        public void deleteTask(View view) {
            View parent = (View) view.getParent();
            TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
            String task = String.valueOf(taskTextView.getText());
            int ind=task.lastIndexOf("/");
            int ind1=task.lastIndexOf("/",ind-1);
            String a=task.substring(ind1+1,ind);
            int aint=Integer.parseInt(a);
            SQLiteDatabase db = mHelper.getWritableDatabase();
            db.delete(TaskContract.TaskEntry.TABLEN,
                    TaskContract.TaskEntry._ID + " = ?",
                    new String[]{a});
            deleteNotification(aint);
            db.close();
            updateUI();
        }
        public void deleteNotification(int id)
        {
            Intent myIntent1 = new Intent(this, UtilityReceiver1.class);
            myIntent1.setAction("MyBroadcast1");
            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, id, myIntent1,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.cancel(pendingIntent1);
            Intent myIntent2 = new Intent(getApplicationContext(), UtilityReceiver2.class);
            myIntent2.setAction("MyBroadcast");
            PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getApplicationContext(), id, myIntent2,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager1 = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager1.cancel(pendingIntent2);

            Intent myIntent3= new Intent(this, UtilityReceiverWeek.class);
            myIntent3.setAction("mon");
            PendingIntent pendingIntent3 = PendingIntent.getBroadcast(this, id, myIntent3,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager2 = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager2.cancel(pendingIntent3);

            Intent myIntent4= new Intent(this, UtilityReceiverWeek.class);
            myIntent4.setAction("tue");
            PendingIntent pendingIntent4 = PendingIntent.getBroadcast(this, id, myIntent4,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager3 = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager3.cancel(pendingIntent4);

            Intent myIntent5= new Intent(this, UtilityReceiverWeek.class);
            myIntent5.setAction("wed");
            PendingIntent pendingIntent5 = PendingIntent.getBroadcast(this, id, myIntent5,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager4 = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager4.cancel(pendingIntent5);

            Intent myIntent6= new Intent(this, UtilityReceiverWeek.class);
            myIntent6.setAction("thurs");
            PendingIntent pendingIntent6= PendingIntent.getBroadcast(this, id, myIntent6,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager5 = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager5.cancel(pendingIntent6);

            Intent myIntent7= new Intent(this, UtilityReceiverWeek.class);
            myIntent7.setAction("fri");
            PendingIntent pendingIntent7= PendingIntent.getBroadcast(this, id, myIntent7,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager6 = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager6.cancel(pendingIntent7);

            Intent myIntent8= new Intent(this, UtilityReceiverWeek.class);
            myIntent8.setAction("sat");
            PendingIntent pendingIntent8= PendingIntent.getBroadcast(this, id, myIntent8,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager7 = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager7.cancel(pendingIntent8);

            Intent myIntent9= new Intent(this, UtilityReceiverWeek.class);
            myIntent9.setAction("sun");
            PendingIntent pendingIntent9= PendingIntent.getBroadcast(this, id, myIntent9,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager8 = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager8.cancel(pendingIntent9);
        }


    public int count=0;
    public void buttonClickFunction3(View v)
    {
        final TimePicker picker1=new TimePicker(this);
        final EditText editText=new EditText(this);
        AlertDialog dialog=new AlertDialog.Builder(this,R.style.f)
                .setTitle("Enter what the reminder is about")
                .setView(editText)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String s=String.valueOf(editText.getText()).toUpperCase();
                        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                        final AlertDialog dialog1=new AlertDialog.Builder(Main5Activity.this,R.style.f)
                                .setTitle("Select the starting time for the reminder")
                                .setView(picker1)
                                .setPositiveButton("Specific date", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        int hour;
                                        int minute;
                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                            hour=picker1.getHour();
                                            minute= picker1.getMinute();
                                        }
                                        else
                                        {
                                            hour=picker1.getCurrentHour();
                                            minute=picker1.getCurrentMinute();
                                        }
                                        final int hour1=hour;
                                        final int minute1=minute;
                                        final AlertDialog dialog2=new AlertDialog.Builder(Main5Activity.this,R.style.f)
                                        .setTitle("Select date and other settings")
                                        .setView(R.layout.dialog1)
                                                .create();

                                        dialog2.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                DatePicker datePicker=(DatePicker)dialog2.findViewById(R.id.datePicker);
                                                    assert datePicker!=null;
                                                    int year = datePicker.getYear();
                                                    int month = datePicker.getMonth() + 1;
                                                    int day = datePicker.getDayOfMonth();
                                                Calendar c=Calendar.getInstance();
                                                if(!((year==c.get(Calendar.YEAR)&&month-1==c.get(Calendar.MONTH)&&day==c.get(Calendar.DAY_OF_MONTH)&&hour1==c.get(Calendar.HOUR_OF_DAY)&&minute1<=c.get(Calendar.MINUTE))||(year==c.get(Calendar.YEAR)&&month-1==c.get(Calendar.MONTH)&&day==c.get(Calendar.DAY_OF_MONTH)&&hour1<c.get(Calendar.HOUR_OF_DAY)||year==c.get(Calendar.YEAR)&&month-1==c.get(Calendar.MONTH)&&day<c.get(Calendar.DAY_OF_MONTH))))
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
                                                    String s1 = "" + day + sub + " " + monthst + " " + year+" ";
                                                    int hourini=hour1;
                                                    String am_pm = (hourini < 12) ? "am" : "pm";
                                                    if (hourini >12)
                                                        hourini = hourini - 12;
                                                    if(hourini==0)
                                                        hourini=12;
                                                    String s2;
                                                    if (minute1 == 0) {
                                                        s2 = "" + hourini + ":" + minute1 + "0" + am_pm;
                                                    } else if (minute1> 0 && minute1 <= 9)
                                                        s2 = "" + hourini + ":" + "0" + minute1 + am_pm;
                                                    else
                                                        s2 = "" + hourini + ":" + minute1 + am_pm;
                                                    SQLiteDatabase db=mHelper.getWritableDatabase();
                                                    ContentValues values=new ContentValues();
                                                    Calendar cal = Calendar.getInstance();
                                                    cal.setTimeInMillis(System.currentTimeMillis());
                                                    cal.clear();
                                                    cal.set(year,month-1,day,hour1,minute1);
                                                    long t=cal.getTimeInMillis();
                                                    values.put(TaskContract.TaskEntry.COL_INITIME,t);
                                                    values.put(TaskContract.TaskEntry.COL_TASK_TITLE,s+"ˑ"+s1+" "+s2);
                                                    int id=(int)db.insertWithOnConflict(TaskContract.TaskEntry.TABLEN,
                                                            null,
                                                            values,
                                                            SQLiteDatabase.CONFLICT_REPLACE);
                                                    db.close();
                                                    SwitchCompat vib=(SwitchCompat) dialog2.findViewById(R.id.switch4);
                                                    SwitchCompat sound=(SwitchCompat) dialog2.findViewById(R.id.switch5);
                                                    boolean vib1=false;
                                                    boolean sound1=false;
                                                    if (vib != null && vib.isChecked())
                                                        vib1 = true;
                                                    if (sound != null && sound.isChecked())
                                                        sound1 = true;
                                                    updateUI();
                                                    setNotification1(day,month-1,year,hour1,minute1,s,id,vib1,sound1);
                                                }
                                            }
                                        });
                                        dialog2.show();
                                        DatePicker datePicker=(DatePicker)dialog2.findViewById(R.id.datePicker);
                                        if(datePicker!=null)
                                        datePicker.setMinDate(System.currentTimeMillis()-1000);
                                        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP&&datePicker!=null)
                                            datePicker.setCalendarViewShown(false);

                                    }
                                })
                                .setNegativeButton("periodic\ndate", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        int hour;
                                        int minute;
                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                            hour=picker1.getHour();
                                            minute= picker1.getMinute();
                                        }
                                        else
                                        {
                                            hour=picker1.getCurrentHour();
                                            minute=picker1.getCurrentMinute();
                                        }
                                        final int hour1=hour;
                                        final int minute1=minute;
                                        final AlertDialog dialog3=new AlertDialog.Builder(Main5Activity.this,R.style.f)
                                                .setTitle("SELECT WEEK DAYS AND OTHER SETTINGS ")
                                                .setView(R.layout.dialog)
                                                .create();
                                        dialog3.setButton(AlertDialog.BUTTON_POSITIVE, "SELECT", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                SwitchCompat vib=(SwitchCompat)dialog3.findViewById(R.id.switch1);
                                                SwitchCompat sound=(SwitchCompat)dialog3.findViewById(R.id.switch2);
                                                final CheckBox c1=(CheckBox)dialog3.findViewById(R.id.checkBox);
                                                final CheckBox c2=(CheckBox)dialog3.findViewById(R.id.checkBox2);
                                                final CheckBox c3=(CheckBox)dialog3.findViewById(R.id.checkBox3);
                                                final CheckBox c4=(CheckBox)dialog3.findViewById(R.id.checkBox4);
                                                final CheckBox c5=(CheckBox)dialog3.findViewById(R.id.checkBox5);
                                                final CheckBox c6=(CheckBox)dialog3.findViewById(R.id.checkBox6);
                                                final CheckBox c7=(CheckBox)dialog3.findViewById(R.id.checkBox7);
                                                if(c1!=null&&c2!=null&&c3!=null&&c4!=null&&c5!=null&&c6!=null&&c7!=null) {
                                                    boolean vib1 = false;
                                                    boolean sound1 = false;
                                                    if (vib != null && vib.isChecked())
                                                        vib1 = true;
                                                    if (sound != null && sound.isChecked())
                                                        sound1 = true;
                                                    String s1 = "";
                                                    if (c1.isChecked())
                                                        s1 = s1 + "M";
                                                    if (c2.isChecked())
                                                        s1 = s1 + "Tu";
                                                    if (c3.isChecked())
                                                        s1 = s1 + "W";
                                                    if (c4.isChecked())
                                                        s1 = s1 + "Th";
                                                    if (c6.isChecked())
                                                        s1 = s1 + "F";
                                                    if (c5.isChecked())
                                                        s1 = s1 + "Sa";
                                                    if (c7.isChecked())
                                                        s1 = s1 + "Su";
                                                    int hourini = hour1;
                                                    String am_pm = (hourini < 12) ? "am" : "pm";
                                                    if (hourini > 12)
                                                        hourini = hourini - 12;
                                                    if (hourini == 0)
                                                        hourini = 12;
                                                    String s2;
                                                    if (minute1 == 0) {
                                                        s2 = "" + hourini + ":" + minute1 + "0" + am_pm;
                                                    } else if (minute1 > 0 && minute1 <= 9)
                                                        s2 = "" + hourini + ":" + "0" + minute1 + am_pm;
                                                    else
                                                        s2 = "" + hourini + ":" + minute1 + am_pm;
                                                    SQLiteDatabase db = mHelper.getWritableDatabase();
                                                    ContentValues values = new ContentValues();
                                                    long t = (hour1 * 60 * 60 * 1000) + (minute1 * 60 * 1000);
                                                    values.put(TaskContract.TaskEntry.COL_INITIME, t);
                                                    values.put(TaskContract.TaskEntry.COL_TASK_TITLE, s + "ˑ" + s1 + "  " + s2);
                                                    int id=(int)db.insertWithOnConflict(TaskContract.TaskEntry.TABLEN,
                                                            null,
                                                            values,
                                                            SQLiteDatabase.CONFLICT_REPLACE);
                                                    db.close();
                                                    updateUI();
                                                    if (c1.isChecked())
                                                    {
                                                        setNotificationWeek(hour1,minute1,s,id,vib1,sound1,"mon");
                                                    }
                                                    if(c2.isChecked())
                                                    {
                                                        setNotificationWeek(hour1,minute1,s,id,vib1,sound1,"tue");
                                                    }
                                                    if(c3.isChecked())
                                                    {
                                                        setNotificationWeek(hour1,minute1,s,id,vib1,sound1,"wed");
                                                    }
                                                    if(c4.isChecked())
                                                    {
                                                        setNotificationWeek(hour1,minute1,s,id,vib1,sound1,"thurs");
                                                    }
                                                    if(c5.isChecked())
                                                    {
                                                        setNotificationWeek(hour1,minute1,s,id,vib1,sound1,"sat");
                                                    }
                                                    if(c6.isChecked())
                                                    {
                                                        setNotificationWeek(hour1,minute1,s,id,vib1,sound1,"fri");
                                                    }
                                                    if(c7.isChecked())
                                                    {
                                                        setNotificationWeek(hour1,minute1,s,id,vib1,sound1,"sun");
                                                    }
                                                }
                                            }
                                        });
                                        dialog3.show();
                                        final SwitchCompat all=(SwitchCompat)dialog3.findViewById(R.id.switch3);
                                        final CheckBox c1=(CheckBox)dialog3.findViewById(R.id.checkBox);
                                        final CheckBox c2=(CheckBox)dialog3.findViewById(R.id.checkBox2);
                                        final CheckBox c3=(CheckBox)dialog3.findViewById(R.id.checkBox3);
                                        final CheckBox c4=(CheckBox)dialog3.findViewById(R.id.checkBox4);
                                        final CheckBox c5=(CheckBox)dialog3.findViewById(R.id.checkBox5);
                                        final CheckBox c6=(CheckBox)dialog3.findViewById(R.id.checkBox6);
                                        final CheckBox c7=(CheckBox)dialog3.findViewById(R.id.checkBox7);
                                        if (all != null&&c1!=null&&c2!=null&&c3!=null&&c4!=null&&c5!=null&&c6!=null&&c7!=null) {
                                            all.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    if(all.isChecked())
                                                    {
                                                        c1.setChecked(true);
                                                        c2.setChecked(true);
                                                        c3.setChecked(true);
                                                        c4.setChecked(true);
                                                        c5.setChecked(true);
                                                        c6.setChecked(true);
                                                        c7.setChecked(true);
                                                    }
                                                    else
                                                    {
                                                        c1.setChecked(false);
                                                        c2.setChecked(false);
                                                        c3.setChecked(false);
                                                        c4.setChecked(false);
                                                        c5.setChecked(false);
                                                        c6.setChecked(false);
                                                        c7.setChecked(false);
                                                    }
                                                }
                                            });
                                                c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                    @Override
                                                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                    if(!c1.isChecked()) {
                                                        all.setChecked(false);
                                                        count--;
                                                    }
                                                        else
                                                        count++;
                                                        if(count==7)
                                                        {
                                                            all.setChecked(true);
                                                        }
                                                    }
                                                });
                                                c2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                    @Override
                                                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                        if(!c2.isChecked()) {
                                                            all.setChecked(false);
                                                            count--;
                                                        }
                                                        else
                                                            count++;
                                                        if(count==7)
                                                        {
                                                            all.setChecked(true);
                                                        }
                                                    }
                                                });
                                            c3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                    if(!c3.isChecked()) {
                                                        all.setChecked(false);
                                                        count--;
                                                    }
                                                    else
                                                        count++;
                                                    if(count==7)
                                                    {
                                                        all.setChecked(true);
                                                    }
                                                }
                                            });
                                            c4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                    if(!c4.isChecked())
                                                    {
                                                        all.setChecked(false);
                                                        count--;
                                                    }
                                                    else
                                                        count++;
                                                    if(count==7)
                                                    {
                                                        all.setChecked(true);
                                                    }
                                                }
                                            });
                                            c5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                    if(!c5.isChecked())
                                                    {
                                                        all.setChecked(false);
                                                        count--;
                                                    }
                                                    else
                                                        count++;
                                                    if(count==7)
                                                    {
                                                        all.setChecked(true);
                                                    }
                                                }
                                            });
                                            c6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                    if(!c6.isChecked())
                                                    {
                                                        all.setChecked(false);
                                                        count--;
                                                    }
                                                    else
                                                        count++;
                                                    if(count==7)
                                                    {
                                                        all.setChecked(true);
                                                    }
                                                }
                                            });
                                            c7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                @Override
                                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                                    if(!c7.isChecked())
                                                    {
                                                        all.setChecked(false);
                                                        count--;
                                                    }
                                                    else
                                                        count++;
                                                    if(count==7)
                                                    {
                                                        all.setChecked(true);
                                                    }
                                                }
                                            });
                                        }

                                    }
                                })
                                .setNeutralButton("PERIODIC\nTIME", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        int hour;
                                        int minute;
                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                            hour=picker1.getHour();
                                            minute= picker1.getMinute();
                                        }
                                        else
                                        {
                                            hour=picker1.getCurrentHour();
                                            minute=picker1.getCurrentMinute();
                                        }
                                        final int hour1=hour;
                                        final int minute1=minute;
                                        long t = (hour1 * 60 * 60 * 1000) + (minute1 * 60 * 1000);
                                        String[] si = { "30 secs","1 min", "5 mins", "10 mins", "15 mins", "30 mins",
                                                "1 hour", "2 hours", "5 hours", "10 hours" };
                                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                                Main5Activity.this, android.R.layout.simple_spinner_dropdown_item,
                                                si);
                                        final Spinner s1=new Spinner(Main5Activity.this);
                                        s1.setAdapter(arrayAdapter);
                                        final AlertDialog dialog3 = new AlertDialog.Builder(Main5Activity.this,R.style.f)
                                        .setTitle("SELECT INTERVAL ")
                                        .setView(s1)
                                        .create();
                                        dialog3.setButton(AlertDialog.BUTTON_POSITIVE, "SELECT", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                    final String text = s1.getSelectedItem().toString();
                                                    final AlertDialog dialog4=new AlertDialog.Builder(Main5Activity.this,R.style.f)
                                                            .setTitle("Select ending time and other settings")
                                                            .setView(R.layout.dialog2)
                                                            .create();
                                                    dialog4.setButton(AlertDialog.BUTTON_POSITIVE, "SELECT", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialogInterface, int i) {
                                                            final TimePicker picker2=(TimePicker)dialog4.findViewById(R.id.timePicker);
                                                            final SwitchCompat vib=(SwitchCompat)dialog4.findViewById(R.id.switch8);
                                                            final SwitchCompat sound=(SwitchCompat)dialog4.findViewById(R.id.switch7);
                                                            String text1= text+" ";
                                                            int hourf;
                                                            int minutef;
                                                            if(picker2!=null) {
                                                                boolean vib1 = false;
                                                                boolean sound1 = false;
                                                                if (vib != null && vib.isChecked())
                                                                    vib1 = true;
                                                                if (sound != null && sound.isChecked())
                                                                    sound1 = true;
                                                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                                                    hourf = picker2.getHour();
                                                                    minutef = picker2.getMinute();
                                                                } else {
                                                                    hourf = picker2.getCurrentHour();
                                                                    minutef = picker2.getCurrentMinute();
                                                                }
                                                                long t1 = (hour1 * 60 * 60 * 1000) + (minute1 * 60 * 1000);
                                                                long t2 = (hourf * 60 * 60 * 1000) + (minutef * 60 * 1000);
                                                                int ind = text1.indexOf(" ");
                                                                int ind1 = text1.lastIndexOf(" ");
                                                                int mag = Integer.parseInt(text1.substring(0, ind));
                                                                String type = text1.substring(ind + 1, ind1);
                                                                int add;
                                                                switch (type) {
                                                                    case "hour":
                                                                    case "hours":
                                                                        add = mag * 60 * 60 * 1000;
                                                                        break;
                                                                    case "min":
                                                                    case "mins":
                                                                        add = mag * 60 * 1000;
                                                                        break;
                                                                    default:
                                                                        add = mag * 1000;
                                                                        break;
                                                                }
                                                                long addt = t1 + add;
                                                                if(addt<=t2)
                                                                {

                                                                    int hourini = hour1;
                                                                    String am_pm = (hourini < 12) ? "am" : "pm";
                                                                    if (hourini > 12)
                                                                        hourini = hourini - 12;
                                                                    if (hourini == 0)
                                                                        hourini = 12;
                                                                    String s2;
                                                                    if (minute1 == 0) {
                                                                        s2 = "" + hourini + ":" + minute1 + "0" + am_pm;
                                                                    } else if (minute1 > 0 && minute1 <= 9)
                                                                        s2 = "" + hourini + ":" + "0" + minute1 + am_pm;
                                                                    else
                                                                        s2 = "" + hourini + ":" + minute1 + am_pm;

                                                                    int hourfin = hourf;
                                                                    String am_pmf = (hourfin < 12) ? "am" : "pm";
                                                                    if (hourfin > 12)
                                                                        hourfin = hourfin - 12;
                                                                    if (hourfin == 0)
                                                                        hourfin = 12;
                                                                    String s3;
                                                                    if (minutef == 0) {
                                                                        s3 = "" + hourfin + ":" + minutef + "0" + am_pmf;
                                                                    } else if (minutef > 0 && minutef <= 9)
                                                                        s3 = "" + hourfin + ":" + "0" + minutef + am_pmf;
                                                                    else
                                                                        s3 = "" + hourfin + ":" + minutef + am_pmf;
                                                                    SQLiteDatabase db = mHelper.getWritableDatabase();
                                                                    ContentValues values = new ContentValues();
                                                                    values.put(TaskContract.TaskEntry.COL_INITIME, t1);
                                                                    values.put(TaskContract.TaskEntry.COL_TASK_TITLE, s + "ˑ" +s2+" to "+s3+" with interval "+text);
                                                                    int id=(int)db.insertWithOnConflict(TaskContract.TaskEntry.TABLEN,
                                                                            null,
                                                                            values,
                                                                            SQLiteDatabase.CONFLICT_REPLACE);
                                                                    db.close();
                                                                    updateUI();
                                                                    setNotification2(hour1,minute1,hourf,minutef,s,add,id,vib1,sound1);
                                                                }

                                                            }

                                                        }
                                                    });
                                                dialog4.show();
                                                final TimePicker picker2=(TimePicker)dialog4.findViewById(R.id.timePicker);
                                                if(picker2!=null) {
                                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                                        picker2.setHour(hour1);
                                                        picker2.setMinute(minute1);
                                                    } else {
                                                        picker2.setCurrentHour(hour1);
                                                        picker2.setCurrentMinute(minute1);
                                                    }
                                                }
                                            }
                                        });
                                        dialog3.show();
                                    }
                                })
                                .create();
                                dialog1.show();
                    }
                })
                .create();
                dialog.show();



    }
    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
        startActivity(intent);
        finish();
    }
}

