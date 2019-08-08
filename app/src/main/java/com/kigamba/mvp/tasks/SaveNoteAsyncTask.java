package com.kigamba.mvp.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.kigamba.mvp.communication.BroadcastManager;
import com.kigamba.mvp.persistence.entities.Note;
import com.kigamba.mvp.presenters.NoteViewPresenter;
import com.kigamba.mvp.views.NoteView;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 30/04/2018.
 */

public class SaveNoteAsyncTask extends AsyncTask {

    private NoteViewPresenter noteViewPresenter;
    private Note note;

    public void setNoteViewPresenter(@NonNull NoteViewPresenter noteViewPresenter) {
        this.noteViewPresenter = noteViewPresenter;
    }

    public void setNote(@NonNull Note note) {
        this.note = note;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        if (noteViewPresenter != null && note != null) {
            noteViewPresenter.saveNote(note);
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        // Should show a progress dialog here or something
    }

    @Override
    protected void onPostExecute(Object o) {
        NoteView noteView = noteViewPresenter.getView();
        if (noteView instanceof Context) {
            Context context = (Context) noteView;
            BroadcastManager.sendNewNoteEvent(context);
        }
    }
}
