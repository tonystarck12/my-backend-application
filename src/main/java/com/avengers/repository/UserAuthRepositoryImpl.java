package com.avengers.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.avengers.model.Role;
import com.avengers.model.RoleName;
import com.avengers.model.User;
import com.avengers.security.jwt.JwtAuthEntryPoint;
import com.avengers.model.UserPrinciple;

@Repository
public class UserAuthRepositoryImpl implements UserAuthRepository, UserDetailsService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthEntryPoint.class);

	@Override
	public Boolean existsByUsername(String username) {
		logger.info("In existsByUsername() method");
		List<User> result = jdbcTemplate.query(
				"SELECT name, email, password, username FROM users where username='" + username + "'",
				(rs, rowNum) -> new User(rs.getString("name"), rs.getString("username"), rs.getString("email"),
						rs.getString("password")));

		if (null != result && !result.isEmpty())
			return true;
		else
			return false;
	}

	@Override
	public Boolean existsByEmail(String email) {
		logger.info("In existsByEmail() method");
		List<User> result = jdbcTemplate.query(
				"SELECT name, email, password, username FROM users where email='" + email + "'",
				(rs, rowNum) -> new User(rs.getString("name"), rs.getString("username"), rs.getString("email"),
						rs.getString("password")));

		if (null != result && !result.isEmpty())
			return true;
		else
			return false;
	}

	@Override
	public Optional<User> findByUsername(String username) {
		logger.info("In findByUsername() method");
		List<User> result = jdbcTemplate.query(
				"SELECT id, name, email, password, username FROM users where username='" + username + "'",
				(rs, rowNum) -> new User(rs.getString("name"), rs.getString("username"), rs.getString("email"),
						rs.getString("password")));

		if (null != result && !result.isEmpty())
			return Optional.of(result.get(0));
		else
			throw new UsernameNotFoundException("User Not Found with -> username : " + username);
	}

	@Override
	public Optional<Role> findByName(RoleName roleName) {
		logger.info("In findByName() method");
		List<Role> result = jdbcTemplate.query("SELECT id, name FROM roles where name='" + roleName + "'",
				(rs, rowNum) -> new Role(RoleName.valueOf(rs.getString("name"))));

		if (null != result && !result.isEmpty())
			return Optional.of(result.get(0));
		else
			throw new UsernameNotFoundException("Role Not Found with -> role name : " + roleName);

	}

	@Override
	public void save(User user) {
		logger.info("In save() method");
		jdbcTemplate.update("INSERT INTO users(name, email, password, username) VALUES (?,?,?,?)", user.getName(),
				user.getEmail(), user.getPassword(), user.getUsername());
	}

	@Override
	public List<User> getAllUsers() {
		logger.info("In getAllUsers() method");
		List<User> result = jdbcTemplate.query("SELECT id, name, email, password, username FROM users",
				(rs, rowNum) -> new User(rs.getString("name"), rs.getString("username"), rs.getString("email"),
						rs.getString("password")));

		if (null != result && !result.isEmpty())
			return result;
		else
			throw new UsernameNotFoundException("Users are not found");
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("In loadUserByUsername() method");

		String sql = "select u.id, u.name, u.email, u.password, u.username, r.name as role_name from users as u "
				+ "inner join user_roles as ur on u.id=ur.user_id inner join roles as r on ur.role_id=r.id where u.username='"
				+ username + "'";
		
		List <User> result = jdbcTemplate.query(sql, new UserMapper());

		if (null != result && !result.isEmpty())
			return UserPrinciple.build(result.get(0));
		else
			throw new UsernameNotFoundException("User Not Found with -> username : " + username);
	}
	
	public static class UserMapper implements RowMapper<User> {
		@Override
		   public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			   User user = new User();
			   Set<Role> roleList = new HashSet<Role>();
			   Role role = new Role(RoleName.valueOf(rs.getString("role_name")));
			   roleList.add(role);
			   user.setName(rs.getString("name"));
			   user.setUsername(rs.getString("username"));
			   user.setEmail(rs.getString("email"));
			   user.setPassword(rs.getString("password"));
			   user.setRoles(roleList);
		       return user;
		   }

	
		}

}
