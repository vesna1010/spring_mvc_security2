package test.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.Arrays;
import java.util.HashSet;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import college.controllers.UserController;
import college.enums.Role;
import college.model.User;
import college.service.UserService;


public class UserControllerTest extends BaseControllerTest {

	
	@Mock
	private UserService userService;
	@InjectMocks
	@Autowired
	private UserController userController;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wax).apply(springSecurity()).build();
	}

	@Test
	public void mocksTest() {
		assertNotNull(userService);
		assertNotNull(userController);
	}

	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD_USER", roles = { "USER", "PROFESSOR" })
	public void renderUsersPageTest_By_User_Professor() throws Exception {
		when(userService.findUsers()).thenReturn(new HashSet<User>(Arrays.asList(user1, user2)));

		mockMvc.perform(get("/users"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));

		verify(userService, times(0)).findUsers();
	}

	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD_ADMIN", roles = "ADMIN")
	public void renderUsersPageTest_By_Admin() throws Exception {
		when(userService.findUsers()).thenReturn(new HashSet<User>(Arrays.asList(user1, user2)));

		mockMvc.perform(get("/users"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("users", hasSize(2)))
		       .andExpect(view().name("usersPage"));

		verify(userService, times(1)).findUsers();
	}
	
	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD_USER", roles = { "USER", "PROFESSOR" })
	public void renderEmptyUserFormTest_By_User_Professor() throws Exception {
		mockMvc.perform(get("/users/userForm"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));
	}
	
	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD_ADMIN", roles = "ADMIN")
	public void renderEmptyUserFormTest_By_Admin() throws Exception {
		mockMvc.perform(get("/users/userForm"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("user", is(new User())))
		       .andExpect(model().attribute("roles", is(Arrays.asList(Role.values()))))
		       .andExpect(view().name("userForm"));
	}
	
	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD_ADMIN", roles = "ADMIN")
	public void saveUserAndRenderUserForm_ValidForm_By_Admin() throws Exception {
		User user = new User("USERNAME", "email@gmail.com", "PASSWORD", 
				             new HashSet<Role>(Arrays.asList(Role.PROFESSOR)));

		doNothing().when(userService).saveOrUpdateUser(user);

		mockMvc.perform(post("/users/save").with(csrf())
				.param("username", "USERNAME")
				.param("email", "email@gmail.com")
				.param("password", "PASSWORD")
				.param("confirmPassword", "PASSWORD")
				.param("roles", "PROFESSOR"))
		       .andExpect(model().hasNoErrors())
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/users/userForm"));

		verify(userService, times(1)).saveOrUpdateUser(user);
	}
	
	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD_ADMIN", roles = "ADMIN")
	public void saveUserAndRenderUserForm_InvalidForm_By_Admin() throws Exception {
		User user = new User("USERNAME", "email@gmail.com", "PASSWORD", 
	             new HashSet<Role>());

		doNothing().when(userService).saveOrUpdateUser(user);

		mockMvc.perform(post("/users/save").with(csrf())
				.param("username", "USERNAME")
				.param("email", "email@gmail.com")
				.param("password", "PASSWORD")
				.param("confirmPassword", "PASSWORD")
				.param("roles", ""))
		       .andExpect(status().isOk())
		       .andExpect(model().attributeHasFieldErrors("user", "roles"))
		       .andExpect(model().attribute("user", is(user)))
		       .andExpect(model().attribute("roles", is(Arrays.asList(Role.values()))))
		       .andExpect(view().name("userForm"));

		verify(userService, times(0)).saveOrUpdateUser(user);
	}
	
	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD_USER", roles = { "USER", "PROFESSOR" })
	public void deleteUserAndRenderUsersPageTest_By_User() throws Exception {
		doNothing().when(userService).deleteUserByUsername("USERNAME");

		mockMvc.perform(get("/users/delete/" + "USERNAME"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));

		verify(userService, times(0)).deleteUserByUsername("USERNAME");
	}
	
	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD_ADMIN", roles = "ADMIN")
	public void deleteUserAndRenderUsersPageTest_By_Admin() throws Exception {
		doNothing().when(userService).deleteUserByUsername("USERNAME");

		mockMvc.perform(get("/users/delete/" + "USERNAME"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/users"));

		verify(userService, times(1)).deleteUserByUsername("USERNAME");
	}
	
	@Test
	@WithMockUser(username = "USERNAME_USER", password = "PASSWORD_USER", roles = { "USER", "PROFESSOR" })
	public void disableUserAndRenderUsersPageTest_By_User() throws Exception {
		doNothing().when(userService).disableUserByUsername("USERNAME");

		mockMvc.perform(get("/users/disable/" + "USERNAME"))
		       .andExpect(status().isForbidden())
		       .andExpect(forwardedUrl("/denied"));

		verify(userService, times(0)).disableUserByUsername("USERNAME");
	}
	
	@Test
	@WithMockUser(username = "USERNAME_ADMIN", password = "PASSWORD_ADMIN", roles = "ADMIN")
	public void disableUserAndRenderUsersPageTest_By_Admin() throws Exception {
		doNothing().when(userService).disableUserByUsername("USERNAME");

		mockMvc.perform(get("/users/disable/" + "USERNAME"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrl("/users"));

		verify(userService, times(1)).disableUserByUsername("USERNAME");
	}
	
	@Test
	@WithAnonymousUser
	public void renderPasswordFormTest_By_AnonymousUser() throws Exception {
		mockMvc.perform(get("/users/changePassword"))
		       .andExpect(status().is3xxRedirection())
		       .andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	@WithMockUser(username = "USERNAME", password = "PASSWORD", roles = "USER")
	public void renderPasswordFormTest_By_User() throws Exception {
		User user = new User("USERNAME", "email@gmail.com", "PASSWORD", 
	             new HashSet<Role>(Arrays.asList(Role.USER)));
		
		when(userService.findUserByUsername("USERNAME")).thenReturn(user);
		
		mockMvc.perform(get("/users/changePassword"))
		       .andExpect(status().isOk())
		       .andExpect(model().attribute("roles", is(Arrays.asList(Role.values()))))
		       .andExpect(model().attribute("user", is(user)))
		       .andExpect(view().name("userForm"));
		
		verify(userService, times(1)).findUserByUsername("USERNAME");
	}
	
}


