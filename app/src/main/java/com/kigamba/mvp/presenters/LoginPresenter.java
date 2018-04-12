package com.kigamba.mvp.presenters;

public interface LoginPresenter {
    void validateCredentials(String username, String password);

    void onDestroy();
}
