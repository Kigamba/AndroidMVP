package com.kigamba.mvp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.kigamba.mvp.R;
import com.kigamba.mvp.communication.BroadcastManager;
import com.kigamba.mvp.contract.MainContract;
import com.kigamba.mvp.persistence.entities.Note;
import com.kigamba.mvp.presenters.MainPagePresenter;

/**
 *
 * Created by Ephraim Kigamba - ekigamba@ona.io on 12/04/2018.
 *
 */
public class MainActivity extends AppCompatActivity implements MainContract.View, AdapterView.OnItemClickListener {

    private ListView listView;
    private ProgressBar progressBar;
    private MainContract.Presenter presenter;
    private FloatingActionButton newNoteBtn;
    private BroadcastReceiver newNoteUpdateReceiver;

    public static final String PARCELEABLE_KEY_NOTE_ID = "NOTE ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        newNoteBtn = (FloatingActionButton) findViewById(R.id.fab_mainActivity_newNoteBtn);

        presenter = new MainPagePresenter(this);

        newNoteBtn.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(android.view.View v) {
                presenter.onNewNoteButtonClicked();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();

        if (newNoteUpdateReceiver == null) {
            newNoteUpdateReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    presenter.fetchNotes();
                }
            };
        }

        LocalBroadcastManager.getInstance(this)
                .registerReceiver(newNoteUpdateReceiver, new IntentFilter(BroadcastManager.NEW_NOTE_EVENT));
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
    protected void onPause() {
        if (newNoteUpdateReceiver != null) {
            LocalBroadcastManager.getInstance(this)
                    .unregisterReceiver(newNoteUpdateReceiver);
        }

        super.onPause();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(android.view.View.VISIBLE);
        listView.setVisibility(android.view.View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(android.view.View.INVISIBLE);
        listView.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void setNotes(final Note[] notes) {
        listView.setAdapter(new ArrayAdapter(this, R.layout.item_row, notes){

            @NonNull
            @Override
            public android.view.View getView(int position, @Nullable android.view.View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item_row, null);
                }

                Note note = notes[position];

                TextView title = (TextView) convertView.findViewById(R.id.tv_itemRow_title);
                TextView description = (TextView) convertView.findViewById(R.id.tv_itemRow_description);

                title.setText(note.getTitle());
                description.setText(note.getDescription());

                return convertView;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                presenter.onItemClicked(position);
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
    public void openNewNoteView() {
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
        presenter.onItemClicked(position);
    }
}
