package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.android.habittracker.data.HabitContract.HabitEntry;
import com.example.android.habittracker.data.HabitDbHelper;

import static com.example.android.habittracker.data.HabitContract.HabitEntry.TABLE_NAME;

public class MainActivity extends AppCompatActivity {
    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new HabitDbHelper(this);
        insertHabit();
        Cursor cursor = queryAllHabits();
        displayDatabaseInfo(cursor);
    }

    private void insertHabit() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HabitEntry.HABIT_NAME, "Jogging");
        values.put(HabitEntry.HABIT_DATE, "16.07");
        values.put(HabitEntry.HABIT_TIME, "13:37");
        // length displayed in minutes
        values.put(HabitEntry.HABIT_LENGTH, 35);
        values.put(HabitEntry.HABIT_REPETITIONS, 1);
        long newRowId = db.insert(TABLE_NAME, null, values);
        Log.v("MainActivity.java", "values = " + values);
    }

    private void displayDatabaseInfo(Cursor cursor) {
        try {
            // Display the number of rows in the Cursor (which reflects the number of rows in the
            // habit table in the database).
            TextView displayView = (TextView) findViewById(R.id.text_view_habit);
            displayView.setText("The habit table contains " + cursor.getCount() + " habits. \n\n");
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.HABIT_NAME + " - " +
                    HabitEntry.HABIT_DATE + " - " +
                    HabitEntry.HABIT_TIME + " - " +
                    HabitEntry.HABIT_LENGTH + " - " +
                    HabitEntry.HABIT_REPETITIONS + "\n");

            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.HABIT_NAME);
            int dateColumnIndex = cursor.getColumnIndex(HabitEntry.HABIT_DATE);
            int timeColumnIndex = cursor.getColumnIndex(HabitEntry.HABIT_TIME);
            int lengthColumnIndex = cursor.getColumnIndex(HabitEntry.HABIT_LENGTH);
            int repetitionColumnIndex = cursor.getColumnIndex(HabitEntry.HABIT_REPETITIONS);
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);
                int currentTime = cursor.getInt(timeColumnIndex);
                int currentLength = cursor.getInt(lengthColumnIndex);
                int currentRepetition = cursor.getInt(repetitionColumnIndex);
                displayView.append("\n" + currentID + " - " +
                        currentName + " - " +
                        currentDate + " - " +
                        currentTime + " - " +
                        currentLength + " - " +
                        currentRepetition);
            }
        } finally {
            // This releases all its resources and makes it invalid.
            cursor.close();
        }
    }

    // method returning Cursor
    public Cursor queryAllHabits() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.HABIT_NAME,
                HabitEntry.HABIT_DATE,
                HabitEntry.HABIT_TIME,
                HabitEntry.HABIT_LENGTH,
                HabitEntry.HABIT_REPETITIONS};
        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);
        return cursor;
    }
}
