package com.avengers.configuration;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.avengers.repository.ApplicationConfigRepository;

@Component
public class ApplicationConfiguration {
	
	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);
	
	@Autowired
	ApplicationConfigRepository  applicationConfigRepository;
	
	public static int maxWrongPasswordCount;
	
	@PostConstruct
	private void getMaxWrngPasswordAttemptCount() {
		logger.info("<<< Max Password Attempt Count >>> "+ applicationConfigRepository.getMaxWrngPasswordAttemptCount());
		maxWrongPasswordCount = applicationConfigRepository.getMaxWrngPasswordAttemptCount();
	}
}
