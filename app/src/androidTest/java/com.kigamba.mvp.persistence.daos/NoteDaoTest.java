package com.kigamba.mvp.persistence.daos;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.kigamba.mvp.persistence.AppDatabase;
import com.kigamba.mvp.persistence.entities.Note;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 23/04/2018.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class NoteDaoTest {

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

        List<Note> noteList = noteDao.getAll();

        boolean noteFound = false;
        for(Note noteInList: noteList) {
            if (noteTitle.equals(noteInList.getTitle())
                    && noteDescription.equals(noteInList.getDescription())
                    && note.getLastModified() == noteInList.getLastModified()
                    && note.getDateCreated() == noteInList.getDateCreated()) {
                noteFound = true;
            }
        }

        Assert.assertTrue(noteFound);
    }

    @Test
    public void readAllNotesShouldReturnsAllNotes() {

    }

}