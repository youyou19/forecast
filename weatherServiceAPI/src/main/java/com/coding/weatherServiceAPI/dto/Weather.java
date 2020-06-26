package com.coding.weatherServiceAPI.dto;

import java.io.Serializable;
import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(description = "All details about the weather. ")
public class Weather implements Serializable{

 @ApiModelProperty(notes = "Tomorrow weather")
 private LocalDate date;
 @ApiModelProperty(notes = "The Maximum of the temperature farenheit")
 private Integer maxtempC;
 @ApiModelProperty(notes = "The Maximum of the temperature celcius")
 private Integer maxtempF;
 @ApiModelProperty(notes = "The Minimum of the  temperature farenheit")
 private Integer mintempC;
 @ApiModelProperty(notes = "The Minimum of the temperature celcius")
 private Integer mintempF;
 private Integer avgtempC;
 private Integer avgtempF;
 private Double totalSnow_cm;
 private Double sunHour;
 private Integer uvIndex;
 private Astronomy astronomy;
 private Hourly hourly;
 @ApiModelProperty(notes = "The City")
 private City city;
}
