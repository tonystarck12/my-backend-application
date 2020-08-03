package com.avengers.configuration;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avengers.model.AppConfiguration;
import com.avengers.service.AppConfigurationServiceImpl;

@Component
public class ApplicationConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);
	
	private static HashMap<String, String> appConfigProp = new HashMap<>();
	
	@Autowired
	AppConfigurationServiceImpl service;
	
	@PostConstruct
	private void loadApplicationConfiguration() {
		List<AppConfiguration> appConfig = service.findAll();
		for (AppConfiguration appConfiguration : appConfig) {
			appConfigProp.put(appConfiguration.getKey(), appConfiguration.getValue());
			logger.info("<<Key>>"+appConfiguration.getKey() + "<<Value>>" + appConfiguration.getValue());
		}
	}
	
	public static String getProperty(String key){
		return appConfigProp.get(key);
	}

}
