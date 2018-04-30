package com.kigamba.mvp.presenters;

import android.content.Context;

import com.kigamba.mvp.persistence.AppDatabase;
import com.kigamba.mvp.persistence.daos.NoteDao;
import com.kigamba.mvp.persistence.entities.Note;
import com.kigamba.mvp.tasks.SaveNoteAsyncTask;
import com.kigamba.mvp.views.NoteView;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 24/04/2018.
 */

public class NoteViewPresenterImpl implements NoteViewPresenter {

    private NoteView noteView;

    public NoteViewPresenterImpl(NoteView noteView) {
        this.noteView = noteView;
    }

    @Override
    public void saveNote(Note note) {
        if (noteView instanceof Context) {
            AppDatabase appDatabase = AppDatabase.getInstance((Context) noteView);
            NoteDao noteDao = appDatabase.noteDao();

            if (note.getId() != 0) {
                noteDao.insertAll(note);
            } else {
                noteDao.update(note);
            }
        }
    }

    @Override
    public Note getNote(int id) {

        if (noteView instanceof Context && id > 0) {
            AppDatabase appDatabase = AppDatabase.getInstance((Context) noteView);
            NoteDao noteDao = appDatabase.noteDao();
            return noteDao.getNoteById(id);
        }

        return null;
    }

    @Override
    public void onDestroy() {
        Note note = noteView.getNote();

        SaveNoteAsyncTask saveNoteAsyncTask = new SaveNoteAsyncTask();
        saveNoteAsyncTask.setNoteViewPresenter(this);
        saveNoteAsyncTask.setNote(note);
    }
}
