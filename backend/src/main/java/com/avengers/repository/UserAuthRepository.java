package com.avengers.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.avengers.model.Role;
import com.avengers.model.RoleName;
import com.avengers.model.User;

public interface UserAuthRepository {

	Optional<User> findByUsername(String username);

	Optional<Role> findByName(RoleName roleName);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	void save(User user);

	List<User> getAllUsers();

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	void updateLoginTimeStamp(String username);
	
	void incrementFailedAttemptCount(String username);
}
