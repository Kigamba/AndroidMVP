package com.kigamba.mvp;

import android.support.test.runner.AndroidJUnit4;

import org.junit.runner.RunWith;

import java.util.Random;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 04/05/2018.
 */
@RunWith(AndroidJUnit4.class)
public abstract class BaseTest {

    public static final int MAX_LENGTH = 200;

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
}
