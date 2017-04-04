package org.aapkerala.broomRevolution;

import android.*;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import org.aapkerala.broomRevolution.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.aapkerala.broomRevolution.Main4Activity.getStringIdentifier;

public class pc_lac_sector_observer extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    ArrayList<Member_lac_data> arrayList=new ArrayList();
    ListView sector_observers;
    String s="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pc_lac_sector_observer);
        spinner= (Spinner) findViewById(R.id.lac_names);
        Bundle extras=getIntent().getExtras();
        String pc_name=extras.getString("pc");

            pc_name =pc_name.substring(0,1).toUpperCase()+pc_name.substring(1).toLowerCase();

        String string[]={"Wayanad","Kozhikode","Chalakudy","Pathanamthitta"};
        boolean a =true;
        for(int i=0;i<4;i++){
            if(!(pc_name.trim().equals(string[i])))
            a=false;
        }
        if(!a)
        pc_name=pc_name+"1";
        Log.w("app",pc_name);
        int resourceID=this.getResources().getIdentifier(pc_name.trim(),"array",this.getPackageName());
        String[] cRaces = getResources().getStringArray(getStringIdentifier(pc_lac_sector_observer.this, pc_name));
        String[] r=new String[cRaces.length-1];
        System.arraycopy(cRaces,1,r,0,cRaces.length-1);
        final ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(
                pc_lac_sector_observer.this, android.R.layout.simple_spinner_item,
                r);
        spinner.setAdapter(arrayAdapter);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(this);

    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        final ProgressDialog pd = new ProgressDialog(pc_lac_sector_observer.this);
        pd.setMessage("loading");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        String getlac=spinner.getSelectedItem().toString();
        String[] strings=getlac.split("-");
        Log.e("ye hai dushman",strings[1]);
        Query query=databaseReference.child("MEMBERS").orderByChild("LAC").equalTo(strings[1].trim().toUpperCase());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                Log.e("here",dataSnapshot+"");
                for(DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    String status = (String) messageSnapshot.child("STATUS").getValue();
                    if (status.equals("SECTOR OBSERVER")) {
                        String name = (String) messageSnapshot.child("NAME").getValue();
                        String sector= (String) messageSnapshot.child("SECTOR").getValue();
                        String id = messageSnapshot.child("ID").getValue()+"";
                        Log.e("hfdgjhkjdfg",name+sector+id);
                        arrayList.add(new Member_lac_data(name, sector, null, id));
                    }
                }
                sector_observers= (ListView) findViewById(R.id.sector_observers_pc_show);
                Member_Lac_Adapter adapter=new Member_Lac_Adapter(pc_lac_sector_observer.this,arrayList);
                sector_observers.setAdapter(adapter);
                pd.hide();
                sector_observers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView textView = (TextView) view.findViewById(R.id.member_id);
                        String number = textView.getText().toString();
                        s=number;
                        makeCall(s);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                    Toast.makeText(pc_lac_sector_observer.this, "Some Permission is Denied", Toast.LENGTH_SHORT)
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
            if (ActivityCompat.checkSelfPermission(pc_lac_sector_observer.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                ActivityCompat.requestPermissions(pc_lac_sector_observer.this, new String[]{android.Manifest.permission.CALL_PHONE},
                        1);
            }
            else {
                startActivity(in);
            }
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(pc_lac_sector_observer.this, "Could not find an activity to place the call.", Toast.LENGTH_SHORT).show();
        }
    }
}
