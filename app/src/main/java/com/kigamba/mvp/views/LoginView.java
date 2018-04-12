package com.kigamba.mvp.views;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 12/04/2018.
 */
public interface LoginView {
    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void showOtherError(String error);

    void navigateToHome();
}
