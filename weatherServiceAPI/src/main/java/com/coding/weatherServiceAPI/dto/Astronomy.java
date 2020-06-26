package com.coding.weatherServiceAPI.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Astronomy implements Serializable {
  private String sunrise;
  private String sunset;
  private String moonrise;
  private String moonset;
  private String moon_phase;
  private String  moon_illumination;
}
