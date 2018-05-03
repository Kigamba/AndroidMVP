package com.kigamba.mvp.presenters;

import com.kigamba.mvp.interactors.LoginInteractor;
import com.kigamba.mvp.views.LoginView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 12/04/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock
    private LoginView loginView;

    @Mock
    private LoginInteractor loginInteractor;

    private LoginPresenterImpl loginPresenter;

    @Before
    public void setup() {
        loginPresenter = new LoginPresenterImpl(loginView, loginInteractor);
    }

    @Test
    public void validateCredentialsShouldShowProgressWhenCalled() {
        loginPresenter.validateCredentials("username", "pwd");

        Mockito.verify(loginView, Mockito.times(1))
                .showProgress();

        Mockito.verify(loginInteractor, Mockito.times(1))
                .login("username", "pwd", loginPresenter);
    }

    @Test
    public void onDestoryInvalidatesView() {
        loginPresenter.onDestroy();

        Object newLoginView = Whitebox.getInternalState(loginPresenter, "loginView");

        Assert.assertEquals(null, newLoginView);

        Whitebox.setInternalState(loginPresenter, "loginView", loginView);
    }

    @Test
    public void onSuccessShouldNavigateToHome() {
        loginPresenter.onSuccess();

        Mockito.verify(loginView, Mockito.times(1))
                .navigateToHome();
    }

    @Test
    public void onOtherError() {
        String otherError = "some error";
        loginPresenter.onOtherError(otherError);

        Mockito.verify(loginView, Mockito.times(1))
                .hideProgress();

        Mockito.verify(loginView, Mockito.times(1))
                .showOtherError(otherError);
    }

    @Test
    public void onUsernameError() {
        loginPresenter.onUsernameError();

        Mockito.verify(loginView, Mockito.times(1))
                .hideProgress();

        Mockito.verify(loginView, Mockito.times(1))
                .setUsernameError();
    }

    @Test
    public void onPasswordError() {
        loginPresenter.onPasswordError();

        Mockito.verify(loginView, Mockito.times(1))
                .setPasswordError();
    }
}