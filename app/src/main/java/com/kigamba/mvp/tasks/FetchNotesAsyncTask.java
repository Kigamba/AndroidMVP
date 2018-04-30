package com.kigamba.mvp.tasks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.kigamba.mvp.persistence.entities.Note;
import com.kigamba.mvp.presenters.MainPresenter;
import com.kigamba.mvp.views.MainView;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 30/04/2018.
 */

public class FetchNotesAsyncTask extends AsyncTask<Void, Void, Void> {

    private MainPresenter mainPresenter;
    private MainView mainView;
    private Note[] notes;

    public void setMainPresenter(@NonNull MainPresenter mainPresenter) {
        this.mainPresenter = mainPresenter;
        this.mainView = mainPresenter.getMainView();
    }

    @Override
    protected Void doInBackground(Void... params) {
        notes = mainPresenter.getNotes();
        return null;
    }

    @Override
    protected void onPreExecute() {
        if (mainView != null) {
            mainView.showProgress();
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        if (mainView != null) {
            mainView.setNotes(notes);
            mainView.hideProgress();
        }

    }
}
