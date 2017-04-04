package org.aapkerala.broomRevolution;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.aapkerala.broomRevolution.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private static final String TAG = "MainActivity";
    private EditText loginedit;
    private EditText passedit;
    private Button loginbutton;
    private SharedPreferences pref;
    private Spinner spin;
    Spinner spinner;
    public static final String ANONYMOUS = "anonymous";
    private String mUsername;
    private String mPhotoUrl;
    private SharedPreferences mSharedPreferences;
    private GoogleApiClient mGoogleApiClient;
    private String pc_new_name;
    private DatabaseReference mFirebaseDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // View textView=  findViewById(R.id.textView);
       // registerForContextMenu(textView);
        setContentView(R.layout.login_activity);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // Set default username is anonymous.
        mUsername = ANONYMOUS;
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        boolean loggedin = pref.getBoolean("loggedin", false);
       if(loggedin)
        {
            String as=pref.getString("as",null);
            if(as!= null && as.equals("pc")){
                String name=pref.getString("pc_obs_name",null);
                String pc_name=pref.getString("pc_name",null);
                String key=pref.getString("key",null);
               Intent intent=new Intent(this,Pc.class);
                intent.putExtra("name",name);
                intent.putExtra("pc",pc_name);
                intent.putExtra("key",key);
                startActivity(intent);
                finish();
            }
            else if(as!= null && as.equals("lac")){
                String name=pref.getString("lac_obs_name",null);
                String pc_name=pref.getString("lac_pc_name",null);
                String lac_name=pref.getString("lac_name",null);
                Intent intent=new Intent(this,Lac_database.class);
                intent.putExtra("name",name);
                intent.putExtra("pc_name",pc_name);
                intent.putExtra("lac_name",lac_name);
                startActivity(intent);
                finish();
            } else if(as!= null && as.equals("sector")){
                String name=pref.getString("sector_obs_name",null);
                String pc_name=pref.getString("sector_pc_name",null);
                String lac_name=pref.getString("sector_lac_name",null);
                Intent intent=new Intent(this,sector_database.class);
                intent.putExtra("name",name);
                intent.putExtra("pc",pc_name);
                intent.putExtra("lac",lac_name);
                startActivity(intent);
                finish();
            } else if(as!=null && as.equals("ground"))
            {
                String lac=pref.getString("LacG",null);
                String resource=pref.getString("resource",null);
                Intent intent =new Intent(getApplicationContext(),ground_volunteer.class);
                intent.putExtra("lac",lac);
                intent.putExtra("resource",resource);
                startActivity(intent);
                finish();
            }
            else if (as != null && as.equals("phone"))
            {
                Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
                startActivity(intent);
                finish();
            }else if(as!= null && as.equals("admin"))  {
           Intent intent=new Intent(this,Lookover.class);
           startActivity(intent);
                finish();
       } else if(as!= null && as.equals("observer")){
           Intent intent=new Intent(this,observers.class);
           startActivity(intent);
                finish();
       } else if(as!= null && as.equals("look_up")){
           Intent intent=new Intent(this,representative.class);
           startActivity(intent);
                finish();
       }

        }


        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        loginedit = (EditText) findViewById(R.id.loginedit);
        passedit = (EditText) findViewById(R.id.passedit);
        loginbutton = (Button) findViewById(R.id.loginbutton);
        spin = (Spinner) findViewById(R.id.spinner);
        String[] si = {"ADMIN","PC O", "LAC O", "SECTOR O", "CALLING VOLUNTEER", "GROUND VOLUNTEER"};
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                LoginActivity.this, android.R.layout.simple_spinner_dropdown_item,
                si);
        spin.setAdapter(arrayAdapter);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loginedit.getText().toString().equals("HUGGAMA"))
                {
                    if(passedit.getText().toString().equals("AMAGGUH"))
                    {
                        startActivity(new Intent(LoginActivity.this,credits.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, "YOU ARE NOT A CREATER", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (spin.getSelectedItem().toString().equals("PC O")) {
                    try {
                        Long s = Long.parseLong(loginedit.getText().toString());
                        Query query = mFirebaseDatabaseReference.child("PC_DATA").orderByChild("NUMBER").equalTo(Long.parseLong(loginedit.getText().toString()));
                        query.keepSynced(true);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (!(snapshot.exists())) {
                                    Toast.makeText(LoginActivity.this, "Wrong ID", Toast.LENGTH_SHORT).show();
                                }
                                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                                    if (messageSnapshot.child("PASSWORD").getValue().toString().equals(passedit.getText().toString())) {
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putBoolean("loggedin", true);
                                        editor.putString("as", "pc");
                                        String name = messageSnapshot.child("NAME").getValue().toString();
                                        Intent intent = new Intent(getApplicationContext(), Pc.class);
                                        intent.putExtra("name", name);
                                        editor.putString("pc_obs_name", name);
                                        intent.putExtra("pc", messageSnapshot.child("PC").getValue().toString());
                                        editor.putString("pc_name", messageSnapshot.child("PC").getValue().toString());
                                        intent.putExtra("key", messageSnapshot.child("KEY").getValue().toString());
                                        editor.putString("key", messageSnapshot.child("KEY").getValue().toString());
                                        editor.apply();
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "wrong password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }catch (Exception e){
                        Toast.makeText(LoginActivity.this, "Wrong ID", Toast.LENGTH_SHORT).show();
                    }
                }
                if (spin.getSelectedItem().toString().equals("LAC O")) {
                    try {
                        Long s = Long.parseLong(loginedit.getText().toString());

                       // pc_new_name = spinner.getSelectedItem().toString().toUpperCase();

                        Query query = mFirebaseDatabaseReference.child("LAC_DATA").orderByChild("NUMBER")
                                .equalTo(Long.parseLong(loginedit.getText().toString()));
                        query.keepSynced(true);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (!(snapshot.exists())) {
                                    Toast.makeText(LoginActivity.this, "Wrong ID", Toast.LENGTH_SHORT).show();
                                }
                                Log.w("here", snapshot + "");
                                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                                    if (messageSnapshot.child("PASSWORD").getValue().toString().equals(passedit.getText().toString())) {
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putBoolean("loggedin", true);
                                        editor.putString("as", "lac");

                                        Log.w("here", messageSnapshot.toString());
                                        String name = messageSnapshot.child("NAME").getValue().toString();
                                        Intent intent = new Intent(getApplicationContext(), Lac_database.class);
                                        intent.putExtra("name", name);
                                        editor.putString("lac_obs_name", name);
                                        intent.putExtra("pc_name", messageSnapshot.child("PC").getValue().toString());
                                        editor.putString("lac_pc_name",  messageSnapshot.child("PC").getValue().toString());
                                        intent.putExtra("lac_name", messageSnapshot.child("LAC").getValue().toString());
                                        editor.putString("lac_name", messageSnapshot.child("LAC").getValue().toString());
                                        editor.apply();
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "wrong password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }catch (Exception e){
                        Toast.makeText(LoginActivity.this, "Wrong ID", Toast.LENGTH_SHORT).show();
                    }
                }
                if (spin.getSelectedItem().toString().equals("SECTOR O")) {
                    try {
                        Query query = mFirebaseDatabaseReference.child("SECTOR_DATA").orderByChild("ID").equalTo(loginedit.getText().toString());
                        query.keepSynced(true);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                Log.w("id",""+snapshot);
                                if (!(snapshot.exists())) {
                                    Toast.makeText(LoginActivity.this, "Wrong ID", Toast.LENGTH_SHORT).show();
                                }
                                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                                    if (messageSnapshot.child("PASSWORD").getValue().toString().equals(passedit.getText().toString())) {
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putBoolean("loggedin", true);
                                        editor.putString("as", "sector");

                                        Log.w("here", messageSnapshot.toString());

                                        Intent intent = new Intent(getApplicationContext(), sector_database.class);
                                        intent.putExtra("pc", messageSnapshot.child("PC").getValue().toString());
                                        editor.putString("sector_pc_name", messageSnapshot.child("PC").getValue().toString());
                                        intent.putExtra("lac", messageSnapshot.child("LAC").getValue().toString());
                                        editor.putString("sector_lac_name", messageSnapshot.child("LAC").getValue().toString());
                                        editor.apply();
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "wrong password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }catch (Exception e){
                        Toast.makeText(LoginActivity.this, "Wrong ID", Toast.LENGTH_SHORT).show();
                    }
                }
                if (spin.getSelectedItem().toString().equals("CALLING VOLUNTEER")) {
                    try {
                        String logid = loginedit.getText().toString();
                        Log.w("id",logid);
                        Query query = mFirebaseDatabaseReference.child("CALLING_VOLUNTEER").orderByChild("ID").equalTo(loginedit.getText().toString());
                        query.keepSynced(true);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                Log.w("id",""+snapshot);
                                if (!(snapshot.exists())) {
                                    Toast.makeText(LoginActivity.this, "Wrong ID", Toast.LENGTH_SHORT).show();
                                }
                                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                                    if (messageSnapshot.child("PASSWORD").getValue().toString().equals(passedit.getText().toString())) {
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putBoolean("loggedin", true);
                                        editor.putString("as", "phone");
                                        editor.apply();
                                        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "wrong password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }catch (Exception e){
                        Toast.makeText(LoginActivity.this, "Wrong ID", Toast.LENGTH_SHORT).show();
                    }
                }
                else if (spin.getSelectedItem().toString().equals("GROUND VOLUNTEER")) {
                    try {
                        String logid =loginedit.getText().toString();
                        Query query = mFirebaseDatabaseReference.child("GROUND_VOLUNTEER_LOGIN").orderByChild("ID").equalTo(logid);
                        query.keepSynced(true);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (!(snapshot.exists())) {
                                    Toast.makeText(LoginActivity.this, "Wrong ID", Toast.LENGTH_SHORT).show();
                                }
                                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                                    if (messageSnapshot.child("PASSWORD").getValue().toString().equals(passedit.getText().toString())) {
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putBoolean("loggedin", true);
                                        editor.putString("as", "ground");
                                        editor.apply();
                                        String lac = ""+ messageSnapshot.child("LAC").getValue().toString();
                                        int indspc1 = lac.indexOf(" ");
                                        lac=lac.substring(indspc1+1);
                                        int indspc2 = lac.lastIndexOf(" ");
                                        if(lac.charAt(indspc2+1)=='(')
                                            lac=lac.substring(0,indspc2);
                                        lac = lac.toLowerCase();
                                        char[] lac1 = lac.toCharArray();
                                        lac1[0] = Character.toUpperCase(lac1[0]);
                                        int indspc = lac.indexOf(" ");
                                        if (indspc != -1) {
                                            lac1[indspc] = '_';
                                            lac1[indspc + 1] = Character.toUpperCase(lac1[indspc + 1]);
                                        }
                                        String lac2 = new String(lac1);
                                        Intent intent = new Intent(getApplicationContext(), ground_volunteer.class);
                                        intent.putExtra("lac", lac.toUpperCase());
                                        intent.putExtra("resource", lac2);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "wrong password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }catch (Exception e){
                    Toast.makeText(LoginActivity.this, "Wrong ID", Toast.LENGTH_SHORT).show();
                }
                }
                if (spin.getSelectedItem().toString().equals("ADMIN")) {
                    try {
                        Long s = Long.parseLong(loginedit.getText().toString());


                        Query query = mFirebaseDatabaseReference.child("ADMIN").orderByChild("ID").equalTo(Long.parseLong(loginedit.getText().toString()));
                        query.keepSynced(true);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                if (!(snapshot.exists())) {
                                    Toast.makeText(LoginActivity.this, "Wrong ID", Toast.LENGTH_SHORT).show();
                                }
                                Log.e("hi how are you",snapshot+"");
                                for (DataSnapshot messageSnapshot : snapshot.getChildren()) {
                                    if (messageSnapshot.child("PASSWORD").getValue().toString().equals(passedit.getText().toString())) {
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putBoolean("loggedin", true);
                                        editor.putString("as", "admin");
                                        editor.apply();
                                        Intent intent = new Intent(getApplicationContext(),Lookover.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "wrong password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }catch (Exception e){
                        Toast.makeText(LoginActivity.this, "Wrong ID", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }






    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("CREATORS");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_member, menu);
    }
}
