package com.coding.weatherServiceAPI.controller;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.coding.weatherServiceAPI.dto.Response;
import com.coding.weatherServiceAPI.dto.Weather;
import com.coding.weatherServiceAPI.exception.ResourceNotFoundException;
import com.coding.weatherServiceAPI.service.WeatherTomorrowService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RequestMapping("/weatherTomorrow")
@RestController
@CrossOrigin(origins = "http://localhost:8086", maxAge = 6000)
@Api(value="api", description = "Data service operations for Weather", tags=("weather"))
public class WeatherTomorrowController {
  @Autowired
  WeatherTomorrowService weatherService;
	
//@ApiOperation(value="Get Weather", notes="Gets Weather in the system", nickname="getWeather")
  @ApiOperation(value = "Get Weather by zip code",notes = "Gets Weather in the system",
   response = Weather.class,responseContainer = "getWeather")
  @GetMapping(value="/{zipCode}", produces="application/json")
@ResponseStatus(HttpStatus.OK)
@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid Zip Code"),
@ApiResponse(code = 404, message = "Zip Code you were trying to reach is not found"),
@ApiResponse(code = 401, message = "You are not authorized to view the zip code"),
@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
//@ApiResponse(code = 200, message = "Successfully retrieved list"),
@ApiResponse(code = 201, message = "Successfully retrieved")})
//  public ResponseEntity<?> getWeather(@ApiParam(value = "zip code of the City", required = true)
//@PathVariable Integer zipCode) throws ResourceNotFoundException {
//		  Weather weather = ((Object) weatherService.getWeather(zipCode))
//		    .orElseThrow(() -> new ResourceNotFoundException("Country not found for :: " + zipCode));
//		  return ResponseEntity.ok().body(weather);
//		 }
 
  public ResponseEntity<?> getWeather(@ApiParam(value = "zip code of the City", required = true)
 @PathVariable Integer zipCode) {
      try {
          return ResponseEntity.status(201).body(weatherService.getWeather(zipCode));
      } catch (Exception e) {
          return ResponseEntity.status(400).body(new Response(400, "input errors"));
      }
  }
}
