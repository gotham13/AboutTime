package org.aapkerala.broomRevolution;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.aapkerala.broomRevolution.R;

import org.aapkerala.broomRevolution.db.TaskContract;
import org.aapkerala.broomRevolution.db.TaskDbHelper;

public class Main2Activity extends AppCompatActivity {
    private TaskDbHelper mHelper;
    private SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        final EditText editText=(EditText)findViewById(R.id.editText3);
        Button button1=(Button)findViewById(R.id.button2);
        Button button2=(Button)findViewById(R.id.button3);
        mHelper = new TaskDbHelper(this);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String s= editText.getText().toString();
                s=s+"/";
                int len=s.length();
                int count=0;
                int totalcount=0;
                String num="";
                for(int i=0;i<len;i++)
                {
                    if(Character.isDigit(s.charAt(i)))
                    {
                        count++;
                        num=num+s.charAt(i);
                    }
                    else
                    {
                            if((count==12&&s.charAt(i-11)=='1'&&s.charAt(i-12)=='9')||(count==11&&s.charAt(i-11)=='0')||count==10)
                            {
                            totalcount++;
                            SQLiteDatabase db = mHelper.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            int l2=num.length();
                            int l1=l2-10;
                                num=num.substring(l1,l2);
                            values.put(TaskContract.TaskEntry.NUMBER,num);
                            db.insertWithOnConflict(TaskContract.TaskEntry.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
                            db.close();
                            Log.w("myapp", "" + num);
                        }
                        Log.w("myapp",""+count);
                        count=0;
                        num="";
                    }

                }
                Toast.makeText(Main2Activity.this, ""+totalcount+" numbers added", Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.secondry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sec_sign_out_menu:
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
}
