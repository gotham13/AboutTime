package org.aapkerala.broomRevolution;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.aapkerala.broomRevolution.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class sector_observers extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    ArrayList<Member_lac_data> arrayList=new ArrayList();
    ListView sector_observers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sector_observers);
        Bundle extras=getIntent().getExtras();
        String lac=extras.getString("lac");
        String[] strings=lac.split("-");
        Log.e("Again here",strings[1].trim());
        final ProgressDialog pd = new ProgressDialog(sector_observers.this);
        pd.setMessage("loading");
        pd.setCanceledOnTouchOutside(false);
        pd.show();
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
                        arrayList.add(new Member_lac_data(name, sector, null, id));
                    }
                }
                sector_observers= (ListView) findViewById(R.id.sector_observers_show);
            Member_Lac_Adapter adapter=new Member_Lac_Adapter(sector_observers.this,arrayList);
                sector_observers.setAdapter(adapter);
                pd.hide();
                registerForContextMenu(sector_observers);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
String member_id;
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.sector_observers_show) {
           ListView listView= (ListView) v;
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            int position=info.position;
            Member_lac_data new_data= (Member_lac_data) listView.getItemAtPosition(position);
            member_id=new_data.getId();
            menu.setHeaderTitle("MOVE TO LEVEL");
            String[] menuItems = {"VOLUNTEER"};
            for (int i = 0; i < menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        databaseReference.child("MEMBERS").child(member_id).child("STATUS").setValue("VOLUNTEER");
        Toast.makeText(this,"DOWNGRADED TO VOLUNTEER", Toast.LENGTH_SHORT).show();
        return true;
    }
}
