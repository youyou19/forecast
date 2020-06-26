package weatherServiceAPI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.coding.weatherServiceAPI.WeatherApplication;
import com.coding.weatherServiceAPI.dto.Weather;
import com.coding.weatherServiceAPI.service.WeatherTomorrowService;
import com.coding.weatherServiceAPI.serviceImpl.WeatherTomorrowServiceImpl;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = WeatherApplication.class)
public class WeatherServiceTests {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WeatherTomorrowService weatherService;
	
	private Integer zipCode = 52557;
	
	void getWeatherDetails() throws Exception{
		 mockMvc.perform(get("/weatherTomorrow/")
	                .contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().is4xxClientError());
	}
	
	@Test
    void getWeatherDetailsWithZipCode() throws Exception {
        mockMvc.perform(get("/weatherTomorrow/52557")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }

    @Test
    void getAstronomyWeather() throws Exception {
        Weather w = weatherService.getWeather(zipCode);
       assertNotNull(w.getAstronomy());
    }
    
    
    @Test
    public void zipCode_NotFound_404() throws Exception {
        mockMvc.perform(get("/temperature/52557")).andExpect(status().isNotFound());
    }

}
