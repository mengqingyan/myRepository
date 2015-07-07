/**
 * 
 */
package com.revencoft.example.service.hystrix.webservice;

import org.springframework.stereotype.Service;

import com.revencoft.example.annotation.Hystrix;
import com.revencoft.example.annotation.TimerLog;
import com.revencoft.example.annotation.TimerLog.LEVEL;

import cn.com.WebXml.WeatherWS;
import cn.com.WebXml.WeatherWSLocator;
import cn.com.WebXml.WeatherWSSoap;

/**
 * 利用aop代理，将ws调用操作委托给Hystrix
 * @author mengqingyan
 * @version 
 */
@Service
public class WeatherWebService {
	
	private WeatherWS fs = new WeatherWSLocator();
	
	@Hystrix
	@TimerLog(LEVEL.DEBUG)
	public String[] getWeather(String theCityCode, String theUserID) throws Exception {
		WeatherWSSoap soap = fs.getWeatherWSSoap();
		return soap.getWeather(theCityCode, theUserID);
	}

}
