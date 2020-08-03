package com.avengers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avengers.model.AppConfiguration;
import com.avengers.repository.ApplicationConfigRepository;

@Service
public class AppConfigurationServiceImpl implements AppConfigurationService {

	@Autowired
    private ApplicationConfigRepository repository;
	
	@Override
	public List<AppConfiguration> findAll() {
		 return (List<AppConfiguration>) repository.findAll();
	}

}
