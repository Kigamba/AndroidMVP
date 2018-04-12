package com.kigamba.mvp.interactors;

import android.os.Handler;
import android.text.TextUtils;

public class LoginInteractorImpl implements LoginInteractor {

    boolean fail = true;

    @Override
    public void login(final String username, final String password, final OnLoginFinishedListener listener) {
        // Mock login. I'm creating a handler to delay the answer a couple of seconds
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                if (TextUtils.isEmpty(username)) {
                    listener.onUsernameError();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    listener.onPasswordError();
                    return;
                }

                if (fail) {
                    listener.onOtherError("Server error occured!");
                    return;
                }

                listener.onSuccess();
            }
        }, 2000);
    }
}
