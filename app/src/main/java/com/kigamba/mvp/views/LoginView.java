package com.kigamba.mvp.views;

public interface LoginView {
    void showProgress();

    void hideProgress();

    void setUsernameError();

    void setPasswordError();

    void showOtherError(String error);

    void navigateToHome();
}
