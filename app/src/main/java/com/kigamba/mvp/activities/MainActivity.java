package com.kigamba.mvp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kigamba.mvp.R;
import com.kigamba.mvp.interactors.FindItemsInteractorImpl;
import com.kigamba.mvp.persistence.entities.Note;
import com.kigamba.mvp.views.MainView;
import com.kigamba.mvp.presenters.MainPresenter;
import com.kigamba.mvp.presenters.MainPresenterImpl;

import java.util.List;

/**
 *
 * Created by Ephraim Kigamba - ekigamba@ona.io on 12/04/2018.
 *
 */
public class MainActivity extends Activity implements MainView, AdapterView.OnItemClickListener {

    private ListView listView;
    private ProgressBar progressBar;
    private MainPresenter presenter;

    public static final String PARCELEABLE_KEY_NOTE_ID = "NOTE ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        presenter = new MainPresenterImpl(this, new FindItemsInteractorImpl());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setNotes(final List<Note> notes) {
        listView.setAdapter(new ArrayAdapter(this, R.layout.item_row, notes){

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item_row, null);
                }

                Note note = notes.get(position);

                TextView title = (TextView) convertView.findViewById(R.id.tv_itemRow_title);
                TextView description = (TextView) convertView.findViewById(R.id.tv_itemRow_title);

                title.setText(note.getTitle());
                description.setText(note.getDescription());

                return convertView;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showNote(notes.get(position));
            }
        });
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showNote(Note note) {
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt(PARCELEABLE_KEY_NOTE_ID, note.getId());
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        presenter.onItemClicked(position);
    }
}
