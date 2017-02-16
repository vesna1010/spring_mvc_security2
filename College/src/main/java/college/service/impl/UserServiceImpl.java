package college.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import college.dao.UserDao;
import college.model.Role;
import college.model.User;
import college.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserDao userDao;
	
	@Override
	public List<User> findUsers() {
		return userDao.findUsers();
	}

	@Override
	public User findUser(String username) {
		return userDao.findUser(username);
	}

	@Override
	public void saveOrUpdate(User user) {
		userDao.saveOrUpdate(user);
	}

	@Override
	public void deleteUser(String username) {
		userDao.deleteUser(username);
	}

	@Override
	public void disableUser(String username) {
		userDao.disableUser(username);
	}

	@Override
	public List<Role> findRoles() {
		return userDao.findRoles();
	}

	@Override
	public Role findRole(String id) {
		return userDao.findRole(id);
	}

	@Override
	public void saveOrUpdate(Role role) {
		userDao.saveOrUpdate(role);
	}

	@Override
	public void deleteFromTables() {
		userDao.deleteFromTables();
	}

}
