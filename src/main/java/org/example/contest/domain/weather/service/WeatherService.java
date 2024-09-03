package org.example.contest.domain.weather.service;

import org.json.JSONObject;

public interface WeatherService{
    JSONObject getWeatherData(String nx, String ny);
}
