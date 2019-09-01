package college.dao;

import college.dao.HibernateDao;
import college.model.User;

public interface UserDao extends HibernateDao<User, Long> {

	User findByEmail(String email);

	void disableById(Long id);
	
}
