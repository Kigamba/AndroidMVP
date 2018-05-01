package com.kigamba.mvp.presenters;

import android.content.Context;

import com.kigamba.mvp.interactors.GetWeatherDataInteractor;
import com.kigamba.mvp.interactors.GetWeatherDataInteractorImpl;
import com.kigamba.mvp.interactors.NotesInteractor;
import com.kigamba.mvp.interactors.NotesInteractorImpl;
import com.kigamba.mvp.persistence.entities.Note;
import com.kigamba.mvp.views.MainView;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 12/04/2018.
 */
public class MainPresenterImpl implements MainPresenter, NotesInteractor.OnFinishedListener {

    private MainView mainView;
    private NotesInteractor notesInteractor;
    private GetWeatherDataInteractor getWeatherDataInteractor;
    private Note[] notes;

    public MainPresenterImpl(MainView mainView) {
        this.mainView = mainView;
        this.notesInteractor = new NotesInteractorImpl();
        this.getWeatherDataInteractor = new GetWeatherDataInteractorImpl();

        if (mainView instanceof Context) {
            this.notesInteractor.setContext((Context) mainView);
            this.getWeatherDataInteractor.setContext((Context) mainView);
        }
    }

    @Override
    public void onResume() {
        if (mainView != null) {
            mainView.showProgress();
        }

        notesInteractor.getAllNotes(this);
        fetchWeatherData();
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
    public MainView getMainView() {
        return mainView;
    }

    @Override
    public void fetchNotes() {
        notesInteractor.getAllNotes(this);
    }

    @Override
    public void fetchWeatherData() {
        getWeatherDataInteractor.getLatestWeather(new GetWeatherDataInteractor.OnFinishedListener() {
            @Override
            public void onSuccess(String weatherString) {
                mainView.showMessage("Weather: " + weatherString);
            }

            @Override
            public void onError(String error) {
                //Should probably show a red-colored message here
                mainView.showMessage("An error occurred retrieving weather data");
            }
        });
    }

    @Override
    public void onFinished(Note[] notes) {
        this.notes = notes;
        if (mainView != null) {
            mainView.hideProgress();
            mainView.setNotes(notes);
        }
    }
}
