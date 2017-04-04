package org.aapkerala.broomRevolution;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
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

public class ground_volunteer_form1 extends AppCompatActivity {
    private EditText name,phone,pincode,ward,booth;
    private RadioButton male,female;
    private AutoCompleteTextView dist,pc,la,pan_mu;
    private DatabaseReference mFirebaseDatabaseReference;
    private Button send,delete;
    private TaskDbHelper mHelper;
    boolean a=true;
    private String key;
    Long phid=0L;
    Boolean offline=false;
    private String blockCharacterSet = "/\\@~#^|$%&*!";
    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };
    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name, "array", context.getPackageName());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        mHelper = new TaskDbHelper(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            phid=extras.getLong("phid");
            offline=extras.getBoolean("offline");
        }
        phone=(EditText)findViewById(R.id.phoneNum);
        name=(EditText)findViewById(R.id.editName);
        dist=(AutoCompleteTextView) findViewById(R.id.spindistrict);
        pc=(AutoCompleteTextView) findViewById(R.id.spinpc);
        la=(AutoCompleteTextView) findViewById(R.id.spinla);
        pan_mu=(AutoCompleteTextView) findViewById(R.id.spinpan_mun);
        send=(Button)findViewById(R.id.sendFire);
        delete=(Button) findViewById(R.id.button6);
        male=(RadioButton)findViewById(R.id.radioButton);
        female=(RadioButton)findViewById(R.id.radioButton2);
        pincode=(EditText)findViewById(R.id.pincode) ;
        ward=(EditText) findViewById(R.id.editText);
        booth=(EditText) findViewById(R.id.editText2);
        delete.setVisibility(View.GONE);
        RelativeLayout.LayoutParams paramsBtn=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        paramsBtn.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        send.setLayoutParams(paramsBtn);
        send.setText("NEXT");
        phone.setFilters(new InputFilter[] { filter });
        name.setFilters(new InputFilter[] { filter });
        dist.setFilters(new InputFilter[] { filter });
        pc.setFilters(new InputFilter[] { filter });
        la.setFilters(new InputFilter[] { filter });
        pan_mu.setFilters(new InputFilter[] { filter });
        send.setFilters(new InputFilter[] { filter });
        ward.setFilters(new InputFilter[] { filter });
        booth.setFilters(new InputFilter[] { filter });
        pincode.setFilters(new InputFilter[] { filter });
        final ArrayAdapter pc_adapter = ArrayAdapter.createFromResource(this,R.array.pc_names, android.R.layout.simple_spinner_item);
        pc.setAdapter(pc_adapter);
        pc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String sel=pc.getText().toString();
                la.setText("");
                pan_mu.setText("");
                if(!sel.equals("")) {
                    if (!sel.equals("Wayanad") && !sel.equals("Kozhikode") && !sel.equals("Chalakudy") && !sel.equals("Pathanamthitta"))
                        sel = sel + "1";
                    String[] cRaces = getResources().getStringArray(getStringIdentifier(ground_volunteer_form1.this, sel));
                    String[] r=new String[cRaces.length-1];
                    System.arraycopy(cRaces,1,r,0,cRaces.length-1);
                    final ArrayAdapter<String> lac_adapter= new ArrayAdapter<String>(
                            ground_volunteer_form1.this, android.R.layout.simple_spinner_item,
                            r);
                    la.setAdapter(lac_adapter);
                }}
        });
        pc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                pc.setText("");
                pc.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        pc.showDropDown();
                        return true;
                    }
                });
                return true;
            }
        });
        la.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                la.showDropDown();
                return true;
            }
        });
        pan_mu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                pan_mu.showDropDown();
                return true;
            }
        });
        ArrayAdapter dist_adapter = ArrayAdapter.createFromResource(this,R.array.district, android.R.layout.simple_spinner_item);
        dist.setAdapter(dist_adapter);
        dist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dist.setText("");
                dist.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        dist.showDropDown();
                        return true;
                    }
                });
                return true;
            }
        });
        la.setAdapter(null);
        pan_mu.setAdapter(null);
        la.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pan_mu.setText("");
                String sel=la.getText().toString();
                int ind=sel.indexOf("-");
                int ind1=sel.lastIndexOf(" ");
                if(sel.charAt(ind1+1)=='(')
                    sel=sel.substring(ind+2,ind1);
                else if(ind1!=ind+1)
                {
                    char[] a=sel.toCharArray();
                    a[ind1]='_';
                    sel=new String(a);
                    sel=sel.substring(ind+2);

                }
                else
                {
                    sel=sel.substring(ind+2);
                    if(sel.equals("Chalakudy"))
                    {
                        sel=sel+2;
                    }
                }
                String[] cRaces = getResources().getStringArray(getStringIdentifier(ground_volunteer_form1.this, sel));
                String[] r=new String[cRaces.length-1];
                System.arraycopy(cRaces,1,r,0,cRaces.length-1);
                final ArrayAdapter<String> sector_adapter= new ArrayAdapter<String>(
                       ground_volunteer_form1.this, R.layout.my_custom_dropdown,R.id.item,
                        r);
                pan_mu.setAdapter(sector_adapter);

            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name2=name.getText().toString().trim();
                String dist2=dist.getText().toString();
                String pc2=pc.getText().toString();
                String la2=la.getText().toString();
                int ind=la2.indexOf("-");
                int ind1=la2.lastIndexOf(" ");
                if(ind!=-1) {
                    if (la2.charAt(ind1 + 1) == '(')
                        la2 = la2.substring(ind + 2, ind1);
                    else {
                        la2 = la2.substring(ind + 2);
                    }
                }
                String pan_mu2=pan_mu.getText().toString();
                String phonenum=phone.getText().toString();
                if(phonenum.matches("[0-9]+")) {
                    String pincode1 = pincode.getText().toString();
                    String ward1 = ward.getText().toString();
                    String booth1 = booth.getText().toString();
                    DatabaseReference mypostref = mFirebaseDatabaseReference.child("MEMBERS");
                    mypostref.child(phonenum).child("NAME").setValue(name2.toUpperCase());
                    mypostref.child(phonenum).child("DISTRICT").setValue(dist2.toUpperCase());
                    mypostref.child(phonenum).child("PC").setValue(pc2.toUpperCase());
                    mypostref.child(phonenum).child("LAC").setValue(la2.toUpperCase());
                    mypostref.child(phonenum).child("SECTOR").setValue(pan_mu2.toUpperCase());
                    mypostref.child(phonenum).child("ID").setValue(Long.parseLong(phonenum));
                    mypostref.child(phonenum).child("PINCODE").setValue(pincode1);
                    mypostref.child(phonenum).child("WARD").setValue(ward1.toUpperCase());
                    mypostref.child(phonenum).child("BOOTH").setValue(booth1.toUpperCase());
                    if (male.isChecked())
                        mypostref.child(phonenum).child("GENDER").setValue("MALE");
                    if (female.isChecked())
                        mypostref.child(phonenum).child("GENDER").setValue("FEMALE");
                    Intent intent = new Intent(ground_volunteer_form1.this, ground_volunteer_form2.class);
                    intent.putExtra("phid", phid);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(ground_volunteer_form1.this, "PHONE NO INCORRECT", Toast.LENGTH_SHORT).show();
                }
            }
        });
        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    female.setChecked(false);
            }
        });
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    male.setChecked(false);
            }
        });
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        if (phid != 0L) {
            if (!offline) {
                final ProgressDialog pd = new ProgressDialog(ground_volunteer_form1.this);
                pd.setMessage("loading");
                pd.setCanceledOnTouchOutside(false);
                pd.show();
                Query query = mFirebaseDatabaseReference.child("MEMBERS").orderByChild("ID").equalTo(phid);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        key = dataSnapshot.getKey();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                                name.setText("" + (messageSnapshot.child("NAME").getValue() != null ? messageSnapshot.child("NAME").getValue() : ""));
                                dist.setText("" + (messageSnapshot.child("DISTRICT").getValue() != null ? messageSnapshot.child("DISTRICT").getValue() : ""));
                                pc.setText("" + (messageSnapshot.child("PC").getValue() != null ? messageSnapshot.child("PC").getValue() : ""));
                                la.setText("" + (messageSnapshot.child("LAC").getValue() != null ? messageSnapshot.child("LAC").getValue() : ""));
                                pan_mu.setText("" + (messageSnapshot.child("SECTOR").getValue() != null ? messageSnapshot.child("SECTOR").getValue() : ""));
                                if (messageSnapshot.child("PINCODE").getValue() != null)
                                    pincode.setText("" + messageSnapshot.child("PINCODE").getValue());
                                ward.setText("" + (messageSnapshot.child("WARD").getValue() != null ? messageSnapshot.child("WARD").getValue() : ""));
                                booth.setText("" + (messageSnapshot.child("BOOTH").getValue() != null ? messageSnapshot.child("BOOTH").getValue() : ""));
                                if (("" + messageSnapshot.child("GENDER").getValue()).equals("MALE"))
                                    male.setChecked(true);
                                else if (("" + messageSnapshot.child("GENDER").getValue()).equals("FEMALE"))
                                    female.setChecked(true);
                                phone.setText("" + (messageSnapshot.child("ID").getValue() != null ? messageSnapshot.child("ID").getValue() : ""));
                            }
                        }
                        if(!phone.getText().toString().equals(""))
                            pd.hide();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                            }
            else
            {
                SQLiteDatabase db = mHelper.getReadableDatabase();
                Cursor cursor = db.query(TaskContract.TaskEntry.TABLE1,
                    new String[]{TaskContract.TaskEntry.NAME,TaskContract.TaskEntry.SECTOR,TaskContract.TaskEntry.BOOTH,TaskContract.TaskEntry.DISTRICT,TaskContract.TaskEntry.PINCODE,TaskContract.TaskEntry.ID,TaskContract.TaskEntry.LAC,TaskContract.TaskEntry.PC,TaskContract.TaskEntry.WARD,TaskContract.TaskEntry.STATUS,TaskContract.TaskEntry.GENDER},
                        TaskContract.TaskEntry.ID+ "=?", new String[] { String.valueOf(phid) }, null, null, null, null);
                while (cursor.moveToNext()) {
                    name.setText(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.NAME)));
                    dist.setText(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.DISTRICT)));
                    pc.setText(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.PC)));
                    la.setText(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.LAC)));
                    pan_mu.setText(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.SECTOR)));
                    if (!cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.PINCODE)).equals(""))
                        pincode.setText(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.PINCODE)));
                    ward.setText(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.WARD)));
                    booth.setText(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.BOOTH)));
                    if (cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.GENDER)).equals("MALE"))
                        male.setChecked(true);
                    else if (cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.GENDER)).equals("FEMALE"))
                        female.setChecked(true);
                    phone.setText(cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.ID)));

                }
                cursor.close();

            }
        }

    }
}
