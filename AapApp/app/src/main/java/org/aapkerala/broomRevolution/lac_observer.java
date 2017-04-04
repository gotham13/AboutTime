package org.aapkerala.broomRevolution;

import android.*;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class lac_observer extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ArrayList<Member_lac_data> arrayList = new ArrayList();
    ListView lac_observers;
    String s="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lac_observer);
        Bundle extras = getIntent().getExtras();
        final String pc = extras.getString("pc");
        final ProgressDialog pd = new ProgressDialog(lac_observer.this);
        pd.setMessage("loading");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        Query query = databaseReference.child("LAC_DATA").orderByChild("PC").equalTo(pc.toUpperCase());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                Log.e("here", dataSnapshot + "");
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    String name = (String) messageSnapshot.child("NAME").getValue();
                    String lac = (String) messageSnapshot.child("LAC").getValue();
                    String id = messageSnapshot.child("NUMBER").getValue() + "";
                    arrayList.add(new Member_lac_data(name, null, lac, id));
                    Log.e("number ye hai",id);

                }
                lac_observers = (ListView) findViewById(R.id.lac_observers_show);
                Member_Lac_Adapter adapter = new Member_Lac_Adapter(lac_observer.this, arrayList);
                lac_observers.setAdapter(adapter);
                pd.hide();
                lac_observers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        TextView textView = (TextView) view.findViewById(R.id.member_id);
                        String number = textView.getText().toString();
                        Log.e("main number ye hai",number);
                        String call = "tel:" + number;
                        s=call;
                        Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse(call));
                        try {
                            startActivity(intent);
                        }catch(android.content.ActivityNotFoundException ex){
                            Toast.makeText(lac_observer.this,"COULD NOT FIND AN ACTIVITY TO CALL",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
                    Toast.makeText(lac_observer.this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    void makeCall(String string)
    {
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse(string));
        try {
            if (ActivityCompat.checkSelfPermission(lac_observer.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                ActivityCompat.requestPermissions(lac_observer.this, new String[]{android.Manifest.permission.CALL_PHONE},
                        1);
            }
            else {
                startActivity(in);
            }
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(lac_observer.this, "Could not find an activity to place the call.", Toast.LENGTH_SHORT).show();
        }
    }

}
