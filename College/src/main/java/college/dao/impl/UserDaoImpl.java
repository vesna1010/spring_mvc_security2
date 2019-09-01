package college.dao.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import college.dao.UserDao;
import college.dao.impl.HibernateDaoImpl;
import college.model.User;

@Repository
public class UserDaoImpl extends HibernateDaoImpl<User, Long> implements UserDao {

	public UserDaoImpl() {
		setEntityClass(User.class);
	}

	@Override
	public User findByEmail(String email) {
		Query query = getSession().createQuery("select u from User u where u.email=:email");

		query.setString("email", email);

		return (User) query.uniqueResult();
	}

	@Override
	public void disableById(Long id) {
		Query query = getSession().createQuery("update User u set u.enabled=false where u.id=:id");

		query.setLong("id", id);

		query.executeUpdate();
	}

}

