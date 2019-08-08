package com.kigamba.mvp.contract;

import com.kigamba.mvp.persistence.entities.Note;
import com.kigamba.mvp.views.NoteView;

public interface NotePageContract {

    interface Presenter {

        void setNote(Note note);

        void fetchNote(int id);

        void onDestroy();

        NoteView getView();
    }
}
