package com.kigamba.mvp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.kigamba.mvp.R;
import com.kigamba.mvp.contract.LoginContract;
import com.kigamba.mvp.interactors.LoginInteractor;
import com.kigamba.mvp.presenters.LoginPresenter;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 12/04/2018.
 */
public class LoginActivity extends Activity implements LoginContract.View, android.view.View.OnClickListener {

    private ProgressDialog progressDialog;
    private EditText username;
    private EditText password;
    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog = new ProgressDialog(this);
        username = (EditText) findViewById(R.id.login_userNameText);
        password = (EditText) findViewById(R.id.login_passwordText);
        findViewById(R.id.login_loginButton).setOnClickListener(this);

        presenter = new LoginPresenter(this, new LoginInteractor());
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void setUsernameError() {
        username.setError(getString(R.string.username_error));
    }

    @Override
    public void setPasswordError() {
        password.setError(getString(R.string.password_error));
    }

    @Override
    public void showOtherError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void navigateToHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void onClick(android.view.View v) {
        presenter.validateCredentials(username.getText().toString(), password.getText().toString());
    }
}
