package com.kigamba.mvp.contract;

public interface LoginContract {

    interface Interactor {

        interface OnLoginFinishedListener {

            void onUsernameError();

            void onPasswordError();

            void onOtherError(String error);

            void onSuccess();

        }

        void login(String username, String password, OnLoginFinishedListener listener);

    }

    interface Presenter {
        void validateCredentials(String username, String password);

        void onDestroy();
    }

    interface View {
        void showProgress();

        void hideProgress();

        void setUsernameError();

        void setPasswordError();

        void showOtherError(String error);

        void navigateToHome();
    }
}
