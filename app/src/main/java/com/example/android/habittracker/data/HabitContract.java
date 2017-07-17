package com.example.android.habittracker.data;

import android.provider.BaseColumns;

public final class HabitContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private
    private HabitContract() {
    }

    // Inner class defining table contents
    public static class HabitEntry implements BaseColumns {
        public static final String TABLE_NAME = "habits";
        public static final String _ID = BaseColumns._ID;
        public static final String HABIT_NAME = "name";
        public static final String HABIT_DATE = "date";
        public static final String HABIT_TIME = "time";
        public static final String HABIT_LENGTH = "length";
        public static final String HABIT_REPETITIONS = "repetitions";
    }
}
