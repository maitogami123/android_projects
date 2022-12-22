package com.example.lab2;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class NoteActivity extends AppCompatActivity {
    private static final int ADD_NOTE_ITEM_REQUEST = 0;
    private static final int EDIT_NOTE_ITEM_REQUEST = 1;
    private static final String FILE_NAME = "NoteManagerActivityData.txt";
    private static final String TAG = "MultiNotes";

    private ArrayList<NoteItem> mNotes ;
    private RecyclerView mRecyclerNote;
    private NoteAdapter mNoteAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerNote = findViewById(R.id.recyclerNote);
        mNotes = new ArrayList<>();
        loadItems();
        mNoteAdapter = new NoteAdapter(this, mNotes);
        mRecyclerNote.setAdapter(mNoteAdapter);
        mRecyclerNote.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.info) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Do you want to visit my github profile?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent baseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/maitogami123"));
                            startActivity(baseIntent);
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder1.show();
            return true;
        } else if (id == R.id.addNote) {
            Intent explicitIntent = new Intent(NoteActivity.this, AddNoteActivity.class);
            startActivityForResult(explicitIntent, ADD_NOTE_ITEM_REQUEST);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_ITEM_REQUEST && resultCode == -1) {
            NoteItem newItem = new NoteItem(data);
            mNoteAdapter.add(newItem);
        } else if (requestCode == EDIT_NOTE_ITEM_REQUEST && resultCode == -1 ) {
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            String date = data.getStringExtra("date");
            int pos = data.getIntExtra("pos", 0);
            NoteItem newItem = new NoteItem(title, description, new Date(date));
            mNoteAdapter.edit(pos, newItem);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mNoteAdapter.getItemCount() == 0)
            loadItems();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveItems();
    }

    private void loadItems() {
        BufferedReader reader = null;
        try {
            FileInputStream fis = openFileInput(FILE_NAME);
            reader = new BufferedReader(new InputStreamReader(fis));

            String title = null;
            String description = null;
            Date date = null;

            while (null != (title = reader.readLine())) {
                description = reader.readLine();
                date = NoteItem.FORMAT.parse(reader.readLine());
                mNotes.add(new NoteItem(title, description, date));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void saveItems() {
        PrintWriter writer = null;
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos)));

            for (int idx = 0; idx < mNoteAdapter.getItemCount(); idx++) {
                writer.println(mNoteAdapter.getItem(idx));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }


}
