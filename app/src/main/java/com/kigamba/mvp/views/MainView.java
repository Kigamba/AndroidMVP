package com.kigamba.mvp.views;

import com.kigamba.mvp.persistence.entities.Note;

import java.util.List;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 12/04/2018.
 */
public interface MainView {

    void showProgress();

    void hideProgress();

    void setNotes(Note[] notes);

    void showMessage(String message);

    void showNote(Note note);

    void openNewNoteView();
}
