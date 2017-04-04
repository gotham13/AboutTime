
        package org.aapkerala.broomRevolution;

        import android.app.ProgressDialog;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Bitmap;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.ContextMenu;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
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

        import static android.graphics.Paint.ANTI_ALIAS_FLAG;

        public class Lac_database extends AppCompatActivity {
    ListView member_new_listview;
            FloatingActionButton fab;
    DatabaseReference databaseReference;
    Spinner SECTOR;
    RadioGroup radioGroup;
    RadioButton radioButton;
    String get_name = null;

    private SharedPreferences pref;
    String l_n;
    final ArrayList<Member_lac_data> arrayList = new ArrayList();
    String new_sector;
    CheckBox checkBox;
    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lac_database);
        fab= (FloatingActionButton) findViewById(R.id.fab);
        radioGroup = (RadioGroup) findViewById(R.id.check);
        member_new_listview = (ListView) findViewById(R.id.member_lac_listview);
        SECTOR = (Spinner) findViewById(R.id.spinner_sector);
        Button Button_show = (Button) findViewById(R.id.Button);
        Bundle lac_obs_name = getIntent().getExtras();
        Bundle pc_name = getIntent().getExtras();
        Bundle lac_name = getIntent().getExtras();
        String l_o_n = lac_obs_name.getString("name");
        String p_n = pc_name.getString("pc_name");
        l_n = lac_name.getString("lac_name");
        checkBox= (CheckBox) findViewById(R.id.donor);

        TextView lon = (TextView) findViewById(R.id.lac_obs_name);
        lon.setText(l_o_n);
        TextView pcn = (TextView) findViewById(R.id.pc_name);
        pcn.setText(p_n);
        TextView lacn = (TextView) findViewById(R.id.lac_name);
        lacn.setText(l_n);
        String[] strings = l_n.split("-");
        int lac_number = Integer.parseInt(strings[0].trim());
        ArrayAdapter sector_adapter = null;
        switch (lac_number) {
            case 1:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Manjeshwar, android.R.layout.simple_spinner_item);
                break;
            case 2:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kasaragod, android.R.layout.simple_spinner_item);
                break;
            case 3:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Udma, android.R.layout.simple_spinner_item);
                break;
            case 4:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kanhangad, android.R.layout.simple_spinner_item);
                break;
            case 5:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Trikaripur, android.R.layout.simple_spinner_item);
                break;
            case 6:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Payyannur, android.R.layout.simple_spinner_item);
                break;
            case 7:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kalliasseri, android.R.layout.simple_spinner_item);
                break;
            case 8:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Taliparamba, android.R.layout.simple_spinner_item);
                break;
            case 9:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Irikkur, android.R.layout.simple_spinner_item);
                break;
            case 10:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Azhikode, android.R.layout.simple_spinner_item);
                break;
            case 11:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kannur, android.R.layout.simple_spinner_item);
                break;
            case 12:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Dharmadam, android.R.layout.simple_spinner_item);
                break;
            case 13:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Thalassery, android.R.layout.simple_spinner_item);
                break;
            case 14:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kuthuparamba, android.R.layout.simple_spinner_item);
                break;
            case 15:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Mattannur, android.R.layout.simple_spinner_item);
                break;
            case 16:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Peravoor, android.R.layout.simple_spinner_item);
                break;
            case 17:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Mananthavady, android.R.layout.simple_spinner_item);
                break;
            case 18:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Sulthanbathery, android.R.layout.simple_spinner_item);
                break;
            case 19:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kalpetta, android.R.layout.simple_spinner_item);
                break;
            case 20:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Vadakara, android.R.layout.simple_spinner_item);
                break;
            case 21:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kuttiadi, android.R.layout.simple_spinner_item);
                break;
            case 22:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Nadapuram, android.R.layout.simple_spinner_item);
                break;
            case 23:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Quilandy, android.R.layout.simple_spinner_item);
                break;
            case 24:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Perambra, android.R.layout.simple_spinner_item);
                break;
            case 25:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Balusseri, android.R.layout.simple_spinner_item);
                break;
            case 26:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Elathur, android.R.layout.simple_spinner_item);
                break;
            case 27:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kozhikode_North, android.R.layout.simple_spinner_item);
                break;
            case 28:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kozhikode_South, android.R.layout.simple_spinner_item);
                break;
            case 29:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Beypore, android.R.layout.simple_spinner_item);
                break;
            case 30:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kunnamangalam, android.R.layout.simple_spinner_item);
                break;
            case 31:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Koduvally, android.R.layout.simple_spinner_item);
                break;
            case 32:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Thiruvambadi, android.R.layout.simple_spinner_item);
                break;
            case 33:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kondotty, android.R.layout.simple_spinner_item);
                break;
            case 34:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Ernad, android.R.layout.simple_spinner_item);
                break;
            case 35:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Nilambur, android.R.layout.simple_spinner_item);
                break;
            case 36:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Wandoor, android.R.layout.simple_spinner_item);
                break;
            case 37:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Manjeri, android.R.layout.simple_spinner_item);
                break;
            case 38:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Perinthalmanna, android.R.layout.simple_spinner_item);
                break;
            case 39:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Mankada, android.R.layout.simple_spinner_item);
                break;
            case 40:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Malappuram, android.R.layout.simple_spinner_item);
                break;
            case 41:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Vengara, android.R.layout.simple_spinner_item);
                break;
            case 42:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Vallikkunnu, android.R.layout.simple_spinner_item);
                break;
            case 43:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Tirurangadi, android.R.layout.simple_spinner_item);
                break;
            case 44:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Tanur, android.R.layout.simple_spinner_item);
                break;
            case 45:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Tirur, android.R.layout.simple_spinner_item);
                break;
            case 46:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kottakkal, android.R.layout.simple_spinner_item);
                break;
            case 47:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Thavanur, android.R.layout.simple_spinner_item);
                break;
            case 48:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Ponnani, android.R.layout.simple_spinner_item);
                break;
            case 49:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Thrithala, android.R.layout.simple_spinner_item);
                break;
            case 50:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Pattambi, android.R.layout.simple_spinner_item);
                break;
            case 51:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Shoranur, android.R.layout.simple_spinner_item);
                break;
            case 52:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Ottappalam, android.R.layout.simple_spinner_item);
                break;
            case 53:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kongad, android.R.layout.simple_spinner_item);
                break;
            case 54:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Mannarkkad, android.R.layout.simple_spinner_item);
                break;
            case 55:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Malampuzha, android.R.layout.simple_spinner_item);
                break;
            case 56:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Palakkad, android.R.layout.simple_spinner_item);
                break;
            case 57:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Tarur, android.R.layout.simple_spinner_item);
                break;
            case 58:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Chittur, android.R.layout.simple_spinner_item);
                break;
            case 59:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Nemmara, android.R.layout.simple_spinner_item);
                break;
            case 60:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Alathur, android.R.layout.simple_spinner_item);
                break;
            case 61:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Chelakkara, android.R.layout.simple_spinner_item);
                break;
            case 62:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kunnamkulam, android.R.layout.simple_spinner_item);
                break;
            case 63:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Guruvayoor, android.R.layout.simple_spinner_item);
                break;
            case 64:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Manalur, android.R.layout.simple_spinner_item);
                break;
            case 65:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Wadakkanchery, android.R.layout.simple_spinner_item);
                break;
            case 66:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Ollur, android.R.layout.simple_spinner_item);
                break;
            case 67:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Thrissur, android.R.layout.simple_spinner_item);
                break;
            case 68:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Nattika, android.R.layout.simple_spinner_item);
                break;
            case 69:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kaipamangalam, android.R.layout.simple_spinner_item);
                break;
            case 70:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Irinjalakuda, android.R.layout.simple_spinner_item);
                break;
            case 71:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Pudukkad, android.R.layout.simple_spinner_item);
                break;
            case 72:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Chalakudy2, android.R.layout.simple_spinner_item);
                break;
            case 73:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kodungallur, android.R.layout.simple_spinner_item);
                break;
            case 74:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Perumbavoor, android.R.layout.simple_spinner_item);
                break;
            case 75:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Angamaly, android.R.layout.simple_spinner_item);
                break;
            case 76:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Aluva, android.R.layout.simple_spinner_item);
                break;
            case 77:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kalamassery, android.R.layout.simple_spinner_item);
                break;
            case 78:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Paravur, android.R.layout.simple_spinner_item);
                break;
            case 79:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Vypeen, android.R.layout.simple_spinner_item);
                break;
            case 80:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kochi, android.R.layout.simple_spinner_item);
                break;
            case 81:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Tripunithura, android.R.layout.simple_spinner_item);
                break;
            case 82:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Ernakulam, android.R.layout.simple_spinner_item);
                break;
            case 83:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Thrikkakara, android.R.layout.simple_spinner_item);
                break;
            case 84:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kunnathunad, android.R.layout.simple_spinner_item);
                break;
            case 85:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Piravom, android.R.layout.simple_spinner_item);
                break;
            case 86:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Muvattupuzha, android.R.layout.simple_spinner_item);
                break;
            case 87:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kothamangalam, android.R.layout.simple_spinner_item);
                break;
            case 88:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Devikulam, android.R.layout.simple_spinner_item);
                break;
            case 89:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Udumbanchola, android.R.layout.simple_spinner_item);
                break;
            case 90:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Thodupuzha, android.R.layout.simple_spinner_item);
                break;
            case 91:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Idukki, android.R.layout.simple_spinner_item);
                break;
            case 92:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Peerumade, android.R.layout.simple_spinner_item);
                break;
            case 93:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Pala, android.R.layout.simple_spinner_item);
                break;
            case 94:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kaduthuruthy, android.R.layout.simple_spinner_item);
                break;
            case 95:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Vaikom, android.R.layout.simple_spinner_item);
                break;
            case 96:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Ettumanoor, android.R.layout.simple_spinner_item);
                break;
            case 97:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kottayam, android.R.layout.simple_spinner_item);
                break;
            case 98:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Puthuppally, android.R.layout.simple_spinner_item);
                break;
            case 99:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Changanassery, android.R.layout.simple_spinner_item);
                break;
            case 100:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kanjirappally, android.R.layout.simple_spinner_item);
                break;
            case 101:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Poonjar, android.R.layout.simple_spinner_item);
                break;
            case 102:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Aroor, android.R.layout.simple_spinner_item);
                break;
            case 103:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Cherthala, android.R.layout.simple_spinner_item);
                break;
            case 104:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Alappuzha, android.R.layout.simple_spinner_item);
                break;
            case 105:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Ambalappuzha, android.R.layout.simple_spinner_item);
                break;
            case 106:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kuttanad, android.R.layout.simple_spinner_item);
                break;
            case 107:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Haripad, android.R.layout.simple_spinner_item);
                break;
            case 108:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kayamkulam, android.R.layout.simple_spinner_item);
                break;
            case 109:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Mavelikkara, android.R.layout.simple_spinner_item);
                break;
            case 110:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Chengannur, android.R.layout.simple_spinner_item);
                break;
            case 111:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Thiruvalla, android.R.layout.simple_spinner_item);
                break;
            case 112:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Ranni, android.R.layout.simple_spinner_item);
                break;
            case 113:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Aranmula, android.R.layout.simple_spinner_item);
                break;
            case 114:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Konni, android.R.layout.simple_spinner_item);
                break;
            case 115:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Adoor, android.R.layout.simple_spinner_item);
                break;
            case 116:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Karunagappally, android.R.layout.simple_spinner_item);
                break;
            case 117:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Chavara, android.R.layout.simple_spinner_item);
                break;
            case 118:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kunnathur, android.R.layout.simple_spinner_item);
                break;
            case 119:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kottarakkara, android.R.layout.simple_spinner_item);
                break;
            case 120:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Pathanapuram, android.R.layout.simple_spinner_item);
                break;
            case 121:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Punalur, android.R.layout.simple_spinner_item);
                break;
            case 122:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Chadayamangalam, android.R.layout.simple_spinner_item);
                break;
            case 123:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kundara, android.R.layout.simple_spinner_item);
                break;
            case 124:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kollam, android.R.layout.simple_spinner_item);
                break;
            case 125:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Eravipuram, android.R.layout.simple_spinner_item);
                break;
            case 126:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Chathannoor, android.R.layout.simple_spinner_item);
                break;
            case 127:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Varkala, android.R.layout.simple_spinner_item);
                break;
            case 128:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Attingal, android.R.layout.simple_spinner_item);
                break;
            case 129:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Chirayinkeezhu, android.R.layout.simple_spinner_item);
                break;
            case 130:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Nedumangad, android.R.layout.simple_spinner_item);
                break;
            case 131:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Vamanapuram, android.R.layout.simple_spinner_item);
                break;
            case 132:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kazhakoottam, android.R.layout.simple_spinner_item);
                break;
            case 133:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Vattiyoorkavu, android.R.layout.simple_spinner_item);
                break;
            case 134:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Thiruvananthapuram, android.R.layout.simple_spinner_item);
                break;
            case 135:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Nemom, android.R.layout.simple_spinner_item);
                break;
            case 136:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Aruvikkara, android.R.layout.simple_spinner_item);
                break;
            case 137:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Parassala, android.R.layout.simple_spinner_item);
                break;
            case 138:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kattakkada, android.R.layout.simple_spinner_item);
                break;
            case 139:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Kovalam, android.R.layout.simple_spinner_item);
                break;
            case 140:
                sector_adapter = ArrayAdapter.createFromResource(this, R.array.Neyyattinkara, android.R.layout.simple_spinner_item);
                break;
            default:
                break;


        }
        SECTOR.setAdapter(sector_adapter);
        sector_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        Button_show.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                final ProgressDialog pd = new ProgressDialog(Lac_database.this);
                pd.setMessage("loading");
                pd.setCanceledOnTouchOutside(false);
                pd.show();
                new_sector = SECTOR.getSelectedItem().toString();

                if (!(new_sector.trim().equals("All"))) {

                    Query query = databaseReference.child("MEMBERS").orderByChild("SECTOR").equalTo(new_sector.toUpperCase());
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            arrayList.clear();
                            int i = 0;
                            if (dataSnapshot.exists()) {


                                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                                    String status = (String) messageSnapshot.child("STATUS").getValue();
                                    String donor= (String) messageSnapshot.child("DONOR").getValue();
                                    if ((!(radioGroup.getCheckedRadioButtonId() == -1))&&(checkBox.isChecked())) {
                                        if (status.trim().equals(get_name)&&(((!donor.equals(""))&&(!(donor.equals("null")))))) {
                                            String name = (String) messageSnapshot.child("NAME").getValue();
                                            String sector = (String) messageSnapshot.child("SECTOR").getValue();
                                            String lac_name = (String) messageSnapshot.child("LAC").getValue();
                                            String id = messageSnapshot.child("ID").getValue() + "";
                                            arrayList.add(new Member_lac_data(name, null, null, id.trim()));
                                            i++;
                                        }
                                    } else if ((radioGroup.getCheckedRadioButtonId() == -1)&& (!(checkBox.isChecked()))) {
                                        String name = (String) messageSnapshot.child("NAME").getValue();
                                        String sector = (String) messageSnapshot.child("SECTOR").getValue();
                                        String lac_name = (String) messageSnapshot.child("LAC").getValue();
                                        String id = messageSnapshot.child("ID").getValue() + "";
                                        arrayList.add(new Member_lac_data(name, null, null, id.trim()));
                                        i++;
                                    }
                                    else  if ((radioGroup.getCheckedRadioButtonId() == -1)&&(checkBox.isChecked())){
                                        if (((!donor.equals(""))&&(!(donor.equals("null"))))) {
                                            String name = (String) messageSnapshot.child("NAME").getValue();
                                            String sector = (String) messageSnapshot.child("SECTOR").getValue();
                                            String lac_name = (String) messageSnapshot.child("LAC").getValue();
                                            String id = messageSnapshot.child("ID").getValue() + "";
                                            arrayList.add(new Member_lac_data(name, null, null, id.trim()));
                                            i++;
                                        }
                                    }
                                    else  if (!(radioGroup.getCheckedRadioButtonId() == -1)&&(!(checkBox.isChecked()))){
                                        if (status.trim().equals(get_name)) {
                                            String name = (String) messageSnapshot.child("NAME").getValue();
                                            String sector = (String) messageSnapshot.child("SECTOR").getValue();
                                            String lac_name = (String) messageSnapshot.child("LAC").getValue();
                                            String id = messageSnapshot.child("ID").getValue() + "";
                                            arrayList.add(new Member_lac_data(name, null, null, id.trim()));
                                            i++;
                                        }
                                    }

                                }
                                TextView count = (TextView) findViewById(R.id.count);
                                count.setText(i + "");
                                member_new_listview = (ListView) findViewById(R.id.member_lac_listview);
                                Member_Lac_Adapter adapter = new Member_Lac_Adapter(Lac_database.this, arrayList);
                                member_new_listview.setAdapter(adapter);
                                registerForContextMenu(member_new_listview);
                                pd.hide();
                            } else {
                                Toast.makeText(Lac_database.this, "There is no data available", Toast.LENGTH_SHORT).show();
                                member_new_listview = (ListView) findViewById(R.id.member_lac_listview);
                                Member_Lac_Adapter adapter = new Member_Lac_Adapter(Lac_database.this, arrayList);
                                member_new_listview.setAdapter(adapter);
                                TextView count = (TextView) findViewById(R.id.count);
                                count.setText(i + "");
                                pd.hide();
                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else if (new_sector.equals("All")) {
                    String[] strings = l_n.split("-");
                    String new_lac = strings[1].trim();
                    Log.w("here", new_lac);


                    Query query = databaseReference.child("MEMBERS").orderByChild("LAC").equalTo(new_lac.toUpperCase());
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.w("here i am see me", dataSnapshot + "");
                            arrayList.clear();
                            int i = 0;
                            if (dataSnapshot.exists()) {
                                if (new_sector.equals("All")) {
                                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                                        String status = (String) messageSnapshot.child("STATUS").getValue();
                                        String donor= (String) messageSnapshot.child("DONOR").getValue();
                                        if ((!(radioGroup.getCheckedRadioButtonId() == -1))&&(checkBox.isChecked())) {
                                            if (status.trim().equals(get_name)&&(((!donor.equals(""))&&(!(donor.equals("null")))))) {
                                                String name = (String) messageSnapshot.child("NAME").getValue();
                                                String sector = (String) messageSnapshot.child("SECTOR").getValue();
                                                String id = messageSnapshot.child("ID").getValue() + "";
                                                arrayList.add(new Member_lac_data(name, sector, null, id.trim()));
                                                i++;
                                            }
                                        } else if ((radioGroup.getCheckedRadioButtonId() == -1)&&(!(checkBox.isChecked()))) {
                                            String name = (String) messageSnapshot.child("NAME").getValue();
                                            String sector = (String) messageSnapshot.child("SECTOR").getValue();
                                            String id = messageSnapshot.child("ID").getValue() + "";
                                            arrayList.add(new Member_lac_data(name, sector, null, id.trim()));
                                            i++;
                                        }else if ((radioGroup.getCheckedRadioButtonId() == -1)&&((checkBox.isChecked()))){
                                            if(((!donor.equals(""))&&(!(donor.equals("null"))))){
                                                String name = (String) messageSnapshot.child("NAME").getValue();
                                                String sector = (String) messageSnapshot.child("SECTOR").getValue();
                                                String id = messageSnapshot.child("ID").getValue() + "";
                                                arrayList.add(new Member_lac_data(name, sector, null, id.trim()));
                                                i++;
                                            }

                                        }else if (!(radioGroup.getCheckedRadioButtonId() == -1)&&(!(checkBox.isChecked()))){
                                            if (status.trim().equals(get_name)){
                                                String name = (String) messageSnapshot.child("NAME").getValue();
                                                String sector = (String) messageSnapshot.child("SECTOR").getValue();
                                                String id = messageSnapshot.child("ID").getValue() + "";
                                                arrayList.add(new Member_lac_data(name, sector, null, id.trim()));
                                                i++;
                                            }

                                        }
                                    }
                                    Log.w("holla", arrayList.toString());

                                    TextView count = (TextView) findViewById(R.id.count);
                                    count.setText(i + "");
                                    member_new_listview = (ListView) findViewById(R.id.member_lac_listview);
                                    Member_Lac_Adapter adapter = new Member_Lac_Adapter(Lac_database.this, arrayList);
                                    member_new_listview.setAdapter(adapter);
                                    registerForContextMenu(member_new_listview);
                                    pd.hide();

                                }
                                new_sector = SECTOR.getSelectedItem().toString();
                                if (!(new_sector.trim().equals("All"))) {

                                    Query query = databaseReference.child("MEMBERS").orderByChild("SECTOR").equalTo(new_sector.toUpperCase());
                                    query.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            arrayList.clear();
                                            int i = 0;
                                            if (dataSnapshot.exists()) {


                                                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                                                    String status = (String) messageSnapshot.child("STATUS").getValue();
                                                    String donor= (String) messageSnapshot.child("DONOR").getValue();
                                                    if ((!(radioGroup.getCheckedRadioButtonId() == -1))&&(checkBox.isChecked())) {
                                                        if (status.trim().equals(get_name)&&(((!donor.equals(""))&&(!(donor.equals("null")))))) {
                                                            String name = (String) messageSnapshot.child("NAME").getValue();
                                                            String sector = (String) messageSnapshot.child("SECTOR").getValue();
                                                            String lac_name = (String) messageSnapshot.child("LAC").getValue();
                                                            String id = messageSnapshot.child("ID").getValue() + "";
                                                            arrayList.add(new Member_lac_data(name, null, null, id.trim()));
                                                            i++;
                                                        }
                                                    } else if ((radioGroup.getCheckedRadioButtonId() == -1)&& (!(checkBox.isChecked()))) {
                                                        String name = (String) messageSnapshot.child("NAME").getValue();
                                                        String sector = (String) messageSnapshot.child("SECTOR").getValue();
                                                        String lac_name = (String) messageSnapshot.child("LAC").getValue();
                                                        String id = messageSnapshot.child("ID").getValue() + "";
                                                        arrayList.add(new Member_lac_data(name, null, null, id.trim()));
                                                        i++;
                                                    }
                                                    else  if ((radioGroup.getCheckedRadioButtonId() == -1)&&(checkBox.isChecked())){
                                                        if (((!donor.equals(""))&&(!(donor.equals("null"))))) {
                                                            String name = (String) messageSnapshot.child("NAME").getValue();
                                                            String sector = (String) messageSnapshot.child("SECTOR").getValue();
                                                            String lac_name = (String) messageSnapshot.child("LAC").getValue();
                                                            String id = messageSnapshot.child("ID").getValue() + "";
                                                            arrayList.add(new Member_lac_data(name, null, null, id.trim()));
                                                            i++;
                                                        }
                                                    }
                                                    else  if (!(radioGroup.getCheckedRadioButtonId() == -1)&&(!(checkBox.isChecked()))){
                                                        if (status.trim().equals(get_name)) {
                                                            String name = (String) messageSnapshot.child("NAME").getValue();
                                                            String sector = (String) messageSnapshot.child("SECTOR").getValue();
                                                            String lac_name = (String) messageSnapshot.child("LAC").getValue();
                                                            String id = messageSnapshot.child("ID").getValue() + "";
                                                            arrayList.add(new Member_lac_data(name, null, null, id.trim()));
                                                            i++;
                                                        }
                                                    }


                                                }
                                                TextView count = (TextView) findViewById(R.id.count);
                                                count.setText(i + "");
                                                member_new_listview = (ListView) findViewById(R.id.member_lac_listview);
                                                Member_Lac_Adapter adapter = new Member_Lac_Adapter(Lac_database.this, arrayList);
                                                member_new_listview.setAdapter(adapter);
                                                registerForContextMenu(member_new_listview);
                                                pd.hide();
                                            } else {
                                                Toast.makeText(Lac_database.this, "There is no data available", Toast.LENGTH_SHORT).show();
                                                member_new_listview = (ListView) findViewById(R.id.member_lac_listview);
                                                Member_Lac_Adapter adapter = new Member_Lac_Adapter(Lac_database.this, arrayList);
                                                member_new_listview.setAdapter(adapter);
                                                TextView count = (TextView) findViewById(R.id.count);
                                                count.setText(i + "");
                                                pd.hide();
                                            }


                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }


                            }
                            else {
                                Toast.makeText(Lac_database.this, "There is no data available", Toast.LENGTH_SHORT).show();
                                member_new_listview = (ListView) findViewById(R.id.member_lac_listview);
                                Member_Lac_Adapter adapter = new Member_Lac_Adapter(Lac_database.this, arrayList);
                                member_new_listview.setAdapter(adapter);
                                TextView count = (TextView) findViewById(R.id.count);
                                count.setText(i + "");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                    });
                }
            }

        });
        member_new_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView= (TextView) view.findViewById(R.id.member_id);
                String phid=textView.getText().toString();
                Log.w("myapp",textView.getText().toString());
                Intent intent=new Intent(Lac_database.this,details.class);
                intent.putExtra("phid",Long.parseLong(phid));
                startActivity(intent);

            }
        });
    }



    String member_id;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.member_lac_listview) {
            ListView list = (ListView) v;
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            int position = info.position;
            Member_lac_data new_data = (Member_lac_data) list.getItemAtPosition(position);

            member_id = new_data.getId();

            Log.e("yaha toh kuch hai", R.id.member_lac_listview + "");
            Log.e("yaha hai problem", member_id);

            menu.setHeaderTitle("MOVE TO LEVEL");
            if (get_name == null) {

            } else if (get_name.equals("MEMBER")) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.context_menu_member, menu);
            } else if (get_name.equals("VOLUNTEER")) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.context_menu_volunteer, menu);
            } else if (get_name.equals("AAM AADMI")) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.context_menu_aam, menu);
            }


        }
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (get_name.equals("AAM AADMI")) {

            databaseReference.child("MEMBERS").child(member_id).child("STATUS").setValue("MEMBER");
            Toast.makeText(this, "UPGRADED TO MEMBER", Toast.LENGTH_SHORT).show();
        }
        if (get_name.equals("MEMBER")) {
            switch (item.getItemId()) {
                case R.id.member_volunteer:
                    databaseReference.child("MEMBERS").child(member_id).child("STATUS").setValue("VOLUNTEER");
                    Toast.makeText(this, "UPGRADED TO VOLUNTEER", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.member_aam:
                    databaseReference.child("MEMBERS").child(member_id).child("STATUS").setValue("AAM AADMI");
                    Toast.makeText(this, "DOWNGRADED TO AAM AADMI", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        if (get_name.equals("VOLUNTEER")) {
            switch (item.getItemId()) {
                case R.id.volunteer_sector:
                    databaseReference.child("MEMBERS").child(member_id).child("STATUS").setValue("SECTOR OBSERVER");
                    Toast.makeText(this, "UPGRADED TO SECTOR OBSERVER", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.volunteer_member:
                    databaseReference.child("MEMBERS").child(member_id).child("STATUS").setValue("MEMBER");
                    Toast.makeText(this, "DOWNGRADED TO MEMBER", Toast.LENGTH_SHORT).show();
                    break;

            }
        }


        return true;
    }

    public void RadioButtonClick(View v) {
        boolean checked = ((RadioButton) v).isChecked();

        switch (v.getId()) {
            case R.id.aam_aadmi:
                if (checked)
                    get_name = "AAM AADMI";
                break;
            case R.id.member:
                if (checked)
                    get_name = "MEMBER";
                break;
            case R.id.volunteer:
                if (checked)
                    get_name = "VOLUNTEER";
                break;
            default:
                get_name = null;
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void sector_observer(View view) {
        Intent intent = new Intent(this, sector_observers.class);
        intent.putExtra("lac", l_n);
        startActivity(intent);
    }

    public void add(View view) {
        Intent intent=new Intent(this,Main4Activity.class);
        startActivity(intent);
    }
            @Override
            public void onResume(){
                super.onResume();
                fab.setImageBitmap(textAsBitmap("SEC", 40, Color.WHITE));

            }
}