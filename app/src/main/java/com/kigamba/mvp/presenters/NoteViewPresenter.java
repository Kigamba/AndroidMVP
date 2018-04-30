package com.kigamba.mvp.presenters;

import com.kigamba.mvp.persistence.entities.Note;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 24/04/2018.
 */

public interface NoteViewPresenter {

    void saveNote(Note note);

    Note getNote(int id);

    void onDestroy();
}
