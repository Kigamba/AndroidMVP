package com.kigamba.mvp.interactors;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 12/04/2018.
 */
public interface LoginInteractor {

    interface OnLoginFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onOtherError(String error);

        void onSuccess();
    }

    void login(String username, String password, OnLoginFinishedListener listener);

}
