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

public class GetWeatherDataInteractorImpl implements GetWeatherDataInteractor {

    private Context context;
    public static final String WEATHER_DATA_API_URL = "http://api.openweathermap.org/data/2.5/weather?q=Nairobi&APPID=0a3d55e210916e7bc4ae50f246cc07d0";

    private static final String TAG = MainActivity.class.getName();

    @Override
    public void getLatestWeather(final OnFinishedListener onFinishedListener) {
        if (context != null) {
            RequestQueue queue = Volley.newRequestQueue(context);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, WEATHER_DATA_API_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                if (jsonObject.has("weather") && jsonObject.getJSONArray("weather").length() > 0) {
                                    JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);

                                    if (onFinishedListener != null) {
                                        onFinishedListener.onSuccess(weather.getString("description"));
                                    }
                                }
                            } catch (JSONException e) {
                                Log.e(TAG, Log.getStackTraceString(e));
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (onFinishedListener != null) {
                                onFinishedListener.onError(error.getMessage());
                            }
                        }
                    }
            );

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }

    @Override
    public void setContext(Context context) {
        this.context = context;
    }
}
