package com.kigamba.mvp.presenters;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 12/04/2018.
 */
public interface LoginPresenter {
    void validateCredentials(String username, String password);

    void onDestroy();
}
