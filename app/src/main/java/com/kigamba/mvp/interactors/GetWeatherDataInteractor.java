package com.kigamba.mvp.interactors;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kigamba on 01-May-18.
 */

public interface GetWeatherDataInteractor {

    interface OnFinishedListener {
        void onSuccess(String weatherString);
        void onError(String error);
    }

    void getLatestWeather(OnFinishedListener onFinishedListener);

    String getHumanReadableWeather(String jsonResponse) throws JSONException;

    void setContext(Context context);
}
