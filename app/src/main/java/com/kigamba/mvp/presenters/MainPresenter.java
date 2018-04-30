package com.kigamba.mvp.presenters;

import com.kigamba.mvp.persistence.entities.Note;
import com.kigamba.mvp.views.MainView;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 12/04/2018.
 */
public interface MainPresenter {

    void onResume();

    void onNewNoteButtonClicked();

    void onItemClicked(int position);

    void onDestroy();

    Note[] getNotes();

    MainView getMainView();

    void fetchNotes();
}
