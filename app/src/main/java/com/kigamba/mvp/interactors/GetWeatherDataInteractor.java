package com.kigamba.mvp.interactors;

import android.content.Context;

/**
 * Created by Kigamba on 01-May-18.
 */

public interface GetWeatherDataInteractor {

    interface OnFinishedListener {
        void onSuccess(String weatherString);
        void onError(String error);
    }

    void getLatestWeather(OnFinishedListener onFinishedListener);

    void setContext(Context context);
}
