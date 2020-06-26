package com.coding.weatherServiceAPI.serviceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.coding.weatherServiceAPI.dto.Astronomy;
import com.coding.weatherServiceAPI.dto.City;
import com.coding.weatherServiceAPI.dto.Hourly;
import com.coding.weatherServiceAPI.dto.Weather;
import com.coding.weatherServiceAPI.dto.WeatherDescr;
import com.coding.weatherServiceAPI.service.WeatherTomorrowService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.type.TypeReference;

@Slf4j
@Service
public class WeatherTomorrowServiceImpl implements WeatherTomorrowService {
	@Autowired
    private  RestTemplate restTemplate;

	@Override
   	public Weather getWeather(Integer zipCode) throws Exception{
        if (zipCode == null) {
            throw new Exception("400");
      }
      Weather weather = new Weather();
      LocalDate today = LocalDate.now();
	  LocalDate tomorrow = today.plusDays(1);
	  String URL="https://api.worldweatheronline.com/premium/v1/weather.ashx?key=0855445b5f334b74a0240218193012&q="+zipCode+",united&format=json&num_of_days=2&tp=1";
     
	  ObjectMapper mapper = new ObjectMapper();
      mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
      ResponseEntity<Object> rateResponse =
                restTemplate.exchange(URL,
                        HttpMethod.GET, null, new ParameterizedTypeReference<Object>() {
                        });
        LinkedHashMap<String, ?> bodyResponse = (LinkedHashMap) rateResponse.getBody();
        Object variables = (Object) bodyResponse.get("data");
        LinkedHashMap variableList = mapper.convertValue(variables, new TypeReference<LinkedHashMap>() {
         });
  	   
        
        List<City> city = (List<City>) variableList.get("request");
        List<LinkedHashMap> cityList = mapper.convertValue(city, new TypeReference<List<LinkedHashMap>>() {
         });
        
        City c=new City();
        c.setQuery(""+cityList.get(0).get("query"));
        c.setType(""+cityList.get(0).get("type"));
        
        List<Weather> weathers = (List<Weather>) variableList.get("weather");
        List<LinkedHashMap> weatherList = mapper.convertValue(weathers, new TypeReference<List<LinkedHashMap>>() {
         });
        
        int j=0;
        for (LinkedHashMap wet : weatherList) {
        	Astronomy astronomy=new Astronomy();
        	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        	LocalDate localDateObj = LocalDate.parse(""+wet.get("date"), dateTimeFormatter);
        	if(tomorrow.isEqual(localDateObj)) {
        	weather.setDate(localDateObj);
        	weather.setMaxtempC(Integer.parseInt(""+wet.get("maxtempC")));
           weather.setMaxtempF(Integer.parseInt(""+wet.get("maxtempF")));
           weather.setMintempC(Integer.parseInt(""+wet.get("mintempC")));
           weather.setMintempF(Integer.parseInt(""+wet.get("mintempF")));
           weather.setAvgtempC(Integer.parseInt(""+wet.get("avgtempC")));
           weather.setAvgtempF(Integer.parseInt(""+wet.get("avgtempF")));
          weather.setTotalSnow_cm(Double.parseDouble(""+wet.get("totalSnow_cm")));  
          weather.setSunHour(Double.parseDouble(""+wet.get("sunHour")));  
          weather.setUvIndex(Integer.parseInt(""+wet.get("uvIndex")));
        
          List<Astronomy> astronomies = (List<Astronomy>) wet.get("astronomy");
          List<LinkedHashMap> astronomyList = mapper.convertValue(astronomies, new TypeReference<List<LinkedHashMap>>() {
           });
         
        	  astronomy.setSunset(""+astronomyList.get(0).get("sunset"));
        	  astronomy.setSunrise(""+astronomyList.get(0).get("sunrise"));
        	  astronomy.setMoonrise(""+astronomyList.get(0).get("moonrise"));
        	  astronomy.setMoonset(""+astronomyList.get(0).get("moonset"));
        	   astronomy.setMoon_phase(""+astronomyList.get(0).get("moon_phase"));
        	   astronomy.setMoon_illumination(""+astronomyList.get(0).get("moon_illumination"));
        	   weather.setAstronomy(astronomy);
          
          List<Hourly> hourlies = (List<Hourly>) wet.get("hourly");
          List<LinkedHashMap> hourlyList = mapper.convertValue(hourlies, new TypeReference<List<LinkedHashMap>>() {
           });
         
          Integer coolestF=Integer.MAX_VALUE;
         Hourly hour=new Hourly();
         String time=null;
         Integer tempC=null;
         Integer tempF=null;
         Integer chanceofsnow=null;
         for (LinkedHashMap h : hourlyList) {
                  if(coolestF>Integer.parseInt(""+h.get("tempF"))) {
        	   time=""+h.get("time");    
             tempF=Integer.parseInt(""+h.get("tempF"));
             tempC=Integer.parseInt(""+h.get("tempC"));
             coolestF=tempF;
             chanceofsnow=Integer.parseInt(""+h.get("chanceofsnow")); 
           }
         }
         
         String temp;
         if(time.trim().length()==1)
        	temp="00:00 AM"; 
         else if(time.trim().length()==3) {
        temp="0"+time.substring(0,1)+":00 AM";	 
         }else {
        	int h=Integer.parseInt(time.substring(0,2));
         if(h<12)
        	 temp=time.substring(0,2)+":00 AM";	 
         else  temp=time.substring(0,2)+":00 PM";	 
          	 
         }
         
    	  List<WeatherDescr> weatherDesc = (List<WeatherDescr>) hourlyList.get(0).get("weatherDesc");
          List<LinkedHashMap> weatherDescList = mapper.convertValue(weatherDesc, new TypeReference<List<LinkedHashMap>>() {
           });
        
        
          
          
          WeatherDescr des=new WeatherDescr();
         des.setValue(""+weatherDescList.get(0).get("value"));
         hour.setTempC(tempC);
         hour.setTempF(tempF);
         hour.setWeatherDesc(des);
         hour.setTime(temp);
        hour.setChanceofsnow(chanceofsnow);
         
         weather.setHourly(hour);
    	 weather.setCity(c);   
          
          
        	}
            
        }
       
        return weather;
    
	}
}
