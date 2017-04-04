package org.aapkerala.broomRevolution;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main4Activity extends AppCompatActivity {
    private TaskDbHelper mHelper;
    private EditText name,phone,pincode,ward,booth;
    private RadioButton male,female;
    private AutoCompleteTextView dist,pc,la,pan_mu;
    private DatabaseReference mFirebaseDatabaseReference;
    private SharedPreferences pref;
    private Button send,delete;
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
        Bundle extras = getIntent().getExtras();
        String phonenu="";
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        if (extras != null) {
            if(extras.getString("phone")!=null)
            phonenu = extras.getString("phone");
        }
        phone=(EditText)findViewById(R.id.phoneNum);
        name=(EditText)findViewById(R.id.editName);
        dist=(AutoCompleteTextView) findViewById(R.id.spindistrict);
        pc=(AutoCompleteTextView) findViewById(R.id.spinpc);
        la=(AutoCompleteTextView) findViewById(R.id.spinla);
        pan_mu=(AutoCompleteTextView) findViewById(R.id.spinpan_mun);
        send=(Button)findViewById(R.id.sendFire);
        delete=(Button) findViewById(R.id.button6);
        String as=pref.getString("as",null);
        if(as!=null&&!as.equals("phone")) {
            delete.setVisibility(View.GONE);
            RelativeLayout.LayoutParams paramsBtn=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            paramsBtn.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            send.setLayoutParams(paramsBtn);
        }
        male=(RadioButton)findViewById(R.id.radioButton);
        female=(RadioButton)findViewById(R.id.radioButton2);
        pincode=(EditText)findViewById(R.id.pincode) ;
        ward=(EditText) findViewById(R.id.editText);
        booth=(EditText) findViewById(R.id.editText2);
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
        phone.setText(phonenu);
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
                pan_mu.setAdapter(null);
                if (!sel.equals("")) {
                    ArrayAdapter lac_adapter = ArrayAdapter.createFromResource(Main4Activity.this,getStringIdentifier(Main4Activity.this, sel+"_D"), android.R.layout.simple_spinner_item);
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
                String[] cRaces = getResources().getStringArray(getStringIdentifier(Main4Activity.this, sel));
                String[] r=new String[cRaces.length-1];
                System.arraycopy(cRaces,1,r,0,cRaces.length-1);
                final ArrayAdapter<String> sector_adapter= new ArrayAdapter<String>(
                        Main4Activity.this, R.layout.my_custom_dropdown,R.id.item,
                        r);
                pan_mu.setAdapter(sector_adapter);

            }
        });
        mHelper = new TaskDbHelper(this);
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String name2 = name.getText().toString().trim();
                    String dist2 = dist.getText().toString();
                    String pc2 = pc.getText().toString();
                    String la2 = la.getText().toString();
                    int ind = la2.indexOf("-");
                    if (ind != -1) {
                        int ind1 = la2.lastIndexOf(" ");
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
                        mypostref.child(phonenum).child("PINCODE").setValue(pincode1.toUpperCase());
                        mypostref.child(phonenum).child("WARD").setValue(ward1.toUpperCase());
                        mypostref.child(phonenum).child("BOOTH").setValue(booth1.toUpperCase());
                        mypostref.child(phonenum).child("STATUS").setValue("AAM AADMI");
                        if (male.isChecked())
                            mypostref.child(phonenum).child("GENDER").setValue("MALE");
                        if (female.isChecked())
                            mypostref.child(phonenum).child("GENDER").setValue("FEMALE");
                        SQLiteDatabase db = mHelper.getWritableDatabase();
                        db.delete(TaskContract.TaskEntry.TABLE,
                                TaskContract.TaskEntry.NUMBER + " = ?",
                                new String[]{phonenum});
                        db.close();
                        Toast.makeText(Main4Activity.this, "saved", Toast.LENGTH_LONG).show();
                        String as = pref.getString("as", null);
                        if (as != null && as.equals("ground")) {
                            Intent intent = new Intent(Main4Activity.this, ground_volunteer.class);
                            intent.putExtra("lac", pref.getString("LacG", ""));
                            intent.putExtra("resource", pref.getString("resource", ""));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else if (as != null && as.equals("pc")) {
                            String name = pref.getString("pc_obs_name", null);
                            String pc_name = pref.getString("pc_name", null);
                            String key = pref.getString("key", null);
                            Intent intent = new Intent(Main4Activity.this, Pc.class);
                            intent.putExtra("name", name);
                            intent.putExtra("pc", pc_name);
                            intent.putExtra("key", key);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else if (as != null && as.equals("lac")) {
                            String name = pref.getString("lac_obs_name", null);
                            String pc_name = pref.getString("lac_pc_name", null);
                            String lac_name = pref.getString("lac_name", null);
                            Intent intent = new Intent(Main4Activity.this, Lac_database.class);
                            intent.putExtra("name", name);
                            intent.putExtra("pc_name", pc_name);
                            intent.putExtra("lac_name", lac_name);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else if (as != null && as.equals("sector")) {
                            String name = pref.getString("sector_obs_name", null);
                            String pc_name = pref.getString("sector_pc_name", null);
                            String lac_name = pref.getString("sector_lac_name", null);
                            Intent intent = new Intent(Main4Activity.this, sector_database.class);
                            intent.putExtra("name", name);
                            intent.putExtra("pc", pc_name);
                            intent.putExtra("lac", lac_name);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else if (as != null && as.equals("phone")) {
                            Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else if(as!= null && as.equals("look_up")){
                            Intent intent=new Intent(Main4Activity.this,representative.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        finish();
                    }else
                    {
                        Toast.makeText(Main4Activity.this, "PHONE NO INCORRECT", Toast.LENGTH_SHORT).show();
                    }

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phonenum=phone.getText().toString();
                SQLiteDatabase db = mHelper.getWritableDatabase();
                db.delete(TaskContract.TaskEntry.TABLE,
                        TaskContract.TaskEntry.NUMBER + " = ?",
                        new String[]{phonenum});
                db.close();
                Toast.makeText(Main4Activity.this, "deleted", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Main3Activity.class));
                finish();
            }
        });

    }
}
