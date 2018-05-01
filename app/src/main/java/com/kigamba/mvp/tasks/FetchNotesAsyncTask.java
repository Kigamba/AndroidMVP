package com.kigamba.mvp.tasks;

import android.os.AsyncTask;

import com.kigamba.mvp.interactors.NotesInteractor;
import com.kigamba.mvp.persistence.entities.Note;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 30/04/2018.
 */

public class FetchNotesAsyncTask extends AsyncTask<Void, Void, Void> {

    private NotesInteractor notesInteractor;
    private NotesInteractor.OnFinishedListener onFinishedListener;
    private Note[] notes;

    public void setNotesInteractor(NotesInteractor notesInteractor) {
        this.notesInteractor = notesInteractor;
    }

    public void setOnFinishedListener(NotesInteractor.OnFinishedListener onFinishedListener) {
        this.onFinishedListener = onFinishedListener;
    }

    @Override
    protected Void doInBackground(Void... params) {
        notes = notesInteractor.getAllNotesFromLayer();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (onFinishedListener != null) {
            onFinishedListener.onFinished(notes);
        }

    }
}
