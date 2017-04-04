package org.aapkerala.broomRevolution;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
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

import org.aapkerala.broomRevolution.db.TaskContract;
import org.aapkerala.broomRevolution.db.TaskDbHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ground_volunteer_form3 extends AppCompatActivity {
    private EditText twi,fac,ins,wapp;
    private Spinner don,oth,art;
    private TextView arttype;
    private TaskDbHelper mHelper;
    private Button submit;
    private DatabaseReference mFirebaseDatabaseReference;
    Long phid=0L;
    String editable="novalue";
    private SharedPreferences pref;
    private String blockCharacterSet = "/@~#^|$%&*!\\";
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
            editable=extras.getString("editable");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_large2);
        mHelper = new TaskDbHelper(this);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        don=(Spinner) findViewById(R.id.spinner4);
        oth=(Spinner) findViewById(R.id.spinner5);
        art=(Spinner) findViewById(R.id.spinner6);
        twi=(EditText) findViewById(R.id.editText14);
        fac=(EditText) findViewById(R.id.editText13);
        ins=(EditText) findViewById(R.id.editText16);
        wapp=(EditText) findViewById(R.id.editText17);
        arttype=(TextView) findViewById(R.id.textView25);
        submit=(Button) findViewById(R.id.button5);
        twi.setFilters(new InputFilter[] { filter });
        fac.setFilters(new InputFilter[] { filter });
        ins.setFilters(new InputFilter[] { filter });
        wapp.setFilters(new InputFilter[] { filter });
        if(editable.equals("NO"))
        {
            don.setEnabled(false);
            oth.setEnabled(false);
            art.setEnabled(false);
            twi.setEnabled(false);
            fac.setEnabled(false);
            ins.setEnabled(false);
            wapp.setEnabled(false);
        }
        String[] don1 = {"","NACH","AKD","SPONSOR"};
        final ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(
                ground_volunteer_form3.this, android.R.layout.simple_spinner_dropdown_item,
                don1);
        don.setAdapter(arrayAdapter3);
        String[] oth1={"","ORGANIZER",
                "DATA ENTRY",
                "IT SUPPORT",
                "TELE CAMPAIGNING",
                "TRANSLATOR",
                "RESEARCH TRAINING",
                "LOGISTICS",
                "ART",
                "ACCOUNTING OR FINANCE",
                "ADVERTISING OR PUBLICITY",
                "DOCTOR OR HEALTHCARE",
                "LEGAL",
                "PUBLIC SPEAKING",
                "MEDIA OR JOURNALISM",
                "PHOTOGRAPHER OR VIDEOGRAPHER",
                "VIDEO EDITOR OR GFX DESIGNER",
                "OTHER"};
        final ArrayAdapter<String> arrayAdapter4 = new ArrayAdapter<String>(
                ground_volunteer_form3.this, android.R.layout.simple_spinner_dropdown_item,
                oth1);
        oth.setAdapter(arrayAdapter4);
        String[] art1={"","WRITER","MUSICIAN","ARTISTS","STREET", "THEATER"};
        final ArrayAdapter<String> arrayAdapter5 = new ArrayAdapter<String>(
                ground_volunteer_form3.this, android.R.layout.simple_spinner_dropdown_item,
                art1);
        art.setAdapter(arrayAdapter5);
        oth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sel=adapterView.getItemAtPosition(i).toString();
                if(!sel.equals("ART"))
                {
                    arttype.setVisibility(View.GONE);
                    art.setVisibility(View.GONE);

                }
                else
                {
                    arttype.setVisibility(View.VISIBLE);
                    art.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        if (phid != 0L) {
            final ProgressDialog pd = new ProgressDialog(ground_volunteer_form3.this);
            pd.setMessage("loading");
            pd.setCanceledOnTouchOutside(false);
            pd.show();
            Query query = mFirebaseDatabaseReference.child("MEMBERS").orderByChild("ID").equalTo(phid);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                            twi.setText(""+(messageSnapshot.child("TWITTER").getValue()!=null?messageSnapshot.child("TWITTER").getValue():""));
                            fac.setText(""+(messageSnapshot.child("FACEBOOK").getValue()!=null?messageSnapshot.child("FACEBOOK").getValue():""));
                            ins.setText(""+(messageSnapshot.child("INSTAGRAM").getValue()!=null?messageSnapshot.child("INSTAGRAM").getValue():""));
                            wapp.setText(""+(messageSnapshot.child("WHATSAPP").getValue()!=null?messageSnapshot.child("WHATSAPP").getValue():""));
                            String don2=""+(messageSnapshot.child("DONOR").getValue()!=null?messageSnapshot.child("DONOR").getValue():"");
                            don.setSelection((arrayAdapter3.getPosition(don2) != -1)?arrayAdapter3.getPosition(don2) : 0);
                            String oth2=""+(messageSnapshot.child("OTHER VOLUNTEERING").getValue()!=null?messageSnapshot.child("OTHER VOLUNTEERING").getValue():"");
                            oth.setSelection((arrayAdapter4.getPosition(oth2) != -1)?arrayAdapter4.getPosition(oth2) : 0);
                            String art2=""+(messageSnapshot.child("ART TYPE").getValue()!=null?messageSnapshot.child("ART TYPE").getValue():"");
                            art.setSelection((arrayAdapter5.getPosition(art2) != -1)?arrayAdapter5.getPosition(art2) : 0);
                        }
                    }
                    pd.hide();
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mypostref =  mFirebaseDatabaseReference.child("MEMBERS");
                mypostref.child(""+phid).child("TWITTER").setValue(twi.getText().toString().toUpperCase());
                mypostref.child(""+phid).child("FACEBOOK").setValue(fac.getText().toString().toUpperCase());
                mypostref.child(""+phid).child("INSTAGRAM").setValue(ins.getText().toString().toUpperCase());
                mypostref.child(""+phid).child("WHATSAPP").setValue(wapp.getText().toString().toUpperCase());
                mypostref.child(""+phid).child("DONOR").setValue(don.getSelectedItem().toString().toUpperCase());
                mypostref.child(""+phid).child("OTHER VOLUNTEERING").setValue(oth.getSelectedItem().toString().toUpperCase());
                mypostref.child(""+phid).child("ART TYPE").setValue(art.getSelectedItem().toString().toUpperCase());
                String as=pref.getString("as",null);
                 if(as!=null&&as.equals("ground"))
                {
                    mypostref.child(""+phid).child("STATUS").setValue("MEMBER");
                    SQLiteDatabase db=mHelper.getWritableDatabase();
                    db.delete(TaskContract.TaskEntry.TABLE1,
                            TaskContract.TaskEntry.ID + " = ?",
                            new String[]{""+phid});
                    db.close();
                    Intent intent =new Intent(ground_volunteer_form3.this,ground_volunteer.class);
                    intent.putExtra("lac",pref.getString("LacG",""));
                    intent.putExtra("resource",pref.getString("resource",""));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else if(as!=null&&as.equals("pc"))
                {
                    String name=pref.getString("pc_obs_name",null);
                    String pc_name=pref.getString("pc_name",null);
                    String key=pref.getString("key",null);
                    Intent intent=new Intent(ground_volunteer_form3.this,Pc.class);
                    intent.putExtra("name",name);
                    intent.putExtra("pc",pc_name);
                    intent.putExtra("key",key);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                 else if(as!= null && as.equals("lac")){
                     String name=pref.getString("lac_obs_name",null);
                     String pc_name=pref.getString("lac_pc_name",null);
                     String lac_name=pref.getString("lac_name",null);
                     Intent intent=new Intent(ground_volunteer_form3.this,Lac_database.class);
                     intent.putExtra("name",name);
                     intent.putExtra("pc_name",pc_name);
                     intent.putExtra("lac_name",lac_name);
                     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     startActivity(intent);
                 } else if(as!= null && as.equals("sector")){
                     String name=pref.getString("sector_obs_name",null);
                     String pc_name=pref.getString("sector_pc_name",null);
                     String lac_name=pref.getString("sector_lac_name",null);
                     Intent intent=new Intent(ground_volunteer_form3.this,sector_database.class);
                     intent.putExtra("name",name);
                     intent.putExtra("pc",pc_name);
                     intent.putExtra("lac",lac_name);
                     intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     startActivity(intent);
                 }
                 else if(as!= null && as.equals("observer")){
                     Intent intent=new Intent(ground_volunteer_form3.this,observers.class);
                     startActivity(intent);
                 } else if(as!= null && as.equals("look_up")){
                     Intent intent=new Intent(ground_volunteer_form3.this,representative.class);
                     startActivity(intent);
                 }

            }
        });

    }
}
