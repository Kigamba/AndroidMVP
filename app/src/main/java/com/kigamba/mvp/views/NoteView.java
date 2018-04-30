package com.kigamba.mvp.views;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 23/04/2018.
 */

public interface NoteView {

    void setNoteDetails(String title, String description);

    String[] getNoteDetails();

    void showProgress();

    void hideProgress();
}
