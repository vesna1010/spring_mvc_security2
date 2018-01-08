package college.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import college.dao.extensions.UserDao;
import college.enums.Role;
import college.model.User;
import college.service.UserService;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Resource
	private UserDao userDao;

	@Override
	public Set<User> findUsers() {
		return userDao.findAll();
	}

	@Override
	public User findUserByUsername(String username) {
		return userDao.findOneById(username);
	}

	@Override
	public void saveOrUpdateUser(User user) {
		userDao.saveOrUpdate(user);
	}

	@Override
	public void deleteUserByUsername(String username) {
		userDao.deleteById(username);
	}

	@Override
	public void disableUserByUsername(String username) {
		userDao.disableByUsername(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findOneById(username);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (Role role : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toString()));
		}

		return new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), authorities);
	}
}

