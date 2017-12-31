package college.dao.impl.extensions;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import college.dao.extensions.UserDao;
import college.dao.impl.HibernateDaoImpl;
import college.model.User;

@Component("userDao")
public class UserDaoImpl extends HibernateDaoImpl<String, User> implements UserDao {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserDaoImpl() {
		setEntityClass(User.class);
	}

	@Override
	public void saveOrUpdate(User user) {
		user.setPassword(encodePassword(user.getPassword()));
		super.saveOrUpdate(user);
	}

	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}

	@Override
	public void disableByUsername(String username) {
		Query query = getSession().createQuery(
				"update User set enabled=false where username=:username");

		query.setString("username", username);
		query.executeUpdate();
	}

}
