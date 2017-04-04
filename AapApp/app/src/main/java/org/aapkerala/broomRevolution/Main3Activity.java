package org.aapkerala.broomRevolution;

import android.*;
import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.aapkerala.broomRevolution.R;

import org.aapkerala.broomRevolution.db.TaskContract;
import org.aapkerala.broomRevolution.db.TaskDbHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Main3Activity extends AppCompatActivity {
    private ListView mTaskListView;
    private TaskDbHelper mHelper;
    private ArrayAdapter mAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mTaskListView = (ListView) findViewById(R.id.mtasklistview);
        mHelper = new TaskDbHelper(this);
        updateUI();
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
            TextView text = (TextView) view.findViewById(R.id.task_title);
            text.setText(taskList.get(position));
            return view;
        }
    }
    int il=0;
    String s = "";
    PhoneStateListener callStateListener;
     TelephonyManager telephonyManager;
    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry.NUMBER},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.NUMBER);
            taskList.add(cursor.getString(idx));
        }

        if (mAdapter2 == null) {
            mAdapter2 = new MyAdapter(this, R.layout.item_todo, R.id.task_title, taskList);
            mTaskListView.setAdapter(mAdapter2);
        } else {
            mAdapter2.clear();
            mAdapter2.addAll(taskList);
            mAdapter2.notifyDataSetChanged();
        }
        mTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String no = adapterView.getItemAtPosition(i).toString();
                s = no;
                il=0;
                AlertDialog dialog =new AlertDialog.Builder(Main3Activity.this).setTitle("PLACE PHONE CALL?")
                        .setView(R.layout.dialog)
                        .create();

                dialog.show();
                ImageView dontcall=(ImageView) dialog.findViewById(R.id.call_icon);
                TextView dontcall1=(TextView) dialog.findViewById(R.id.textView31);
                ImageView call=(ImageView) dialog.findViewById(R.id.call_icon2);
                TextView call1=(TextView) dialog.findViewById(R.id.textView36);
                dontcall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getApplicationContext(), Main4Activity.class);
                        i.putExtra("phone", no);
                        startActivity(i);
                    }
                });
                dontcall1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(getApplicationContext(), Main4Activity.class);
                        i.putExtra("phone", no);
                        startActivity(i);
                    }
                });
                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        makeCall(s);
                    }
                });
                call1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        makeCall(s);
                    }
                });
            }
        });
        mTaskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String id = adapterView.getItemAtPosition(i).toString();
                final SQLiteDatabase db=mHelper.getWritableDatabase();
                AlertDialog dialog =new AlertDialog.Builder(Main3Activity.this).setTitle("DELETE")
                        .setMessage("DO YOU WANT TO REMOVE THIS NUMBER")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.delete(TaskContract.TaskEntry.TABLE,
                                        TaskContract.TaskEntry.NUMBER + " = ?",
                                        new String[]{""+id});
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("NO",null)
                        .create();
                dialog.show();
                return true;
            }
        });
        cursor.close();
        db.close();
    }
    void makeCall(final String no) {
        final Intent intent = new Intent(getApplicationContext(), Main4Activity.class);
        intent.putExtra("phone", no);
        int l2=no.length();
        String no1=no.substring(l2-10,l2);
        no1="+91"+no1;
        String number = "tel:" + no1.trim();
        Intent callIntent = new Intent(Intent.ACTION_CALL,Uri.parse(number));
        if (ActivityCompat.checkSelfPermission(Main3Activity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(Main3Activity.this, new String[]{android.Manifest.permission.CALL_PHONE,
                            Manifest.permission.READ_PHONE_STATE},
                    1);
        }
        else {
            startActivity(callIntent);
            finish();
            final TelephonyManager telephonyManager =
                    (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
              callStateListener = new PhoneStateListener() {
                public void onCallStateChanged(int state, String incomingNumber) {
                    if(il<3) {
                        Log.e("aa",""+il);
                        if (state == TelephonyManager.CALL_STATE_RINGING) {
                            il++;
                        }
                        if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
                            Intent i = new Intent(getApplicationContext(), Main4Activity.class);
                                    i.putExtra("phone", no);
                                    startActivity(i);
                                  finish();
                           il++;
                        }

                        if (state == TelephonyManager.CALL_STATE_IDLE) {
                            il++;
                        }
                    }
                }
            };
            telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_PHONE_STATE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                if (perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    makeCall(s);
                } else {
                    // Permission Denied
                    Toast.makeText(Main3Activity.this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
