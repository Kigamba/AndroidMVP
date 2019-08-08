package com.kigamba.mvp.presenters;

import android.content.Context;

import com.kigamba.mvp.Utils;
import com.kigamba.mvp.contract.NotePageContract;
import com.kigamba.mvp.interactors.NotesInteractor;
import com.kigamba.mvp.interactors.NotesInteractorImpl;
import com.kigamba.mvp.persistence.entities.Note;
import com.kigamba.mvp.views.NoteView;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 24/04/2018.
 */

public class NotePagePresenter implements NotePageContract.Presenter {

    private NoteView noteView;
    private Note note;
    private NotesInteractor notesInteractor;

    public NotePagePresenter(NoteView noteView) {
        this.noteView = noteView;
        notesInteractor = new NotesInteractorImpl();

        if (noteView instanceof Context) {
            notesInteractor.setContext((Context) noteView);
        }
    }

    @Override
    public void setNote(Note note) {
        this.note = note;

        noteView.hideProgress();
        noteView.setNoteDetails(note.getTitle(), note.getDescription());
    }

    @Override
    public void fetchNote(int id) {
        noteView.showProgress();

        notesInteractor.getNote(id, new NotesInteractor.OnFinishedListener() {
            @Override
            public void onFinished(Note[] notes) {
                noteView.hideProgress();

                if (notes != null && notes.length > 0) {
                    note = notes[0];
                    noteView.setNoteDetails(note.getTitle(), note.getDescription());
                }
            }
        });
    }

    @Override
    public NoteView getView() {
        return noteView;
    }

    @Override
    public void onDestroy() {
        String[] noteDetails = noteView.getNoteDetails();

        note = Utils.prepareNoteForSaving(note);

        if (!Utils.validateNoteDetails(noteDetails[0], noteDetails[1])) {
            return;
        }

        note.setTitle(noteDetails[0]);
        note.setDescription(noteDetails[1]);

        notesInteractor.saveNote(note);
    }


}
