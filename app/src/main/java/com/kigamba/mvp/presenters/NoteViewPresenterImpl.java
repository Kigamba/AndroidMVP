package com.kigamba.mvp.presenters;

import android.content.Context;
import android.text.TextUtils;

import com.kigamba.mvp.persistence.AppDatabase;
import com.kigamba.mvp.persistence.daos.NoteDao;
import com.kigamba.mvp.persistence.entities.Note;
import com.kigamba.mvp.tasks.GetNoteAsyncTask;
import com.kigamba.mvp.tasks.SaveNoteAsyncTask;
import com.kigamba.mvp.views.NoteView;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 24/04/2018.
 */

public class NoteViewPresenterImpl implements NoteViewPresenter {

    private NoteView noteView;
    private Note note;


    public NoteViewPresenterImpl(NoteView noteView) {
        this.noteView = noteView;
    }

    @Override
    public void saveNote(Note note) {
        if (noteView instanceof Context) {
            AppDatabase appDatabase = AppDatabase.getInstance((Context) noteView);
            NoteDao noteDao = appDatabase.noteDao();

            if (note.getId() == 0) {
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
    public void setNote(Note note) {
        this.note = note;

        noteView.hideProgress();
        noteView.setNoteDetails(note.getTitle(), note.getDescription());
    }

    @Override
    public void fetchNote(int id) {
        noteView.showProgress();

        GetNoteAsyncTask getNoteAsyncTask = new GetNoteAsyncTask();
        getNoteAsyncTask.setNoteViewPresenter(this);
        getNoteAsyncTask.execute(id);
    }

    @Override
    public NoteView getView() {
        return noteView;
    }

    @Override
    public void onDestroy() {
        String[] noteDetails = noteView.getNoteDetails();

        if (note == null) {
            note = new Note();
        } else {
            note.setLastModified(System.currentTimeMillis());
        }

        if (!validateNote(noteDetails[0], noteDetails[1])) {
            return;
        }

        note.setTitle(noteDetails[0]);
        note.setDescription(noteDetails[1]);

        SaveNoteAsyncTask saveNoteAsyncTask = new SaveNoteAsyncTask();
        saveNoteAsyncTask.setNoteViewPresenter(this);
        saveNoteAsyncTask.setNote(note);
        saveNoteAsyncTask.execute();
    }

    private boolean validateNote(String title, String description) {
        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description)) {
            return true;
        }

        return false;
    }
}
