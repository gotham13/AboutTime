package org.aapkerala.broomRevolution;
import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class observers extends AppCompatActivity {
    private TextView sector_textView;
    private Spinner sector_spinner;
    private TextView textView;
    private Button find;
    private Spinner observer_spinner;
    private Spinner sub_observer;
    private ArrayAdapter<CharSequence> observeradapter;
    private ListView listView;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private ArrayList<observerclass> observerArraylist = new ArrayList<>();
    SharedPreferences pref ;
    String s="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observers);
        textView = (TextView) findViewById(R.id.spinner_textview);
        textView.setVisibility(View.GONE);
        sector_textView = (TextView) findViewById(R.id.sector_textview);
        sector_textView.setVisibility(View.GONE);
        sector_spinner = (Spinner) findViewById(R.id.sector_sub_observer);
        sector_spinner.setVisibility(View.GONE);
        find = (Button) findViewById(R.id.observer_find);
        sub_observer = (Spinner) findViewById(R.id.sub_observer);
        sub_observer.setVisibility(View.GONE);
        observer_spinner = (Spinner) findViewById(R.id.observers_spinner);
        observeradapter = ArrayAdapter.createFromResource(this, R.array.observers, android.R.layout.simple_spinner_item);
        observer_spinner.setAdapter(observeradapter);
        observeradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        listView = (ListView) findViewById(R.id.observers_listview);
        sub_observer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String pc = sub_observer.getSelectedItem().toString();
                String[] pc_rejected = {"Wayanad", "Kozhikode", "Chalakudy", "Pathanamthitta"};
                int a = 0;

                for (int j = 0; j < 4; j++) {
                    if (!(pc.equals(pc_rejected[j]))) {
                        a++;
                    }
                }
                if (a == 4)
                    pc = pc.trim() + "1";
                Log.e("pc_name", pc);
                int resourceID = observers.this.getResources().getIdentifier(pc, "array", observers.this.getPackageName());
                String[] string_array = getResources().getStringArray(resourceID);
                List<String> arrayList = new ArrayList<String>(Arrays.asList(string_array));
                arrayList.remove(0);
                ArrayAdapter<String> lac_adapter = new ArrayAdapter(observers.this, android.R.layout.simple_spinner_dropdown_item, arrayList);
                sector_spinner.setAdapter(lac_adapter);
                lac_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }
        });
        observer_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (observer_spinner.getSelectedItem().toString().equals("lac observers")) {
                    sector_textView.setVisibility(View.GONE);
                    sector_spinner.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                    sub_observer.setVisibility(View.VISIBLE);
                    ArrayAdapter adapter = ArrayAdapter.createFromResource(observers.this, R.array.pc_names, android.R.layout.simple_spinner_item);
                    sub_observer.setAdapter(adapter);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }
                if (observer_spinner.getSelectedItem().toString().equals("pc observers")) {
                    sub_observer.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    sector_textView.setVisibility(View.GONE);
                    sector_spinner.setVisibility(View.GONE);
                }
                if (observer_spinner.getSelectedItem().toString().equals("sector observers")) {
                    textView.setVisibility(View.VISIBLE);
                    sub_observer.setVisibility(View.VISIBLE);
                    sector_textView.setVisibility(View.VISIBLE);
                    sector_spinner.setVisibility(View.VISIBLE);
                    ArrayAdapter adapter = ArrayAdapter.createFromResource(observers.this, R.array.pc_names, android.R.layout.simple_spinner_item);
                    sub_observer.setAdapter(adapter);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    String pc = sub_observer.getSelectedItem().toString();
                    String[] pc_rejected = {"Wayanad", "Kozhikode", "Chalakudy", "Pathanamthitta"};
                    int a = 0;

                    for (int j = 0; j < 4; j++) {
                        if (!(pc.equals(pc_rejected[j]))) {
                            a++;
                        }
                    }
                    if (a == 4)
                        pc = pc.trim() + "1";
                    Log.e("pc_name", pc);
                    int resourceID = observers.this.getResources().getIdentifier(pc, "array", observers.this.getPackageName());
                    String[] string_array = getResources().getStringArray(resourceID);
                    List<String> arrayList = new ArrayList<String>(Arrays.asList(string_array));
                    arrayList.remove(0);
                    ArrayAdapter<String> lac_adapter = new ArrayAdapter(observers.this, android.R.layout.simple_spinner_dropdown_item, arrayList);
                    sector_spinner.setAdapter(lac_adapter);
                    lac_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String observer_check = observer_spinner.getSelectedItem() + "";
                final ProgressDialog pd = new ProgressDialog(observers.this);
                pd.setMessage("loading");
                pd.setCanceledOnTouchOutside(false);
                pd.show();
                if (observer_check.equals("pc observers")) {
                    observerArraylist.clear();
                    Query query = databaseReference.child("PC_DATA").orderByChild("PC");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override

                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.e("ooye hooye", dataSnapshot + "");
                            for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                                String name = (String) messageSnapshot.child("NAME").getValue();
                                String pc_name = (String) messageSnapshot.child("PC").getValue();
                                String number = messageSnapshot.child("NUMBER").getValue() + "";
                                Log.e("dekho kaun aaya", name + pc_name + number);
                                observerArraylist.add(new observerclass(name, pc_name, number));
                            }
                            ObserverAdapter observerAdapter = new ObserverAdapter(observers.this, observerArraylist);
                            listView.setAdapter(observerAdapter);
                            pd.hide();
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    final TextView number = (TextView) view.findViewById(R.id.observer_number);
                                    String string = "" + number.getText();
                                    s=string;
                                    makeCall(s);
                                }


                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
                if (observer_check.equals("lac observers")) {
                    observerArraylist.clear();

                    String pc_name = sub_observer.getSelectedItem().toString();
                    Query query = databaseReference.child("LAC_DATA").orderByChild("PC").equalTo(pc_name.trim().toUpperCase());
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                                String name = messageSnapshot.child("NAME").getValue().toString();
                                String lac_name = messageSnapshot.child("LAC").getValue().toString();
                                String number = messageSnapshot.child("NUMBER").getValue().toString();
                                observerArraylist.add(new observerclass(name, lac_name, number));
                            }
                            ObserverAdapter lac_adapter = new ObserverAdapter(observers.this, observerArraylist);
                            listView.setAdapter(lac_adapter);
                            pd.hide();
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    final TextView number = (TextView) view.findViewById(R.id.observer_number);
                                    String string = "" + number.getText();
                                    s=string;
                                    makeCall(s);

                                }


                            });


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                if (observer_check.equals("sector observers")) {
                    observerArraylist.clear();

                    final String pc = sub_observer.getSelectedItem().toString().toUpperCase();
                    final String lac = sector_spinner.getSelectedItem().toString().toUpperCase();
                    final String[] strings = lac.split("-");
                    Query query = databaseReference.child("MEMBERS").orderByChild("STATUS").equalTo("SECTOR OBSERVER");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.e("pata chala",dataSnapshot.toString());
                            for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                                String pc_name = messageSnapshot.child("PC").getValue().toString();
                                String lac_name = messageSnapshot.child("LAC").getValue().toString();
                                Log.e("hdjg",pc_name+" "+lac_name);
                                if (pc_name.equals(pc) && lac_name.equals(strings[1].trim().toUpperCase())) {
                                    String name = messageSnapshot.child("NAME").getValue().toString();
                                    String lacp_name = messageSnapshot.child("SECTOR").getValue().toString();
                                    String number = messageSnapshot.child("ID").getValue().toString();
                                    observerArraylist.add(new observerclass(name, lacp_name, number));
                                }
                            }
                            ObserverAdapter lac_adapter = new ObserverAdapter(observers.this, observerArraylist);
                            listView.setAdapter(lac_adapter);
                            pd.hide();
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    final TextView number = (TextView) view.findViewById(R.id.observer_number);
                                    String string =number.getText().toString();
                                    s=string;
                                    makeCall(s);


                                }


                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }


            }


        });

    }
    void makeCall(String string)
    {int l2=string.length();
        String no1=string.substring(l2-10,l2);
        no1="+91"+no1;
        String number = "tel:" + no1.trim();
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse(number));
        try {
            if (ActivityCompat.checkSelfPermission(observers.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                ActivityCompat.requestPermissions(observers.this, new String[]{android.Manifest.permission.CALL_PHONE},
                        1);
            }
            else {
                startActivity(in);
            }
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(observers.this, "Could not find an activity to place the call.", Toast.LENGTH_SHORT).show();
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
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                if (perms.get(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                    makeCall(s);
                } else {
                    // Permission Denied
                    Toast.makeText(observers.this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    @Override
    public void onBackPressed()
    {
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("loggedin", true);
        editor.putString("as","admin");
        editor.apply();
        startActivity(new Intent(observers.this, Lookover.class));
        super.onBackPressed();
    }
}