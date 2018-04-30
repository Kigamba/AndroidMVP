package com.kigamba.mvp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.kigamba.mvp.R;
import com.kigamba.mvp.persistence.entities.Note;
import com.kigamba.mvp.presenters.NoteViewPresenterImpl;
import com.kigamba.mvp.views.NoteView;

public class NoteActivity extends AppCompatActivity implements NoteView {

    private Note note;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private NoteViewPresenterImpl noteViewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        noteViewPresenter = new NoteViewPresenterImpl(this);

        instantiateViews();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int noteId = bundle.getInt(MainActivity.PARCELEABLE_KEY_NOTE_ID);
            note = noteViewPresenter.getNote(noteId);

            if (note != null) {
                setNoteDetails(note.getTitle(), note.getDescription());
            }
        }

    }

    private void instantiateViews() {
        titleEditText = (EditText) findViewById(R.id.et_noteView_title);
        descriptionEditText = (EditText) findViewById(R.id.et_noteView_description);
    }

    @Override
    public void setNoteDetails(String title, String description) {
        titleEditText.setText(title);
        descriptionEditText.setText(description);
    }

    @Override
    public Note getNote() {
        if (note == null) {
            note = new Note();
        }

        note.setTitle(titleEditText.getText().toString());
        note.setDescription(descriptionEditText.getText().toString());

        return note;
    }

    @Override
    protected void onDestroy() {
        noteViewPresenter.onDestroy();
        super.onDestroy();
    }
}
