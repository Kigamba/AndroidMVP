package com.kigamba.mvp.presenters;

import com.kigamba.mvp.persistence.entities.Note;
import com.kigamba.mvp.views.NoteView;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 24/04/2018.
 */

public interface NoteViewPresenter {

    void saveNote(Note note);

    Note getNote(int id);

    void setNote(Note note);

    void fetchNote(int id);

    void onDestroy();

    NoteView getView();
}
