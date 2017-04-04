package org.aapkerala.broomRevolution.db;

import android.provider.BaseColumns;

/**
 * Created by New on 18-10-2016.
 */
public class TaskContract {
    public static final String DB_NAME = "com.aap.aapapp.db";
    public static final int DB_VERSION = 11;
    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "callers";
        public static final String TABLE1 = "ground";
        public static final String NAME = "name";
        public static final String STATUS= "status";
        public static final String DISTRICT = "district";
        public static final String PC = "pc";
        public static final String LAC = "lac";
        public static final String SECTOR = "sector";
        public static final String PINCODE = "pincode";
        public static final String WARD = "ward";
        public static final String BOOTH = "booth";
        public static final String GENDER = "gender";
        public static final String ID = "id";
             public static final String NUMBER="phone";
    }
}
