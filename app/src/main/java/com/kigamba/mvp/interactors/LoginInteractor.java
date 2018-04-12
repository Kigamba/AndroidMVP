package com.kigamba.mvp.interactors;

public interface LoginInteractor {

    interface OnLoginFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onOtherError(String error);

        void onSuccess();
    }

    void login(String username, String password, OnLoginFinishedListener listener);

}
