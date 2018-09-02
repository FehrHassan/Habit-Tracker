package com.fehr.nanodegree.habittracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fehr.nanodegree.habittracker.data.HabitContract.HabitEntry;
import com.fehr.nanodegree.habittracker.data.HabitDbHelper;


public class MainActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new HabitDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabase();
    }

    private Cursor read() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_NAME,
                HabitEntry.COLUMN_FREQUENCY
        };

        Cursor cursor = db.query(HabitEntry.TABLE_NAME, projection, null, null, null, null, null);
        return cursor;
    }

    private void displayDatabase() {
        Cursor cursor = read();

        TextView displayView = (TextView) findViewById(R.id.text_view_habit);

        try {
            displayView.setText("The habit table contains " + cursor.getCount() + " pets.\n\n");
            displayView.append(HabitEntry._ID + " \t " +
                    HabitEntry.COLUMN_NAME + " \t " +
                    HabitEntry.COLUMN_FREQUENCY);

            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_NAME);
            int frequencyColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_FREQUENCY);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentFrequency = cursor.getInt(frequencyColumnIndex);

                String stringCurrentFrequency;

                switch (currentFrequency) {
                    case 0:
                        stringCurrentFrequency = getString(R.string.random);
                        break;
                    case 1:
                        stringCurrentFrequency = getString(R.string.daily);
                        break;
                    case 2:
                        stringCurrentFrequency = getString(R.string.weekly);
                        break;
                    case 3:
                        stringCurrentFrequency = getString(R.string.monthly);
                        break;
                    case 4:
                        stringCurrentFrequency = getString(R.string.annual);
                        break;
                    default:
                        stringCurrentFrequency = getString(R.string.random);
                        break;
                }

                displayView.append(("\n" + currentID + " \t     " +
                        currentName + " \t          " +
                        stringCurrentFrequency));
            }
        } finally {
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    private void insert() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_NAME, "Running");
        values.put(HabitEntry.COLUMN_FREQUENCY, HabitEntry.FREQUENCY_DAILY);
        db.insert(HabitEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insert();
                displayDatabase();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
