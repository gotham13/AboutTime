package org.aapkerala.broomRevolution;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.aapkerala.broomRevolution.R;

import org.aapkerala.broomRevolution.db.TaskContract;
import org.aapkerala.broomRevolution.db.TaskDbHelper;

import java.util.ArrayList;

public class offline extends AppCompatActivity {
    private ListView mTaskListView;
    private ArrayAdapter mAdapter2;
    private TaskDbHelper mHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline);
        mTaskListView=(ListView)findViewById(R.id.mtasklistview1);
        mHelper = new TaskDbHelper(this);
        updateUI();
        }
    private class MyAdapter extends ArrayAdapter<CharSequence> {

        ArrayList<CharSequence> taskList;
        LayoutInflater mInflater;

        public MyAdapter(Context context, int resource, int textViewResourceId,
                         ArrayList<CharSequence> taskList1) {
            super(context, resource, textViewResourceId, taskList1);
            taskList = taskList1;
            mInflater = (LayoutInflater) offline.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = mInflater.inflate(R.layout.item_todo, null, false);
            TextView text = (TextView) view.findViewById(R.id.task_title);
            text.setText(taskList.get(position));
            return view;
        }
    }
    private void updateUI() {
        ArrayList<CharSequence> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE1,
                new String[]{TaskContract.TaskEntry.NAME,TaskContract.TaskEntry.SECTOR,TaskContract.TaskEntry.BOOTH,TaskContract.TaskEntry.DISTRICT,TaskContract.TaskEntry.PINCODE,TaskContract.TaskEntry.ID,TaskContract.TaskEntry.LAC,TaskContract.TaskEntry.PC,TaskContract.TaskEntry.WARD,TaskContract.TaskEntry.STATUS,TaskContract.TaskEntry.GENDER},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.NAME);
            int idx1=cursor.getColumnIndex(TaskContract.TaskEntry.ID);
            int idx2=cursor.getColumnIndex(TaskContract.TaskEntry.SECTOR);
            SpannableString name = new SpannableString("" + cursor.getString(idx)+"\n");
            SpannableString sector =new SpannableString( "SECTOR: " + cursor.getString(idx2)+"\n");
            SpannableString id = new SpannableString(""+cursor.getString(idx1));
            SpannableString id1 = new SpannableString("/");
            SpannableString id2=new SpannableString("/");
            name.setSpan(new RelativeSizeSpan(0.8f), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            sector.setSpan(new RelativeSizeSpan(0.6f), 0, sector.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            id.setSpan(new RelativeSizeSpan(0.6f), 0, id.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            id.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, id.length(), 0);
            id1.setSpan(new RelativeSizeSpan(0.0001f), 0, id1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            id1.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, id1.length(), 0);
            id2.setSpan(new RelativeSizeSpan(0.0001f), 0, id2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            id2.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, id2.length(), 0);
            taskList.add(TextUtils.concat(name,sector,id1,id,id2));
        }
        if (mAdapter2 == null) {
            mAdapter2 = new MyAdapter(this, R.layout.item_todo, R.id.task_title, taskList);
            mTaskListView.setAdapter(mAdapter2);
        }else {
            mAdapter2.clear();
            mAdapter2.addAll(taskList);
            mAdapter2.notifyDataSetChanged();
        }
        cursor.close();
        db.close();
        mTaskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String details = adapterView.getItemAtPosition(i).toString();
                int indx=details.lastIndexOf('/');
                int indx1=details.lastIndexOf('/',indx-1);
                final String id=details.substring(indx1+1,indx);
                Intent intent = new Intent(getApplicationContext(), ground_volunteer_form1.class);
                intent.putExtra("phid",Long.parseLong(id));
                intent.putExtra("offline",true);
                startActivity(intent);
            }
        });
        mTaskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String details = adapterView.getItemAtPosition(i).toString();
                int indx=details.lastIndexOf('/');
                int indx1=details.lastIndexOf('/',indx-1);
                final String id=details.substring(indx1+1,indx);
                final SQLiteDatabase db=mHelper.getWritableDatabase();
                AlertDialog dialog =new AlertDialog.Builder(offline.this).setTitle("DELETE")
                        .setMessage("DO YOU WANT TO REMOVE THIS FROM OFFLINE DATA")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.delete(TaskContract.TaskEntry.TABLE1,
                                        TaskContract.TaskEntry.ID + " = ?",
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
    }
}
