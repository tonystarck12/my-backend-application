package com.avengers.repository;

import org.springframework.stereotype.Repository;

@Repository
public class ApplicationConfigRepositoryImpl implements ApplicationConfigRepository {

	@Override
	public int getMaxWrngPasswordAttemptCount() {
		return 2;
	}

}
