package com.grokonez.jwtauthentication.security.services;

import com.grokonez.jwtauthentication.model.User;
import com.grokonez.jwtauthentication.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		/*
		 * User user = userRepository.findByUsername(username) .orElseThrow(() -> new
		 * UsernameNotFoundException("User Not Found with -> username or email : " +
		 * username) );
		 */
		return UserPrinciple.build(getUserByUserName(username));
	}

	public User getUserByUserName(String username) {
		List<User> result = jdbcTemplate.query(
				"SELECT id, name, email, password, username FROM users where username='" + username + "'",
				(rs, rowNum) -> new User(rs.getString("name"), rs.getString("username"), rs.getString("email"),
						rs.getString("password")));

		if (null != result && null != result.get(0))
			return result.get(0);
		else
			throw new UsernameNotFoundException("User Not Found with -> username or email : " + username);
	}

	// Get all Users
	public List<User> getAllUsers() {
		List<User> result = jdbcTemplate.query("SELECT id, name, email, password, username FROM users",
				(rs, rowNum) -> new User(rs.getString("name"), rs.getString("username"), rs.getString("email"),
						rs.getString("password")));
		return result;
	}

	// Add new User
	public void addUser(String name, String email, String password, String username) {
		jdbcTemplate.update("INSERT INTO users(name, email, password, username) VALUES (?,?,?,?)", name, email,
				password, username);
	}

}