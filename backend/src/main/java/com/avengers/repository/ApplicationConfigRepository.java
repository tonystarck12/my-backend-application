package com.avengers.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.avengers.model.AppConfiguration;

@Repository
public interface ApplicationConfigRepository extends CrudRepository<AppConfiguration, Long> {

}
