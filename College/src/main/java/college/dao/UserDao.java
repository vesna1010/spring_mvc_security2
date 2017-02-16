package college.dao;

import java.util.List;
import college.model.Role;
import college.model.User;

public interface UserDao {

	List<User> findUsers();
	
	User findUser(String username);
	
	void saveOrUpdate(User user);
	
	void deleteUser(String username);
	
	void disableUser(String username);
	
	List<Role> findRoles();
	
	Role findRole(String id);
	
	void saveOrUpdate(Role role);
		
	void deleteFromTables();

}
