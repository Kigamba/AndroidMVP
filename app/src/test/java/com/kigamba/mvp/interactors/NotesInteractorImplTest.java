package com.kigamba.mvp.interactors;

import com.kigamba.mvp.persistence.daos.NoteDao;
import com.kigamba.mvp.persistence.entities.Note;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 03/05/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class NotesInteractorImplTest {

    private NotesInteractorImpl notesInteractor;

    @Mock
    private NoteDao noteDao;

    @Before
    public void setup() {
        notesInteractor = new NotesInteractorImpl();
    }

    @Test
    public void getNoteDaoShouldReturnNull() throws Exception {
        NoteDao actualValue = notesInteractor.getNoteDao();

        assertNull(actualValue);
    }

    @Test
    public void saveNoteToLayerShouldReturnTrue() {
        Note note = new Note();

        NoteDao previousNoteDao = (NoteDao) Whitebox.getInternalState(notesInteractor, "noteDao");
        Whitebox.setInternalState(notesInteractor, "noteDao", noteDao);
        boolean actualValue = notesInteractor.saveNoteToLayer(note);

        assertTrue(actualValue);
        Whitebox.setInternalState(notesInteractor, "noteDao", previousNoteDao);
    }

    @Test
    public void saveNoteToLayerShouldReturnFalse() {
        Note note = new Note();
        boolean actualValue = notesInteractor.saveNoteToLayer(note);

        assertFalse(actualValue);
    }

    @Test
    public void getAllNotesFromLayerShouldReturnNull() throws Exception {
        Note[] actualValue = notesInteractor.getAllNotesFromLayer();

        assertNull(actualValue);
    }

    @Test
    public void getAllNotesFromLayerShouldCallNoteDao() throws Exception {
        NoteDao previousNoteDao = (NoteDao) Whitebox.getInternalState(notesInteractor, "noteDao");

        Whitebox.setInternalState(notesInteractor, "noteDao", noteDao);

        notesInteractor.getAllNotesFromLayer();

        Mockito.verify(noteDao, Mockito.times(1))
                .getAll();

        Whitebox.setInternalState(notesInteractor, "noteDao", previousNoteDao);
    }

    @Test
    public void getNoteFromLayerShouldCallNoteDao() {
        NoteDao previousNoteDao = (NoteDao) Whitebox.getInternalState(notesInteractor, "noteDao");

        Whitebox.setInternalState(notesInteractor, "noteDao", noteDao);
        notesInteractor.getNoteFromLayer(1);

        Mockito.verify(noteDao, Mockito.times(1))
                .getNoteById(1);

        Whitebox.setInternalState(notesInteractor, "noteDao", previousNoteDao);
    }

    @Test
    public void getNoteFromLayerShouldReturnNull() {
        Note actualValue = notesInteractor.getNoteFromLayer(9);
        assertNull(actualValue);
    }

}