package com.kigamba.mvp.interactors;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kigamba.mvp.activities.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kigamba on 01-May-18.
 */

public class GetWeatherDataInteractorImpl implements GetWeatherDataInteractor, Response.Listener<String>, Response.ErrorListener {

    private Context context;
    public static final String WEATHER_DATA_API_URL = "http://api.openweathermap.org/data/2.5/weather?q=Nairobi&APPID=0a3d55e210916e7bc4ae50f246cc07d0";
    private OnFinishedListener onFinishedListener;

    private static final String TAG = MainActivity.class.getName();

    @Override
    public void getLatestWeather(final OnFinishedListener onFinishedListener) {
        this.onFinishedListener = onFinishedListener;

        if (context != null) {
            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, WEATHER_DATA_API_URL, this, this);
            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }

    @Override
    public String getHumanReadableWeather(String jsonResponse) throws JSONException {
        String humanUnderstandableWeather = null;
        JSONObject jsonObject = new JSONObject(jsonResponse);

        if (jsonObject.has("weather") && jsonObject.getJSONArray("weather").length() > 0) {
            JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);

            humanUnderstandableWeather = weather.getString("description");
        }

        return humanUnderstandableWeather;
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (onFinishedListener != null) {
            onFinishedListener.onError(error.getMessage());
        }
    }

    @Override
    public void onResponse(String response) {
        try {
            String weather = getHumanReadableWeather(response);

            if (onFinishedListener != null) {
                onFinishedListener.onSuccess(weather);
            }
        } catch (JSONException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
    }
}
