package com.kigamba.mvp.validators;

import android.text.TextUtils;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 23/04/2018.
 */

public class CredentialsValidator {

    public static final int USERNAME_ERROR = 1;
    public static final int PASSWORD_ERROR = 2;
    public static final int CREDENTIALS_OK = 3;

    public static int isCredentialsOk(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            return USERNAME_ERROR;
        }

        if (TextUtils.isEmpty(password)) {
            return PASSWORD_ERROR;
        }

        return CREDENTIALS_OK;
    }

}
