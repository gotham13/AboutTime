package org.aapkerala.broomRevolution;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import org.aapkerala.broomRevolution.R;

public class Lookover extends AppCompatActivity {
    SharedPreferences pref;
    Button button;
    RadioGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lookover);
        button= (Button) findViewById(R.id.move);
        group= (RadioGroup) findViewById(R.id.group);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = group.getCheckedRadioButtonId();
                if (id != 0) {
                    switch (id) {
                        case R.id.observers_view:
                            startActivity(new Intent(Lookover.this, observers.class));
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putBoolean("loggedin", true);
                            editor.putString("as", "observer");
                            editor.apply();
                            finish();
                            break;
                        case R.id.look_up:
                            startActivity(new Intent(Lookover.this, representative.class));
                            SharedPreferences.Editor editor1 = pref.edit();
                            editor1.putBoolean("loggedin", true);
                            editor1.putString("as", "look_up");
                            editor1.apply();
                            finish();
                            break;

                    }
                }

            }
        });
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
                editor.putBoolean("loggedin", false);
                editor.apply();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}