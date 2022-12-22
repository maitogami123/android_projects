package com.example.lab2;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.Tag;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<NoteItem> mNotes;
    private static final int EDIT_NOTE_ITEM_REQUEST = 1;
    public final static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    public NoteAdapter(Context mContext, ArrayList<NoteItem> mNotes) {
        this.mContext = mContext;
        this.mNotes = mNotes;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View noteView;
        private TextView mNoteTitle;
        private TextView mNoteDesc;
        private TextView mNoteDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteView = itemView;
            mNoteDate = itemView.findViewById(R.id.noteDate);
            mNoteDesc = itemView.findViewById(R.id.noteDescription);
            mNoteTitle = itemView.findViewById(R.id.noteTitle);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View noteView = inflater.inflate(R.layout.note_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(noteView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NoteItem note = mNotes.get(position);
        holder.mNoteTitle.setText(note.getTitle());
        holder.mNoteDesc.setText(note.getDescription());
        holder.mNoteDate.setText(note.getDate().toString());
        holder.noteView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setTitle("Do you want to delete this note?");
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        delete(holder.getAdapterPosition());
//                        AlertDialog.Builder successAlert = new AlertDialog.Builder(mContext);
//                        successAlert.setTitle("Delete successfully!");
//                        successAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int whichButton) {
//                            }
//                        });
//                        successAlert.show();
                    }
                });
                alert.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        });
                alert.show();
                return true;
            }
        });

        holder.noteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editNote = new Intent(mContext, AddNoteActivity.class);
                editNote.putExtra("title", note.getTitle());
                editNote.putExtra("description", note.getDescription());
                editNote.putExtra("pos", holder.getAdapterPosition());
                ((Activity) mContext).startActivityForResult(editNote,EDIT_NOTE_ITEM_REQUEST);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public Object getItem(int pos) {
        return mNotes.get(pos);
    }

    public void delete(int pos) { mNotes.remove(pos); notifyDataSetChanged();}

    public void add(NoteItem item) {
        mNotes.add(item);
        notifyDataSetChanged();
    }

    public void edit(int pos, NoteItem item) {
        mNotes.set(pos, item);
        notifyDataSetChanged();
    }

}
