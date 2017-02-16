package college.dao.impl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import college.dao.UserDao;
import college.model.Role;
import college.model.User;

@Component("userDao")
@Transactional
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Session session(){
		return sessionFactory.getCurrentSession();
	}

	//returns all enabled users
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUsers() {
		return session().createQuery("from User where enabled=true").list();
	}

	//returns user with specified username
	@Override
	public User findUser(String username) {
		return session().get(User.class, username);
	}

	//save or update the user
	@Override
	public void saveOrUpdate(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		session().saveOrUpdate(user);
	}

	//deletes user with specified username
	@Override
	public void deleteUser(String username) {
		User user=findUser(username);
		if(user!=null)
		session().delete(user);
	}

	//disables user with specified username
	@Override
	public void disableUser(String username) {
		Query query=session().createQuery("update User set enabled=false where username=:username");
		query.setString("username", username);
		query.executeUpdate();
	}

	//returns list roles
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> findRoles() {
		return session().createCriteria(Role.class).list();
	}

	//returns role with specified id
	@Override
	public Role findRole(String id) {
		return session().get(Role.class, id);
	}

	//save or update the role
	@Override
	public void saveOrUpdate(Role role) {
		session().saveOrUpdate(role);
	}

	//deletes all data from tables
	@Override
	public void deleteFromTables() {
		Query query=session().createQuery("delete from User");
		query.executeUpdate();
		query=session().createQuery("delete from Role");
		query.executeUpdate();
	}

}
