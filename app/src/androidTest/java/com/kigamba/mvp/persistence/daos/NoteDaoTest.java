package com.kigamba.mvp.persistence.daos;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;

import com.kigamba.mvp.BaseTest;
import com.kigamba.mvp.persistence.AppDatabase;
import com.kigamba.mvp.persistence.daos.NoteDao;
import com.kigamba.mvp.persistence.entities.Note;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 23/04/2018.
 */

@SmallTest
public class NoteDaoTest extends BaseTest{

    private AppDatabase appDatabase;
    private NoteDao noteDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .build();
        noteDao = appDatabase.noteDao();
    }

    @After
    public void closeDb() {
        appDatabase.close();
    }

    @Test
    public void saveNote() {
        String noteTitle = "Note title";
        String noteDescription = "";

        Note note = new Note();
        note.setTitle(noteTitle);
        note.setDescription(noteDescription);

        noteDao.insertAll(note);

        Note[] noteList = noteDao.getAll();

        boolean noteFound = false;
        for (Note noteInList: noteList) {
            if (noteTitle.equals(noteInList.getTitle())
                    && noteDescription.equals(noteInList.getDescription())
                    && note.getLastModified() == noteInList.getLastModified()
                    && note.getDateCreated() == noteInList.getDateCreated()) {
                noteFound = true;
            }
        }

        Assert.assertTrue(noteFound);
        noteDao.delete(note);
    }

    @Test
    public void getAllShouldReturnAllNotes() {
        // Save several notes
        int notesLength = 500;
        Note[] notes = generateAndSaveNotes(notesLength);

        int similarNotesFound = 0;
        //Search for the notes

        Note[] actualNotes = noteDao.getAll();
        HashMap<Integer, Note> actualNotesHashmap = new HashMap<>(notesLength);
        for (int i = 0; i < actualNotes.length; i++) {
            actualNotesHashmap.put(actualNotes[i].getId(), actualNotes[i]);
        }

        for (int i = 0; i < notes.length; i++) {
            Note note = notes[i];

            Note foundNote = actualNotesHashmap.get(note.getId());
            if (foundNote != null && areNotesEqual(note, foundNote)) {
                similarNotesFound++;
            }
        }

        assertEquals(notesLength, similarNotesFound);

        // Delete the notes from the db
        deleteNotes(notes);
    }

    @Test
    public void getNoteById() {
        // Save several notes
        int notesLength = 50;
        Note[] notes = generateAndSaveNotes(notesLength);

        // Pick 10 random notes to search for
        int randomNotesLen = 10;
        for (int i = 0; i < randomNotesLen; i++) {
            Note randomNote = notes[(int) (Math.random() * 50)];

            Note foundNote = noteDao.getNoteById(randomNote.getId());
            assertTrue(areNotesEqual(foundNote, randomNote));
        }

         // Delete the notes
        deleteNotes(notes);
    }

    @Test
    public void update() {
        // Save several notes
        int notesLength = 50;
        Note[] notes = generateAndSaveNotes(notesLength);

        // Pick 10 random notes, update them & query them
        int randomNotesLen = 10;
        for (int i = 0; i < randomNotesLen; i++) {
            Note randomNote = notes[(int) (Math.random() * 50)];

            randomNote.setTitle(random());
            randomNote.setDescription(random());
            randomNote.setLastModified(System.currentTimeMillis());

            noteDao.update(randomNote);

            Note foundNote = noteDao.getNoteById(randomNote.getId());
            assertTrue(areNotesEqual(foundNote, randomNote));
        }

        // Delete the notes
        deleteNotes(notes);
    }

    @Test
    public void delete() {
        // Save several notes
        int notesLength = 50;
        Note[] notes = generateAndSaveNotes(notesLength);

        int randomNotesLen = 10;
        for (int i = 0; i < randomNotesLen; i++) {
            Note randomNote = notes[(int) (Math.random() * 50)];
            noteDao.delete(randomNote);

            Note foundNote = noteDao.getNoteById(randomNote.getId());
            assertNull(foundNote);
        }

        Note[] foundNotes = noteDao.getAll();
        assertEquals(notesLength - randomNotesLen, foundNotes.length);

        deleteNotes(notes);
    }

    public static boolean areNotesEqual(Note n1, Note n2) {
        if (n1.getId() == n2.getId()
                && n1.getTitle().equals(n2.getTitle())
                && n1.getDescription().equals(n2.getDescription())
                && n1.getDateCreated() == n2.getDateCreated()
                && n1.getLastModified() == n2.getLastModified()) {
            return true;
        }

        return false;
    }

    public Note[] generateAndSaveNotes(int no) {
        // Save several notes
        Note[] notes = new Note[no];

        for (int i = 0; i < no; i++) {
            Note note = new Note();
            note.setTitle(random());
            note.setDescription(random());

            note.setId((int) noteDao.insertAll(note)[0]);
            notes[i] = note;
        }

        return notes;
    }

    public void deleteNotes(Note[] notes) {
        int notesLength = notes.length;
        for (int i = 0; i < notesLength; i++) {
            noteDao.delete(notes[i]);
        }
    }

}