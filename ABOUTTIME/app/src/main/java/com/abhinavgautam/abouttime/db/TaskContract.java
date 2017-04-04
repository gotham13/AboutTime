package com.abhinavgautam.abouttime.db;

import android.provider.BaseColumns;

/**
 * Created by New on 18-10-2016.
 */
public class TaskContract {
    public static final String DB_NAME = "com.abhinavgautam.abouttime.db";
    public static final int DB_VERSION = 17;
    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";
        public static final String COL_TASK_TITLE = "title";
        public static final String COL_DATE="date1";
        public static final String TABLE0="sunday";
        public static final String TABLE1="monday";
        public static final String TABLE2="tuesday";
        public static final String TABLE3="wednesday";
        public static final String TABLE4="thursday";
        public static final String TABLEN="notification";
        public static final String COL_NOTI="noti";
        public static final String TABLE5="friday";
        public static final String TABLE6="saturday";
        public static final String COL_ROUTINE="routine";
        public static final String COL_INITIME="initime";
    }
}
