package com.kigamba.mvp;

import com.kigamba.mvp.persistence.daos.NoteDao;
import com.kigamba.mvp.persistence.entities.Note;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 03/05/2018.
 */
public class UtilsTest {

    @Test
    public void prepareNoteForSavingShouldCreateNewNoteWhenGivenNullNote() throws Exception {
        Note nullNote = null;

        Note newNote = Utils.prepareNoteForSaving(nullNote);

        assertNotNull(newNote);
    }

    @Test
    public void prepareNoteForSavingShouldChangeNoteModifiedDateWhenGivenNunNullNote() throws Exception {
        long beforeValue = 6700;

        Note note = new Note();
        note.setLastModified(beforeValue);

        Note resultNote = Utils.prepareNoteForSaving(note);

        assertNotEquals(beforeValue, resultNote.getLastModified());
    }

    @Test
    public void validateNoteDetailsShouldReturnTrue() throws Exception {
        String title = "some title";
        String description = "some description";

        assertTrue(Utils.validateNoteDetails(title, description));
    }

    @Test
    public void validateNoteDetailsShouldReturnFalse() throws Exception {
        String title = "  ";
        String description = "some description";

        assertFalse(Utils.validateNoteDetails(title, description));

        description = "";
        assertFalse(Utils.validateNoteDetails(title, description));

        title = null;
        assertFalse(Utils.validateNoteDetails(title, description));

        title = "some title";
        description = "";
        assertFalse(Utils.validateNoteDetails(title, description));
    }

    @Test
    public void isNoteNewShouldReturnTrue() throws Exception {
        Note newNote = new Note();

        assertTrue(Utils.isNoteNew(newNote));
    }

    @Test
    public void isNoteNewShouldReturnFalse() throws Exception {
        Note oldNote = new Note();
        oldNote.setId(9);

        assertFalse(Utils.isNoteNew(oldNote));
    }

    @Test
    public void saveNoteShouldCallInsert() throws Exception {
        NoteDao noteDao = Mockito.mock(NoteDao.class);

        Note newNote = new Note();
        newNote.setTitle("some title");
        newNote.setDescription("some description");

        Utils.saveNote(noteDao, newNote);

        Mockito.verify(noteDao, Mockito.times(1))
                .insertAll(newNote);
    }

    @Test
    public void saveNoteShouldCallUpdate() throws Exception {
        NoteDao noteDao = Mockito.mock(NoteDao.class);

        Note oldNote = new Note();
        oldNote.setId(9);
        oldNote.setTitle("some title");
        oldNote.setDescription("some description");

        Utils.saveNote(noteDao, oldNote);

        Mockito.verify(noteDao, Mockito.times(1))
                .update(oldNote);
    }

}