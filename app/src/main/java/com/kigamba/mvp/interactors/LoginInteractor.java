package com.kigamba.mvp.interactors;

import android.os.Handler;

import com.kigamba.mvp.contract.LoginContract;
import com.kigamba.mvp.validators.CredentialsValidator;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 12/04/2018.
 */
public class LoginInteractor implements LoginContract.Interactor {

    private boolean fail = false;

    @Override
    public void login(final String username, final String password, final OnLoginFinishedListener listener) {
        // Mock login. I'm creating a handler to delay the answer a couple of seconds
        // No other way to do this within the given time !!!!!
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {

                switch(CredentialsValidator.isCredentialsOk(username, password)) {
                    case CredentialsValidator.USERNAME_ERROR:
                        listener.onUsernameError();
                        return;

                    case CredentialsValidator.PASSWORD_ERROR:
                        listener.onPasswordError();
                        return;

                    case CredentialsValidator.CREDENTIALS_OK:
                        break;
                }

                if (fail) {
                    listener.onOtherError("Server Error Occurred!");
                    return;
                }

                listener.onSuccess();
            }
        }, 2000);
    }
}
