package com.kigamba.mvp.views;

import com.kigamba.mvp.persistence.entities.Note;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 23/04/2018.
 */

public interface NoteView {

    void setNoteDetails(String title, String description);

    Note getNote();
}
