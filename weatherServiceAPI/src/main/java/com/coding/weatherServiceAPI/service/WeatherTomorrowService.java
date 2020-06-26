package com.coding.weatherServiceAPI.service;

import com.coding.weatherServiceAPI.dto.Weather;

public interface WeatherTomorrowService {
	public Weather getWeather(Integer zipCode) throws Exception;
}
