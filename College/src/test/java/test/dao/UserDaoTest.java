package test.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import college.dao.UserDao;
import college.enums.Role;
import college.model.User;

public class UserDaoTest extends BaseDaoTest {

	@Autowired
	private UserDao userDao;

	@Test
	public void findAllUsersTest() {
		List<User> users = userDao.findAll();

		assertThat(users, hasSize(3));
	}

	@Test
	public void findUserByIdTest() {
		User user = userDao.findById(1L);

		assertThat(user.getAuthorities(), hasSize(1));
		assertThat(user.getEmail(), is("userA@gmail.com"));
		assertTrue(user.isEnabled());
	}

	@Test
	public void findUserByEmailTest() {
		User user = userDao.findByEmail("userA@gmail.com");

		assertThat(user.getAuthorities(), hasSize(1));
		assertThat(user.getName(), is("Full NameA"));
		assertTrue(user.isEnabled());
	}

	@Test
	public void saveOrUpdateUserTest() {
		User user = userDao.findById(2L);

		user.setRoles(new HashSet<Role>(Arrays.asList(Role.PROFESSOR)));

		userDao.saveOrUpdate(user);

		user = userDao.findById(2L);

		assertThat(user.getAuthorities(), hasSize(1));
	}

	@Test
	public void deleteUserByUsernameTest() {
		userDao.deleteById(1L);

		assertNull(userDao.findById(1L));
	}

	@Test
	public void disableUserByUsernameTest() {
		userDao.disableById(1L);

		User user = userDao.findById(1L);

		assertFalse(user.isEnabled());
	}

}
