package com.abhinavgautam.abouttime;
import com.abhinavgautam.abouttime.R;
import com.abhinavgautam.abouttime.db.TaskContract;
import com.abhinavgautam.abouttime.db.TaskDbHelper;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

public class WednesdayFragment extends Fragment {
    private TaskDbHelper mHelperWed;
    private ArrayAdapter mAdapter2;
    private ListView mTaskListView3;
    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_global, container, false);
        TextView t=(TextView)rootView.findViewById(R.id.tvf);
        t.setText("WEDNESDAY");
        if(Build.VERSION.SDK_INT>=23)
            t.setTextColor(getResources().getColor(R.color.brown,null));
        else
            t.setTextColor(getResources().getColor(R.color.brown));
        mHelperWed = new TaskDbHelper(getActivity());
        mTaskListView3 = (ListView) rootView.findViewById(R.id.listView1);
        return rootView;
    }
    private class BackgroundTask extends AsyncTask <Void, Void, Void> {
        SQLiteDatabase db ;
        ArrayList<CharSequence> taskList ;
        ProgressDialog dialog = new ProgressDialog(getActivity());
        @Override
        protected void onPreExecute() {
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("Loading. Please wait...");
            dialog.setIndeterminate(true);
            dialog.show();
            db= mHelperWed.getReadableDatabase();
            taskList= new ArrayList<>();
        }
        @Override
        protected void onPostExecute(Void result) {
            if (mAdapter2 == null) {
                mAdapter2 = new MyAdapter(getContext(), R.layout.item_todo, R.id.task_title, taskList);
                mTaskListView3.setAdapter(mAdapter2);
            } else {
                mAdapter2.clear();
                mAdapter2.addAll(taskList);
                mAdapter2.notifyDataSetChanged();
            }
            dialog.cancel();
            TextView empty=(TextView) rootView.findViewById(R.id.empty1);
            mTaskListView3.setEmptyView(empty);
        }

        @Override
        protected Void doInBackground(Void... params) {
            Cursor cursor = db.query(TaskContract.TaskEntry.TABLE3,
                    new String[]{TaskContract.TaskEntry._ID,TaskContract.TaskEntry.COL_NOTI, TaskContract.TaskEntry.COL_INITIME, TaskContract.TaskEntry.COL_ROUTINE},
                    null, null, null, null, TaskContract.TaskEntry.COL_INITIME+ " ASC");
            while (cursor.moveToNext()) {
                int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_ROUTINE);
                int idx1=cursor.getColumnIndex(TaskContract.TaskEntry._ID);
                int idx2=cursor.getColumnIndex(TaskContract.TaskEntry.COL_NOTI);
                String s=(cursor.getString(idx)+"/"+cursor.getString(idx1)+"/"+"<"+cursor.getInt(idx2)+">");
                s = s + "\n";
                int ind = s.lastIndexOf("/");
                int ind1 = s.lastIndexOf("/", ind - 1);
                int indn1=s.lastIndexOf("<");
                int indn2=s.lastIndexOf(">");
                String noti=s.substring(indn1+1,indn2);
                String a = s.substring(ind1 + 1, ind);
                int indx1 = s.indexOf('\n');
                int indx3 = s.lastIndexOf('\n');
                int indx2 = s.lastIndexOf('\n', indx3 - 1);
                String initime = s.substring(0, indx1);
                String endtime = s.substring(indx2 + 1, ind1);
                String content = s.substring(indx1 + 1, indx2);
                SpannableString initime1 = new SpannableString(initime + "\n");
                SpannableString endtime1 = new SpannableString("\n" + endtime);
                SpannableString content1 = new SpannableString(content);
                initime1.setSpan(new RelativeSizeSpan(0.7f), 0, initime1.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                endtime1.setSpan(new RelativeSizeSpan(0.7f), 0, endtime1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                if (Build.VERSION.SDK_INT >= 23) {
                    initime1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown, null)), 0, initime1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    endtime1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown, null)), 0, endtime1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                } else
                {
                    initime1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown)), 0, initime1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    endtime1.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.brown)), 0, endtime1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                SpannableString a1=new SpannableString("/"+a+"/");
                SpannableString n1=new SpannableString("<"+noti+">");
                a1.setSpan(new RelativeSizeSpan(0.0001f), 0,a1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                a1.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, a1.length(), 0);
                n1.setSpan(new RelativeSizeSpan(0.0001f), 0,n1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                n1.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, n1.length(), 0);
                taskList.add(TextUtils.concat(initime1,content1,endtime1,a1,n1));
            }
            cursor.close();
            db.close();
            return null;
        }
    }
    private class MyAdapter extends ArrayAdapter<CharSequence> {
        ArrayList<CharSequence> taskList;
        LayoutInflater mInflater;

        public MyAdapter(Context context, int resource, int textViewResourceId,
                         ArrayList<CharSequence> taskList1) {
            super(context, resource, textViewResourceId, taskList1);
            taskList = taskList1;
            mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        int[] colorIntArray = {R.color.cyan,R.color.green,R.color.orange,R.color.brown,R.color.blue,R.color.grey,R.color.darkorange};
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = mInflater.inflate(R.layout.item_todo, null, false);
            FloatingActionButton i1=(FloatingActionButton) view.findViewById(R.id.task_delete);
            i1.setBackgroundTintList(ContextCompat.getColorStateList(getActivity().getApplicationContext(),colorIntArray[3]));
            i1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View parent = (View) view.getParent();
                    TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
                    String task = String.valueOf(taskTextView.getText());
                    int ind=task.lastIndexOf("/");
                    int ind1=task.lastIndexOf("/",ind-1);
                    String a=task.substring(ind1+1,ind);
                    SQLiteDatabase db = mHelperWed.getWritableDatabase();
                    db.delete(TaskContract.TaskEntry.TABLE3,
                            TaskContract.TaskEntry._ID + " = ?",
                            new String[]{a});
                    deleteNotification(Integer.parseInt(a));
                    db.close();
                    updateUI();
                }
            });
            int[] iconIntArray = {R.drawable.noti,R.drawable.nonoti};
            final Main4Activity main4Activity = (Main4Activity)getActivity();
            final ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(main4Activity,R.style.wednesday);
            FloatingActionButton noti=(FloatingActionButton) view.findViewById(R.id.noti);
            FloatingActionButton taski=(FloatingActionButton) view.findViewById(R.id.taski);
            noti.setBackgroundTintList(ContextCompat.getColorStateList(getActivity().getApplicationContext(),colorIntArray[3]));
            taski.setBackgroundTintList(ContextCompat.getColorStateList(getActivity().getApplicationContext(),colorIntArray[3]));
            taski.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View parent = (View) view.getParent();
                    TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
                    String task = String.valueOf(taskTextView.getText());
                    int ind=task.lastIndexOf("/");
                    int ind1=task.lastIndexOf("/",ind-1);
                    int aint=Integer.parseInt(task.substring(ind1+1,ind));
                    SQLiteDatabase db=mHelperWed.getReadableDatabase();
                    Cursor cursor1 = db.query(TaskContract.TaskEntry.TABLE3,
                            new String[]{TaskContract.TaskEntry._ID,TaskContract.TaskEntry.COL_NOTI, TaskContract.TaskEntry.COL_INITIME, TaskContract.TaskEntry.COL_ROUTINE},
                            null, null, null, null, null);
                    while (cursor1.moveToNext()) {
                        int cidx = cursor1.getColumnIndex(TaskContract.TaskEntry._ID);
                        if (cursor1.getInt(cidx) == aint) {
                            int cidx1 = cursor1.getColumnIndex(TaskContract.TaskEntry.COL_ROUTINE);
                            String s = cursor1.getString(cidx1);
                            int indstart = s.indexOf("\n");
                            int indend=s.lastIndexOf("\n");
                            s=s.substring(indstart+1,indend);
                            beginTime(contextThemeWrapper,main4Activity,s,true,aint);
                            break;
                        }
                    }
                    cursor1.close();
                    db.close();
                }
            });
            TextView text = (TextView) view.findViewById(R.id.task_title);
            text.setText(taskList.get(position));
            String s = String.valueOf(text.getText());
            int indn=s.lastIndexOf(">");
            int indn1=s.lastIndexOf("<");
            String notis=s.substring(indn1+1,indn);
            if(notis.equals("1"))
                noti.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), iconIntArray[0]));
            else
                noti.setImageDrawable(ContextCompat.getDrawable(getActivity().getApplicationContext(), iconIntArray[1]));
            return view;

        }
    }
    private void updateUI() {

        BackgroundTask task=new BackgroundTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void setNotification(int hour,int minute,String task,int id)
    {
        Calendar cal = Calendar.getInstance();
        Calendar dt=Calendar.getInstance();
        dt.clear();
        dt.set(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE),hour,minute);
        while(dt.get(Calendar.DAY_OF_WEEK)!=Calendar.WEDNESDAY)
        {
            dt.add(Calendar.DATE,1);
        }
        Intent myIntent1 = new Intent(getContext(), UtilityReceiverWed.class);
        myIntent1.setAction("MyBroadcast");
        myIntent1.putExtra("ID",id);
        myIntent1.putExtra("task",task);
        myIntent1.putExtra("date",dt.get(Calendar.DATE));
        myIntent1.putExtra("year",dt.get(Calendar.YEAR));
        myIntent1.putExtra("month",dt.get(Calendar.MONTH));
        myIntent1.putExtra("hour",hour);
        myIntent1.putExtra("minute",minute);
        SQLiteDatabase db = mHelperWed.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COL_NOTI,1);
        db.update(TaskContract.TaskEntry.TABLE3, values, "_id="+id, null);
        db.close();
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getContext(), id, myIntent1,
                PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager1 = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
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
    public static class UtilityReceiverWed  extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            Intent notificationIntent = new Intent(context, Main4Activity.class);
            PendingIntent pendingIntent2 = PendingIntent.getActivity(context,0,notificationIntent,
                    0);
            Uri alarmSound = Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.noti);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.routi)
                    .setContentTitle("Routine reminder")
                    .setPriority(1)
                    .setContentText("The time for "+extras.getString("task")+" started")
                    .setSound(alarmSound)
                    .setVibrate(new long[]{0,500})
                    .setContentIntent(pendingIntent2)
                    .setAutoCancel(true);
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(extras.getInt("ID"), mBuilder.build());
            Calendar dt=Calendar.getInstance();
            dt.clear();
            dt.set(extras.getInt("year"),extras.getInt("month"),extras.getInt("date"),extras.getInt("hour"),extras.getInt("minute"));
            dt.add(Calendar.DATE,7);
            Intent myIntent1 = new Intent(context, UtilityReceiverWed.class);
            myIntent1.setAction("MyBroadcast");
            myIntent1.putExtra("ID",extras.getInt("ID"));
            myIntent1.putExtra("task",extras.getString("task"));
            myIntent1.putExtra("date",dt.get(Calendar.DATE));
            myIntent1.putExtra("year",dt.get(Calendar.YEAR));
            myIntent1.putExtra("month",dt.get(Calendar.MONTH));
            myIntent1.putExtra("hour",extras.getInt("hour"));
            myIntent1.putExtra("minute",extras.getInt("minute"));
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
    public void deleteNotification(int id)
    {
        Intent myIntent1 = new Intent(getContext(), UtilityReceiverWed.class);
        myIntent1.setAction("MyBroadcast");
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getContext(), id, myIntent1,
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent1);
    }
    public void bulkDelete()
    {
        SQLiteDatabase db = mHelperWed.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE3,
                new String[]{TaskContract.TaskEntry._ID,TaskContract.TaskEntry.COL_NOTI, TaskContract.TaskEntry.COL_INITIME, TaskContract.TaskEntry.COL_ROUTINE},
                null, null, null, null, TaskContract.TaskEntry.COL_INITIME+ " ASC");
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
        db.delete(TaskContract.TaskEntry.TABLE3, null, null);
        db.close();
        updateUI();

    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            onResume();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (!getUserVisibleHint()) {
            return;
        }
        updateUI();
        final Main4Activity main4Activity = (Main4Activity)getActivity();
        final ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(main4Activity,R.style.wednesday);
        main4Activity.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bulkDelete();
            }
        });
        main4Activity.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main4Activity.button2.hide();
                main4Activity.button1.hide();
                main4Activity.button3.hide();
                beginTime(contextThemeWrapper,main4Activity,"",false,0);
            }
        });
    }
    public void beginTime(final ContextThemeWrapper contextThemeWrapper, final Main4Activity main4Activity,final String t,final boolean edit,final int id)
    {
        final TimePicker picker1 = new TimePicker(contextThemeWrapper);
        if (Build.VERSION.SDK_INT >=23)
        {
            picker1.setHour(0);
            picker1.setMinute(0);
        }
        else
        {
            picker1.setCurrentHour(0);
            picker1.setCurrentMinute(0);
        }
        AlertDialog dialog1= new AlertDialog.Builder(contextThemeWrapper,R.style.f)
                .setTitle("Select beginning time")
                .setView(picker1)

                .setNeutralButton("SELECT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int hour;
                        int minute;
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
                        textDialog(hour1,minute1,contextThemeWrapper,main4Activity,t,edit,id);
                    }
                })
                .create();
        dialog1.show();
    }
    public void textDialog(final int hour1,final int minute1,final ContextThemeWrapper contextThemeWrapper,final Main4Activity main4Activity,final String t,final boolean edit,final int id)
    {
        final EditText taskEditText = new EditText(contextThemeWrapper);
        taskEditText.setText(t);
        final AlertDialog dialog2 = new AlertDialog.Builder(contextThemeWrapper,R.style.f)
                .setTitle("Add what you will like to do")
                .setView(taskEditText)
                .setPositiveButton("add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String task = String.valueOf(taskEditText.getText()).toUpperCase();
                        InputMethodManager mgr = (InputMethodManager) main4Activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                        mgr.hideSoftInputFromWindow(taskEditText.getWindowToken(), 0);
                        finalTimeDialog(hour1,minute1,contextThemeWrapper,main4Activity,task,edit,id);
                    }
                }).create();
        dialog2.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if(i==KeyEvent.KEYCODE_BACK)
                {
                    dialog2.cancel();
                    beginTime(contextThemeWrapper,main4Activity,t,edit,id);
                }
                return true;
            }
        });
        dialog2.show();
    }
    public void finalTimeDialog(final int hour1,final int minute1,final ContextThemeWrapper contextThemeWrapper,final Main4Activity main4Activity,final String task,final boolean edit,final int idimpo)
    {
        final TimePicker picker2=new TimePicker(contextThemeWrapper);
        if (Build.VERSION.SDK_INT >=23)
        {
            picker2.setHour(hour1);
            picker2.setMinute(minute1);
        }
        else
        {
            picker2.setCurrentHour(hour1);
            picker2.setCurrentMinute(minute1);
        }
        final AlertDialog dialog3= new AlertDialog.Builder(contextThemeWrapper,R.style.f)
                .setTitle("Select ending time")
                .setView(picker2)
                .setNeutralButton("SELECT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int hour2;
                        int minute2;
                        if (Build.VERSION.SDK_INT >=23)
                        {
                            hour2 = picker2.getHour();
                            minute2 = picker2.getMinute();
                        }
                        else
                        {
                            hour2 = picker2.getCurrentHour();
                            minute2 = picker2.getCurrentMinute();
                        }
                        if(hour2>hour1||(hour2==hour1)&&(minute2>minute1))
                        {
                            final long initime=(hour1*60*60*1000)+(minute1*60*1000);
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
                            final String s5=s2;
                            String am_pm2 = (hour2 < 12) ? "am" : "pm";
                            if (hour2 >12)
                                hour2 = hour2 - 12;
                            if(hour2==0)
                                hour2=12;
                            String s3;
                            if (minute2 == 0) {
                                s3 = "" + hour2 + ":" + minute2 + "0" + am_pm2;
                            } else if (minute2> 0 && minute2 <= 9)
                                s3 = "" + hour2 + ":" + "0" + minute2 + am_pm;
                            else
                                s3 = "" + hour2 + ":" + minute2 + am_pm;
                            final String s4=s3;
                            final AlertDialog dialog4= new AlertDialog.Builder(contextThemeWrapper,R.style.f)
                                    .setTitle("Do you want to get a reminder about this task every wednesday?")
                                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            SQLiteDatabase db = mHelperWed.getWritableDatabase();
                                            ContentValues values = new ContentValues();
                                            values.put(TaskContract.TaskEntry.COL_INITIME,initime);
                                            values.put(TaskContract.TaskEntry.COL_ROUTINE, s5+"\n"+task+"\n"+s4);
                                            if(edit)
                                            {
                                                db.update(TaskContract.TaskEntry.TABLE3, values, "_id="+idimpo, null);
                                                db.close();
                                                deleteNotification(idimpo);
                                                setNotification(hour1,minute1,task,idimpo);
                                            }
                                            else {
                                                final int id = (int) db.insertWithOnConflict(TaskContract.TaskEntry.TABLE3,
                                                        null,
                                                        values,
                                                        SQLiteDatabase.CONFLICT_REPLACE);
                                                db.close();
                                                setNotification(hour1, minute1, task, id);
                                            }
                                            updateUI();
                                        }
                                    })
                                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            SQLiteDatabase db = mHelperWed.getWritableDatabase();
                                            ContentValues values = new ContentValues();
                                            values.put(TaskContract.TaskEntry.COL_INITIME,initime);
                                            values.put(TaskContract.TaskEntry.COL_ROUTINE, s5+"\n"+task+"\n"+s4);
                                            values.put(TaskContract.TaskEntry.COL_NOTI,0);
                                            if(edit)
                                            {
                                                db.update(TaskContract.TaskEntry.TABLE3, values, "_id="+idimpo, null);
                                                db.close();
                                                deleteNotification(idimpo);
                                            }
                                            else {
                                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE3,
                                                        null,
                                                        values,
                                                        SQLiteDatabase.CONFLICT_REPLACE);
                                                db.close();
                                            }
                                            updateUI();
                                        }
                                    })
                                    .create();
                            dialog4.setOnKeyListener(new DialogInterface.OnKeyListener() {
                                @Override
                                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                                    if(i==KeyEvent.KEYCODE_BACK)
                                    {
                                        dialog4.cancel();
                                        finalTimeDialog(hour1,minute1,contextThemeWrapper,main4Activity,task,edit,idimpo);
                                    }
                                    return true;
                                }
                            });
                            dialog4.show();
                        }
                    }
                }).create();
        dialog3.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if(i==KeyEvent.KEYCODE_BACK)
                {
                    dialog3.cancel();
                    textDialog(hour1,minute1,contextThemeWrapper,main4Activity,task,edit,idimpo);
                }
                return true;
            }
        });
        dialog3.show();
    }
}