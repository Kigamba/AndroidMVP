package com.kigamba.mvp.tasks;

import android.os.AsyncTask;

import com.kigamba.mvp.persistence.entities.Note;
import com.kigamba.mvp.presenters.NoteViewPresenter;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 30/04/2018.
 */

public class GetNoteAsyncTask extends AsyncTask<Integer, Note, Note> {

    private NoteViewPresenter noteViewPresenter;

    public void setNoteViewPresenter(NoteViewPresenter noteViewPresenter) {
        this.noteViewPresenter = noteViewPresenter;
    }

    @Override
    protected Note doInBackground(Integer... params) {
        if (noteViewPresenter != null && params != null && params.length > 0) {
            return noteViewPresenter.getNote(params[0]);
        }

        return null;
    }

    @Override
    protected void onPostExecute(Note note) {
        super.onPostExecute(note);

        if (noteViewPresenter != null) {
            noteViewPresenter.setNote(note);
        }
    }
}
