package com.avengers.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.avengers.model.Role;
import com.avengers.model.RoleName;
import com.avengers.model.User;
import com.avengers.repository.UserAuthRepository;

@Service
public class UserAuthServiceImpl implements UserAuthService {

	@Autowired
	UserAuthRepository userAuthRepository;

	@Override
	public Boolean existsByUsername(String username) {
		return userAuthRepository.existsByUsername(username);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return userAuthRepository.existsByEmail(email);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		return userAuthRepository.findByUsername(username);
	}

	@Override
	public Optional<Role> findByName(RoleName roleName) {
		return userAuthRepository.findByName(roleName);
	}

	@Override
	public void save(User user) {
		userAuthRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		return userAuthRepository.getAllUsers();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userAuthRepository.loadUserByUsername(username);
	}
}
