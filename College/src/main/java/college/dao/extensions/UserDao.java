package college.dao.extensions;

import college.dao.HibernateDao;
import college.model.User;

public interface UserDao extends HibernateDao<String, User> {

	void disableByUsername(String username);
}

