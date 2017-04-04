package org.aapkerala.broomRevolution;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.aapkerala.broomRevolution.R;

import org.aapkerala.broomRevolution.db.TaskContract;
import org.aapkerala.broomRevolution.db.TaskDbHelper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ground_volunteer extends AppCompatActivity {
    private ListView groundListView;
    private FloatingActionButton download10;
    private FloatingActionButton add,offline;
    private ArrayAdapter mAdapter2;
    private DatabaseReference mFirebaseDatabaseReference;
    private TextView lac2;
    private Spinner sector;
    private TaskDbHelper mHelper;
    private Button button;
    private SharedPreferences pref;
    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name, "array", context.getPackageName());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_volunteer);
        mHelper = new TaskDbHelper(this);
        Bundle extras = getIntent().getExtras();
        String lac1="";
        String lacresource="";
        if (extras != null) {
            lac1=extras.getString("lac");
            lacresource=extras.getString("resource");
        }
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        if (lac1 != null) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("LacG",lac1);
            editor.putString("resource",lacresource);
            editor.apply();
        }
        add=(FloatingActionButton)findViewById(R.id.fab1);
        offline=(FloatingActionButton)findViewById(R.id.fab3);
        download10=(FloatingActionButton)findViewById(R.id.fab2);
        groundListView = (ListView) findViewById(R.id.groundListView);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        lac2 = (TextView) findViewById(R.id.textView35);
        lac2.setText(pref.getString("LacG",""));
        sector = (Spinner) findViewById(R.id.spinner8);
        Log.w("myapp",pref.getString("resource",""));
        String[] cRaces = getResources().getStringArray(getStringIdentifier(ground_volunteer.this, pref.getString("resource","")));
        String[] r=new String[cRaces.length+1];
        System.arraycopy(cRaces,0,r,0,cRaces.length);
        r[cRaces.length]="others";
        final ArrayAdapter<String> sector_adapter= new ArrayAdapter<String>(
                ground_volunteer.this, android.R.layout.simple_spinner_item,
                r);
        sector.setAdapter(sector_adapter);
        offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ground_volunteer.this, offline.class));
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ground_volunteer.this,Main4Activity.class);
                startActivity(intent);
            }
        });
        sector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sel=adapterView.getItemAtPosition(i).toString().toUpperCase();
                if(mAdapter2!=null) {
                    mAdapter2.clear();
                    mAdapter2.notifyDataSetChanged();
                }
                    updateUI(sel);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        download10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=sector.getSelectedItem().toString().toUpperCase();
                mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
                Query query;
                if(s.equals("ALL"))
                    query = mFirebaseDatabaseReference.child("MEMBERS").orderByChild("LAC").equalTo(pref.getString("LacG",""));
                else
                    query = mFirebaseDatabaseReference.child("MEMBERS").orderByChild("SECTOR").equalTo(s);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            int i=1;
                            for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                                if(messageSnapshot.child("STATUS").getValue().equals("AAM AADMI")&&!(""+messageSnapshot.child("DOWNLOAD").getValue()).equals("YES")) {
                                    mFirebaseDatabaseReference.child("MEMBERS").child("" +(messageSnapshot.child("ID").getValue())).child("DOWNLOAD").setValue("YES");
                                    ContentValues values = new ContentValues();
                                    values.put(TaskContract.TaskEntry.NAME, "" + (messageSnapshot.child("NAME").getValue() != null ? messageSnapshot.child("NAME").getValue() : ""));
                                    values.put(TaskContract.TaskEntry.STATUS, "AAM AADMI");
                                    values.put(TaskContract.TaskEntry.DISTRICT, "" + (messageSnapshot.child("DISTRICT").getValue() != null ? messageSnapshot.child("DISTRICT").getValue() : ""));
                                    values.put(TaskContract.TaskEntry.PC, "" + (messageSnapshot.child("PC").getValue() != null ? messageSnapshot.child("PC").getValue() : ""));
                                    values.put(TaskContract.TaskEntry.LAC, "" + (messageSnapshot.child("LAC").getValue() != null ? messageSnapshot.child("LAC").getValue() : ""));
                                    values.put(TaskContract.TaskEntry.SECTOR, "" + (messageSnapshot.child("SECTOR").getValue() != null ? messageSnapshot.child("SECTOR").getValue() : ""));
                                    values.put(TaskContract.TaskEntry.PINCODE, "" + (messageSnapshot.child("PINCODE").getValue() != null ? messageSnapshot.child("PINCODE").getValue() : ""));
                                    values.put(TaskContract.TaskEntry.WARD, "" + (messageSnapshot.child("WARD").getValue() != null ? messageSnapshot.child("WARD").getValue() : ""));
                                    values.put(TaskContract.TaskEntry.BOOTH, "" + (messageSnapshot.child("BOOTH").getValue() != null ? messageSnapshot.child("BOOTH").getValue() : ""));
                                    values.put(TaskContract.TaskEntry.GENDER, "" + (messageSnapshot.child("GENDER").getValue() != null ? messageSnapshot.child("GENDER").getValue() : ""));
                                    values.put(TaskContract.TaskEntry.ID, "" + (messageSnapshot.child("ID").getValue() != null ? messageSnapshot.child("ID").getValue() : ""));
                                    SQLiteDatabase db = mHelper.getWritableDatabase();
                                    db.insertWithOnConflict(TaskContract.TaskEntry.TABLE1, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                                    db.close();
                                    if(i==10)
                                        break;
                                        i++;
                                }
                            }
                            Toast.makeText(ground_volunteer.this, "OFFLINED DATA", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                   updateUI(sector.getSelectedItem().toString().toUpperCase());
            }
        });
        SQLiteDatabase db = mHelper.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(db,TaskContract.TaskEntry.TABLE1);
        if(cnt==0)
        {
            download10.show();
        }
        else
        {
            download10.hide();
        }

    }



    private class MyAdapter extends ArrayAdapter<CharSequence> {

        ArrayList<CharSequence> taskList;
        LayoutInflater mInflater;

        public MyAdapter(Context context, int resource, int textViewResourceId,
                         ArrayList<CharSequence> taskList1) {
            super(context, resource, textViewResourceId, taskList1);
            taskList = taskList1;
            mInflater = (LayoutInflater) ground_volunteer.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = mInflater.inflate(R.layout.item_todo, null, false);
            TextView text = (TextView) view.findViewById(R.id.task_title);
            text.setText(taskList.get(position));
            return view;
        }
    }

    private void updateUI(final String sector) {
        final ProgressDialog pd = new ProgressDialog(ground_volunteer.this);
        pd.setMessage("loading");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        final ArrayList<CharSequence> taskList = new ArrayList<>();
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        Query query;
        if(sector.equals("ALL")||sector.equals("OTHERS"))
        query = mFirebaseDatabaseReference.child("MEMBERS").orderByChild("LAC").equalTo(pref.getString("LacG",""));
        else
            query = mFirebaseDatabaseReference.child("MEMBERS").orderByChild("SECTOR").equalTo(sector);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taskList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                        if (sector.equals("OTHERS")) {
                            if (messageSnapshot.child("SECTOR").getValue() == null || ("" + messageSnapshot.child("SECTOR").getValue()).equals("")) {
                                Log.w("tag",""+messageSnapshot);
                                SpannableString name = new SpannableString("" + (String) messageSnapshot.child("NAME").getValue() + "\n");
                                SpannableString sector = new SpannableString("SECTOR: " + (String) messageSnapshot.child("SECTOR").getValue() + "\n");
                                SpannableString id = new SpannableString("" + messageSnapshot.child("ID").getValue());
                                SpannableString id1 = new SpannableString("/");
                                SpannableString id2 = new SpannableString("/");
                                name.setSpan(new RelativeSizeSpan(0.8f), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                sector.setSpan(new RelativeSizeSpan(0.6f), 0, sector.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                id.setSpan(new RelativeSizeSpan(0.6f), 0, id.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                id.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, id.length(), 0);
                                id1.setSpan(new RelativeSizeSpan(0.0001f), 0, id1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                id1.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, id1.length(), 0);
                                id2.setSpan(new RelativeSizeSpan(0.0001f), 0, id2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                id2.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, id2.length(), 0);
                                taskList.add(TextUtils.concat(name, sector, id1, id, id2));
                            }

                        } else if((""+messageSnapshot.child("STATUS").getValue()).equals("AAM AADMI")){
                            SpannableString name = new SpannableString("" + (String) messageSnapshot.child("NAME").getValue() + "\n");
                            SpannableString sector = new SpannableString("SECTOR: " + (String) messageSnapshot.child("SECTOR").getValue() + "\n");
                            SpannableString id = new SpannableString("" + messageSnapshot.child("ID").getValue());
                            SpannableString id1 = new SpannableString("/");
                            SpannableString id2 = new SpannableString("/");
                            name.setSpan(new RelativeSizeSpan(0.8f), 0, name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            sector.setSpan(new RelativeSizeSpan(0.6f), 0, sector.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            id.setSpan(new RelativeSizeSpan(0.6f), 0, id.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            id.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, id.length(), 0);
                            id1.setSpan(new RelativeSizeSpan(0.0001f), 0, id1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            id1.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, id1.length(), 0);
                            id2.setSpan(new RelativeSizeSpan(0.0001f), 0, id2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            id2.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, id2.length(), 0);
                            taskList.add(TextUtils.concat(name, sector, id1, id, id2));
                        }
                    }
                }
                else {
                    Toast.makeText(ground_volunteer.this, "There is no data available", Toast.LENGTH_SHORT).show();
                }
                if (mAdapter2 == null) {
                    mAdapter2 = new MyAdapter(ground_volunteer.this, R.layout.item_todo, R.id.task_title, taskList);
                    groundListView.setAdapter(mAdapter2);
                    pd.hide();
                } else {
                    mAdapter2.clear();
                    mAdapter2.addAll(taskList);
                    mAdapter2.notifyDataSetChanged();
                    pd.hide();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        groundListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String details = adapterView.getItemAtPosition(i).toString();
                int indx=details.lastIndexOf('/');
                int indx1=details.lastIndexOf('/',indx-1);
                final String id=details.substring(indx1+1,indx);
                Intent intent = new Intent(getApplicationContext(), ground_volunteer_form1.class);
                intent.putExtra("phid",Long.parseLong(id));
                intent.putExtra("offline",false);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.secondry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sec_sign_out_menu:
                pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        long cnt  = DatabaseUtils.queryNumEntries(db,TaskContract.TaskEntry.TABLE1);
        if(cnt==0)
        {
            download10.show();
        }
        else
        {
            download10.hide();
        }


    }

}
