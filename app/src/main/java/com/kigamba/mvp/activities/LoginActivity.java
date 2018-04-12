package com.kigamba.mvp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kigamba.mvp.R;
import com.kigamba.mvp.interactors.LoginInteractorImpl;
import com.kigamba.mvp.views.LoginView;
import com.kigamba.mvp.presenters.LoginPresenter;
import com.kigamba.mvp.presenters.LoginPresenterImpl;

public class LoginActivity extends Activity implements LoginView, View.OnClickListener {

    private ProgressDialog progressDialog;
    private EditText username;
    private EditText password;
    private LoginPresenter presenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        findViewById(R.id.button).setOnClickListener(this);

        presenter = new LoginPresenterImpl(this,new LoginInteractorImpl());
    }

    @Override protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override public void showProgress() {
        progressDialog.show();
    }

    @Override public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override public void setUsernameError() {
        username.setError(getString(R.string.username_error));
    }

    @Override public void setPasswordError() {
        password.setError(getString(R.string.password_error));
    }

    @Override
    public void showOtherError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG)
                .show();
    }

    @Override public void navigateToHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override public void onClick(View v) {
        presenter.validateCredentials(username.getText().toString(), password.getText().toString());
    }
}
