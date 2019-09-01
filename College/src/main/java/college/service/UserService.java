package college.service;

import java.util.List;
import college.model.User;

public interface UserService {

	List<User> findAllUsers();

	User findUserByEmail(String email);

	void saveOrUpdateUser(User user);

	void deleteUserById(Long id);

	void disableUserById(Long id);

}


