package org.aapkerala.broomRevolution;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by New on 19-12-2016.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
