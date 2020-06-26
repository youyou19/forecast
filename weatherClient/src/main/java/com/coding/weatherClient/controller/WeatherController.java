package com.coding.weatherClient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WeatherController {
	@RequestMapping(value="/")
	public String input() {
		   return "weather";
	}

}
