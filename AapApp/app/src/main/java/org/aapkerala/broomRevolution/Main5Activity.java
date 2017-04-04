package org.aapkerala.broomRevolution;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.aapkerala.broomRevolution.R;

public class Main5Activity extends AppCompatActivity {
     private EditText house,street,nov,vid,gender,age,col,sem,memno;
    private TextView col1,sem1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_large);
        house=(EditText)findViewById(R.id.editText4);
        street=(EditText)findViewById(R.id.editText5);
        nov=(EditText)findViewById(R.id.editText6);
        vid=(EditText)findViewById(R.id.editText7);
        gender=(EditText)findViewById(R.id.editText8);
        age=(EditText)findViewById(R.id.editText9);
        col=(EditText)findViewById(R.id.editText10);
        sem=(EditText)findViewById(R.id.editText11);
        memno=(EditText)findViewById(R.id.editText12);
        col1=(TextView) findViewById(R.id.textView17);
        sem1=(TextView) findViewById(R.id.textView18);
    }
}
