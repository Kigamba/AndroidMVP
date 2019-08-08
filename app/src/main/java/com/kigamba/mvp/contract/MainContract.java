package com.kigamba.mvp.contract;

import com.kigamba.mvp.persistence.entities.Note;
import com.kigamba.mvp.views.ProgressView;

public interface MainContract {

    interface Presenter {

        void onResume();

        void onNewNoteButtonClicked();

        void onItemClicked(int position);

        void onDestroy();

        View getMainView();

        void fetchNotes();

        void fetchWeatherData();
    }

    /**
     * Created by Ephraim Kigamba - ekigamba@ona.io on 12/04/2018.
     */
    interface View extends ProgressView {

        void setNotes(Note[] notes);

        void showMessage(String message);

        void showNote(Note note);

        void openNewNoteView();
    }
}
