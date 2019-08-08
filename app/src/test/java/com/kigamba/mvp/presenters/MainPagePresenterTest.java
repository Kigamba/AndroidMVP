package com.kigamba.mvp.presenters;

import com.kigamba.mvp.contract.MainContract;
import com.kigamba.mvp.interactors.GetWeatherDataInteractor;
import com.kigamba.mvp.interactors.NotesInteractor;
import com.kigamba.mvp.persistence.entities.Note;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 12/04/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPagePresenterTest {

    @Mock
    MainContract.View mainView;

    @Mock
    NotesInteractor interactor;

    @Mock
    GetWeatherDataInteractor getWeatherDataInteractor;

    private MainPagePresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new MainPagePresenter(mainView);
        Whitebox.setInternalState(presenter, "notesInteractor", interactor);
        Whitebox.setInternalState(presenter, "getWeatherDataInteractor", getWeatherDataInteractor);
    }

    @Test
    public void checkIfShowsProgressOnResume() {
        presenter.onResume();
        verify(mainView, times(1)).showProgress();
        verify(interactor, times(1)).getAllNotes(presenter);
        verify(getWeatherDataInteractor, times(1)).getLatestWeather(Mockito.any(GetWeatherDataInteractor.OnFinishedListener.class));
    }

    @Test
    public void onItemClickedShouldShowNote() {
        Note selectedNote = new Note();
        Note[] notes = new Note[]{selectedNote};

        Whitebox.setInternalState(presenter, "notes", notes);
        presenter.onItemClicked(0);

        verify(mainView, times(1)).showNote(selectedNote);

        Whitebox.setInternalState(presenter, "notes", null);
    }

    @Test
    public void onItemClickedShouldNotShowNote() {
        Note selectedNote = new Note();
        Note[] notes = new Note[]{selectedNote};

        presenter.onItemClicked(0);
        verify(mainView, times(0)).showNote(selectedNote);

        Whitebox.setInternalState(presenter, "notes", notes);
        presenter.onItemClicked(2);
        verify(mainView, times(0)).showNote(selectedNote);

        Whitebox.setInternalState(presenter, "mainView", null);
        presenter.onItemClicked(1);
        verify(mainView, times(0)).showNote(selectedNote);

        Whitebox.setInternalState(presenter, "notes", null);
        Whitebox.setInternalState(presenter, "mainView", mainView);
    }

    @Test
    public void checkIfViewIsReleasedOnDestroy() {
        presenter.onDestroy();
        assertNull(presenter.getMainView());
    }

    @Test
    public void onFinished() {
        Note[] notes = new Note[]{new Note()};

        presenter.onFinished(notes);

        verify(mainView, Mockito.times(1)).hideProgress();
        verify(mainView, Mockito.times(1)).setNotes(notes);
    }

    @Test
    public void onFinishedShouldNotCallMainView() {
        Note[] notes = new Note[]{new Note()};

        Whitebox.setInternalState(presenter, "mainView", null);
        presenter.onFinished(notes);

        verify(mainView, Mockito.times(0)).hideProgress();
        verify(mainView, Mockito.times(0)).setNotes(notes);

        Whitebox.setInternalState(presenter, "mainView", mainView);
    }
}
