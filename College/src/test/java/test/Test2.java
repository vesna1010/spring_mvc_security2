package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import college.model.Role;
import college.model.User;
import college.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="file:src/main/webapp/WEB-INF/spring/root-context.xm")
public class Test2 {

	@Autowired
	private UserService userService;
		
	private Role role1=new Role("R1","ROLE_ADMIN");
	private Role role2=new Role("R2", "ROLE");
	private Role role3=new Role("R3", "ROLE_PROFESSOR");
	
	private User user1=new User("Username 1", "Email 1", "Password");
	private User user2=new User("Username 2", "Email 2", "Password2");
	private User user3=new User("Username 3", "Email 3", "Password3");
	private User user4=new User("Username 4", "Email 4", "Password4");
	private User user5=new User("Username 5", "Email 5", "Password5");
	
	@Before
	public void init(){
		userService.deleteFromTables();
	}
	
	@Test
	public void test(){
		
		assertTrue(userService.findRoles().size()==0);
		assertTrue(userService.findUsers().size()==0);
		
	    userService.saveOrUpdate(role1);
		userService.saveOrUpdate(role2);
		userService.saveOrUpdate(role3);
		
		assertNull(userService.findUser("Username 1"));
		
		user1.getRoles().add(role1);
		user1.getRoles().add(role2);
		user1.getRoles().add(role3);
		user2.getRoles().add(role2);
		user2.getRoles().add(role3);
		user3.getRoles().add(role3);
		user4.getRoles().add(role3);
		user5.getRoles().add(role3);
	
		userService.saveOrUpdate(user1);
		userService.saveOrUpdate(user2);
		userService.saveOrUpdate(user3);
		userService.saveOrUpdate(user4);
		userService.saveOrUpdate(user5);
		assertNotNull(userService.findUser("Username 1"));
		assertTrue(userService.findRoles().size()==3);
		assertTrue(userService.findUsers().size()==5);
		assertTrue(userService.findUser(user1.getUsername()).getRoles().size()==3);
		
		role2.setRole("ROLE_USER");
		userService.saveOrUpdate(role2);
		assertTrue(userService.findRoles().size()==3);
		assertTrue(userService.findRole(role2.getId()).getUsers().size()==2);
		
		List<Role> roles=new ArrayList<Role>();
		roles.add(role1);
		roles.add(role2);
		user1.setRoles(roles);
		user1.setPassword("Password1");
		userService.saveOrUpdate(user1);
		assertTrue(userService.findUser(user1.getUsername()).getRoles().size()==2);
		
		userService.disableUser(user4.getUsername());
		assertTrue(userService.findUsers().size()==4);
		
		userService.deleteUser(user3.getUsername());
		assertNull(userService.findUser(user3.getUsername()));
		
		assertTrue(userService.findRoles().size()==3);
		assertTrue(userService.findUsers().size()==3);
	}
}
