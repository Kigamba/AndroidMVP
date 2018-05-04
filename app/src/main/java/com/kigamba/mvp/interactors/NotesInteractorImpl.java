package com.kigamba.mvp.interactors;

import android.content.Context;
import android.util.Log;

import com.kigamba.mvp.Utils;
import com.kigamba.mvp.activities.NoteActivity;
import com.kigamba.mvp.communication.BroadcastManager;
import com.kigamba.mvp.persistence.AppDatabase;
import com.kigamba.mvp.persistence.daos.NoteDao;
import com.kigamba.mvp.persistence.entities.Note;
import com.kigamba.mvp.tasks.FetchNotesAsyncTask;
import com.kigamba.mvp.tasks.GetNoteAsyncTask;
import com.kigamba.mvp.tasks.SaveNoteAsyncTask;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 12/04/2018.
 */
public class NotesInteractorImpl implements NotesInteractor {

    private Context context;
    private NoteDao noteDao;

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public NoteDao getNoteDao() {
        if (noteDao == null && context != null) {
            noteDao = AppDatabase.getInstance(context).noteDao();
        }

        return noteDao;
    }

    @Override
    public void saveNote(Note note, OnFinishedListener onFinishedListener) {
        SaveNoteAsyncTask saveNoteAsyncTask = new SaveNoteAsyncTask();
        saveNoteAsyncTask.setNotesInteractor(this);
        saveNoteAsyncTask.setOnFinishedListener(onFinishedListener);
        saveNoteAsyncTask.setNote(note);
        saveNoteAsyncTask.execute();
    }

    @Override
    public void saveNote(Note note) {
        saveNote(note, new OnFinishedListener() {
            @Override
            public void onFinished(Note[] notes) {
                if (context != null) {
                    BroadcastManager.sendNewNoteEvent(context);
                }
            }
        });
    }

    @Override
    public boolean saveNoteToLayer(Note note) {
        NoteDao noteDao = getNoteDao();
        if (noteDao != null) {
            try {
                return Utils.saveNote(noteDao, note);
            } catch (Exception e) {
                Log.e(NoteActivity.class.getName(), Log.getStackTraceString(e));
            }
        }

        return false;
    }

    @Override
    public void getAllNotes(OnFinishedListener listener) {
        FetchNotesAsyncTask fetchNotesAsyncTask = new FetchNotesAsyncTask();
        fetchNotesAsyncTask.setNotesInteractor(this);
        fetchNotesAsyncTask.setOnFinishedListener(listener);
        fetchNotesAsyncTask.execute();
    }

    @Override
    public Note[] getAllNotesFromLayer() {
        Note[] notes = null;
        NoteDao noteDao = getNoteDao();
        if (noteDao != null) {
            notes = noteDao.getAll();
        }

        return notes;
    }

    @Override
    public void getNote(int noteId, OnFinishedListener listener) {
        GetNoteAsyncTask getNoteAsyncTask = new GetNoteAsyncTask();
        getNoteAsyncTask.setNotesInteractor(this);
        getNoteAsyncTask.setOnFinishedListener(listener);
        getNoteAsyncTask.execute(noteId);
    }

    @Override
    public Note getNoteFromLayer(int id) {
        NoteDao noteDao = getNoteDao();
        if (noteDao != null && id > 0) {
            return noteDao.getNoteById(id);
        }

        return null;
    }


}
