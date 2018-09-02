package com.fehr.nanodegree.habittracker.data;

import android.provider.BaseColumns;


public class HabitContract {

    public static final class HabitEntry implements BaseColumns {
        public final static String TABLE_NAME = "HabitTracker";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "Habit_Name";
        public final static String COLUMN_FREQUENCY = "Frequency";

        public final static int FREQUENCY_RANDOM = 0;
        public final static int FREQUENCY_DAILY = 1;
        public final static int FREQUENCY_WEEKLY = 2;
        public final static int FREQUENCY_MONTHLY = 3;
        public final static int FREQUENCY_ANNUAL = 4;
    }
}
