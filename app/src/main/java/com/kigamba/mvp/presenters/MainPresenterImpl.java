package com.kigamba.mvp.presenters;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
        fetchNotes();
    }

    @Override
    public Note[] getNotes() {
        if (mainView instanceof Context) {
            AppDatabase appDatabase = AppDatabase.getInstance((Context) mainView);
            notes = appDatabase.noteDao().getAll();
            return notes;
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

    @Override
    public void fetchNotes() {
        FetchNotesAsyncTask fetchNotesAsyncTask = new FetchNotesAsyncTask();
        fetchNotesAsyncTask.setMainPresenter(this);
        fetchNotesAsyncTask.execute();
    }


    private void fetchWeatherData() {
        if (mainView instanceof Context) {
            Context context = (Context) mainView;
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = "http://www.google.com";

// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the first 500 characters of the response string.
                            mTextView.setText("Response is: " + response.substring(0, 500));
                        }
                    }, new Response.ErrorListener().ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mTextView.setText("That didn't work!");
                }
            });

// Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }
}
