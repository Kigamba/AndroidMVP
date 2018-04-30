package com.kigamba.mvp.presenters;

import android.content.Context;

import com.kigamba.mvp.interactors.FindItemsInteractor;
import com.kigamba.mvp.persistence.AppDatabase;
import com.kigamba.mvp.persistence.entities.Note;
import com.kigamba.mvp.tasks.FetchNotesAsyncTask;
import com.kigamba.mvp.views.MainView;

import java.util.List;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 12/04/2018.
 */
public class MainPresenterImpl implements MainPresenter, FindItemsInteractor.OnFinishedListener {

    private MainView mainView;
    private FindItemsInteractor findItemsInteractor;
    private Note[] notes;

    public MainPresenterImpl(MainView mainView, FindItemsInteractor findItemsInteractor) {
        this.mainView = mainView;
        this.findItemsInteractor = findItemsInteractor;
    }

    @Override
    public void onResume() {
        if (mainView != null) {
            mainView.showProgress();
        }

        findItemsInteractor.findItems(this);

        FetchNotesAsyncTask fetchNotesAsyncTask = new FetchNotesAsyncTask();
        fetchNotesAsyncTask.setMainPresenter(this);
        fetchNotesAsyncTask.execute();
    }

    @Override
    public Note[] getNotes() {
        if (mainView instanceof Context) {
            AppDatabase appDatabase = AppDatabase.getInstance((Context) mainView);
            return appDatabase.noteDao().getAll();
        }

        return null;
    }

    @Override
    public void onNewNoteButtonClicked() {
        mainView.openNewNoteView();
    }

    @Override
    public void onItemClicked(int position) {
        if (mainView != null && notes != null && position < notes.length) {
            mainView.showNote(notes[position]);
        }
    }

    @Override
    public void onDestroy() {
        mainView = null;
    }

    @Override
    public void onFinished(List<String> items) {
        if (mainView != null) {
            //mainView.setItems(items);
            mainView.hideProgress();
        }
    }

    @Override
    public MainView getMainView() {
        return mainView;
    }
}
