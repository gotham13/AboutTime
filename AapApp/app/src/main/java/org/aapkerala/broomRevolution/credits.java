package org.aapkerala.broomRevolution;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.aapkerala.broomRevolution.R;

public class credits extends AppCompatActivity {
    private TextView t1,t2,t3,t4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        t1=(TextView)findViewById(R.id.credit);
        t2=(TextView)findViewById(R.id.name1);
        t3=(TextView)findViewById(R.id.name2);
        t4=(TextView)findViewById(R.id.name3);
        t1.setText("MADE WITH LOVE");
        t2.setText("SAMIR DAYAL SINGH");
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.facebook.com/samirdayalsingh.aman"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        t3.setText("MAYANK RAJ");
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://github.com/MayankRajSinha"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        t4.setText("ABHINAV GAUTAM");
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.facebook.com/abhinav.gautam.716"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }
}
