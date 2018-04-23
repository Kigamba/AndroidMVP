package com.kigamba.mvp.persistence;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.kigamba.mvp.persistence.daos.NoteDao;
import com.kigamba.mvp.persistence.entities.Note;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 23/04/2018.
 */
@Database(entities = {Note.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    public static final String DB_NAME = "android-mvp";

    public abstract NoteDao noteDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .build();
        }

        return instance;
    }
}
