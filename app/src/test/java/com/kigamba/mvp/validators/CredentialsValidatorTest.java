package com.kigamba.mvp.validators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 03/05/2018.
 */
@RunWith(RobolectricTestRunner.class)
public class CredentialsValidatorTest {

    @Test
    public void isCredentialsOkShouldReturnUsernameError() throws Exception {
        int actualValue = CredentialsValidator.isCredentialsOk("", "");

        assertEquals(CredentialsValidator.USERNAME_ERROR, actualValue);
    }

    @Test
    public void isCredentialsOkShouldReturnPasswordError() throws Exception {
        int actualValue = CredentialsValidator.isCredentialsOk("username", "");

        assertEquals(CredentialsValidator.PASSWORD_ERROR, actualValue);
    }

    @Test
    public void isCredentialsOkShouldReturnCredentialsOk() throws Exception {
        int actualValue = CredentialsValidator.isCredentialsOk("username", "password");

        assertEquals(CredentialsValidator.CREDENTIALS_OK, actualValue);
    }

}