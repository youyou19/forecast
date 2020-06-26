package com.coding.weatherServiceAPI.dto;

import java.io.Serializable;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Hourly implements Serializable {
 private String time;
 private Integer tempC;
 private Integer tempF;
 private Integer chanceofsnow;
 private WeatherDescr weatherDesc;

}
