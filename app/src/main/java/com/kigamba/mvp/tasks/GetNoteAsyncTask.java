package com.kigamba.mvp.tasks;

import android.os.AsyncTask;

import com.kigamba.mvp.interactors.NotesInteractor;
import com.kigamba.mvp.persistence.entities.Note;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 30/04/2018.
 */

public class GetNoteAsyncTask extends AsyncTask<Integer, Note, Note> {

    private NotesInteractor notesInteractor;
    private NotesInteractor.OnFinishedListener onFinishedListener;

    public void setOnFinishedListener(NotesInteractor.OnFinishedListener onFinishedListener) {
        this.onFinishedListener = onFinishedListener;
    }

    public void setNotesInteractor(NotesInteractor notesInteractor) {
        this.notesInteractor = notesInteractor;
    }

    @Override
    protected Note doInBackground(Integer... params) {
        if (notesInteractor != null && params != null && params.length > 0) {
            return notesInteractor.getNoteFromLayer(params[0]);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Note note) {
        super.onPostExecute(note);

        if (onFinishedListener != null) {
            onFinishedListener.onFinished(new Note[]{note});
        }
    }
}
