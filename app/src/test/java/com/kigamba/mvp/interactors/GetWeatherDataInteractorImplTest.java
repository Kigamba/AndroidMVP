package com.kigamba.mvp.interactors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;

/**
 * Created by Ephraim Kigamba - ekigamba@ona.io on 04/05/2018.
 */
public class GetWeatherDataInteractorImplTest {

    private GetWeatherDataInteractorImpl getWeatherDataInteractor;

    @Before
    public void setup() {
        getWeatherDataInteractor = new GetWeatherDataInteractorImpl();
    }

    @Test
    public void getHumanReadableWeather() throws Exception {
        String expectedWeather = "scattered clouds";
        String weather = getWeatherDataInteractor.getHumanReadableWeather(weatherResponseForNairobi);

        assertTrue(expectedWeather.equals(weather));
    }

    @Test
    public void onErrorResponse() throws Exception {

    }

    @Test
    public void onResponse() throws Exception {

    }


    private String weatherResponseForNairobi = "{\n" +
            "    \"coord\": {\n" +
            "        \"lon\": 36.82,\n" +
            "        \"lat\": -1.28\n" +
            "    },\n" +
            "    \"weather\": [\n" +
            "        {\n" +
            "            \"id\": 802,\n" +
            "            \"main\": \"Clouds\",\n" +
            "            \"description\": \"scattered clouds\",\n" +
            "            \"icon\": \"03d\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"base\": \"stations\",\n" +
            "    \"main\": {\n" +
            "        \"temp\": 295.286,\n" +
            "        \"pressure\": 832.78,\n" +
            "        \"humidity\": 76,\n" +
            "        \"temp_min\": 295.286,\n" +
            "        \"temp_max\": 295.286,\n" +
            "        \"sea_level\": 1019.84,\n" +
            "        \"grnd_level\": 832.78\n" +
            "    },\n" +
            "    \"wind\": {\n" +
            "        \"speed\": 1.76,\n" +
            "        \"deg\": 107.505\n" +
            "    },\n" +
            "    \"clouds\": {\n" +
            "        \"all\": 36\n" +
            "    },\n" +
            "    \"dt\": 1525438612,\n" +
            "    \"sys\": {\n" +
            "        \"message\": 0.0033,\n" +
            "        \"country\": \"KE\",\n" +
            "        \"sunrise\": 1525404450,\n" +
            "        \"sunset\": 1525447885\n" +
            "    },\n" +
            "    \"id\": 184745,\n" +
            "    \"name\": \"Nairobi\",\n" +
            "    \"cod\": 200\n" +
            "}";

}