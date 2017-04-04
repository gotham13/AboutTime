package org.aapkerala.broomRevolution;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.aapkerala.broomRevolution.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ground_volunteer_form2 extends AppCompatActivity {
    private Button next;
    private EditText hNo,Street,Nov,vid,gen,age,col,sem,memNo;
    private Spinner occu,ready,don;
    private TextView col1,sem1;
    private DatabaseReference mFirebaseDatabaseReference;
    private String key;
    Long phid=0L;
    String editable="novalue";
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            phid=extras.getLong("phid");
            if(extras.getString("editable")!=null)
            {
                editable=extras.getString("editable");
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_large);
        next=(Button)findViewById(R.id.button4);
        hNo=(EditText)findViewById(R.id.editText4);
        Street=(EditText)findViewById(R.id.editText5);
        Nov=(EditText)findViewById(R.id.editText6);
        vid=(EditText)findViewById(R.id.editText7);
        gen=(EditText)findViewById(R.id.editText8);
        age=(EditText)findViewById(R.id.editText9);
        col=(EditText) findViewById(R.id.editText10);
        sem=(EditText) findViewById(R.id.editText11);
        memNo=(EditText) findViewById(R.id.editText12);
        occu=(Spinner) findViewById(R.id.spinner2);
        ready=(Spinner) findViewById(R.id.spinner3);
        col1=(TextView) findViewById(R.id.textView17);
        sem1=(TextView) findViewById(R.id.textView18);
        if(editable.equals("NO"))
        {
            hNo.setEnabled(false);
            Street.setEnabled(false);
            Nov.setEnabled(false);
            vid.setEnabled(false);
            gen.setEnabled(false);
            age.setEnabled(false);
            col.setEnabled(false);
            sem.setEnabled(false);
            memNo.setEnabled(false);
            occu.setEnabled(false);
            ready.setEnabled(false);
        }
        hNo.setFilters(new InputFilter[] { filter });
        Street.setFilters(new InputFilter[] { filter });
        Nov.setFilters(new InputFilter[] { filter });
        vid.setFilters(new InputFilter[] { filter });
        gen.setFilters(new InputFilter[] { filter });
        age.setFilters(new InputFilter[] { filter });
        col.setFilters(new InputFilter[] { filter });
        sem.setFilters(new InputFilter[] { filter });
        memNo.setFilters(new InputFilter[] { filter });
        String[] occu1 = {"","JOB","STUDENT","BUISNESS","NRI","NRK","RETIRED,SENIOR CITIZEN"};
        final ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(
                ground_volunteer_form2.this, android.R.layout.simple_spinner_dropdown_item,
                occu1);
        occu.setAdapter(arrayAdapter1);
        final String[] ready1 = {"","FULL TIME","PART TIME","NO"};
        final ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(
                ground_volunteer_form2.this, android.R.layout.simple_spinner_dropdown_item,
                ready1);
        ready.setAdapter(arrayAdapter2);
        occu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sel=adapterView.getItemAtPosition(i).toString();
                if(!sel.equals("STUDENT"))
                {
                    col1.setVisibility(View.GONE);
                    sem1.setVisibility(View.GONE);
                    col.setVisibility(View.GONE);
                    sem.setVisibility(View.GONE);

                }
                else
                {
                    col1.setVisibility(View.VISIBLE);
                    sem1.setVisibility(View.VISIBLE);
                    col.setVisibility(View.VISIBLE);
                    sem.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mypostref =  mFirebaseDatabaseReference.child("MEMBERS");
                mypostref.child(""+phid).child("HOUSE NO").setValue(hNo.getText().toString().toUpperCase());
                mypostref.child(""+phid).child("STREET NAME").setValue(Street.getText().toString().toUpperCase());
                mypostref.child(""+phid).child("NUMBER OF VOTERS").setValue(Nov.getText().toString().toUpperCase());
                mypostref.child(""+phid).child("VOTER ID").setValue(vid.getText().toString().toUpperCase());
                mypostref.child(""+phid).child("AGE").setValue(age.getText().toString().toUpperCase());
                mypostref.child(""+phid).child("COLLEGE").setValue(col.getText().toString().toUpperCase());
                mypostref.child(""+phid).child("SEMESTER").setValue(sem.getText().toString().toUpperCase());
                mypostref.child(""+phid).child("MEMBERSHIP NUMBER").setValue(memNo.getText().toString().toUpperCase());
                mypostref.child(""+phid).child("OCCUPATION").setValue(occu.getSelectedItem().toString().toUpperCase());
                mypostref.child(""+phid).child("READY TO VOLUNTEER").setValue(ready.getSelectedItem().toString().toUpperCase());
                Intent intent=new Intent(ground_volunteer_form2.this,ground_volunteer_form3.class);
                intent.putExtra("phid",phid);
                intent.putExtra("editable",editable);
                startActivity(intent);
            }
        });


        if (phid != 0L) {
            final ProgressDialog pd = new ProgressDialog(ground_volunteer_form2.this);
            pd.setMessage("loading");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
            Query query = mFirebaseDatabaseReference.child("MEMBERS").orderByChild("ID").equalTo(phid);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    key=dataSnapshot.getKey();
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                            hNo.setText(""+(messageSnapshot.child("HOUSE NO").getValue()!=null?messageSnapshot.child("HOUSE NO").getValue():""));
                            Street.setText(""+(messageSnapshot.child("STREET NAME").getValue()!=null?messageSnapshot.child("STREET NAME").getValue():""));
                            Nov.setText(""+(messageSnapshot.child("NUMBER OF VOTERS").getValue()!=null?messageSnapshot.child("NUMBER OF VOTERS").getValue():""));
                            vid.setText(""+(messageSnapshot.child("VOTER ID").getValue()!=null?messageSnapshot.child("VOTER ID").getValue():""));
                            gen.setText(""+(messageSnapshot.child("GENDER").getValue()!=null?messageSnapshot.child("GENDER").getValue():""));
                            age.setText(""+(messageSnapshot.child("AGE").getValue()!=null?messageSnapshot.child("AGE").getValue():""));
                            col.setText(""+(messageSnapshot.child("COLLEGE").getValue()!=null?messageSnapshot.child("COLLEGE").getValue():""));
                            sem.setText(""+(messageSnapshot.child("SEMESTER").getValue()!=null?messageSnapshot.child("SEMESTER").getValue():""));
                            memNo.setText(""+(messageSnapshot.child("MEMBERSHIP NUMBER").getValue()!=null?messageSnapshot.child("MEMBERSHIP NUMBER").getValue():""));
                            String occu2=""+(messageSnapshot.child("OCCUPATION").getValue()!=null?messageSnapshot.child("OCCUPATION").getValue():"");
                            occu.setSelection((arrayAdapter1.getPosition(occu2) != -1)?arrayAdapter1.getPosition(occu2) : 0);
                            String ready2=""+(messageSnapshot.child("READY TO VOLUNTEER").getValue()!=null?messageSnapshot.child("READY TO VOLUNTEER").getValue():"");
                            ready.setSelection((arrayAdapter2.getPosition(ready2) != -1)?arrayAdapter2.getPosition(ready2) : 0);
                        }
                    }
                    pd.hide();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
