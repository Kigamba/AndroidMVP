package com.kigamba.mvp.persistence.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.kigamba.mvp.persistence.entities.Note;

import java.util.List;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 23/04/2018.
 */
@Dao
public interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY last_modified DESC")
    Note[] getAll();

    @Insert
    void insertAll(Note... notes);

    @Query("SELECT * FROM notes where id = :noteId")
    Note getNoteById(int noteId);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}
