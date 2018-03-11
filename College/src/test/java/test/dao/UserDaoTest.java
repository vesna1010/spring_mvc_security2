package test.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import college.dao.extensions.UserDao;
import college.enums.Role;
import college.model.User;

public class UserDaoTest extends BaseDaoTest {

	@Autowired
	private UserDao userDao;
	
	@Before
	public void setUp() {
		userDao.deleteAll();
		userDao.saveOrUpdate(user1);
		userDao.saveOrUpdate(user2);
	}
	
	@Test
	public void findAllUsersTest() {
		Set<User> users = userDao.findAll();

		assertThat(users, hasSize(2));
		assertTrue(users.contains(user1));
		assertTrue(users.contains(user2));
		assertFalse(users.contains(user3));
	}
	
	@Test
	public void findOneUserByUsernameTest() {
		User user = userDao.findById("Username 1");

		assertThat(user.getRoles(), hasSize(2));
		assertThat(user.getEmail(), is("username1@gmail.com"));
		assertTrue(user.getEnabled());
	}
	
	@Test
	public void saveUserTest() {
		userDao.saveOrUpdate(user3);
		
		assertNotNull(userDao.findById("Username 3"));
	}
	

	@Test
	public void updateUserTest() {
		user2.setRoles(new HashSet<>(Arrays.asList(Role.PROFESSOR)));
		userDao.saveOrUpdate(user2);

		User user = userDao.findById("Username 2");

		assertThat(user.getRoles(), hasSize(1));
	}
	
	@Test
	public void deleteUserByUsernameTest() {
		userDao.deleteById("Username 2");

		assertNull(userDao.findById("Username 2"));
	}
	
	@Test
	public void deleteUserTest() {
		userDao.delete(user2);

		assertNull(userDao.findById("Username 2"));
	}

	@Test
	public void disableUserByUsernameTest() {
		userDao.disableByUsername("Username 2");

		User user = userDao.findById("Username 2");

		assertFalse(user.getEnabled());
	}

}

