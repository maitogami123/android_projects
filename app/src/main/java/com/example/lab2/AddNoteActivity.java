package com.example.lab2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.Date;

public class AddNoteActivity extends Activity {

    private static final int SEVEN_DAYS = 604800000;

    private static final String TAG = "MultiNotes";
    private static int EDIT_STATE = 0;

    private EditText mTitleText;
    private EditText mDescriptionText;
    private int mPos ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_form);
        mTitleText = findViewById(R.id.editTxtTitle);
        mDescriptionText = findViewById(R.id.editTxtDesc);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                Log.e(TAG, key + " : " + (bundle.get(key) != null ? bundle.get(key) : "NULL"));
            }
        }
        Intent intent = getIntent();
        if (intent != null) {
            EDIT_STATE = 1;
            String title = intent.getStringExtra("title");
            String description = intent.getStringExtra("description");
            mPos = intent.getIntExtra("pos", 0);
            mTitleText.setText(title);
            mDescriptionText.setText(description);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save && EDIT_STATE == 0) {
            String titleString = null;
            titleString = mTitleText.getText().toString();
            String descString = null;
            descString = mDescriptionText.getText().toString();

            // Construct the Date string
            String fullDate = (new Date()).toString();

            // Package ToDoItem data into an Intent
            Intent data = new Intent();
            NoteItem.packageIntent(data, titleString,descString, fullDate);

            setResult(RESULT_OK, data);
            finish();
            return true;
        }
        else if (id == R.id.save && EDIT_STATE == 1) {
            String titleString = mTitleText.getText().toString();
            String descString = mDescriptionText.getText().toString();
            String fullDate = (new Date()).toString();
            Intent data = new Intent();
            NoteItem.packageIntent(data, titleString, descString, fullDate, mPos);
            setResult(RESULT_OK, data);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}