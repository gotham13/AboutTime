package org.aapkerala.broomRevolution;

import android.*;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;

import org.aapkerala.broomRevolution.R;

import org.aapkerala.broomRevolution.db.TaskDbHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class details extends AppCompatActivity {
    private EditText name, phone, pincode, ward, booth;
    private RadioButton male, female;
    private AutoCompleteTextView dist, pc, la, pan_mu;
    private DatabaseReference mFirebaseDatabaseReference;
    private Button send, delete,call;
    private TaskDbHelper mHelper;
    boolean a = true;
    private String key;
    Long phid = 0L;
    String editable="NO";
    String s="";
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
            phid = extras.getLong("phid");
        }
        phone = (EditText) findViewById(R.id.phoneNum);
        name = (EditText) findViewById(R.id.editName);
        dist = (AutoCompleteTextView) findViewById(R.id.spindistrict);
        pc = (AutoCompleteTextView) findViewById(R.id.spinpc);
        la = (AutoCompleteTextView) findViewById(R.id.spinla);
        pan_mu = (AutoCompleteTextView) findViewById(R.id.spinpan_mun);
        send = (Button) findViewById(R.id.sendFire);
        call=(Button) findViewById(R.id.button8);
        delete = (Button) findViewById(R.id.button6);
        male = (RadioButton) findViewById(R.id.radioButton);
        female = (RadioButton) findViewById(R.id.radioButton2);
        pincode = (EditText) findViewById(R.id.pincode);
        ward = (EditText) findViewById(R.id.editText);
        booth = (EditText) findViewById(R.id.editText2);
        phone.setEnabled(false);
        name.setEnabled(false);
        dist.setEnabled(false);
        pc.setEnabled(false);
        la.setEnabled(false);
        pan_mu.setEnabled(false);
        male.setEnabled(false);
        female.setEnabled(false);
        pincode.setEnabled(false);
        ward.setEnabled(false);
        booth.setEnabled(false);
        delete.setText("EDIT");
        send.setText("NEXT");
        call.setVisibility(View.VISIBLE);
        phone.setFilters(new InputFilter[]{filter});
        name.setFilters(new InputFilter[]{filter});
        dist.setFilters(new InputFilter[]{filter});
        pc.setFilters(new InputFilter[]{filter});
        la.setFilters(new InputFilter[]{filter});
        pan_mu.setFilters(new InputFilter[]{filter});
        ward.setFilters(new InputFilter[]{filter});
        booth.setFilters(new InputFilter[]{filter});
        pincode.setFilters(new InputFilter[]{filter});
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone.setEnabled(true);
                name.setEnabled(true);
                dist.setEnabled(true);
                pc.setEnabled(true);
                la.setEnabled(true);
                pan_mu.setEnabled(true);
                male.setEnabled(true);
                female.setEnabled(true);
                pincode.setEnabled(true);
                ward.setEnabled(true);
                booth.setEnabled(true);
                editable="YES";
            }
        });
       call.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               s=phone.getText().toString();
               makeCall(s);
           }
       });
        ArrayAdapter dist_adapter = ArrayAdapter.createFromResource(this,R.array.district, android.R.layout.simple_spinner_item);
        dist.setAdapter(dist_adapter);
        dist.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dist.showDropDown();
                return true;
            }
        });
        pc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                pc.showDropDown();
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
        la.setAdapter(null);
        pan_mu.setAdapter(null);
        dist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pc.setText("");
                String sel = dist.getText().toString();
                la.setText("");
                pan_mu.setText("");
                if (!sel.equals("")) {
                    ArrayAdapter lac_adapter = ArrayAdapter.createFromResource(details.this,getStringIdentifier(details.this, sel+"_D"), android.R.layout.simple_spinner_item);
                    la.setAdapter(lac_adapter);
                }
            }
        });
        la.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pan_mu.setText("");
                pc.setText("");
                String sel = la.getText().toString();
                int ind = sel.indexOf("-");
                String s = sel.substring(0, ind);
                int num = Integer.parseInt(s);
                if (num <= 7) {
                    pc.setText("Kasaragod");
                } else if (num <= 16 && num != 13 && num != 14) {
                    pc.setText("Kannur");
                } else if ((num >= 20 && num <= 24) || num == 13 || num == 14) {
                    pc.setText("Vadakara");
                } else if ((num >= 17 && num <= 19) || (num >= 34 && num <= 36) || num == 32) {
                    pc.setText("Wayanad");
                } else if (num >= 25 && num <= 31) {
                    pc.setText("Kozhikode");
                } else if ((num >= 37 && num <= 42) || num == 33) {
                    pc.setText("Malappuram");
                } else if (num >= 43 && num <= 49) {
                    pc.setText("Ponnani");
                } else if (num >= 50 && num <= 56) {
                    pc.setText("Palakkad");
                } else if (num>=57&&num<=62||num==65)
                {
                    pc.setText("Alathur");
                }
                else if((num>=63&&num<=64)||(num>=66&&num<=68)||(num>=70&&num<=71))
                {
                    pc.setText("Thrissur");
                }
                else if((num>=72&&num<=76)||num==69||num==84)
                {
                    pc.setText("Chalakudy");
                }
                else if((num>=77&&num<=83))
                {
                    pc.setText("Ernakulam");
                }
                else if((num>=86&&num<=92))
                {
                    pc.setText("Idukki");
                }
                else if((num>=93&&num<=98)||num==85)
                {
                    pc.setText("Kottayam");
                }
                else if((num>=102&&num<=108)||num==116)
                {
                    pc.setText("Alappuzha");
                }
                else if((num>=109&&num<=110)||(num>=118&&num<=119)||num==99||num==106||num==120)
                {
                    pc.setText("Mavelikkara");
                }
                else if((num>=111&&num<=115)||(num>=100&&num<=101))
                {
                    pc.setText("Pathanamthitta");
                }
                else if((num>=121&&num<=126)||num==117)
                {
                    pc.setText("Kollam");
                }
                else if((num>=127&&num<=131)||num==136||num==138)
                {
                    pc.setText("Attingal");
                }
                else if((num>=132&&num<=135)||num==137||num==139||num==140)
                {
                    pc.setText("Thiruvananthapuram");
                }
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
                String[] cRaces = getResources().getStringArray(getStringIdentifier(details.this, sel));
                String[] r=new String[cRaces.length-1];
                System.arraycopy(cRaces,1,r,0,cRaces.length-1);
                final ArrayAdapter<String> sector_adapter= new ArrayAdapter<String>(
                       details.this, R.layout.my_custom_dropdown,R.id.item,
                        r);
                pan_mu.setAdapter(sector_adapter);

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name2 = name.getText().toString().trim();
                String dist2 = dist.getText().toString();
                String pc2 = pc.getText().toString();
                String la2 = la.getText().toString();
                int ind = la2.indexOf("-");
                int ind1 = la2.lastIndexOf(" ");
                if (ind != -1) {
                    if (la2.charAt(ind1 + 1) == '(')
                        la2 = la2.substring(ind + 2, ind1);
                    else {
                        la2 = la2.substring(ind + 2);
                    }
                }
                String pan_mu2 = pan_mu.getText().toString();
                String phonenum = phone.getText().toString();
                String pincode1 = pincode.getText().toString();
                String ward1 = ward.getText().toString();
                String booth1 = booth.getText().toString();
                if(phonenum.matches("[0-9]+")) {
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
                    mypostref.child(phonenum).child("STATUS").setValue("AAM AADMI");
                    if (male.isChecked())
                        mypostref.child(phonenum).child("GENDER").setValue("MALE");
                    if (female.isChecked())
                        mypostref.child(phonenum).child("GENDER").setValue("FEMALE");
                    Intent intent = new Intent(details.this, ground_volunteer_form2.class);
                    intent.putExtra("phid", phid);
                    intent.putExtra("editable", editable);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(details.this, "PHONE NO INCORRECT", Toast.LENGTH_SHORT).show();
                }
            }
        });
        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    female.setChecked(false);
            }
        });
        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    male.setChecked(false);
            }
        });
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        if (phid != 0L) {
                final ProgressDialog pd = new ProgressDialog(details.this);
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
                        if (!phone.getText().toString().equals(""))
                            pd.hide();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
            {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(android.Manifest.permission.CALL_PHONE, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                if (perms.get(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    makeCall(s);
                } else {
                    // Permission Denied
                    Toast.makeText(details.this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    void makeCall(String string)
    {  int l2=string.length();
        String no1=string.substring(l2-10,l2);
        no1="+91"+no1;
        String number = "tel:" + no1.trim();
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse(number));
        try {
            if (ActivityCompat.checkSelfPermission(details.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                ActivityCompat.requestPermissions(details.this, new String[]{android.Manifest.permission.CALL_PHONE},
                        1);
            }
            else {
                startActivity(in);
            }
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(details.this, "Could not find an activity to place the call.", Toast.LENGTH_SHORT).show();
        }
    }
}
