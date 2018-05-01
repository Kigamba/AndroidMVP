package com.kigamba.mvp.tasks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.kigamba.mvp.interactors.NotesInteractor;
import com.kigamba.mvp.persistence.entities.Note;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 30/04/2018.
 */

public class SaveNoteAsyncTask extends AsyncTask {

    private NotesInteractor.OnFinishedListener onFinishedListener;
    private NotesInteractor notesInteractor;
    private Note note;

    public void setOnFinishedListener(NotesInteractor.OnFinishedListener onFinishedListener) {
        this.onFinishedListener = onFinishedListener;
    }

    public void setNotesInteractor(NotesInteractor notesInteractor) {
        this.notesInteractor = notesInteractor;
    }

    public void setNote(@NonNull Note note) {
        this.note = note;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        if (notesInteractor != null && note != null) {
            notesInteractor.saveNoteToLayer(note);
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        // Should show a progress dialog here or something
    }

    @Override
    protected void onPostExecute(Object o) {
        if (onFinishedListener != null) {
            onFinishedListener.onFinished(new Note[]{note});
        }
    }
}
