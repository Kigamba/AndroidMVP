package com.kigamba.mvp.interactors;

import android.content.Context;

import com.kigamba.mvp.persistence.daos.NoteDao;
import com.kigamba.mvp.persistence.entities.Note;

import java.util.List;
/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 12/04/2018.
 */
public interface NotesInteractor {

    interface OnFinishedListener {
        void onFinished(Note[] notes);
    }

    void getNote(int noteId, OnFinishedListener listener);

    Note getNoteFromLayer(int noteId);

    void getAllNotes(OnFinishedListener listener);

    Note[] getAllNotesFromLayer();

    void saveNote(Note note, OnFinishedListener listener);

    void saveNote(Note note);

    boolean saveNoteToLayer(Note note);

    void setContext(Context context);

    NoteDao getNoteDao();
}
