package org.aapkerala.broomRevolution;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.List;

public class representative extends AppCompatActivity {

    private Spinner pc_spinner;
    private Spinner lac_spinner;
    private Spinner sector_spinner;
    private Button submit_button;
    private SharedPreferences pref;
    private String check_data;
    private DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();
    private CheckBox checkBox;
    private RadioGroup radioGroup;
    private FloatingActionButton fab;
    private ListView member_listview;
    private ArrayList<Member_lac_data> arrayList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_representative);
        member_listview= (ListView) findViewById(R.id.member_listview);
        radioGroup= (RadioGroup) findViewById(R.id.radio_check);
        fab= (FloatingActionButton) findViewById(R.id.fab1);
        checkBox= (CheckBox) findViewById(R.id.donor);
        submit_button= (Button) findViewById(R.id.Button);
        pc_spinner= (Spinner) findViewById(R.id.spinner_pc);
        lac_spinner= (Spinner) findViewById(R.id.spinner_lac);
        sector_spinner= (Spinner) findViewById(R.id.spinner_sector);
        final String[] string=getResources().getStringArray(R.array.pc_names);
        List<String> new_string= new ArrayList<>(Arrays.asList(string));
        new_string.add(0,"All");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(representative.this,Main4Activity.class);
                startActivity(intent);
            }
        });
        final ArrayAdapter adapter=new ArrayAdapter(representative.this,android.R.layout.simple_spinner_dropdown_item,new_string);
        pc_spinner.setAdapter(adapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        member_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView textView= (TextView) view.findViewById(R.id.member_id);
                String phid=textView.getText().toString();
                Log.w("myapp",textView.getText().toString());
                Intent intent=new Intent(representative.this,details.class);
                intent.putExtra("phid",Long.parseLong(phid));
                startActivity(intent);
            }
        });
        pc_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String pc = pc_spinner.getSelectedItem().toString();
                {
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
                    if (!(pc.equals("All1"))) {
                        int resourceID = representative.this.getResources().getIdentifier(pc, "array", representative.this.getPackageName());
                        Log.e("faaaf", resourceID + "");
                        ArrayAdapter lac_adapter = ArrayAdapter.createFromResource(representative.this, resourceID, android.R.layout.simple_spinner_item);
                        lac_spinner.setAdapter(lac_adapter);
                        lac_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    } else {
                        ArrayList<String> arrayList = new ArrayList<String>();
                        arrayList.add("All");
                        ArrayAdapter lac_adapter = new ArrayAdapter(representative.this, android.R.layout.simple_spinner_item, arrayList);
                        lac_spinner.setAdapter(lac_adapter);
                    }


                }
            }

            @Override
            public void onNothingSelected (AdapterView < ? > adapterView){

            }

        });
        lac_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String lac = lac_spinner.getSelectedItem().toString();
                if (!(lac.equals("All"))) {
                    String[] strings = lac.split("-");
                    int lac_number = Integer.parseInt(strings[0].trim());
                    ArrayAdapter sector_adapter = null;
                    switch (lac_number) {
                        case 1:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Manjeshwar, android.R.layout.simple_spinner_item);
                            break;
                        case 2:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kasaragod, android.R.layout.simple_spinner_item);
                            break;
                        case 3:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Udma, android.R.layout.simple_spinner_item);
                            break;
                        case 4:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kanhangad, android.R.layout.simple_spinner_item);
                            break;
                        case 5:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Trikaripur, android.R.layout.simple_spinner_item);
                            break;
                        case 6:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Payyannur, android.R.layout.simple_spinner_item);
                            break;
                        case 7:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kalliasseri, android.R.layout.simple_spinner_item);
                            break;
                        case 8:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Taliparamba, android.R.layout.simple_spinner_item);
                            break;
                        case 9:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Irikkur, android.R.layout.simple_spinner_item);
                            break;
                        case 10:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Azhikode, android.R.layout.simple_spinner_item);
                            break;
                        case 11:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kannur, android.R.layout.simple_spinner_item);
                            break;
                        case 12:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Dharmadam, android.R.layout.simple_spinner_item);
                            break;
                        case 13:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Thalassery, android.R.layout.simple_spinner_item);
                            break;
                        case 14:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kuthuparamba, android.R.layout.simple_spinner_item);
                            break;
                        case 15:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Mattannur, android.R.layout.simple_spinner_item);
                            break;
                        case 16:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Peravoor, android.R.layout.simple_spinner_item);
                            break;
                        case 17:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Mananthavady, android.R.layout.simple_spinner_item);
                            break;
                        case 18:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Sulthanbathery, android.R.layout.simple_spinner_item);
                            break;
                        case 19:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kalpetta, android.R.layout.simple_spinner_item);
                            break;
                        case 20:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Vadakara, android.R.layout.simple_spinner_item);
                            break;
                        case 21:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kuttiadi, android.R.layout.simple_spinner_item);
                            break;
                        case 22:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Nadapuram, android.R.layout.simple_spinner_item);
                            break;
                        case 23:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Quilandy, android.R.layout.simple_spinner_item);
                            break;
                        case 24:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Perambra, android.R.layout.simple_spinner_item);
                            break;
                        case 25:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Balusseri, android.R.layout.simple_spinner_item);
                            break;
                        case 26:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Elathur, android.R.layout.simple_spinner_item);
                            break;
                        case 27:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kozhikode_North, android.R.layout.simple_spinner_item);
                            break;
                        case 28:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kozhikode_South, android.R.layout.simple_spinner_item);
                            break;
                        case 29:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Beypore, android.R.layout.simple_spinner_item);
                            break;
                        case 30:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kunnamangalam, android.R.layout.simple_spinner_item);
                            break;
                        case 31:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Koduvally, android.R.layout.simple_spinner_item);
                            break;
                        case 32:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Thiruvambadi, android.R.layout.simple_spinner_item);
                            break;
                        case 33:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kondotty, android.R.layout.simple_spinner_item);
                            break;
                        case 34:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Ernad, android.R.layout.simple_spinner_item);
                            break;
                        case 35:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Nilambur, android.R.layout.simple_spinner_item);
                            break;
                        case 36:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Wandoor, android.R.layout.simple_spinner_item);
                            break;
                        case 37:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Manjeri, android.R.layout.simple_spinner_item);
                            break;
                        case 38:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Perinthalmanna, android.R.layout.simple_spinner_item);
                            break;
                        case 39:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Mankada, android.R.layout.simple_spinner_item);
                            break;
                        case 40:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Malappuram, android.R.layout.simple_spinner_item);
                            break;
                        case 41:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Vengara, android.R.layout.simple_spinner_item);
                            break;
                        case 42:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Vallikkunnu, android.R.layout.simple_spinner_item);
                            break;
                        case 43:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Tirurangadi, android.R.layout.simple_spinner_item);
                            break;
                        case 44:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Tanur, android.R.layout.simple_spinner_item);
                            break;
                        case 45:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Tirur, android.R.layout.simple_spinner_item);
                            break;
                        case 46:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kottakkal, android.R.layout.simple_spinner_item);
                            break;
                        case 47:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Thavanur, android.R.layout.simple_spinner_item);
                            break;
                        case 48:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Ponnani, android.R.layout.simple_spinner_item);
                            break;
                        case 49:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Thrithala, android.R.layout.simple_spinner_item);
                            break;
                        case 50:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Pattambi, android.R.layout.simple_spinner_item);
                            break;
                        case 51:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Shoranur, android.R.layout.simple_spinner_item);
                            break;
                        case 52:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Ottappalam, android.R.layout.simple_spinner_item);
                            break;
                        case 53:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kongad, android.R.layout.simple_spinner_item);
                            break;
                        case 54:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Mannarkkad, android.R.layout.simple_spinner_item);
                            break;
                        case 55:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Malampuzha, android.R.layout.simple_spinner_item);
                            break;
                        case 56:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Palakkad, android.R.layout.simple_spinner_item);
                            break;
                        case 57:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Tarur, android.R.layout.simple_spinner_item);
                            break;
                        case 58:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Chittur, android.R.layout.simple_spinner_item);
                            break;
                        case 59:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Nemmara, android.R.layout.simple_spinner_item);
                            break;
                        case 60:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Alathur, android.R.layout.simple_spinner_item);
                            break;
                        case 61:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Chelakkara, android.R.layout.simple_spinner_item);
                            break;
                        case 62:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kunnamkulam, android.R.layout.simple_spinner_item);
                            break;
                        case 63:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Guruvayoor, android.R.layout.simple_spinner_item);
                            break;
                        case 64:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Manalur, android.R.layout.simple_spinner_item);
                            break;
                        case 65:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Wadakkanchery, android.R.layout.simple_spinner_item);
                            break;
                        case 66:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Ollur, android.R.layout.simple_spinner_item);
                            break;
                        case 67:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Thrissur, android.R.layout.simple_spinner_item);
                            break;
                        case 68:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Nattika, android.R.layout.simple_spinner_item);
                            break;
                        case 69:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kaipamangalam, android.R.layout.simple_spinner_item);
                            break;
                        case 70:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Irinjalakuda, android.R.layout.simple_spinner_item);
                            break;
                        case 71:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Pudukkad, android.R.layout.simple_spinner_item);
                            break;
                        case 72:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Chalakudy2, android.R.layout.simple_spinner_item);
                            break;
                        case 73:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kodungallur, android.R.layout.simple_spinner_item);
                            break;
                        case 74:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Perumbavoor, android.R.layout.simple_spinner_item);
                            break;
                        case 75:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Angamaly, android.R.layout.simple_spinner_item);
                            break;
                        case 76:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Aluva, android.R.layout.simple_spinner_item);
                            break;
                        case 77:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kalamassery, android.R.layout.simple_spinner_item);
                            break;
                        case 78:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Paravur, android.R.layout.simple_spinner_item);
                            break;
                        case 79:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Vypeen, android.R.layout.simple_spinner_item);
                            break;
                        case 80:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kochi, android.R.layout.simple_spinner_item);
                            break;
                        case 81:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Tripunithura, android.R.layout.simple_spinner_item);
                            break;
                        case 82:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Ernakulam, android.R.layout.simple_spinner_item);
                            break;
                        case 83:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Thrikkakara, android.R.layout.simple_spinner_item);
                            break;
                        case 84:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kunnathunad, android.R.layout.simple_spinner_item);
                            break;
                        case 85:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Piravom, android.R.layout.simple_spinner_item);
                            break;
                        case 86:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Muvattupuzha, android.R.layout.simple_spinner_item);
                            break;
                        case 87:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kothamangalam, android.R.layout.simple_spinner_item);
                            break;
                        case 88:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Devikulam, android.R.layout.simple_spinner_item);
                            break;
                        case 89:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Udumbanchola, android.R.layout.simple_spinner_item);
                            break;
                        case 90:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Thodupuzha, android.R.layout.simple_spinner_item);
                            break;
                        case 91:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Idukki, android.R.layout.simple_spinner_item);
                            break;
                        case 92:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Peerumade, android.R.layout.simple_spinner_item);
                            break;
                        case 93:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Pala, android.R.layout.simple_spinner_item);
                            break;
                        case 94:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kaduthuruthy, android.R.layout.simple_spinner_item);
                            break;
                        case 95:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Vaikom, android.R.layout.simple_spinner_item);
                            break;
                        case 96:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Ettumanoor, android.R.layout.simple_spinner_item);
                            break;
                        case 97:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kottayam, android.R.layout.simple_spinner_item);
                            break;
                        case 98:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Puthuppally, android.R.layout.simple_spinner_item);
                            break;
                        case 99:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Changanassery, android.R.layout.simple_spinner_item);
                            break;
                        case 100:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kanjirappally, android.R.layout.simple_spinner_item);
                            break;
                        case 101:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Poonjar, android.R.layout.simple_spinner_item);
                            break;
                        case 102:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Aroor, android.R.layout.simple_spinner_item);
                            break;
                        case 103:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Cherthala, android.R.layout.simple_spinner_item);
                            break;
                        case 104:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Alappuzha, android.R.layout.simple_spinner_item);
                            break;
                        case 105:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Ambalappuzha, android.R.layout.simple_spinner_item);
                            break;
                        case 106:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kuttanad, android.R.layout.simple_spinner_item);
                            break;
                        case 107:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Haripad, android.R.layout.simple_spinner_item);
                            break;
                        case 108:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kayamkulam, android.R.layout.simple_spinner_item);
                            break;
                        case 109:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Mavelikkara, android.R.layout.simple_spinner_item);
                            break;
                        case 110:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Chengannur, android.R.layout.simple_spinner_item);
                            break;
                        case 111:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Thiruvalla, android.R.layout.simple_spinner_item);
                            break;
                        case 112:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Ranni, android.R.layout.simple_spinner_item);
                            break;
                        case 113:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Aranmula, android.R.layout.simple_spinner_item);
                            break;
                        case 114:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Konni, android.R.layout.simple_spinner_item);
                            break;
                        case 115:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Adoor, android.R.layout.simple_spinner_item);
                            break;
                        case 116:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Karunagappally, android.R.layout.simple_spinner_item);
                            break;
                        case 117:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Chavara, android.R.layout.simple_spinner_item);
                            break;
                        case 118:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kunnathur, android.R.layout.simple_spinner_item);
                            break;
                        case 119:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kottarakkara, android.R.layout.simple_spinner_item);
                            break;
                        case 120:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Pathanapuram, android.R.layout.simple_spinner_item);
                            break;
                        case 121:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Punalur, android.R.layout.simple_spinner_item);
                            break;
                        case 122:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Chadayamangalam, android.R.layout.simple_spinner_item);
                            break;
                        case 123:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kundara, android.R.layout.simple_spinner_item);
                            break;
                        case 124:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kollam, android.R.layout.simple_spinner_item);
                            break;
                        case 125:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Eravipuram, android.R.layout.simple_spinner_item);
                            break;
                        case 126:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Chathannoor, android.R.layout.simple_spinner_item);
                            break;
                        case 127:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Varkala, android.R.layout.simple_spinner_item);
                            break;
                        case 128:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Attingal, android.R.layout.simple_spinner_item);
                            break;
                        case 129:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Chirayinkeezhu, android.R.layout.simple_spinner_item);
                            break;
                        case 130:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Nedumangad, android.R.layout.simple_spinner_item);
                            break;
                        case 131:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Vamanapuram, android.R.layout.simple_spinner_item);
                            break;
                        case 132:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kazhakoottam, android.R.layout.simple_spinner_item);
                            break;
                        case 133:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Vattiyoorkavu, android.R.layout.simple_spinner_item);
                            break;
                        case 134:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Thiruvananthapuram, android.R.layout.simple_spinner_item);
                            break;
                        case 135:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Nemom, android.R.layout.simple_spinner_item);
                            break;
                        case 136:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Aruvikkara, android.R.layout.simple_spinner_item);
                            break;
                        case 137:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Parassala, android.R.layout.simple_spinner_item);
                            break;
                        case 138:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kattakkada, android.R.layout.simple_spinner_item);
                            break;
                        case 139:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Kovalam, android.R.layout.simple_spinner_item);
                            break;
                        case 140:
                            sector_adapter = ArrayAdapter.createFromResource(representative.this, R.array.Neyyattinkara, android.R.layout.simple_spinner_item);
                            break;
                        default:
                            break;


                    }
                    sector_spinner.setAdapter(sector_adapter);
                    sector_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                }
                else{
                    ArrayList<String> arrayList = new ArrayList<String>();
                    arrayList.add("All");
                    ArrayAdapter lac_adapter = new ArrayAdapter(representative.this, android.R.layout.simple_spinner_item, arrayList);
                    sector_spinner.setAdapter(lac_adapter);

                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pc=pc_spinner.getSelectedItem().toString().toUpperCase().trim();
                String lac=lac_spinner.getSelectedItem().toString().toUpperCase().trim();
                String sector=sector_spinner.getSelectedItem().toString().toUpperCase().trim();
                final ProgressDialog pd = new ProgressDialog(representative.this);
                pd.setMessage("loading");
                pd.setCanceledOnTouchOutside(false);
                pd.show();
                if(!(pc.equals("ALL"))){
                    if (lac.equals("ALL") && (sector.equals("ALL"))) {
                        String[] strings = lac.split("-");
                        // String new_lac = strings[1].trim();


                        Query query = databaseReference.child("MEMBERS").orderByChild("PC").equalTo(pc);
                        query.keepSynced(true);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.w("i am here",dataSnapshot+"");
                                arrayList.clear();
                                int i = 0;
                                if (dataSnapshot.exists()) {


                                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                                        String status = ""+ messageSnapshot.child("STATUS").getValue();
                                        String donor= ""+ messageSnapshot.child("DONOR").getValue();
                                        //  Log.e("AAO RAJA",radioGroup.getCheckedRadioButtonId()+""+checkBox.isChecked()+"");
                                        if((!(radioGroup.getCheckedRadioButtonId() == -1))&&(checkBox.isChecked())){
                                            if (status.trim().equals(check_data)&&(!donor.equals(""))&&(!(donor.equals("null"))))
                                            {
                                                String name = ""+ messageSnapshot.child("NAME").getValue();
                                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                                String lac_name = ""+ messageSnapshot.child("LAC").getValue();
                                                String number=""+messageSnapshot.child("ID").getValue();
                                                arrayList.add(new Member_lac_data(name, sector, lac_name,number));
                                                i++;
                                            }
                                        }
                                        else  if((radioGroup.getCheckedRadioButtonId()==-1)&&(!(checkBox.isChecked()))){
                                            String name = ""+ messageSnapshot.child("NAME").getValue();
                                            String number=""+messageSnapshot.child("ID").getValue();
                                            String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                            String lac_name = ""+ messageSnapshot.child("LAC").getValue();
                                            arrayList.add(new Member_lac_data(name, sector, lac_name,number));
                                            i++;
                                        } else  if((radioGroup.getCheckedRadioButtonId()==-1)&&((checkBox.isChecked()))){
                                            if ((!donor.equals(""))&&(!(donor.equals("null"))))
                                            {
                                                String number=""+messageSnapshot.child("ID").getValue();
                                                String name = ""+ messageSnapshot.child("NAME").getValue();
                                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                                String lac_name = ""+ messageSnapshot.child("LAC").getValue();
                                                arrayList.add(new Member_lac_data(name, sector, lac_name,number));
                                                i++;
                                            }
                                        }else  if((!(radioGroup.getCheckedRadioButtonId()==-1))&&(!(checkBox.isChecked()))){
                                            if (status.trim().equals(check_data))
                                            {
                                                String number=""+messageSnapshot.child("ID").getValue();
                                                String name = ""+ messageSnapshot.child("NAME").getValue();
                                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                                String lac_name = ""+ messageSnapshot.child("LAC").getValue();
                                                arrayList.add(new Member_lac_data(name, sector, lac_name,number));
                                                i++;
                                            }
                                        }
                                    }
                                    TextView count = (TextView) findViewById(R.id.count);
                                    count.setText(i + "");
                                    Member_Lac_Adapter adapter = new Member_Lac_Adapter(representative.this, arrayList);
                                    member_listview.setAdapter(adapter);
                                    pd.hide();
                                    //  registerForContextMenu(member_listview);
                                } else {
                                    Toast.makeText(representative.this, "There is no data available", Toast.LENGTH_SHORT).show();
                                    Member_Lac_Adapter adapter = new Member_Lac_Adapter(representative.this, arrayList);
                                    member_listview.setAdapter(adapter);
                                    pd.hide();
                                    registerForContextMenu(member_listview);
                                    TextView count = (TextView) findViewById(R.id.count);
                                    count.setText(i + "");
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } else if (sector.equals("ALL")) {
                        String[] strings = lac.split("-");
                        String new_lac = strings[1].trim();
                        Query query = databaseReference.child("MEMBERS").orderByChild("LAC").equalTo(new_lac.toUpperCase());
                        query.keepSynced(true);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                arrayList.clear();
                                int i = 0;
                                if (dataSnapshot.exists()) {

                                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                                        String status = ""+ messageSnapshot.child("STATUS").getValue();
                                        String donor= ""+ messageSnapshot.child("DONOR").getValue();
                                        if((!(radioGroup.getCheckedRadioButtonId()==-1))&&(checkBox.isChecked())){
                                            if (status.trim().equals(check_data)&&(!donor.equals(""))&&(!(donor.equals("null"))))
                                            {
                                                String name = ""+ messageSnapshot.child("NAME").getValue();
                                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                                String number=""+messageSnapshot.child("ID").getValue();
                                                arrayList.add(new Member_lac_data(name, sector, null,number));
                                                i++;
                                            }
                                        }
                                        else  if((radioGroup.getCheckedRadioButtonId()==-1)&&(!(checkBox.isChecked()))){
                                            String name = ""+ messageSnapshot.child("NAME").getValue();
                                            String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                            String number=""+messageSnapshot.child("ID").getValue();
                                            arrayList.add(new Member_lac_data(name, sector, null,number));
                                            i++;
                                        } else  if((radioGroup.getCheckedRadioButtonId()==-1)&&((checkBox.isChecked()))){
                                            if ((!donor.equals(""))&&(!(donor.equals("null"))))
                                            {
                                                String name = ""+ messageSnapshot.child("NAME").getValue();
                                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                                String number=""+messageSnapshot.child("ID").getValue();
                                                arrayList.add(new Member_lac_data(name, sector, null,number));
                                                i++;
                                            }
                                        }else  if((!(radioGroup.getCheckedRadioButtonId()==-1))&&(!(checkBox.isChecked()))){
                                            if (status.trim().equals(check_data))
                                            {
                                                String name = ""+ messageSnapshot.child("NAME").getValue();
                                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                                String number=""+messageSnapshot.child("ID").getValue();
                                                arrayList.add(new Member_lac_data(name, sector, null,number));
                                                i++;
                                            }
                                        }
                                    }
                                    TextView count = (TextView) findViewById(R.id.count);
                                    count.setText(i + "");
                                    Member_Lac_Adapter adapter = new Member_Lac_Adapter(representative.this, arrayList);
                                    member_listview.setAdapter(adapter);
                                    pd.hide();
                                    //  registerForContextMenu(member_listview);
                                } else {
                                    Toast.makeText(representative.this, "There is no data available", Toast.LENGTH_SHORT).show();
                                    Member_Lac_Adapter adapter = new Member_Lac_Adapter(representative.this, arrayList);
                                    member_listview.setAdapter(adapter);
                                    pd.hide();
                                    registerForContextMenu(member_listview);
                                    TextView count = (TextView) findViewById(R.id.count);
                                    count.setText(i + "");
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    } else if ((!(lac.equals("ALL"))) && (!(sector.equals("ALL")))) {
                        Log.e("ghjguhguj",lac);
                        String[] strings = lac.split("-");
                        Log.e("ghjguhguj",strings[1]+strings[0]);
                        String new_lac = strings[1].trim();

                        final String new_sector = sector_spinner.getSelectedItem().toString();


                        Query query = databaseReference.child("MEMBERS").orderByChild("LAC").equalTo(new_lac.toUpperCase());
                        query.keepSynced(true);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                arrayList.clear();
                                int i = 0;
                                if (dataSnapshot.exists()) {
                               /* String name = ""+ messageSnapshot.child("NAME").getValue();
                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                if (sector.toUpperCase().equals(new_sector.toUpperCase())) {
                                    arrayList.add(new Member_lac_data(name, null, null,null));
                                    i++;
                                    */


                                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                                        String status = ""+ messageSnapshot.child("STATUS").getValue();
                                        String donor= ""+ messageSnapshot.child("DONOR").getValue();
                                        if((!(radioGroup.getCheckedRadioButtonId()==-1))&&(checkBox.isChecked())) {
                                            if (status.trim().equals(check_data) && (!donor.equals(""))&&(!(donor.equals("null")))) {
                                                String name = ""+ messageSnapshot.child("NAME").getValue();
                                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                                String number=""+messageSnapshot.child("ID").getValue();
                                                if (sector.toUpperCase().equals(new_sector.toUpperCase())) {
                                                    arrayList.add(new Member_lac_data(name, null, null, number));
                                                    i++;
                                                }
                                            }
                                        }
                                        else  if((radioGroup.getCheckedRadioButtonId()==-1)&&(!(checkBox.isChecked()))) {
                                            String name = ""+ messageSnapshot.child("NAME").getValue();
                                            String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                            String number=""+messageSnapshot.child("ID").getValue();
                                            if (sector.toUpperCase().equals(new_sector.toUpperCase())) {
                                                arrayList.add(new Member_lac_data(name, null, null, number));
                                                i++;
                                            }
                                        }else  if((radioGroup.getCheckedRadioButtonId()==-1)&&((checkBox.isChecked()))){
                                            if ((!donor.equals(""))&&(!(donor.equals("null")))) {
                                                String name = ""+ messageSnapshot.child("NAME").getValue();
                                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                                String number=""+messageSnapshot.child("ID").getValue();
                                                if (sector.toUpperCase().equals(new_sector.toUpperCase())) {
                                                    arrayList.add(new Member_lac_data(name, null, null, number));
                                                    i++;
                                                }
                                            }
                                        }else  if((!(radioGroup.getCheckedRadioButtonId()==-1))&&(!(checkBox.isChecked()))){
                                            if (status.trim().equals(check_data))
                                            {
                                                String name = ""+ messageSnapshot.child("NAME").getValue();
                                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                                String number=""+messageSnapshot.child("ID").getValue();
                                                if (sector.toUpperCase().equals(new_sector.toUpperCase())) {
                                                    arrayList.add(new Member_lac_data(name, null, null,number));
                                                    i++;
                                                }
                                            }
                                        }
                                    }
                                    TextView count = (TextView) findViewById(R.id.count);
                                    count.setText(i + "");
                                    Member_Lac_Adapter adapter = new Member_Lac_Adapter(representative.this, arrayList);
                                    member_listview.setAdapter(adapter);
                                    pd.hide();
                                } else {
                                    Toast.makeText(representative.this, "There is no data available", Toast.LENGTH_SHORT).show();
                                    Member_Lac_Adapter adapter = new Member_Lac_Adapter(representative.this, arrayList);
                                    member_listview.setAdapter(adapter);
                                    pd.hide();
                                    TextView count = (TextView) findViewById(R.id.count);
                                    count.setText(i + "");
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    };
                }
                if((pc.equals("ALL")))

                {
                    if (lac.equals("ALL") && (sector.equals("ALL"))) {
                        String[] strings = lac.split("-");
                        // String new_lac = strings[1].trim();
                        databaseReference.child("MEMBERS").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.w("i am here",dataSnapshot+"");
                                arrayList.clear();
                                int i = 0;
                                if (dataSnapshot.exists()) {


                                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                                        String status = ""+ messageSnapshot.child("STATUS").getValue();
                                        String donor= ""+ messageSnapshot.child("DONOR").getValue();
                                        //  Log.e("AAO RAJA",radioGroup.getCheckedRadioButtonId()+""+checkBox.isChecked()+"");
                                        if((!(radioGroup.getCheckedRadioButtonId() == -1))&&(checkBox.isChecked())){
                                            if (status.trim().equals(check_data)&&(!donor.equals(""))&&(!(donor.equals("null"))))
                                            {
                                                String name = ""+ messageSnapshot.child("NAME").getValue();
                                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                                String lac_name = ""+ messageSnapshot.child("LAC").getValue();
                                                String number=""+messageSnapshot.child("ID").getValue();
                                                arrayList.add(new Member_lac_data(name, sector, lac_name,number));
                                                i++;
                                            }
                                        }
                                        else  if((radioGroup.getCheckedRadioButtonId()==-1)&&(!(checkBox.isChecked()))){
                                            String name = ""+ messageSnapshot.child("NAME").getValue();
                                            String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                            String lac_name = ""+ messageSnapshot.child("LAC").getValue();
                                            String number=""+messageSnapshot.child("ID").getValue();
                                            arrayList.add(new Member_lac_data(name, sector, lac_name,number));
                                            i++;
                                        } else  if((radioGroup.getCheckedRadioButtonId()==-1)&&((checkBox.isChecked()))){
                                            if ((!donor.equals(""))&&(!(donor.equals("null"))))
                                            {
                                                String name = ""+ messageSnapshot.child("NAME").getValue();
                                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                                String lac_name = ""+ messageSnapshot.child("LAC").getValue();
                                                String number=""+messageSnapshot.child("ID").getValue();
                                                arrayList.add(new Member_lac_data(name, sector, lac_name,number));
                                                i++;
                                            }
                                        }else  if((!(radioGroup.getCheckedRadioButtonId()==-1))&&(!(checkBox.isChecked()))){
                                            if (status.trim().equals(check_data))
                                            {
                                                String name = ""+ messageSnapshot.child("NAME").getValue();
                                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                                String lac_name = ""+ messageSnapshot.child("LAC").getValue();
                                                String number=""+messageSnapshot.child("ID").getValue();
                                                arrayList.add(new Member_lac_data(name, sector, lac_name,number));
                                                i++;
                                            }
                                        }
                                    }
                                    TextView count = (TextView) findViewById(R.id.count);
                                    count.setText(i + "");
                                    Member_Lac_Adapter adapter = new Member_Lac_Adapter(representative.this, arrayList);
                                    member_listview.setAdapter(adapter);
                                    pd.hide();
                                    //  registerForContextMenu(member_listview);
                                } else {
                                    Toast.makeText(representative.this, "There is no data available", Toast.LENGTH_SHORT).show();
                                    Member_Lac_Adapter adapter = new Member_Lac_Adapter(representative.this, arrayList);
                                    member_listview.setAdapter(adapter);
                                    pd.hide();
                                    registerForContextMenu(member_listview);
                                    TextView count = (TextView) findViewById(R.id.count);
                                    count.setText(i + "");
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } else if (sector.equals("ALL")) {
                        String[] strings = lac.split("-");
                        String new_lac = strings[1].trim();



                        databaseReference.child("MEMBERS").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                arrayList.clear();
                                int i = 0;
                                if (dataSnapshot.exists()) {

                                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                                        String status = ""+ messageSnapshot.child("STATUS").getValue();
                                        String donor= ""+ messageSnapshot.child("DONOR").getValue();
                                        if((!(radioGroup.getCheckedRadioButtonId()==-1))&&(checkBox.isChecked())){
                                            if (status.trim().equals(check_data)&&(!donor.equals(""))&&(!(donor.equals("null"))))
                                            {
                                                String name = ""+ messageSnapshot.child("NAME").getValue();
                                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                                String number=""+messageSnapshot.child("ID").getValue();
                                                arrayList.add(new Member_lac_data(name, sector, null,number));
                                                i++;
                                            }
                                        }
                                        else  if((radioGroup.getCheckedRadioButtonId()==-1)&&(!(checkBox.isChecked()))){
                                            String name = ""+ messageSnapshot.child("NAME").getValue();
                                            String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                            String number=""+messageSnapshot.child("ID").getValue();
                                            arrayList.add(new Member_lac_data(name, sector, null,number));
                                            i++;
                                        } else  if((radioGroup.getCheckedRadioButtonId()==-1)&&((checkBox.isChecked()))){
                                            if ((!donor.equals(""))&&(!(donor.equals("null"))))
                                            {
                                                String name = ""+ messageSnapshot.child("NAME").getValue();
                                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                                String number=""+messageSnapshot.child("ID").getValue();
                                                arrayList.add(new Member_lac_data(name, sector, null,number));
                                                i++;
                                            }
                                        }else  if((!(radioGroup.getCheckedRadioButtonId()==-1))&&(!(checkBox.isChecked()))){
                                            if (status.trim().equals(check_data))
                                            {
                                                String name = ""+ messageSnapshot.child("NAME").getValue();
                                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                                String number=""+messageSnapshot.child("ID").getValue();
                                                arrayList.add(new Member_lac_data(name, sector, null,number));
                                                i++;
                                            }
                                        }
                                    }
                                    TextView count = (TextView) findViewById(R.id.count);
                                    count.setText(i + "");
                                    Member_Lac_Adapter adapter = new Member_Lac_Adapter(representative.this, arrayList);
                                    member_listview.setAdapter(adapter);
                                    pd.hide();
                                    //  registerForContextMenu(member_listview);
                                } else {
                                    Toast.makeText(representative.this, "There is no data available", Toast.LENGTH_SHORT).show();
                                    Member_Lac_Adapter adapter = new Member_Lac_Adapter(representative.this, arrayList);
                                    member_listview.setAdapter(adapter);
                                    pd.hide();
                                    registerForContextMenu(member_listview);
                                    TextView count = (TextView) findViewById(R.id.count);
                                    count.setText(i + "");
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    } else if ((!(lac.equals("ALL"))) && (!(sector.equals("ALL")))) {
                        String[] strings = lac.split("-");
                        String new_lac = strings[1].trim();
                        final String new_sector = sector_spinner.getSelectedItem().toString();



                        databaseReference.child("MEMBERS").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                arrayList.clear();
                                int i = 0;
                                if (dataSnapshot.exists()) {
                               /* String name = ""+ messageSnapshot.child("NAME").getValue();
                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                if (sector.toUpperCase().equals(new_sector.toUpperCase())) {
                                    arrayList.add(new Member_lac_data(name, null, null,null));
                                    i++;
                                    */


                                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                                        String status = ""+ messageSnapshot.child("STATUS").getValue();
                                        String donor= ""+ messageSnapshot.child("DONOR").getValue();
                                        if((!(radioGroup.getCheckedRadioButtonId()==-1))&&(checkBox.isChecked())) {
                                            if (status.trim().equals(check_data) && (!donor.equals(""))&&(!(donor.equals("null")))) {
                                                String name = ""+ messageSnapshot.child("NAME").getValue();
                                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                                String number=""+messageSnapshot.child("ID").getValue();
                                                if (sector.toUpperCase().equals(new_sector.toUpperCase())) {
                                                    arrayList.add(new Member_lac_data(name, null, null, number));
                                                    i++;
                                                }
                                            }
                                        }
                                        else  if((radioGroup.getCheckedRadioButtonId()==-1)&&(!(checkBox.isChecked()))) {
                                            String name = ""+ messageSnapshot.child("NAME").getValue();
                                            String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                            String number=""+messageSnapshot.child("ID").getValue();
                                            if (sector.toUpperCase().equals(new_sector.toUpperCase())) {
                                                arrayList.add(new Member_lac_data(name, null, null, number));
                                                i++;
                                            }
                                        }else  if((radioGroup.getCheckedRadioButtonId()==-1)&&((checkBox.isChecked()))){
                                            if ((!donor.equals(""))&&(!(donor.equals("null")))) {
                                                String name = ""+ messageSnapshot.child("NAME").getValue();
                                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                                String number=""+messageSnapshot.child("ID").getValue();
                                                if (sector.toUpperCase().equals(new_sector.toUpperCase())) {
                                                    arrayList.add(new Member_lac_data(name, null, null, number));
                                                    i++;
                                                }
                                            }
                                        }else  if((!(radioGroup.getCheckedRadioButtonId()==-1))&&(!(checkBox.isChecked()))){
                                            if (status.trim().equals(check_data))
                                            {
                                                String name = ""+ messageSnapshot.child("NAME").getValue();
                                                String sector = ""+ messageSnapshot.child("SECTOR").getValue();
                                                String number=""+messageSnapshot.child("ID").getValue();
                                                if (sector.toUpperCase().equals(new_sector.toUpperCase())) {
                                                    arrayList.add(new Member_lac_data(name, null, null,number));
                                                    i++;
                                                }
                                            }
                                        }
                                    }
                                    TextView count = (TextView) findViewById(R.id.count);
                                    count.setText(i + "");
                                    Member_Lac_Adapter adapter = new Member_Lac_Adapter(representative.this, arrayList);
                                    member_listview.setAdapter(adapter);
                                    pd.hide();
                                } else {
                                    Toast.makeText(representative.this, "There is no data available", Toast.LENGTH_SHORT).show();
                                    Member_Lac_Adapter adapter = new Member_Lac_Adapter(representative.this, arrayList);
                                    member_listview.setAdapter(adapter);
                                    pd.hide();
                                    TextView count = (TextView) findViewById(R.id.count);
                                    count.setText(i + "");
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    };
                }
            }

        });

    }
    public void RadioButtonClick(View v) {
        boolean checked = ((RadioButton) v).isChecked();

        // Check which radio button was clicked
        switch (v.getId()) {
            case R.id.aam_aadmi:
                if (checked)
                    check_data = "AAM AADMI";
                break;
            case R.id.member:
                if (checked)
                    check_data = "MEMBER";
                break;
            case R.id.volunteer:
                if (checked)
                    check_data = "VOLUNTEER";
                break;
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
        startActivity(new Intent(representative.this, Lookover.class));
        super.onBackPressed();
    }
}