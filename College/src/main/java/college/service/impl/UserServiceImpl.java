package college.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import college.dao.UserDao;
import college.model.User;
import college.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

	private UserDao userDao;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<User> findAllUsers() {
		return userDao.findAll();
	}

	@Override
	public User findUserByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public void saveOrUpdateUser(User user) {
		user = setUserPassword(user);
		userDao.saveOrUpdate(user);
	}

	private User setUserPassword(User user) {
		String password = user.getPassword();

		password = encodedPassword(password);
		user.setPassword(password);

		return user;
	}

	private String encodedPassword(String password) {
		return passwordEncoder.encode(password);
	}

	@Override
	public void deleteUserById(Long id) {
		userDao.deleteById(id);
	}

	@Override
	public void disableUserById(Long id) {
		userDao.disableById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userDao.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException("Email " + email + "not found");
		}

		return user;
	}

}
