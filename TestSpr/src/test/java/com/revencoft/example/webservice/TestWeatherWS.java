/**
 * 
 */
package com.revencoft.example.webservice;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.revencoft.example.SpringContextTestCase;
import com.revencoft.example.service.hystrix.command.WebServiceCommand;
import com.revencoft.example.service.hystrix.webservice.WeatherWebService;

/**
 * @author mengqingyan
 * @version
 */

@ActiveProfiles(profiles = "testWS")
@DirtiesContext
@ContextConfiguration(locations = { "/application-context.xml" })
public class TestWeatherWS extends SpringContextTestCase {

	private final Logger log = Logger.getLogger(getClass());

	@Autowired
	private WeatherWebService weatherService;

	@Test
	public void getWeather() throws Exception {
		String[] weatherStrs = weatherService.getWeather("广州", null);
		if (weatherStrs == WebServiceCommand.DEFAULT_ERROR_FALLBACK) {
			System.out.println("timeout");
			return;
		}
		for (String weatherStr : weatherStrs) {
			System.out.println(weatherStr);
		}
	}

	@Test
	public void multiAccess() throws Exception {
		for (int i = 0; i < 10; i++) {
			getWeather();
		}
		Thread.sleep(21000);
		getWeather();
	}

}
