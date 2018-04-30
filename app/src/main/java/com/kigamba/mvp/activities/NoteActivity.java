package com.kigamba.mvp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.kigamba.mvp.R;
import com.kigamba.mvp.presenters.NoteViewPresenterImpl;
import com.kigamba.mvp.views.NoteView;

public class NoteActivity extends AppCompatActivity implements NoteView {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private NoteViewPresenterImpl noteViewPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        noteViewPresenter = new NoteViewPresenterImpl(this);

        instantiateViews();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int noteId = bundle.getInt(MainActivity.PARCELEABLE_KEY_NOTE_ID);
            noteViewPresenter.fetchNote(noteId);
        }

    }

    private void instantiateViews() {
        titleEditText = (EditText) findViewById(R.id.et_noteView_title);
        descriptionEditText = (EditText) findViewById(R.id.et_noteView_description);

        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void setNoteDetails(String title, String description) {
        titleEditText.setText(title);
        descriptionEditText.setText(description);
    }

    @Override
    public String[] getNoteDetails() {
        String[] toReturn = new String[2];

        toReturn[0] = titleEditText.getText().toString();
        toReturn[1] = descriptionEditText.getText().toString();

        return toReturn;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onStop() {
        if (isFinishing()) {
            noteViewPresenter.onDestroy();
        }

        super.onStop();
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }
}
