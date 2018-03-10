package college.service;

import java.util.Set;
import college.model.User;

public interface UserService {

	Set<User> findUsers();
	
	User findUserByUsername(String username);
	
	User findUserByUsernameWithoutPassword(String username);
	
	void saveOrUpdateUser(User user);
	
	void deleteUserByUsername(String username);
	
	void disableUserByUsername(String username);
	
}

