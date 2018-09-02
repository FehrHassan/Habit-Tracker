package com.fehr.nanodegree.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fehr.nanodegree.habittracker.data.HabitContract.HabitEntry;
import com.fehr.nanodegree.habittracker.data.HabitDbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EditActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_name)
    EditText mNameEditText;
    @BindView(R.id.spinner_frequency)
    Spinner mSpinner;
    private int frequency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);

        setupSpinner();
    }

    /**
     * Setup the dropdown spinner that allows the user to select the gender of the pet.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_items, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.random))) {
                        frequency = HabitEntry.FREQUENCY_RANDOM;
                    } else if (selection.equals(getString(R.string.daily))) {
                        frequency = HabitEntry.FREQUENCY_DAILY;
                    } else if (selection.equals(getString(R.string.weekly))) {
                        frequency = HabitEntry.FREQUENCY_WEEKLY;
                    } else if (selection.equals(getString(R.string.monthly))) {
                        frequency = HabitEntry.FREQUENCY_MONTHLY;
                    } else if (selection.equals(getString(R.string.annual))) {
                        frequency = HabitEntry.FREQUENCY_ANNUAL;
                    } else {
                        frequency = HabitEntry.FREQUENCY_RANDOM;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                frequency = HabitEntry.FREQUENCY_RANDOM;
            }
        });
    }

    private void insertHabit() {

        String nameString = mNameEditText.getText().toString().trim();

        HabitDbHelper mHabitDbHelper = new HabitDbHelper(this);

        SQLiteDatabase db = mHabitDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_NAME, nameString);
        values.put(HabitEntry.COLUMN_FREQUENCY, frequency);

        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving habit", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Habit saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    public void onSave(View view) {
        if ("".equals(mNameEditText.getText().toString())) {
            Toast.makeText(this, "Habit Name must be not empty", Toast.LENGTH_SHORT).show();
            return;
        }
        insertHabit();
        finish();
    }

    public void onCancel(View view) {
        finish();
    }
}
